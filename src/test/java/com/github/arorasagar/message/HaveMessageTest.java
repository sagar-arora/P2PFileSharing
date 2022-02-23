package com.github.arorasagar.message;

import org.junit.Assert;
import org.junit.Test;

public class HaveMessageTest {

    @Test
    public void test( ) {
        HaveMessage haveMessage = new HaveMessage(1);
        Assert.assertEquals(haveMessage.getLength(), 4);
        Assert.assertEquals(1, haveMessage.val());
    }

    @Test
    public void test2() {
        HaveMessage haveMessage = new HaveMessage(2);
        Assert.assertEquals(haveMessage.getLength(), 4);
        Assert.assertEquals(2, haveMessage.val());
    }
}
