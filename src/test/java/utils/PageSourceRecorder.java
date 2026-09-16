package utils;

import io.qameta.allure.Allure;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class PageSourceRecorder {

    private static final Path PAGE_SOURCE_DIRECTORY = Path.of("page-sources");

    private static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss-SSS");

    private PageSourceRecorder() {
    }

    public static void captureAndAttach(String testName) {
        if (!Driver.isInitialized()) {
            return;
        }

        try {
            String pageSource = Driver.getDriver().getPageSource();
            byte[] pageSourceBytes = pageSource.getBytes(StandardCharsets.UTF_8);

            Files.createDirectories(PAGE_SOURCE_DIRECTORY);

            String timestamp =
                    LocalDateTime.now().format(DATE_FORMAT);

            String fileName = "%s_%s_page_source.xml"
                    .formatted(timestamp, testName);

            Files.write(
                    PAGE_SOURCE_DIRECTORY.resolve(fileName),
                    pageSourceBytes
            );

            Allure.addAttachment(
                    "Page source on failure",
                    "application/xml",
                    new ByteArrayInputStream(pageSourceBytes),
                    ".xml"
            );

        } catch (Exception e) {
            System.err.println(
                    "Failed to capture page source for test '%s': %s"
                            .formatted(testName, e.getMessage())
            );
        }
    }
}
