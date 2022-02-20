package com.github.arorasagar;

import com.github.arorasagar.message.Message;
import com.github.arorasagar.message.MessageType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

// ma
public class ConnectionManager extends Thread {

    // maintains a list of connections
    Collection<PeerConnection> peerConnections;
    // connections which are interested in the data from this peer.
    Collection<PeerConnection> interestedConnections;

    public ConnectionManager(Collection<PeerConnection> peerConnections) {
        this.peerConnections = peerConnections;
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
                    // check if it's handshake message, if it is handshake mesasge add
                    //if (message.getMessageType().equals())
                    // handleMessage
                    // if the message is bitfield message.



                } catch (IOException e) {
                    // log error reading the message.
                }
            }
        }
    }

}
