package com.github.arorasagar;

public class Peer {

    protected Integer peerId;
    protected String address;
    protected int port;
    protected boolean hasFile;

    public Peer(int peerId, String address, int port, boolean hasFile) {
        this.address = address;
        this.peerId = peerId;
        this.port = port;
        this.hasFile = hasFile;
    }

    public Integer getPeerId() {
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

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public boolean isHasFile() {
        return hasFile;
    }

    public void setHasFile(boolean hasFile) {
        this.hasFile = hasFile;
    }
}
