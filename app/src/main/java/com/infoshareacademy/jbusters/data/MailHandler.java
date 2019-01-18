package com.infoshareacademy.jbusters.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.UnsupportedEncodingException;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Properties;

@ApplicationScoped
public class MailHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(MailHandler.class);
    private static final String SENDER_NAME = "JBusters Web App";
    private static final String ENCODING_SUBJECT = "UTF-8";
    private static final String ENCODING_CONTENT = "text/html; charset=UTF-8";
    private static final String TEMP = "jbusters1234";
    private static final String SUBJECT = "Raport z dnia ";
    private static final String WELCOME = "Witaj,";
    private static final String CONTENT = "w załączniku znajdziesz cykliczny raport, zawierający dane statystyczne wyszukiwań w naszej aplikacji.";
    private static String ATTACHMENT_PATH = Paths.get(System.getProperty("jboss.server.temp.dir"), "report.pdf").toString();
    private static final String ATTACHMENT_NAME = "Raport_";
    private static final String ATTACHMENT_TYPE = ".pdf";

    public void sendMail(String login, String[] recipients) throws UnsupportedEncodingException, MessagingException {

        Properties props = System.getProperties();
        String host = "smtp.gmail.com";
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");

        Session session = Session.getInstance(props,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(login, TEMP);
                    }
                });

        Message message = new MimeMessage(session);

        message.setFrom(new InternetAddress(login, SENDER_NAME));
        InternetAddress[] toAddress = new InternetAddress[recipients.length];

        for (int i = 0; i < recipients.length; i++) {
            toAddress[i] = new InternetAddress(recipients[i]);
        }

        for (int i = 0; i < toAddress.length; i++) {
            message.addRecipient(Message.RecipientType.BCC, toAddress[i]);
        }

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat dateFormatAttachment = new SimpleDateFormat("yyyy_MM_dd");
        Date date = new Date();

        ((MimeMessage) message).setSubject(SUBJECT + dateFormat.format(date), ENCODING_SUBJECT);

        MimeBodyPart textBodyPart = new MimeBodyPart();
        textBodyPart.setContent(WELCOME + System.lineSeparator() + CONTENT, ENCODING_CONTENT);

        MimeBodyPart attachmentBodyPart = new MimeBodyPart();
        attachmentBodyPart.setDataHandler(new DataHandler(new FileDataSource(ATTACHMENT_PATH)));
        attachmentBodyPart.setFileName(ATTACHMENT_NAME + dateFormatAttachment.format(date) + ATTACHMENT_TYPE);

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(textBodyPart);
        multipart.addBodyPart(attachmentBodyPart);

        message.setContent(multipart);

        try {
            Transport.send(message);
            LOGGER.info("Mail sent to: {}", Arrays.toString(recipients));
        } catch (MessagingException e) {
            LOGGER.error("Mail not sent, attachment missing under the following path: {}", ATTACHMENT_PATH);
            throw e;
        }
    }
}
