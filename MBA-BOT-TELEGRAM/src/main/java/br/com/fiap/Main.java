package br.com.fiap;

import br.com.fiap.connectionTelegrambot.CallbackMessage;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class Main {

    public static void main(String[] args) {

        final Logger logger = LogManager.getLogger(Main.class.getName());
        logger.info("Initializing...");

        CallbackMessage conversationController = new CallbackMessage();

        try {
            logger.info("Receiving messages");
            conversationController.receiveMessages();
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("Bot stopped");
    }
}
