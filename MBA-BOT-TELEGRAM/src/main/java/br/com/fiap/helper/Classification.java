package br.com.fiap.helper;

public enum Classification {
    UNKOWN, NEWS, GREETINGS, HELP;

    public static Classification getClassification(String message) {
        if (HelperString.isSaudacao(message)) {
            return Classification.GREETINGS;
        } else if (HelperString.isNoticia(message)) {
            return Classification.NEWS;
        } else if (HelperString.isHelp(message)) {
            return Classification.HELP;
        }
        return Classification.UNKOWN;
    }
}
