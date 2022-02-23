package com.github.arorasagar.message;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class FieldMessageTest {

    @Test
    public void testFieldMessage1() throws IOException {
        FieldMessage fieldMessage = new FieldMessage(new byte[]{0,0,0,1,0,0,0,1});
        Assert.assertEquals(1, fieldMessage.getIndex());
    }

    @Test
    public void testFieldMessage2() throws IOException {
        FieldMessage fieldMessage = new FieldMessage(new byte[]{0,0,0,1,0,0,0,1});
        Assert.assertEquals(new String(new byte[]{0,0,0,1}), new String(fieldMessage.getFileBytes()));
    }
}
