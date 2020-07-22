package br.com.fiap.helper;

import br.com.fiap.interfaces.ClassificationKind;

import java.util.List;

import static java.util.Arrays.asList;

/**
 * Classificação da mensagem
 * @author gabriel.ribeiro
 */
public enum Classification {

    UNKOWN(ClassificationKind.dummy, asList()),
    NEWS(ClassificationKind.containsIgnoreCase.or(ClassificationKind.equalsIgnoreCase), asList("news", "notícia", "noticia")),
    GREETINGS(ClassificationKind.containsIgnoreCase, asList("bom dia", "oi", "olá", "ola", "iae", "koe", "oi tudo bem", "fmz", "fmza", "eae")),
    HELP(ClassificationKind.equalsIgnoreCase, asList("ajuda", "help"));

    private final List<String> list;
    private final ClassificationKind kind;

    Classification(ClassificationKind kind, List<String> list) {
        this.kind = kind;
        this.list = list;
    }

    public static Classification findClassification(String message) {
        for (Classification classification : Classification.values()) {
            if (classification.kind != null) {
                if (classification.list.stream()
                        .anyMatch(a -> classification.kind.apply(message, a))) {
                    return classification;
                }
            }
        }
        return Classification.UNKOWN;
    }

    public List<String> getList() {
        return list;
    }

}