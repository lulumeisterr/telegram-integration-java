package br.com.fiap.helper;

import java.util.Optional;

/**
 * Classe auxiliar para manipulacao de contexto de conversa
 *
 * @author lucasrodriguesdonascimento, gabrieltr
 */
public class HelperString {

    /**
     * Adiciona link para saber mais sobre a noticia
     *
     * @param description
     * @param url
     * @return
     */
    public static String addLinkNoticia(String description, String url) {
        String text = "";
        return description.concat("\n\n" + "**" + "Para saber mais acesse" + "**" + " : " + "\n\n" + url);
    }

    /**
     * Obtendo a primeira letra da String , dava pra usar tambem replaceFirst
     *
     * @param letter
     * @return 
     */
    public static String firstLettertoUpperCase(String letter) {
        return letter.substring(0, 1).toUpperCase() + letter.substring(1);
    }

    public static String limparTagNoticia(String text) {
        Optional<String> parte = Classification.NEWS.getList().stream().filter(a -> text.contains(a)).findAny();
        if (parte.isPresent()) {
            return text.replace(parte.get(), "").trim();
        }
        return text;
    }

}
