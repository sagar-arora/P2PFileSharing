package com.github.arorasagar.message;

public enum MessageType {
    CHOKE(0),
    UNCHOKE(1),
    INTERESTED(1),
    NOT_INTERESTED(2),
    HAVE(3),
    BITFIELD(4),
    REQUESTED(5),
    FIELD(6);

    int type;

    MessageType(int type) {
        this.type = type;
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
        }
        return messageType;
    }
}
