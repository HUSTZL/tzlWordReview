import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;

public class SimpleFrame extends JFrame {
    private JLabel wordLabel = new JLabel();
    private JLabel frequencyLabel = new JLabel();
    private MeaningLabel meaningLabel;
    private ArrayList<String> wordList = new ArrayList<>();       //初始读入
    private Set<Integer> wordsOrderSet = new LinkedHashSet<>();         //随机顺序
    private ArrayList<String> wordRandomList = new ArrayList<>();        //按随机顺序存放的单词
    private ArrayList<Integer> frequencyList = new ArrayList<>();       //频率
    private Map<String,Integer> wordFrequencyMap = new HashMap<>();      //Key:按随机顺序存放的单词  Value:对应单词的频率
    int count = -1;

    public SimpleFrame(String name){
        super(name);
        this.setSize(400,300);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        setLocation((screenSize.width-getWidth())/2,(screenSize.height-getHeight())/2);

        JPanel panel=new JPanel();
        this.setContentPane(panel);
        panel.setLayout(new SimpleLayout());

        JButton startButton = new JButton("开始");
        JButton rememberButton = new JButton("认识");
        JButton uncertainButton = new JButton("不确定");
        JButton forgetButton = new JButton("不认识");
        JButton preButton = new JButton("上一个");
        JButton knowButton = new JButton("知道了");

        wordLabel.setFont(new Font("Arial",Font.BOLD,15));
        wordLabel.setHorizontalAlignment(SwingConstants.CENTER);

        panel.add(startButton,new MyDimension(150,60,100,30));
        wordsReadIn();
        getWordsOrder();

        for (Integer i : wordsOrderSet) {
            wordRandomList.add(wordList.get(i));
            wordFrequencyMap.put(wordList.get(i), frequencyList.get(i));
        }


        startButton.addActionListener((e)->{
            startButton.setVisible(false);
            wordLabel.setText(wordRandomList.get(++count));
            frequencyLabel.setText(wordFrequencyMap.get(wordLabel.getText()).toString());
            panel.add(wordLabel, new MyDimension(150,60,100,30));
            panel.add(rememberButton, new MyDimension(150,100,100,30));
            panel.add(uncertainButton, new MyDimension(150,140,100,30));
            panel.add(forgetButton, new MyDimension(150,180,100,30));
            panel.add(preButton, new MyDimension(0,0,100,30));
            panel.add(frequencyLabel, new MyDimension(150,250,100,30));
        });

        rememberButton.addActionListener((e)->{
            if(count < wordRandomList.size()-1){
                String s = wordLabel.getText();
                wordFrequencyMap.put(s, wordFrequencyMap.get(s)-1);
                nextWord();
            }
            if(count == wordRandomList.size()-1){
                String s1 = wordLabel.getText();
                wordFrequencyMap.put(s1, wordFrequencyMap.get(s1)-1);
                frequencyRewrite();
            }

        });

        uncertainButton.addActionListener((e)->{
            if(count < wordRandomList.size()-1){
                nextWord();
            }
            if(count == wordRandomList.size()-1){
                frequencyRewrite();
            }
        });

        forgetButton.addActionListener((e)->{
            if(count < wordRandomList.size()-1){
                String s = wordLabel.getText();
                wordFrequencyMap.put(s, wordFrequencyMap.get(s)+1);
                meaningLabel = new MeaningLabel(s);
                rememberButton.setVisible(false);
                uncertainButton.setVisible(false);
                forgetButton.setVisible(false);
                preButton.setVisible(false);
                knowButton.setVisible(true);
                panel.add(meaningLabel, new MyDimension(100,100,200,90));
                panel.add(knowButton, new MyDimension(150,200,100,30));
                meaningLabel.setBounds(100,100,200,60);
            }
            if(count == wordRandomList.size()-1){
                String s1 = wordLabel.getText();
                wordFrequencyMap.put(s1, wordFrequencyMap.get(s1)+1);
                frequencyRewrite();
            }
        });

        knowButton.addActionListener((e)->{
            nextWord();
            meaningLabel.setVisible(false);
            knowButton.setVisible(false);
            rememberButton.setVisible(true);
            uncertainButton.setVisible(true);
            forgetButton.setVisible(true);
            preButton.setVisible(true);
        });

        preButton.addActionListener((e)->{
            if(count>0){
                if(count == wordRandomList.size())
                    count--;
                wordLabel.setText(wordRandomList.get(--count));
                frequencyLabel.setText(wordFrequencyMap.get(wordLabel.getText()).toString());
            }
        });
    }

