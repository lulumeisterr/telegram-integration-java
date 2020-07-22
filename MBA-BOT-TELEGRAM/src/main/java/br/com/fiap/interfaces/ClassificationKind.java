package br.com.fiap.interfaces;

public interface ClassificationKind {
    boolean apply(String str1, String str2);

    ClassificationKind dummy = (a, b) -> false;

    ClassificationKind equalsIgnoreCase = (String str1, String str2) -> {
        if (str1 == null && str2 == null) {
            return true;
        } else if (str1 != null && str2 != null) {
            return str1.equalsIgnoreCase(str2);
        }
        return false;
    };

    ClassificationKind contains = (String str1, String str2) -> {
        if (str1 == null && str2 == null) {
            return true;
        } else if (str1 != null && str2 != null) {
            return str1.contains(str2);
        }
        return false;
    };

}
