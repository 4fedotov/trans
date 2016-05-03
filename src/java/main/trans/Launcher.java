package trans;

import trans.format.RuEnOriginalFormat;

import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.HashMap;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Launcher {
    public static void main(String[] args) throws Exception {

        TransTriePair loadResult = TransLoader.load(args[0], new RuEnOriginalFormat());
    	TransTrie trie = loadResult.getTrie();
    	TransTrie invertedTrie = loadResult.getInvertedTrie();
    	
        NamesStatistic ns = new NamesStatistic(args[1]);
    	
    	Transliterator ru2en = new Transliterator(trie);
    	Transliterator en2ru = new Transliterator(invertedTrie);
    	
        NameFreqChecker nfc = new NameFreqChecker(ns);

        File file = new File(args[2]);
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        StringBuffer stringBuffer = new StringBuffer();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            System.out.println(line);
            HashSet<String> result = null;
            if (LangDetector.detect(line) == 1) {
                result = ru2en.process(line);
            } else {
                result = en2ru.process(line);
            }
            TreeSet<NameFreq> resultFromStat = nfc.check(result);
            if (!resultFromStat.isEmpty()) {
                for (NameFreq nf: resultFromStat) {
                    System.out.println("\t" + nf.getName() + "\t" + Integer.toString(nf.getFreq()));
                }    
            } else {
                for (String s: result) {
                    System.out.println("\t" + s);
                } 
            }
        }
        fileReader.close();
    }
}