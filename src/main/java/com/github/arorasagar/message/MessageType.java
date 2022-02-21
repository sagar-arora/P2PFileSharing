package com.github.arorasagar.message;

public enum MessageType {
    CHOKE(0),
    UNCHOKE(1),
    INTERESTED(2),
    NOT_INTERESTED(3),
    HAVE(4),
    BITFIELD(5),
    REQUESTED(6),
    FIELD(7),
    HANDSHAKE(8);

    byte type;

    MessageType(int type) {
        this.type = (byte) type;
    }

    public int getType() {
        return type;
    }

    public static MessageType fromType(int type) {
        MessageType messageType = null;
        switch (type) {
            case 0: messageType = CHOKE;
            break;
            case 1: messageType = UNCHOKE;
                break;
            case 2: messageType = INTERESTED;
                break;
            case 3: messageType = NOT_INTERESTED;
                break;
            case 4: messageType = HAVE;
                break;
            case 5: messageType = BITFIELD;
                break;
            case 6: messageType = REQUESTED;
                break;
            case 7: messageType = FIELD;
                break;
            default:


        }
        return messageType;
    }
}
