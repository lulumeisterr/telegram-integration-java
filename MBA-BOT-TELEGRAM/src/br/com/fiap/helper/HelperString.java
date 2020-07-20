package br.com.fiap.helper;

/**
 * Classe auxiliar para manipulacao de contexto de conversa
 * @author lucasrodriguesdonascimento
 *
 */
public class HelperString {

	/**
	 * Metodo responsavel por verificar se tem eh uma saudacao
	 * @param lista
	 * @param msg
	 * @return
	 */
	public static String getSaudacao(String[] lista, String msg) {

		for (String i : lista) {
			if(i.equalsIgnoreCase(msg)) {
				msg = i;
				return msg;
			}
		}
		return null;
	}

	/**
	 * Obtendo a primeira letra da String , dava pra usar tambem replaceFirst
	 * @param letter
	 * @return
	 */
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

	/**
	 * Verifica se contem /noticias
	 * @param text
	 * @return
	 */
	public static String getNoticia(String text) {
		String keyWord = "/noticias";

		if(text.contains(keyWord)) {
			return "/noticias";
		}

		return null;
	}


	/**
	 * Adiciona link para saber mais sobre a noticia
	 * @param description
	 * @param url
	 * @return
	 */
	public static String addLinkNoticia(String description , String url) {
		String text = "";	
		text = description.concat("\n\n" + "**" + "Para saber mais acesse" + "**" + " : " + "\n\n" + url);
		return text;
	}

}
