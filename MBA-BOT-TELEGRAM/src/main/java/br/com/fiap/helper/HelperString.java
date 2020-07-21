package br.com.fiap.helper;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * Classe auxiliar para manipulacao de contexto de conversa
 *
 * @author lucasrodriguesdonascimento, gabrieltr
 */
public class HelperString {

    static private final String[] listaSaudacao = {"bom dia", "oi", "olá", "iae", "koe", "oi tudo bem", "fmz", "fmza", "eae"};
    static private final String[] listaNoticia = {"news", "notícia", "noticia"};

    /**
     * Verifica se contem /ajuda ou /help
     *
     * @param message
     * @return
     */
    public static boolean isHelp(String message) {
        for (String i : listaSaudacao) {
            if (i.equalsIgnoreCase(message)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Metodo responsavel por verificar se tem eh uma saudacao
     *
     * @param message
     * @return
     */
    public static boolean isSaudacao(String message) {
        for (String i : listaSaudacao) {
            if (i.equalsIgnoreCase(message)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Verifica se contem /noticias
     *
     * @param message
     * @return
     */
    public static boolean isNoticia(String message) {

        for (String i : listaSaudacao) {
            if (i.contains(message)) {
                return true;
            }
        }
        return false;
    }


    /**
     * Adiciona link para saber mais sobre a noticia
     *
     * @param description
     * @param url
     * @return
     */
    public static String addLinkNoticia(String description, String url) {
        String text = "";
        text = description.concat("\n\n" + "**" + "Para saber mais acesse" + "**" + " : " + "\n\n" + url);
        return text;
    }

    /**
     * Obtendo a primeira letra da String , dava pra usar tambem replaceFirst
     *
     * @param letter
     * @return
     */
    public static String firstLettertoUpperCase(String letter) {
        String s = letter.substring(0, 1).toUpperCase() + letter.substring(1);
        return s;
    }

    public static String limparTagNoticia(String text) {
        Optional<String> parte = Stream.of(listaNoticia).filter(a -> text.contains(a)).findFirst();
        if (parte.isPresent()) {
            return text.replace(parte.get(), "").trim();
        }
        return text;
    }

}
