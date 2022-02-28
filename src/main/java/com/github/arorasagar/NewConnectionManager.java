package com.github.arorasagar;

import java.util.ArrayList;
import java.util.List;

public final class NewConnectionManager {

    private final List<PeerConnection> peerConnectionList;
    private final List<PeerConnection> unchokedPeers;
    private final List<PeerConnection> interestedPeers;
    private final FileManager fileManager;

    public NewConnectionManager(FileManager fileManager) {
        this.fileManager = fileManager;
        this.peerConnectionList = new ArrayList<>();
        this.unchokedPeers = new ArrayList<>();
        this.interestedPeers = new ArrayList<>();
    }

    public synchronized List<PeerConnection> getPeerConnectionList() {
        return peerConnectionList;
    }

    public synchronized void addPeerConnection(PeerConnection peerConnection) {
        peerConnectionList.add(peerConnection);
    }

    public synchronized boolean removePeerConnection(PeerConnection peerConnection) {
        return peerConnectionList.remove(peerConnection);
    }

    public List<PeerConnection> getInterestedPeers() {
        return interestedPeers;
    }

    public List<PeerConnection> getUnchokedPeers() {
        return unchokedPeers;
    }

    public synchronized void addInterestedPeer(PeerConnection peerConnection) {
        peerConnectionList.add(peerConnection);
    }

    public synchronized void addUnchokedPeer(PeerConnection peerConnection) {
        peerConnectionList.add(peerConnection);
    }
}
