package utils;

import java.util.regex.Pattern;

public final class UiSelectorUtils {

    private UiSelectorUtils() {
    }

    public static String escapeText(String value) {
        if (value == null) {
            throw new IllegalArgumentException("Value cannot be null");
        }

        return value
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }

    public static String escapeRegexLiteral(String value) {
        return escapeText(Pattern.quote(value));
    }
}
