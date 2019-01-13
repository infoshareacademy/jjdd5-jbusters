package com.infoshareacademy.jbusters.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

@ApplicationScoped
public class LanguageManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(LanguageManager.class);
    private static final Path LANGUAGE_PROPERTIES_PATH = StaticFields.getLangPropertiesPath();
    private static final String LANGUAGE_KEY = "language";
    private static final String LANGUAGE_DEFAULT_VALUE = "pl";
    private static final Properties languageProperties = new Properties();

    public String translate(String translationKey) throws IOException {

        Locale currentLocale = new Locale(getLanguage());
        ResourceBundle.Control utf8Control = new Utf8ResourceBundleControl(false);
        ResourceBundle bundle = ResourceBundle.getBundle("translations", currentLocale, utf8Control);
        String message = bundle.getString(translationKey);

        LOGGER.info("Returned translated text: {}", message);

        return message;
    }

    public String getLanguage() throws IOException {

        if (!Files.exists(LANGUAGE_PROPERTIES_PATH)) {
            LOGGER.warn("Missing properties file in path: {}", LANGUAGE_PROPERTIES_PATH.toString());
            LOGGER.info("Language set to default: {}", LANGUAGE_DEFAULT_VALUE);
            return LANGUAGE_DEFAULT_VALUE;
        } else {
            FileInputStream fis = new FileInputStream(LANGUAGE_PROPERTIES_PATH.toString());

            try {
                languageProperties.load(fis);
                LOGGER.info("Language properties loaded from path: {}", LANGUAGE_PROPERTIES_PATH.toString());
                fis.close();
            } catch (IOException e) {
                LOGGER.error("Missing properties file in path: {}", LANGUAGE_PROPERTIES_PATH.toString());
            }

            String language = languageProperties.getProperty(LANGUAGE_KEY, LANGUAGE_DEFAULT_VALUE);

            LOGGER.info("Language set to: {}", language);

            return language;
        }
    }

    public void setLanguage(String language) throws IOException {

        if (!Files.exists(LANGUAGE_PROPERTIES_PATH)) {
            Files.createFile(LANGUAGE_PROPERTIES_PATH);
            LOGGER.info("Language properties file created in path: {}", LANGUAGE_PROPERTIES_PATH.toString());
        }

        String url = StaticFields.getLangPropertiesPath().toString();
        FileOutputStream fos = new FileOutputStream(url);

        languageProperties.setProperty(LANGUAGE_KEY, language);
        languageProperties.store(fos, "Language Properties");

        fos.close();

        LOGGER.info("Language stored and set to: {}", language);
    }
}
