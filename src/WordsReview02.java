import javax.swing.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class WordsReview02 {
    public static void main(String[] args) {
        SimpleFrame frame=new SimpleFrame("WordsReview");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
//        frequencyWriter(150);

    }

    public static void frequencyWriter(int n){
        File file1=new File("txt/wordsFrequency.txt");
        try {
            BufferedWriter bw=new BufferedWriter(new FileWriter(file1,true));
            for(int i=0;i<n;i++) {
                bw.write("5");
                bw.newLine();
            }
            bw.flush();
            bw.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}

