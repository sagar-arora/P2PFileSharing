package com.github.arorasagar;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class PeerProcessConfig {

    private Integer numberOfPreferredNeighbors;
    private Integer unchokingInterval;
    private Integer optimisticUnchokingInterval;
    private String fileName;
    private Integer fileSize;
    private Integer pieceSize;
    private List<Peer> peers;

    public PeerProcessConfig() {

    }

    public Integer getNumberOfPreferredNeighbors() {
        return numberOfPreferredNeighbors;
    }

    public void setNumberOfPreferredNeighbors(int numberOfPreferredNeighbors) {
        this.numberOfPreferredNeighbors = numberOfPreferredNeighbors;
    }

    public Integer getUnchokingInterval() {
        return unchokingInterval;
    }

    public void setUnchokingInterval(int unchokingInterval) {
        this.unchokingInterval = unchokingInterval;
    }

    public Integer getOptimisticUnchokingInterval() {
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

    public Integer getFileSize() {
        return fileSize;
    }

    public void setFileSize(int fileSize) {
        this.fileSize = fileSize;
    }

    public Integer getPieceSize() {
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

        if (peerProcessConfig.getNumberOfPreferredNeighbors() == null) {
            throw new InvalidFileException("numPreferredNeighbors is null.");
        }

        if (peerProcessConfig.getFileSize() == null) {
            throw new InvalidFileException("fileSize is null.");
        }

        if (peerProcessConfig.getPieceSize() == null) {
            throw new InvalidFileException("pieceSize is null.");
        }

        if (peerProcessConfig.getUnchokingInterval() == null) {
            throw new InvalidFileException("pieceSize is null.");
        }

        //getOptimisticUnchokingInterval


        if (peerProcessConfig.getOptimisticUnchokingInterval() == null) {
            throw new InvalidFileException("pieceSize is null.");
        }

        return peerProcessConfig;
    }
}
