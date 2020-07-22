package br.com.fiap.interfaces;

public interface ClassificationKind {

    boolean apply(String message, String str2);

    /**
     * do nothing, just return false
     */
    ClassificationKind dummy = (a, b) -> false;

    /**
     * str1 equals str2
     */
    public ClassificationKind equalsIgnoreCase = (String str1, String str2) -> {
        if (str1 == null && str2 == null) {
            return true;
        } else if (str1 != null && str2 != null) {
            return str1.equalsIgnoreCase(str2);
        }
        return false;
    };

    /**
     * str1 contains str2, with case sensitive
     */
    public ClassificationKind contains = (String str1, String str2) -> {
        if (str1 == null && str2 == null) {
            return true;
        } else if (str1 != null && str2 != null) {
            return str1.contains(str2);
        }
        return false;
    };
    /**
     * str1 contains str2, ignore case sensitve
     */
    public ClassificationKind containsIgnoreCase = (String str1, String str2) -> {
        if (str1 == null && str2 == null) {
            return true;
        } else if (str1 != null && str2 != null) {
            return str1.toLowerCase().contains(str2.toLowerCase());
        }
        return false;
    };

}
