package com.github.arorasagar;

import com.github.arorasagar.message.HandshakeMessage;
import com.github.arorasagar.message.Message;
import com.github.arorasagar.message.MessageType;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.Random;

public class MessageUtils {


    private MessageUtils() {

    }

    public static BitSet getFileBitSetFromBytes(int n, byte[] fileBytes) {
        BitSet bitSet = new BitSet(n);
        int index = 0;
        for (byte fileByte : fileBytes) {
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

    public static int findRandomInterestedPiece(BitSet myBitSet, BitSet peerBitSet) {
        int n = myBitSet.length();
        List<Integer> indexes = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            boolean myBit = myBitSet.get(i);
            boolean peerBit = peerBitSet.get(i);
            if (!myBit && peerBit) {
                indexes.add(i);
            }
        }

        Random random = new Random();
        int randIndex = random.nextInt(indexes.size());
        return indexes.get(randIndex);
    }
}
