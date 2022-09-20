package text;

import com.comparatorText.ComparatorText;

import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        String textA = getTextFromURL(args[0]);
        String textB = getTextFromURL(args[1]);
        String result="相似度="+ ComparatorText.getCosineSimilarity(textA,textB);
        setResultText(args[2],result);
        System.out.println(result);
    }
    public static String getTextFromURL(String URL) throws IOException {
        StringBuilder text= new StringBuilder();
        try {
            BufferedReader brin = new BufferedReader(new FileReader(URL));
            String temp;
            int flag=0;
            while ((temp=brin.readLine())!=null){
                if(flag!=0){
                    text.append("\n");
                    text.append(temp);
                }else {
                    text.append(temp);
                    flag=1;
                }
            }
            brin.close();
        } catch (FileNotFoundException e) {
            System.out.println("地址有误");
        }
        return text.toString();
    }
    public static void setResultText(String URL,String text) throws IOException {
        try{
            BufferedWriter bout=new BufferedWriter(new FileWriter(URL));
            bout.write(text);
            bout.close();
        } catch (FileNotFoundException e){
            System.out.println("地址有误");
        }
    }
}
