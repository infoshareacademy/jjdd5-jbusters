package com.infoshareacademy.jbusters.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.RequestScoped;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

@RequestScoped
public class UploadFileFromUser {

    private static final Logger LOG = LoggerFactory.getLogger(UploadFileFromUser.class);


    public File uploadFile(Part filePart) throws IOException {

        final String uploadFilePath = System.getProperty("jboss.home.dir") + "/upload";

        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();

        File file = new File(uploadFilePath + File.separator + fileName);

        Files.deleteIfExists(file.toPath());

        InputStream fileContent = filePart.getInputStream();

        Files.copy(fileContent, file.toPath());

        fileContent.close();
        LOG.info("Uploaded file with name: " + fileName);
        LOG.info("Directory to " + fileName + " is " + uploadFilePath);

        return file;
    }
}