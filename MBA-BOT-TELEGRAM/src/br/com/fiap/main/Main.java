package br.com.fiap.main;

import br.com.fiap.connectionTelegrambot.CallbackMessage;

public class Main {

	public static void main(String[] args) {


		CallbackMessage conversationController = new CallbackMessage();

		try {
			conversationController.receiveMessages();
		}catch(Exception e) {
			e.printStackTrace();
		}

	}
}
