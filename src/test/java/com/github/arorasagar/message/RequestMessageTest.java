package com.github.arorasagar.message;

import org.junit.Assert;
import org.junit.Test;

public class RequestMessageTest {

    @Test
    public void testRequestMessage() {
        RequestMessage requestMessage = new RequestMessage(1);
        String actualVal = new String(requestMessage.getPayload());
        Assert.assertEquals(1, Integer.parseInt(actualVal));
    }

    @Test
    public void testRequestMessage2() {
        RequestMessage requestMessage = new RequestMessage(2);
        String actualVal = new String(requestMessage.getPayload());
        Assert.assertEquals(2, Integer.parseInt(actualVal));
    }
}
