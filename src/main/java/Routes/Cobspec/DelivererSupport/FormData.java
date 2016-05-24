package Routes.Cobspec.DelivererSupport;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;

public class FormData {

    private final String FORM_FILE_NAME = "form.txt";

    public void saveData(String data){
        byte[] dataBytes = data.getBytes();
        try {
            FileOutputStream out = new FileOutputStream(FORM_FILE_NAME);
            out.write(dataBytes);
            out.close();
        } catch (Exception e){}
    }

    public String getData(){
        String data;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(FORM_FILE_NAME));
            data = reader.readLine();
        } catch (Exception e){
            data = null;
        }
        return data;
    }

    public void deleteData() {
        File file = new File(FORM_FILE_NAME);
        file.delete();
    }

}
