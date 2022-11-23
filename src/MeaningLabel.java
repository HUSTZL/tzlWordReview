import javax.swing.*;
import java.io.*;

public class MeaningLabel extends JLabel {
    public MeaningLabel(String word){
        Process proc;
        try{
            String[] cmd = new String[]{"E:\\PycharmProjects\\test01\\venv\\Scripts\\python","E:\\PycharmProjects\\test01\\regex.py",word};
            proc=Runtime.getRuntime().exec(cmd);
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream(),"gb2312"));
            String line = null;
            StringBuilder resline = new StringBuilder();
            while((line=in.readLine())!=null && !line.equals("")){
                resline.append(line);
                resline.append("\n");
            }
            in.close();
            //proc.waitFor();
            this.setText(resline.toString());
            System.out.println(resline);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
