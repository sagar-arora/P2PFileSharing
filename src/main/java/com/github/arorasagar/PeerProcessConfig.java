package com.github.arorasagar;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class PeerProcessConfig {

    private int numberOfPreferredNeighbors;
    private int unchokingInterval;
    private int optimisticUnchokingInterval;
    private String fileName;
    private int fileSize;
    private int pieceSize;
    private List<Peer> peers;

    public PeerProcessConfig() {

    }

    public int getNumberOfPreferredNeighbors() {
        return numberOfPreferredNeighbors;
    }

    public void setNumberOfPreferredNeighbors(int numberOfPreferredNeighbors) {
        this.numberOfPreferredNeighbors = numberOfPreferredNeighbors;
    }

    public int getUnchokingInterval() {
        return unchokingInterval;
    }

    public void setUnchokingInterval(int unchokingInterval) {
        this.unchokingInterval = unchokingInterval;
    }

    public int getOptimisticUnchokingInterval() {
        return optimisticUnchokingInterval;
    }

    public void setOptimisticUnchokingInterval(int optimisticUnchokingInterval) {
        this.optimisticUnchokingInterval = optimisticUnchokingInterval;
    }

    public String  getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getFileSize() {
        return fileSize;
    }

    public void setFileSize(int fileSize) {
        this.fileSize = fileSize;
    }

    public int getPieceSize() {
        return pieceSize;
    }

    public void setPieceSize(int pieceSize) {
        this.pieceSize = pieceSize;
    }

    public List<Peer> getPeers() {
        return peers;
    }

    public void setPeers(List<Peer> peers) {
        this.peers = peers;
    }

    public static PeerProcessConfig readFromJson(File file) throws IOException, InvalidFileException {
        ObjectMapper objectMapper = new ObjectMapper();
        PeerProcessConfig peerProcessConfig = objectMapper.readValue(file, PeerProcessConfig.class);

        if (peerProcessConfig.getFileName() == null) {
            throw new InvalidFileException("file is null or empty.");
        }

        if (peerProcessConfig.getPeers() == null) {
            throw new InvalidFileException("peer list is empty.");
        }

        return peerProcessConfig;
    }
}
