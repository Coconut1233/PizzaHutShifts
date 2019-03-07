import javax.mail.Message;
import javax.mail.MessagingException;
import java.io.IOException;
import java.util.ArrayList;


public class PizzaHutShifts {
    public static void main(String[] args) throws IOException, MessagingException {
        ArrayList<Message> shiftMails = MailExtractor.getMails();
        ArrayList<String[]> shifts = ContentFormatter.extractShiftTimes(shiftMails.get(2));
        for(String[] shift : shifts){
            ContentFormatter.printShifts(shift);
        }
    }
}
