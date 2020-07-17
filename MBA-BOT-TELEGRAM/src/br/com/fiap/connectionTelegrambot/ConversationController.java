package br.com.fiap.connectionTelegrambot;

import java.util.List;
import java.util.Optional;
import java.util.Properties;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.TelegramBotAdapter;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ChatAction;
import com.pengrad.telegrambot.request.GetUpdates;
import com.pengrad.telegrambot.request.SendChatAction;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.BaseResponse;
import com.pengrad.telegrambot.response.GetUpdatesResponse;
import com.pengrad.telegrambot.response.SendResponse;
import com.sun.javafx.scene.control.skin.Utils;

import br.com.fiap.properties.PropertiesLoader;

/**
 * Classe responsavel por receber a mensagem e realizar a manipulacao da informacao
 * recebida do telegram.
 * 
 *  - objeto responsável por receber as mensagens GetUpdatesResponse updatesResponse;	 
 *  - objeto responsável por gerenciar o envio de respostas SendResponse sendResponse;
 *  - objeto responsável por gerenciar o envio de ações do chat BaseResponse baseResponse;
 *  	
 * @author lucasrodriguesdonascimento
 *
 */
public class ConversationController{

	private String token;
	private GetUpdatesResponse getPedingMessages;
	private SendResponse sendResponse;
	private BaseResponse baseResponse;
	String listaSaudacao [] = {"bom dia","boa tarde","boa noite","oi","olá","iae","koe","tchau","xau","falo","obrigado"};
	static final Logger logger = LogManager.getLogger(ConversationController.class.getName());
	Properties prop = PropertiesLoader.getProp();
	int offSet = 0;


	/**
	 * Este metodo é responsavel por receber a mensagem do usuario e verificar oq enviar.
	 * Sugestoes de melhoria : Integrar um NLP para melhorar a resposta do bot
	 * @param updates
	 * @param bot
	 * @param msg
	 */
	public void sendMessageToTelegram(Optional<List<Update>> updates, TelegramBot bot, String msg) {

		String EmojiSorrir = "\ud83d\ude00";
		getPedingMessages = offSet(bot);

		if(updates.isPresent()) {
			updates.get().forEach(up -> {
				
				enviarEscrevendo(bot, up);

				switch (msg) {

				case "saudacao":
					sendResponse = textoPersonalizavel(bot, up ,up.message().text().toUpperCase().charAt(1) + "  " +  up.message().from().firstName() + 
							" , Tudo bem ? " + EmojiSorrir);
					break;
				case "/start":
					//envio da mensagem de resposta
					sendResponse = textoPersonalizavel(bot, up , "Iae blz ? Digite /sobre para saber mais ..." + EmojiSorrir);
					//verificação de mensagem enviada com sucesso
					System.out.println("Mensagem Enviada?" +sendResponse.isOk());
					System.out.println("Recebendo mensagem:"+ up.message().text());
					break;


				default:
					sendResponse = bot.execute(new SendMessage(up.message().chat().id(),"Nao Entendi ...."));
					break;
				}
			});
		}else {
			logger.info("Objeto Vazio");
		}

	}


	/**
	 * Metodo responsavel por personalizar o envio da mensagem para o cliente
	 * @param bot
	 * @param up
	 * @return
	 */
	private SendResponse textoPersonalizavel(TelegramBot bot, Update up , String msg) {
		return bot.execute(new SendMessage(up.message().chat().id(),msg));
	}

	/**
	 * Metodo responsavel por executar comandos no Telegram para obter as 
	 * mensagens pendentes a partir de um off-set (limite inicial)
	 */

	public void receiveMessages() {

		TelegramBot bot = BotAdapterToken();

		try {
			while (true){
				getPedingMessages =  offSet(bot);
				Optional<List<Update>> updates = Optional.ofNullable(getPedingMessages.updates());

				if(updates.isPresent()) {
					updates.get().forEach(up -> {
						offSet = up.updateId() + 1;

						logger.info("Recebendo mensagem:"+ up.message().text());

						String saudacao = getSaudacao(listaSaudacao, up.message().text());
						if(!(saudacao == null) && !saudacao.isEmpty()) {
							sendMessageToTelegram(updates,bot,"saudacao");
							saudacao = "";
						}else {
							sendMessageToTelegram(updates,bot,String.valueOf(up.message().text()));

						}

					});
				}else {
					logger.info("Objeto Vazio");
				}
			}
		}catch(NullPointerException e) {
			e.printStackTrace();
		}finally {
			logger.info("While rodando");
		}	
	}

	/**
	 * Metodo responsavel por encaminhar o "Typing" como "Escrevendo" antes de enviar a resposta
	 * @param bot
	 * @param up
	 */
	private void enviarEscrevendo(TelegramBot bot, Update up) {
		baseResponse = bot.execute(new SendChatAction(up.message().chat().id(), ChatAction.typing.name()));
	}

	/**
	 * Metodo responsavel por ser ouvinte para receber atualizações de mensagens encaminhadas pelo usuario.
	 * @param bot
	 * @return
	 */
	private GetUpdatesResponse offSet(TelegramBot bot) {
		return bot.execute(new GetUpdates().limit(100).offset(offSet));
	}

	/**
	 * Metodo resposavel por recuperar atraves de um properties o token do bot e inicializar
	 * a chamada do mesmo.
	 * @return
	 */
	private TelegramBot BotAdapterToken() {
		setToken(String.valueOf(prop.getProperty("prop.telegramToken")));
		logger.info("TokenBot : " +  getToken());
		TelegramBot bot = TelegramBotAdapter.build(getToken());
		return bot;
	}		
	
	private String getSaudacao(String[] lista, String msg) {

		for (String i : lista) {
			if(i.equalsIgnoreCase(msg)) {
				msg = i;
				return msg;
			}
		}
		return null;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
