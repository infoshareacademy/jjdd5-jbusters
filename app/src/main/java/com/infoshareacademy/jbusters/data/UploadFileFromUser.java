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

        try {
            LOG.info("1: ");
            File file = new File(uploadFilePath + File.separator + fileName);
            LOG.info("2: " + file.toString());
            Files.deleteIfExists(file.toPath());
            LOG.info("3:delete " );
            InputStream fileContent = filePart.getInputStream();

            long bytesWritten  = Files.copy(fileContent, file.toPath());
            LOG.info("4 writen: " +  bytesWritten);
            fileContent.close();
            LOG.info("Uploaded file with name: " + fileName);
            LOG.info("Directory to " + fileName + " is " + uploadFilePath);
            return file;
        } catch (Exception e ) {
            LOG.error("Error on file writing script", e);
            return null;
        }

    }
}