package br.com.fiap.connectionTelegrambot;

import br.com.fiap.dto.ObjectApi;
import br.com.fiap.helper.Classification;
import br.com.fiap.helper.HelperString;
import br.com.fiap.newsapi.NewsApi;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ChatAction;
import com.pengrad.telegrambot.request.SendChatAction;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.BaseResponse;
import com.pengrad.telegrambot.response.SendResponse;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Optional;

/**
 * Classe responsavel por receber a mensagem do usuario e realizar o encaminhamento
 *
 * @author lucasrodriguesdonascimento, gabrielltr
 */
public class SendMessagesToTelegram {

    static final Logger logger = LogManager.getLogger(SendMessagesToTelegram.class.getName());

    /**
     * Este metodo é responsavel por receber a mensagem do usuario e verificar oq enviar.
     * Sugestoes de melhoria : Integrar um NLP para melhorar a resposta do bot
     *
     * @param updates
     * @param bot
     * @param msg
     */
    public static void send(Optional<List<Update>> updates, TelegramBot bot, Classification classification, String msg) {


        if (updates.isPresent()) {
            updates.get().forEach(up -> {

                enviarEscrevendo(bot, up);

                try {
                    switch (classification) {
                        case NEWS:
                            ObjectApi article = NewsApi.callApi(bot, HelperString.limparTagNoticia(up.message().text()));
                            if (article.getTotalResults() == 0) {
                                textoPersonalizavel(bot, up, "Infelizmente nao encontramos resultados para sua busca \n"
                                        + "Tente novamente , Digite /noticias e oque voce deseja procurar");
                            } else {
                                article.getArticles().forEach(art -> {
                                    textoPersonalizavel(bot, up, "Resultado da sua busca : \n\n"
                                            + HelperString.addLinkNoticia(art.getDescription(), art.getUrl()));
                                });
                            }
                            break;

                        case GREETINGS:
                            String EmojiSorrir = "\ud83d\ude00";
                            textoPersonalizavel(bot, up, HelperString.firstLettertoUpperCase(up.message().text() + "  " + up.message().from().firstName() +
                                    " , Tudo bem ? " + EmojiSorrir));
                            break;

                        case HELP:
                            textoPersonalizavel(bot, up, "Digite noticias [ e o que vc deseja saber ] \n\n "
                                    + "Exemplo : notícias sobre futebol");
                            break;

                        default:
                            SendResponse sendResponse = bot.execute(new SendMessage(up.message().chat().id(), "Nao entendi pode digitar novamente ? \n(tente /ajuda ou /help)"));
                            logger.info("Mensagem Enviada? " + sendResponse.isOk());
                            logger.info("Recebendo mensagem: " + up.message().text());
                            break;
                    }
                    //verificação de mensagem enviada com sucesso
                } catch (ArrayIndexOutOfBoundsException e) {
                    logger.error("HOUVE UM PROBLEMA AO RECEBER A LISTA DE MENSAGEM", e);
                } catch (Exception e) {
                    logger.error("HOUVE UM PROBLEMA", e);
                }
            });

        } else {
            logger.info("Objeto Vazio");
        }

    }

    /**
     * Metodo responsavel por encaminhar o "Typing" como "Escrevendo" antes de enviar a resposta
     *
     * @param bot
     * @param up
     * @return Resposta de envio de mensagem do bot
     */
    public static BaseResponse enviarEscrevendo(TelegramBot bot, Update up) {
        return bot.execute(new SendChatAction(up.message().chat().id(), ChatAction.typing.name()));
    }

    /**
     * Metodo responsavel por personalizar o envio da mensagem para o cliente
     *
     * @param bot
     * @param up
     * @return Resposta de envio de mensagem do bot
     */
    public static SendResponse textoPersonalizavel(TelegramBot bot, Update up, String msg) {
        return bot.execute(new SendMessage(up.message().chat().id(), msg));
    }

}