    public void nextWord() {
        wordLabel.setText(wordRandomList.get(++count));
        frequencyLabel.setText(wordFrequencyMap.get(wordLabel.getText()).toString());
    }

    public void wordsReadIn(){
        File file_word = new File("txt/wordsreview.txt");
        File file_frequency = new File("txt/wordsFrequency.txt");
        try{
            BufferedReader br_word = new BufferedReader(new FileReader(file_word));
            BufferedReader br_frequency = new BufferedReader(new FileReader(file_frequency));
            String str_word;
            while((str_word = br_word.readLine()) != null){
                wordList.add(str_word);
            }
            String str_frequency;
            while ((str_frequency = br_frequency.readLine()) != null && !str_frequency.equals("")){
                frequencyList.add(Integer.parseInt(str_frequency));
            }
            br_word.close();
            br_frequency.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void getWordsOrder(){
        Random random = new Random();
        while(wordsOrderSet.size()<wordList.size()){
            wordsOrderSet.add(random.nextInt(wordList.size()));
        }
    }

    public void frequencyRewrite(){
        count++;
        frequencyRewriter();
    }

    //在所有单词复习结束后将频率重写入文件
    public void frequencyRewriter(){
        File file = new File("txt/wordsFrequency.txt");
        try{
            BufferedWriter bw=new BufferedWriter(new FileWriter(file));
            for(String i:wordList){
                //System.out.println(map.get(i));
                bw.write(wordFrequencyMap.get(i).toString());
                bw.newLine();
            }
            bw.flush();
            bw.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public JLabel getWordLabel() {
        return wordLabel;
    }

    public void setWordLabel(JLabel wordLabel) {
        this.wordLabel = wordLabel;
    }

    public JLabel getFrequencyLabel() {
        return frequencyLabel;
    }

    public void setFrequencyLabel(JLabel frequencyLabel) {
        this.frequencyLabel = frequencyLabel;
    }

    public MeaningLabel getMeaningLabel() {
        return meaningLabel;
    }

    public void setMeaningLabel(MeaningLabel meaningLabel) {
        this.meaningLabel = meaningLabel;
    }

    public ArrayList<String> getWordList() {
        return wordList;
    }

    public void setWordList(ArrayList<String> wordList) {
        this.wordList = wordList;
    }

    public Set<Integer> getWordsOrderSet() {
        return wordsOrderSet;
    }

    public void setWordsOrderSet(Set<Integer> wordsOrderSet) {
        this.wordsOrderSet = wordsOrderSet;
    }

    public ArrayList<String> getWordRandomList() {
        return wordRandomList;
    }

    public void setWordRandomList(ArrayList<String> wordRandomList) {
        this.wordRandomList = wordRandomList;
    }

    public ArrayList<Integer> getFrequencyList() {
        return frequencyList;
    }

    public void setFrequencyList(ArrayList<Integer> frequencyList) {
        this.frequencyList = frequencyList;
    }

    public Map<String, Integer> getWordFrequencyMap() {
        return wordFrequencyMap;
    }

    public void setWordFrequencyMap(Map<String, Integer> wordFrequencyMap) {
        this.wordFrequencyMap = wordFrequencyMap;
    }
}
