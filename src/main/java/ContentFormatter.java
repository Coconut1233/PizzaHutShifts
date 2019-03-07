import javax.mail.Message;
import javax.mail.MessagingException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/*import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;*/

class ContentFormatter {
    private static final String[] DAYS = {"Utery", "Streda", "Ctvrtek", "Patek", "Sobota", "Nedele", "Pondeli"};
    private static Date[] dates = new Date[7];

    /*static void writeRawToFile(ArrayList<Message> messages) throws IOException, MessagingException {
        for(int i = 0; i<messages.size();i++){
            BufferedWriter bw = new BufferedWriter(new FileWriter(new File("message"+i)));
            bw.write(MailExtractor.getTextFromMessage(messages.get(i)));
            bw.close();
        }

    }*/

    static ArrayList<String[]> extractShiftTimes(Message message) throws IOException, MessagingException {
        ArrayList<String[]> shifts = new ArrayList<String[]>();
        String subject = message.getSubject();
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
                        shift[2] = lines[i+8];
                        shift[3] = lines[i+10];
                        shift[4] = lines[i+12];
                        shift[5] = lines[i+14];
                        shift[6] = lines[i+16];
                        shift[7] = lines[i+18];
                        i+=20;
                        shifts.add(shift);
                    }
                }
            }catch (ArrayIndexOutOfBoundsException e){
                System.out.println();
            }
        }
        return shifts;
    }

    static String printShifts(String[] shift){
        String shiftResult = "";
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.");
        shiftResult+="Jmeno:"+"\n";
        shiftResult+=shift[0]+"\n"+"\n";
        for(int i = 0; i<7; i++){
            shiftResult+=sdf.format(dates[i])+" ";
            shiftResult+=DAYS[i]+":"+"\n";
            shiftResult+=shift[i+1]+"\n"+"\n";
        }
        return shiftResult;
    }

    static void getDate(String subject) throws IOException {
        String[] parts = subject.split(" ");
        String date = parts[3];
        BufferedWriter bw = new BufferedWriter(new FileWriter(new File("date")));
        bw.write(date);
        bw.close();
        Date date1 = null;
        try {
            date1 = new SimpleDateFormat("yyyy.MM.dd").parse(date);
        } catch (ParseException e) {
            System.out.println("wrong date");
        }
        for(int i=0; i<7; i++){
            Calendar c = Calendar.getInstance();
            c.setTime(date1);
            c.add(Calendar.DATE, i);
            dates[i] = c.getTime();
        }
        /*for(Date date2 : dates){
            System.out.println(date2);
        }*/
    }
}
