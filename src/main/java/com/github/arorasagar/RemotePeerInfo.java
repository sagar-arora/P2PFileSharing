package com.github.arorasagar;

import java.util.BitSet;
import java.util.Objects;

public class RemotePeerInfo extends Peer {

    BitSet bitSet;

    public RemotePeerInfo(int peerId, String address, int port, boolean hasFile) {
        super(peerId, address, port, hasFile);
    }

    @Override
    public boolean equals (Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof RemotePeerInfo) {
            return (((RemotePeerInfo) obj).getPeerId().equals(this.getPeerId()));
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.peerId);
        return hash;
    }

    @Override
    public String toString() {
        return new StringBuilder(peerId)
                .append (" address:").append (this.getAddress())
                .append(" port: ").append(this.getPort()).toString();
    }
}
