package fr.ynov.collection.service;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.io.InputStream;
import java.io.IOException;

import java.util.Properties;

public class EmailSender {
    private final String smtpHost;
    private final int smtpPort;
    private final String username;
    private final String password;

    public EmailSender() {
        Properties config = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new IOException("Fichier de configuration 'config.properties' introuvable dans resources/");
            }
            config.load(input);
            this.smtpHost = config.getProperty("smtp.host");
            this.smtpPort = Integer.parseInt(config.getProperty("smtp.port"));
            this.username = config.getProperty("smtp.username");
            this.password = config.getProperty("smtp.password");
        } catch (IOException e) {
            throw new RuntimeException("Impossible de charger le fichier de configuration", e);
        }
    }

    public void sendEmail(String toEmail, String subject, String nomDestinataire, String contenu) throws MessagingException {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.ssl.enable", "true"); // tu utilisais le port 465 → SSL
        props.put("mail.smtp.host", smtpHost);
        props.put("mail.smtp.port", smtpPort);

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(username));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
        message.setSubject(subject);

        String finalBody = "Bonjour " + nomDestinataire + ",\n\n" + contenu + "\n\nCordialement,\nTon appli de collection";
        message.setText(finalBody);

        Transport.send(message);
    }
}
