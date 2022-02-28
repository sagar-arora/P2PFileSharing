package com.github.arorasagar;

import com.github.arorasagar.message.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
//import org.slf4j.Logger;

import java.io.IOException;
import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;

// ma
public class ConnectionManager extends Thread {

    // maintains a list of connections
    List<PeerConnection> peerConnections;
    // connections which are interested in the data from this peer.
    List<PeerConnection> interestedConnections;
    Collection<DownloadRate> interestedConnectionsDownloadRate;

    PeerProcessConfig peerProcessConfig;
    Map<Integer, PeerConnection> peerConnectionMap = new HashMap<>();
    LinkedBlockingQueue<PeerMessage> linkedBlockingQueue;
    FileManager fileManager;

    Logger LOGGER = LogManager.getLogger(ConnectionManager.class);

    Set<PeerConnection> unchokedConnections = new HashSet<>();

    int myPeerId;

    public ConnectionManager(PeerProcessConfig peerProcessConfig,
                             List<PeerConnection> peerConnections,
                             FileManager fileManager,
                             Peer peer) {
        this.peerConnections = peerConnections;
        this.peerProcessConfig = peerProcessConfig;
        this.fileManager = fileManager;
        this.myPeerId = peer.getPeerId();
    }

    public void runThread() {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Collections.sort(interestedConnections);

                // pick n
                int n = peerProcessConfig.getNumberOfPreferredNeighbors();

                int index = 0;
                List<PeerConnection> optimisticList = new ArrayList<>();
                for (PeerConnection peerConnection : peerConnections) {
                    if (index < n) {
                        linkedBlockingQueue.add(new PeerMessage(peerConnection, new UnchokeMessage()));
                    } else {
                        //linkedBlockingQueue.add(new PeerMessage(peerConnection, new UnchokeMessage()));
                        optimisticList.add(peerConnection);
                    }
                    index++;
                }

                Random random = new Random();
                int randomIndex = random.nextInt(peerConnections.size());

                PeerConnection optimisticPeer = optimisticList.remove(randomIndex);
                linkedBlockingQueue.add(new PeerMessage(optimisticPeer, new UnchokeMessage()));

                for (PeerConnection peerConnection : optimisticList) {
                    linkedBlockingQueue.add(new PeerMessage(peerConnection, new ChokeMessage()));
                }
            }
        }, 5, 10);
    }

    public void runRequestThread() {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                // send request message at some regular interval.

                for (PeerConnection peerConnection : unchokedConnections) {
                    int pieceToRequest = MessageUtils.findRandomInterestedPiece(fileManager.getBytesFromFilePieces(),
                            peerConnection.getFileSet());

                    linkedBlockingQueue.add(new PeerMessage(peerConnection, new RequestMessage(pieceToRequest)));
                }
            }
            }, 5, 2);
    }

    public synchronized void addConnection(PeerConnection peerConnection) {
        this.peerConnections.add(peerConnection);
    }

    @Override
    public void run() {

        while (true) {
            for (PeerConnection peerConnection : peerConnections) {
                try {
                    Message message = peerConnection.checkForMessage();

                    if (message == null) continue;

                    switch (message.getMessageType()) {
                        case HANDSHAKE:
                            byte[] payload = message.getPayload();
                            int remotePeerId = MessageUtils.convertPayloadToInteger(payload);

                            LOGGER.info("[{}] Received handshake message from remote peer : {}", myPeerId, remotePeerId);
                            peerConnection.setRemotePeerId(remotePeerId);
                            Optional<BitSet> fileSet = fileManager.fileSet();
                            if (fileSet.isPresent()) {
                                LOGGER.info("[{}] Sending bitfield message to remote peer: {}", myPeerId, remotePeerId);
                                linkedBlockingQueue.add(new PeerMessage(peerConnection, new BitfieldMessage(fileSet.get())));
                            }
                            break;
                        case BITFIELD:
                            LOGGER.info("[{}] Received Bitfield message from remote peer : {}", myPeerId, peerConnection.getRemotePeerId());
                            payload = message.getPayload();
                            BitSet bitSet = MessageUtils.getFileBitSetFromBytes(FileManager.totalPieces, payload);
                            peerConnection.setFileSet(bitSet);
                            break;
                        case INTERESTED:
                            interestedConnections.add(peerConnection);
                            break;
                        case NOT_INTERESTED:
                            //interestedConnections.add()
                            // check if the peer is in interest connections list, if so remove this peer from the list.

                        case HAVE:
                            payload = message.getPayload();
                            int index = new BigInteger(payload).intValue();
                            peerConnection.getFileSet().set(index);
                            break;

                        case REQUESTED:
                            payload = message.getPayload();
                            index = new BigInteger(payload).intValue();
                            byte[] filePiece = fileManager.getFilePieceBy(index);
                            byte[] fieldMessageByte = MessageUtils.convertToPieceMessageBytes(
                                    peerConnection.getRemotePeerId(), filePiece);
                            if (filePiece != null) {
                                linkedBlockingQueue.add(new PeerMessage(peerConnection, new FieldMessage(fieldMessageByte)));
                            } else {
                                // something is wrong here, peer calculated wrong piece.
                                // log
                            }
                            break;
                        case FIELD:
                            FieldMessage fieldMessage = (FieldMessage) message;
                            try {
                                index = fieldMessage.getIndex();
                                byte[] fileByte = fieldMessage.getFileBytes();
                                fileManager.updateFilePieceFromByte(index, fileByte);

                                HaveMessage haveMessage = new HaveMessage(index);
                                linkedBlockingQueue.add(new PeerMessage(peerConnection, haveMessage));
                            } catch (Exception e) {

                            }
                            break;
                        case UNCHOKE:
                            unchokedConnections.add(peerConnection);

                    }
                } catch (IOException e) {
                    // log error reading the message.
                }
            }
        }
    }


    public class SendMessageThread extends Thread {

        @Override
        public void run() {
            while (true) {
                PeerMessage peerMessage = linkedBlockingQueue.poll();
                if (peerMessage != null) {
                    try {
                        peerMessage.receiverConnection.sendMessage(peerMessage.message);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

}
