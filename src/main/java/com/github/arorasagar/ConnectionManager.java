package com.github.arorasagar;

import com.github.arorasagar.message.Message;
import com.github.arorasagar.message.MessageType;

import java.io.IOException;
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
    public ConnectionManager(PeerProcessConfig peerProcessConfig, Collection<PeerConnection> peerConnections) {
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
