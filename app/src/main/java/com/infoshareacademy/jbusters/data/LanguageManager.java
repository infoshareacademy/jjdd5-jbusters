package com.infoshareacademy.jbusters.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

@ApplicationScoped
public class LanguageManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(LanguageManager.class);
    private static final String LANGUAGE_KEY = "language";
    private static final String LANGUAGE_DEFAULT_VALUE = "pl";
    private static final Properties languageProperties = new Properties();

    @Inject
    StaticFields staticFields;

    public String translate(String translationKey) throws IOException {

        Locale currentLocale = new Locale(getLanguage());
        ResourceBundle messages = ResourceBundle.getBundle("translations", currentLocale);

        return messages.getString(translationKey);
    }

    public String getLanguage() throws IOException {

        if (!Files.exists(staticFields.getLangPropertiesPath())) {
            Files.createFile(staticFields.getLangPropertiesPath());
        }

        String url = staticFields.getLangPropertiesPath().toString();
        FileInputStream fis = new FileInputStream(url);

        try {
            languageProperties.load(fis);
            fis.close();
        } catch (IOException e) {
            LOGGER.error("Missing properties file in path {}", staticFields.getLangPropertiesPath().toString());
        }

        String language = languageProperties.getProperty(LANGUAGE_KEY, LANGUAGE_DEFAULT_VALUE);

        LOGGER.info("Language set to default: {}", LANGUAGE_DEFAULT_VALUE);

        return language;
    }

    public void setLanguage(String language) throws IOException {

        if (!Files.exists(staticFields.getLangPropertiesPath())) {
            Files.createFile(staticFields.getLangPropertiesPath());
        }

        String url = staticFields.getLangPropertiesPath().toString();
        FileOutputStream fos = new FileOutputStream(url);

        languageProperties.setProperty(LANGUAGE_KEY, language);
        languageProperties.store(fos, "Language Properties");

        fos.close();

        LOGGER.info("Language stored and set to: {}", language);
    }
}
