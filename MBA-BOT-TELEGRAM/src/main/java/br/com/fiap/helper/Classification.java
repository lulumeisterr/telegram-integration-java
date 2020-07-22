package br.com.fiap.helper;

import br.com.fiap.interfaces.ClassificationKind;

import java.util.List;

import static java.util.Arrays.asList;

public enum Classification {

    UNKOWN(ClassificationKind.dummy, asList()),
    NEWS(ClassificationKind.contains, asList("news", "notícia", "noticia")),
    GREETINGS(ClassificationKind.contains, asList("bom dia", "oi", "olá", "iae", "koe", "oi tudo bem", "fmz", "fmza", "eae")),
    HELP(ClassificationKind.equalsIgnoreCase, asList("ajuda", "help"));

    private final List<String> list;
    private final ClassificationKind kind;

    Classification(ClassificationKind kind, List<String> list) {
        this.kind = kind;
        this.list = list;
    }

    public static Classification getClassification(String message) {
        for (Classification classification : Classification.values()) {
            for (String classStrings : classification.list) {
                if (classification.kind != null) {
                    if (classification.kind.apply(classStrings, message)) {
                        return classification;
                    }
                } else {
                    if (ClassificationKind.equalsIgnoreCase.apply(classStrings, message)) {
                        return classification;
                    }

                }
            }
        }
        return Classification.UNKOWN;
    }

    public List<String> getList() {
        return list;
    }
}