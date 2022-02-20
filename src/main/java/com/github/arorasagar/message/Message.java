package com.github.arorasagar.message;

import java.io.*;

public class Message {

    public static int MESSAGE_HEADER_LENGTH = 4;
    public static int TYPE_SIZE = 1;
    int length;
    MessageType messageType;
    byte[] payload;

    public Message(int length, MessageType messageType, byte[] payload) {
        this.length = length;
        this.messageType = messageType;
        this.payload = payload;
    }

    public Message(int length, MessageType messageType) {
        this(length, messageType, new byte[0]);
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public byte[] getPayload() {
        return payload;
    }

    public void setPayload(byte[] payload) {
        this.payload = payload;
    }

    public byte[] getMessageBytes() throws IOException {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);

        dataOutputStream.writeInt(this.length);
        dataOutputStream.writeByte(this.messageType.getType());
        dataOutputStream.writeBytes(new String(this.payload));

        dataOutputStream.flush();

        return byteArrayOutputStream.toByteArray();
    }

    public static Message readMessageBytes(byte[] message) throws IOException {

        DataInputStream dataInputStream = new DataInputStream(new ByteArrayInputStream(message));
        int length = dataInputStream.readInt();
        int type = (int) dataInputStream.readByte();
        byte[] payload = new byte[length];
        dataInputStream.read(payload, Message.MESSAGE_HEADER_LENGTH + Message.TYPE_SIZE, length);

        return new Message(length, MessageType.fromType(type), payload);
    }
}
