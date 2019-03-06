import javax.mail.Message;
import javax.mail.MessagingException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

class ContentFormatter {
    static void writeRawToFile(ArrayList<Message> messages) throws IOException, MessagingException {
        for(int i = 0; i<messages.size();i++){
            BufferedWriter bw = new BufferedWriter(new FileWriter(new File("message"+i)));
            bw.write(PizzaHutShifts.getTextFromMessage(messages.get(i)));
            bw.close();
        }

    }
}
