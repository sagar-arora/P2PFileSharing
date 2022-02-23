package com.github.arorasagar;

import com.github.arorasagar.message.HandshakeMessage;
import com.github.arorasagar.message.Message;
import com.github.arorasagar.message.MessageType;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.BitSet;

public class MessageUtils {


    private MessageUtils() {

    }

    public static BitSet getFileBitSetFromBytes(int n, byte[] fileBytes) {
        BitSet bitSet = new BitSet(n);

        //new BitSet()
        int index = 0;
        for (byte fileByte : fileBytes) {
            //   0 1 1 0 1 1 1 0
            // & 1 1 1 1 1 1 1 1
            // = 0 1 1 0 1 1 1 0
            int temp = 1;
            int byteVal = fileByte;
            for (int i = 0; i < 8; i++) {
                int val = (byteVal) & temp;
                if (val == 1)
                    bitSet.set(index);

                byteVal = byteVal >> 1;
                index++;
            }
        }

        return bitSet;
    }

    public boolean isHandshakeMessage(Message message) {

        return false;
    }


    public static int convertPayloadToInteger(byte[] payload) {
        StringBuilder sb = new StringBuilder();
        return new BigInteger(payload).intValue();
    }

    public static byte[] convertToPieceMessageBytes(int remoteId, byte[] payload) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);

        dataOutputStream.writeInt(remoteId);
        dataOutputStream.write(payload);
        dataOutputStream.flush();

        return byteArrayOutputStream.toByteArray();
    }


    public static byte[] convertMessageBytesTo(int remoteId, byte[] payload) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);

        dataOutputStream.writeInt(remoteId);
        dataOutputStream.write(payload);
        dataOutputStream.flush();

        return byteArrayOutputStream.toByteArray();
    }
}
