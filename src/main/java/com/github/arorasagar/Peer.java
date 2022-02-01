package com.github.arorasagar;

public class Peer {

    private int peerId;
    private String address;
    private String port;
    private boolean hasFile;
    public Peer(int peerId, String address, String port, boolean hasFile) {
        this.address = address;
        this.peerId = peerId;
        this.port = port;
        this.hasFile = hasFile;
    }

    public int getPeerId() {
        return peerId;
    }

    public void setPeerId(int peerId) {
        this.peerId = peerId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public boolean isHasFile() {
        return hasFile;
    }

    public void setHasFile(boolean hasFile) {
        this.hasFile = hasFile;
    }
}
