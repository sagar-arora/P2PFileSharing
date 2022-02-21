package com.github.arorasagar;

import org.junit.Assert;
import org.junit.Test;

import java.util.BitSet;

public class MessageUtilsTest {

    @Test
    public void testGetFileBitSetFromBytes() {
        BitSet actualBitSet = new BitSet(8);
        actualBitSet.set(1);
        actualBitSet.set(4);
        actualBitSet.set(7);

        byte[] actualByte = actualBitSet.toByteArray();

        Assert.assertEquals(actualBitSet, MessageUtils.getFileBitSetFromBytes(8, actualByte));

    }

    @Test
    public void testConvert() {
        Assert.assertEquals(256, MessageUtils.convertPayloadToInteger(new byte[]{1,0}));
    }

    @Test
    public void testConvert2() {
        BitSet bitSet = new BitSet(8);
        bitSet.set(1);
        Assert.assertEquals(2, MessageUtils.convertPayloadToInteger(bitSet.toByteArray()));
    }
}
