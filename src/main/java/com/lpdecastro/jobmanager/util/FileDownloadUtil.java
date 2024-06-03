package com.lpdecastro.jobmanager.util;

import org.springframework.core.io.PathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileDownloadUtil {

    public static Resource getFileAsResource(String downloadDir, String fileName) throws IOException {
        Path filePath = Paths.get(downloadDir, fileName);

        if (Files.exists(filePath) && Files.isReadable(filePath)) {
            return new PathResource(filePath);
        } else {
            throw new IOException("File not found or not readable: " + fileName);
        }
    }
}
