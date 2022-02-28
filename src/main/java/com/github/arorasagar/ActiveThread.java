package com.github.arorasagar;

import com.github.arorasagar.message.Message;

import java.util.concurrent.LinkedBlockingQueue;

public class ActiveThread implements Runnable {

    LinkedBlockingQueue<Message> messages;

    public ActiveThread(LinkedBlockingQueue<Message> messages) {
        this.messages = messages;
    }

    public synchronized void send(Message message) {
        messages.add(message);
    }

    @Override
    public void run() {
    }
}
