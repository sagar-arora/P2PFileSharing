package com.github.arorasagar;

public class PeerProcessManager extends Thread {

    PeerProcessConfig peerProcessConfig;
    Peer peer;
    PeerProcessManager(PeerProcessConfig peerProcessConfig, Peer peer) {
        this.peerProcessConfig = peerProcessConfig;
        this.peer = peer;
    }

    public void start() {

    }
}
