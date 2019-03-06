import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.MimeMultipart;


public class PizzaHutShifts {
    public static void main(String[] args) throws IOException {
        ArrayList<String> shiftMails = new ArrayList<String>();
        Properties props = System.getProperties();
        props.setProperty("mail.store.protocol", "imaps");
        try {
            Session session = Session.getDefaultInstance(props, null);
            Store store = session.getStore("imaps");
            store.connect("imap.gmail.com", "spiridonov.michail1@gmail.com", "fqnrgwruviewulxi");
            System.out.println(store);

            Folder inbox = store.getFolder("Inbox");
            inbox.open(Folder.READ_ONLY);
            Message messages[] = inbox.getMessages();
            for(Message message:messages) {
                if(message.getSubject().toLowerCase().contains("schedule: ph vokovice")){
                    if(shiftMails.size()>2){
                        break;
                    }
                    shiftMails.add(getTextFromMessage(message));
                }
            }
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (MessagingException e) {
            e.printStackTrace();
            System.exit(2);
        }
    }
    private static String getTextFromMessage(Message message) throws MessagingException, IOException {
        String result = "";
        if (message.isMimeType("text/plain")) {
            result = message.getContent().toString();
        } else if (message.isMimeType("multipart/*")) {
            MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
            result = getTextFromMimeMultipart(mimeMultipart);
        }
        return result;
    }
    private static String getTextFromMimeMultipart(
            MimeMultipart mimeMultipart)  throws MessagingException, IOException{
        String result = "";
        int count = mimeMultipart.getCount();
        for (int i = 0; i < count; i++) {
            BodyPart bodyPart = mimeMultipart.getBodyPart(i);
            if (bodyPart.isMimeType("text/plain")) {
                result = result + "\n" + bodyPart.getContent();
                break;
            } else if (bodyPart.isMimeType("text/html")) {
                String html = (String) bodyPart.getContent();
                result = result + "\n" + org.jsoup.Jsoup.parse(html).text();
            } else if (bodyPart.getContent() instanceof MimeMultipart){
                result = result + getTextFromMimeMultipart((MimeMultipart)bodyPart.getContent());
            }
        }
        return result;
    }


}
