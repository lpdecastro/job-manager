package com.lpdecastro.jobmanager.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileUploadUtil {

    public static void saveFile(String uploadDir, String filename, MultipartFile file) throws IOException {
        Path uploadDirPath = Paths.get(uploadDir);
        if (!Files.exists(uploadDirPath)) {
            Files.createDirectories(uploadDirPath);
        }

        Path filePath = uploadDirPath.resolve(filename);

        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {
            throw new IOException("Could not save file " + filename, ioe);
        }
    }
}
