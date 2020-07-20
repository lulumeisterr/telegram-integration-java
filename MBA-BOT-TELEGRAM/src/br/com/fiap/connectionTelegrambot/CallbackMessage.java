package br.com.fiap.connectionTelegrambot;

import java.util.List;
import java.util.Optional;
import java.util.Properties;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.TelegramBotAdapter;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.GetUpdates;
import com.pengrad.telegrambot.response.GetUpdatesResponse;

import br.com.fiap.helper.HelperString;
import br.com.fiap.newsapi.NewsApi;
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
public class CallbackMessage{

	private String token;
	private GetUpdatesResponse getPedingMessages;
	
	Properties prop = PropertiesLoader.getProp();
	NewsApi apideNoticias;
	
	static final Logger logger = LogManager.getLogger(CallbackMessage.class.getName());
	String listaSaudacao [] = {"bom dia","oi","olá","iae","koe","oi tudo bem"};
	static int offSet = 0;


	/**
	 * Metodo responsavel por executar comandos no Telegram para obter as 
	 * mensagens pendentes a partir de um off-set (controle das mensagens processadas)
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

						String saudacao = HelperString.getSaudacao(listaSaudacao, up.message().text());
						String obtemNoticia = HelperString.getNoticia(up.message().text());

						if(!(obtemNoticia == null) && !obtemNoticia.isEmpty()) {
							SendMessagesToTelegram.send(updates,bot,"/noticias");
						}else if(!(saudacao == null) && !saudacao.isEmpty()) {
							SendMessagesToTelegram.send(updates,bot,"saudacao");
						}else {
							SendMessagesToTelegram.send(updates,bot,String.valueOf(up.message().text()));
						}
					});
				}else {
					logger.info("Sem informacoes de mensagem");
				}
			}
		}catch(NullPointerException e) {
			e.printStackTrace();
			logger.error("ERRO AO PROCESSAR A MENSAGEM!!!");
		}catch(Exception e) {
			e.printStackTrace();
			logger.error("ERRO AO PROCESSAR A MENSAGEM!!!");
		}finally {
			logger.info("While rodando");
		}	
	}

	/**
	 * Metodo responsavel por ser ouvinte para receber atualizações de mensagens encaminhadas pelo usuario.
	 * @param bot
	 * @return
	 */
	public static GetUpdatesResponse offSet(TelegramBot bot) {
		return bot.execute(new GetUpdates().limit(100).offset(offSet));
	}
	
	/**
	 * Metodo resposavel por recuperar atraves de um properties o token do bot e inicializar
	 * a chamada do mesmo.
	 * @return
	 */
	private TelegramBot BotAdapterToken() {
		setToken(String.valueOf(prop.getProperty("prop.telegramToken")));
//		logger.info("TokenBot : " +  getToken());
		TelegramBot bot = TelegramBotAdapter.build(getToken());
		return bot;
	}		
	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
