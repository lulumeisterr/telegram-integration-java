package br.com.fiap.helper;

/**
 * Classe auxiliar para manipulacao de contexto de conversa
 * @author lucasrodriguesdonascimento
 *
 */
public class HelperString {

	public static String getSaudacao(String[] lista, String msg) {

		for (String i : lista) {
			if(i.equalsIgnoreCase(msg)) {
				msg = i;
				return msg;
			}
		}
		return null;
	}

	public static String firstLettertoUpperCase(String letter) {
		String s = letter.substring(0,1).toUpperCase() + letter.substring(1);
		return s;	
	}

	public static String limparTagNoticia(String text) {
		String texto = "";
		if(text.contains("/noticias")) {
			texto = text.replace("/noticias", "");
			return texto;
		}
		return null;
	}

	public static String getNoticia(String text) {
		String keyWord = "/noticias";

		if(text.contains(keyWord)) {
			return "/noticias";
		}

		return null;
	}


	public static String identifyDots(String description , String url) {
		String text = "";	
		text = description.concat("\n\n" + "**" + "Para saber mais acesse" + "**" + " : " + "\n\n" + url);
		return text;
	}

}
