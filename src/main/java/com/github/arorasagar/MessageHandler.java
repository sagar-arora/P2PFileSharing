package com.github.arorasagar;

import com.github.arorasagar.message.Message;

public class MessageHandler {

    public static Message handleMessage(Message message) {

        switch (message.getMessageType()) {

            case CHOKE:
                break;
            case UNCHOKE:
                break;

        }

        return null;
    }
}
