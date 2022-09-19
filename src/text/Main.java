package text;

import com.comparatorText.ComparatorText;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        String textA = getTextFromURL(args[0]);
        String textB = getTextFromURL(args[1]);
        System.out.println("相似度="+ ComparatorText.getCosineSimilarity(textA,textB));
    }
    public static String getTextFromURL(String URL) throws IOException {
        String text="";
        BufferedReader brin=new BufferedReader(new FileReader(URL));
        String temp;
        int flag=0;
        while ((temp=brin.readLine())!=null){
            if(flag!=0){
                text+="\n";
                text+=temp;
            }else {
                text+=temp;
                flag=1;
            }
        }
        return text;
    }
}
