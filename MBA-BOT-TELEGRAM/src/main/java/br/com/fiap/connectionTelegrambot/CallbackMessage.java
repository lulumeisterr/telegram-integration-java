package br.com.fiap.connectionTelegrambot;

import br.com.fiap.helper.Classification;
import br.com.fiap.properties.PropertiesLoader;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.TelegramBotAdapter;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.GetUpdates;
import com.pengrad.telegrambot.response.GetUpdatesResponse;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Optional;
import java.util.Properties;

/**
 * Classe responsavel por receber a mensagem e realizar a manipulacao da informacao
 * recebida do telegram.
 * <p>
 * - objeto responsável por receber as mensagens GetUpdatesResponse updatesResponse;
 * - objeto responsável por gerenciar o envio de respostas SendResponse sendResponse;
 * - objeto responsável por gerenciar o envio de ações do chat BaseResponse baseResponse;
 *
 * @author lucasrodriguesdonascimento, gabrielltr
 */
public class CallbackMessage {

    static final Logger logger = LogManager.getLogger(CallbackMessage.class.getName());
    static int offSet = 0;
    final private Properties prop = PropertiesLoader.getProp();
    private String token;
    private GetUpdatesResponse getPedingMessages;

    /**
     * Metodo responsavel por ser ouvinte para receber atualizações de mensagens encaminhadas pelo usuario.
     *
     * @param bot
     * @return
     */
    public static GetUpdatesResponse offSet(TelegramBot bot) {
        return bot.execute(new GetUpdates().limit(100).offset(offSet));
    }

    /**
     * Metodo responsavel por executar comandos no Telegram para obter as
     * mensagens pendentes a partir de um off-set (controle das mensagens processadas)
     */
    public void receiveMessages() {

        TelegramBot bot = BotAdapterToken();

        try {
            while (true) {
                getPedingMessages = offSet(bot);
                Optional<List<Update>> updates = Optional.ofNullable(getPedingMessages.updates());

                if (updates.isPresent()) {
                    updates.get().forEach(up -> {
                        offSet = up.updateId() + 1;

                        logger.info("Recebendo mensagem:" + up.message().text());
                        Classification classification = Classification.getClassification(up.message().text());
                        String msg = String.valueOf(up.message().text());
                        SendMessagesToTelegram.send(updates, bot, msg);
                    });
                } else {
                    logger.info("Sem informacoes de mensagem");
                }
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
            logger.error("ERRO AO PROCESSAR A MENSAGEM!!!");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("ERRO AO PROCESSAR A MENSAGEM!!!");
        } finally {
            logger.info("While rodando");
        }
    }

    /**
     * Metodo resposavel por recuperar atraves de um properties o token do bot e inicializar
     * a chamada do mesmo.
     *
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
