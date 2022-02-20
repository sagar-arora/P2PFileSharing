package com.github.arorasagar;

import com.github.arorasagar.message.Message;

import java.util.concurrent.LinkedBlockingQueue;

public class ActiveThread implements Runnable {

    LinkedBlockingQueue<Message> messages;

    public ActiveThread(LinkedBlockingQueue<Message> messages) {
        this.messages = messages;
    }

    @Override
    public void run() {

    }
}
