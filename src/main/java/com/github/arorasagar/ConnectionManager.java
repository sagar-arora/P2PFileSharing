package com.github.arorasagar;

import com.github.arorasagar.message.BitfieldMessage;
import com.github.arorasagar.message.FieldMessage;
import com.github.arorasagar.message.Message;
import com.github.arorasagar.message.MessageType;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

// ma
public class ConnectionManager extends Thread {

    // maintains a list of connections
    Collection<PeerConnection> peerConnections;
    // connections which are interested in the data from this peer.
    Collection<PeerConnection> interestedConnections;
    PeerProcessConfig peerProcessConfig;
    Map<Integer, PeerConnection> peerConnectionMap = new HashMap<>();
    LinkedBlockingQueue<PeerMessage> linkedBlockingQueue;
    FileManager fileManager;
    public ConnectionManager(PeerProcessConfig peerProcessConfig,
                             Collection<PeerConnection> peerConnections,
                             FileManager fileManager) {
        this.peerConnections = peerConnections;
        this.peerProcessConfig = peerProcessConfig;
    }

    public static class ChokingUnchoking implements Runnable {

        @Override
        public void run() {
            // logic for choking, unchoking.

        }
    }

    public synchronized void addConnection(PeerConnection peerConnection) {
        this.peerConnections.add(peerConnection);
    }

    // listen to messages coming from the peers.
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
                            //peerConnectionMap.put(remotePeerId, peerConnection);
                            peerConnection.setRemotePeerId(remotePeerId);
                            // sendBitField message if this peer has any pieces.
                            // sendMessage()
                            //linkedBlockingQueue.add(new PeerMessage());
                            Optional<BitSet> fileSet = fileManager.fileSet();
                            if (fileSet.isPresent()) {
                                linkedBlockingQueue.add(new PeerMessage(peerConnection, new BitfieldMessage(fileSet.get())));
                            }
                            break;
                        case BITFIELD:
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


                            } catch (Exception e) {

                            }
                    }


                } catch (IOException e) {
                    // log error reading the message.
                }
            }
        }
    }


    public class SendMessageThread extends Thread {

        SendMessageThread() {
            //this.linkedBlockingQueue = peerMessages;
        }

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
