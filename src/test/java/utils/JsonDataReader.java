package utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.List;

public final class JsonDataReader {

    private static final Gson GSON = new Gson();

    private JsonDataReader() {
    }

    public static <T> List<T> readList(
            String resourcePath,
            Class<T> dataClass
    ) {
        InputStream input = JsonDataReader.class
                .getClassLoader()
                .getResourceAsStream(resourcePath);

        if (input == null) {
            throw new IllegalArgumentException(
                    "JSON resource not found: " + resourcePath
            );
        }

        try (Reader reader = new InputStreamReader(
                input,
                StandardCharsets.UTF_8
        )) {
            Type listType = TypeToken
                    .getParameterized(List.class, dataClass)
                    .getType();

            return GSON.fromJson(reader, listType);

        } catch (Exception e) {
            throw new IllegalStateException(
                    "Failed to read JSON resource: " + resourcePath,
                    e
            );
        }
    }

    public static <T> Object[][] readAsDataProvider(
            String resourcePath,
            Class<T> dataClass
    ) {
        return readList(resourcePath, dataClass)
                .stream()
                .map(data -> new Object[]{data})
                .toArray(Object[][]::new);
    }
}