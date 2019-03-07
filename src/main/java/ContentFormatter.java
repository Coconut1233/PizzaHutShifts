import javax.mail.Message;
import javax.mail.MessagingException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

class ContentFormatter {
    static final String[] DAYS = {"Utery", "Streda", "Ctvrtek", "Patek", "Sobota", "Nedele", "Pondeli"};

    static void writeRawToFile(ArrayList<Message> messages) throws IOException, MessagingException {
        for(int i = 0; i<messages.size();i++){
            BufferedWriter bw = new BufferedWriter(new FileWriter(new File("message"+i)));
            bw.write(MailExtractor.getTextFromMessage(messages.get(i)));
            bw.close();
        }

    }

    static ArrayList<String[]> extractShiftTimes(Message message) throws IOException, MessagingException {
        ArrayList<String[]> shifts = new ArrayList<String[]>();
        String content = MailExtractor.getTextFromMessage(message);
        String[] lines =content.split("\n");
        for(int i = 0; i<lines.length; i++){
            String line = lines[i];
            char[] chars = line.toCharArray();
            try{
                if(chars[0] == '1'){
                    while(i<lines.length){
                        String[] shift = new String[8];
                        shift[0] = lines[i+2];
                        shift[1] = lines[i+6];
                        shift[3] = lines[i+8];
                        shift[4] = lines[i+10];
                        shift[5] = lines[i+12];
                        shift[6] = lines[i+14];
                        shift[7] = lines[i+16];
                        i+=20;
                        shifts.add(shift);
                    }
                }
            }catch (ArrayIndexOutOfBoundsException e){

            }
        }
        return shifts;
    }

    static void printShifts(String[] shift){
        System.out.println("Jmeno:");
        System.out.println(shift[0]);
        for(int i = 0; i<7; i++){
            System.out.println(DAYS[i]);
            System.out.println(shift[i+1]);
        }
    }
}
