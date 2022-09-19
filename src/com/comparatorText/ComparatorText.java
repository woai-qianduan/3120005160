package com.comparatorText;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.corpus.tag.Nature;
import com.hankcs.hanlp.seg.common.Term;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComparatorText {
    // 两两对比函数
    public static Double getCosineSimilarity(String textA,String textB){
        // 从文本中提取出关键词数组
        List<String> wordListA = ComparatorText.extractWordFromText(textA);
        List<String> wordListB = ComparatorText.extractWordFromText(textB);

        List<Double> vectorA = new ArrayList<>();
        List<Double> vectorB = new ArrayList<>();
        // 将关键词数组转换为词向量并保存在 vectorA 和 vectorB 中
        ComparatorText.convertWordList2Vector(wordListA,wordListB,vectorA,vectorB);

        // 计算向量夹角的余弦值
        double cosine = Double.parseDouble(String.format("%.4f",ComparatorText.countCosine(vectorA,vectorB)));
        return cosine;
    }

    // 提取文本中有实意的词
    private static List<String> extractWordFromText(String text){
        // resultList 用于保存提取后的结果
        List<String> resultList = new ArrayList<>();

        // 当 text 为空字符串时，使用分词函数会报错，所以需要提前处理这种情况
        if(text.length() == 0){
            return resultList;
        }

        // 分词
        List<Term> termList = HanLP.segment(text);
        // 提取所有的 1.名词/n ; 2.动词/v ; 3.形容词/a
        for (Term term : termList) {
            if(term.nature == Nature.n || term.nature == Nature.v || term.nature == Nature.a
                    || term.nature == Nature.vn){
                resultList.add(term.word);
            }
        }

        return resultList;
    }

    private static List<String> convertWordList2Vector(List<String> wordListA,List<String> wordListB,List<Double> vectorA,List<Double> vectorB){
        // 词汇表
        List<String> vocabulary = new ArrayList<>();

        // 获取词汇表 wordListA 的频率表，并同时建立词汇表
        Map<String,Double> frequencyTableA = buildFrequencyTable(wordListA, vocabulary);

        // 获取词汇表 wordListB 的频率表，并同时建立词汇表
        Map<String,Double> frequencyTableB = buildFrequencyTable(wordListB, vocabulary);

        // 根据频率表得到向量
        getWordVectorFromFrequencyTable(frequencyTableA,vectorA,vocabulary);
        getWordVectorFromFrequencyTable(frequencyTableB,vectorB,vocabulary);

        return vocabulary;
    }

    private static Map<String,Double> buildFrequencyTable(List<String> wordList,List<String> vocabulary){
        // 先建立频数表
        Map<String,Integer> countTable = new HashMap<>();
        for (String word : wordList) {
            if(countTable.containsKey(word)){
                countTable.put(word,countTable.get(word)+1);
            }
            else{
                countTable.put(word,1);
            }
            // 词汇表中是无重复元素的，所以只在 vocabulary 中没有该元素时才加入
            if(!vocabulary.contains(word)){
                vocabulary.add(word);
            }
        }
        // totalCount 用于记录词出现的总次数
        int totalCount = wordList.size();
        // 将频数表转换为频率表
        Map<String,Double> frequencyTable = new HashMap<>();
        for (String key : countTable.keySet()) {
            frequencyTable.put(key,(double)countTable.get(key)/totalCount);
        }
        return frequencyTable;
    }

    private static void getWordVectorFromFrequencyTable(Map<String,Double> frequencyTable,List<Double> wordVector,List<String> vocabulary){
        for (String word : vocabulary) {
            double value = 0.0;
            if(frequencyTable.containsKey(word)){
                value = frequencyTable.get(word);
            }
            wordVector.add(value);
        }
    }

    private static double countCosine(List<Double> vectorA,List<Double> vectorB){
        // 分别计算向量的平方和
        double sqrtA = countSquareSum(vectorA);
        double sqrtB = countSquareSum(vectorB);

        // 计算向量的点积
        double dotProductResult = 0.0;
        for(int i = 0;i < vectorA.size();i++){
            dotProductResult += vectorA.get(i) * vectorB.get(i);
        }

        return dotProductResult/(sqrtA*sqrtB);
    }

    // 计算向量平方和的开方
    private static double countSquareSum(List<Double> vector){
        double result = 0.0;
        for (Double value : vector) {
            result += value*value;
        }
        return Math.sqrt(result);
    }

}
