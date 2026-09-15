package utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public final class EnvReader {

    private EnvReader() {
    }

    public static String getRequired(String name) {
        String value = System.getenv(name);

        if (value == null || value.isBlank()) {
            value = getDotenvValue(name);
        }

        if (value == null || value.isBlank()) {
            throw new IllegalStateException("Missing required environment variable or .env value: " + name);
        }

        return value;
    }

    private static String getDotenvValue(String name) {
        Path dotenvPath = Path.of(".env");

        if (!Files.exists(dotenvPath)) {
            return null;
        }

        try {
            return Files.readAllLines(dotenvPath)
                    .stream()
                    .filter(line -> line.startsWith(name + "="))
                    .map(line -> line.substring((name + "=").length()))
                    .findFirst()
                    .orElse(null);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to read .env file", e);
        }
    }
}
