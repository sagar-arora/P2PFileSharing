package com.github.arorasagar;

import com.github.arorasagar.message.Message;
import com.github.arorasagar.message.MessageType;
import com.google.common.base.Objects;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.BitSet;

public class PeerConnection implements Comparable<PeerConnection> {

    private final Socket socket;
    private int remotePeerId;
    private InputStream inputStream;
    private OutputStream outputStream;
    private boolean isHandshakeDone = false;
    private BitSet fileSet;
    private DownloadRate downloadRate;
    public PeerConnection(Socket socket) throws Exception {
        this(socket, -1);
    }

    public PeerConnection(Socket socket, int remotePeerId) throws Exception {
        this.socket = socket;
        this.remotePeerId = remotePeerId;
        this.inputStream = socket.getInputStream();
        this.outputStream = socket.getOutputStream();
        this.downloadRate = new DownloadRate(0);
    }

    public boolean isHandshakeDone() {
        return isHandshakeDone;
    }

    public Socket getSocket() {
        return socket;
    }

    public int getRemotePeerId() {
        return remotePeerId;
    }

    public void setRemotePeerId(int peerId) {
        this.remotePeerId = peerId;
    }

    public DownloadRate getDownloadRate() {
        return this.downloadRate;
    }

    public void sendMessage(Message message) throws IOException {
        long timeStart = System.currentTimeMillis();
        outputStream.write(message.getMessageBytes());
        long timeEnd = System.currentTimeMillis();

        if (message.getMessageType() == MessageType.FIELD) {
            downloadRate = new DownloadRate(message.getLength(),timeEnd - timeStart);
        }
    }

    public Message checkForMessage() throws IOException {
        Message message = null;
        BufferedInputStream bis = new BufferedInputStream(inputStream);

        while (bis.available() != 0) {
            byte[] messageBytes = bis.readAllBytes();
            message = Message.readMessageBytes(messageBytes);
        }

        return message;
    }

    public BitSet getFileSet() {
        return fileSet;
    }

    public void setFileSet(BitSet fileSet) {
        this.fileSet = fileSet;
    }

    /**
     *   @Override
     *     public boolean equals(Object obj) {
     *         if (obj == null) {
     *             return false;
     *         }
     *
     *         if (obj.getClass() != this.getClass()) {
     *             return false;
     *         }
     *
     *         final Person other = (Person) obj;
     *         if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
     *             return false;
     *         }
     *
     *         if (this.age != other.age) {
     *             return false;
     *         }
     *
     *         return true;
     *     }
     *
     *     @Override
     *     public int hashCode() {
     *         int hash = 3;
     *         hash = 53 * hash + (this.name != null ? this.name.hashCode() : 0);
     *         hash = 53 * hash + this.age;
     *         return hash;
     *     }
     * @param obj
     * @return
     */
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        PeerConnection peerConnection = (PeerConnection) obj;

        return remotePeerId == peerConnection.remotePeerId && Objects.equal(this.socket, peerConnection.socket);
    }

    @Override
    public int compareTo(PeerConnection o) {
        return DownloadRate.compare(this.downloadRate, o.getDownloadRate());
    }
}
