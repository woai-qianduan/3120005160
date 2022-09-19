package text;

import com.comparatorText.ComparatorText;

public class text {
    public static void main(String[] args) {
        String text1="我是你爸爸,你是儿子";
        String text2="我是你爸爸，儿子是你";
        System.out.println("相似度："+ComparatorText.getCosineSimilarity(text1,text2));
    }
}
