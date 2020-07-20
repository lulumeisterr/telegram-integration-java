package br.com.fiap.connectionTelegrambot;

import java.util.List;
import java.util.Optional;
import java.util.Properties;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ChatAction;
import com.pengrad.telegrambot.request.SendChatAction;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.BaseResponse;
import com.pengrad.telegrambot.response.GetUpdatesResponse;
import com.pengrad.telegrambot.response.SendResponse;

import br.com.fiap.dto.ObjectApi;
import br.com.fiap.helper.HelperString;
import br.com.fiap.newsapi.NewsApi;
import br.com.fiap.properties.PropertiesLoader;

/**
 * Classe responsavel por receber a mensagem do usuario e realizar o encaminhamento
 * @author lucasrodriguesdonascimento
 *
 */
public class SendMessagesToTelegram {

	private static GetUpdatesResponse getPedingMessages;
	private static SendResponse sendResponse;
	private static BaseResponse baseResponse;

	Properties prop = PropertiesLoader.getProp();
	static NewsApi apideNoticias;

	static final Logger logger = LogManager.getLogger(SendMessagesToTelegram.class.getName());
	String listaSaudacao [] = {"bom dia","oi","olá","iae","koe"};
	int offSet = 0;

	/**
	 * Este metodo é responsavel por receber a mensagem do usuario e verificar oq enviar.
	 * Sugestoes de melhoria : Integrar um NLP para melhorar a resposta do bot
	 * @param updates
	 * @param bot
	 * @param msg
	 */
	public static void send(Optional<List<Update>> updates, TelegramBot bot, String msg) {

		String EmojiSorrir = "\ud83d\ude00";
		getPedingMessages = CallbackMessage.offSet(bot);

		if(updates.isPresent()) {
			updates.get().forEach(up -> {

				enviarEscrevendo(bot, up);

				try {
					switch (msg) {

					case "/noticias":
						ObjectApi article = NewsApi.callApi(bot, HelperString.limparTagNoticia(up.message().text()));
						if(article.getTotalResults() == 0) {
							sendResponse = textoPersonalizavel(bot, up , "Infelizmente nao encontramos resultados para sua busca \n"
									+ "Tente novamente , Digite /noticias e oque voce deseja procurar");
						}else {
							article.getArticles().forEach(art -> {
								sendResponse = textoPersonalizavel(bot, up , "Resultado da sua busca : \n\n"
										+ HelperString.identifyDots(art.getDescription(),art.getUrl()));
							});
						}
						break;

					case "saudacao":
						sendResponse = textoPersonalizavel(bot, up , HelperString.firstLettertoUpperCase(String.valueOf(up.message().text()) + "  " +  up.message().from().firstName() + 
								" , Tudo bem ? " + EmojiSorrir));
						break;
					case "/start":
						//envio da mensagem de resposta
						sendResponse = textoPersonalizavel(bot, up , "Digite /noticias [ e oque vc deseja saber ] Exemplo : /noticas sobre o dolar hoje" + EmojiSorrir);
						//verificação de mensagem enviada com sucesso
						System.out.println("Mensagem Enviada?" +sendResponse.isOk());
						System.out.println("Recebendo mensagem:"+ up.message().text());
						break;
					default:
						sendResponse = bot.execute(new SendMessage(up.message().chat().id(),"Nao Entendi pode digitar novamente ?"));
						break;
					}
				}catch(NullPointerException e) {
					logger.error("HOUVE UM PROBLEMA AO PROCESSAR SUA MENSAGEM");
				}catch(ArrayIndexOutOfBoundsException e) {
					logger.error("HOUVE UM PROBLEMA AO RECEBER A LISTA DE MENSAGEM");
				}catch(Exception e) {
					logger.error("HOUVE UM PROBLEMA");
				}
			});

		}else {
			logger.info("Objeto Vazio");
		}

	}

	/**
	 * Metodo responsavel por encaminhar o "Typing" como "Escrevendo" antes de enviar a resposta
	 * @param bot
	 * @param up
	 */
	public static void enviarEscrevendo(TelegramBot bot, Update up) {
		baseResponse = bot.execute(new SendChatAction(up.message().chat().id(), ChatAction.typing.name()));
	}

	/**
	 * Metodo responsavel por personalizar o envio da mensagem para o cliente
	 * @param bot
	 * @param up
	 * @return
	 */
	public static SendResponse textoPersonalizavel(TelegramBot bot, Update up , String msg) {
		return bot.execute(new SendMessage(up.message().chat().id(),msg));
	}

}
