package Iamreporter.Service;

import org.json.JSONObject;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;


public class EmailValidator {


    public void sendRegistrationMessage(String email, String json,String generatedPassword) {
        JSONObject jsonObject = new JSONObject(json);
        String name = jsonObject.getString("nameSurName");
        String [] nameSurname = name.split(" ");
        final String username = "dmitrenkonikita1213@gmail.com";
        final String password = "telez1213";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");


        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("noreplyreporter@reporter.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(email));
            message.setSubject("Я репортер");
            message.setText("Добрий день , " + nameSurname[0] + " ," + nameSurname[1] + " ви зареєстровані в программі ,''Я репортер'' :) " +
                    "Ваш пароль = "+generatedPassword);

            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean isValidEmailAddress(String email) {
        boolean result = true;
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            result = false;
        }
        return result;
    }

}
