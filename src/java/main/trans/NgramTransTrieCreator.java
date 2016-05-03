package trans;

import java.util.HashMap;
import java.util.HashSet;

public class NgramTransTrieCreator {
	
	public static TransTriePair create(TransTriePair srcTries, LetterNGramStat stat, int n) {
		Transliterator ru2en = new Transliterator(srcTries.getTrie());
    	Transliterator en2ru = new Transliterator(srcTries.getInvertedTrie());

    	HashMap<String, Long> ngramStat = stat.getNGrams(n);

    	TransTrie transTrie = new TransTrie();
		TransTrie invertedTransTrie = new TransTrie();

		for (String ngram: ngramStat.keySet()) {
			int lang = LangDetector.detect(ngram);
			HashSet<String> res = null;
			if (1 == lang) {
				res = ru2en.process(ngram);
			} else if (0 == lang) {
				res = en2ru.process(ngram);
			}
			else {
				continue;
			}
			for (String s: res) {
				long freq = stat.check(s);
				if (freq > 0) {
					if (1 == lang) {
						transTrie.addRule(ngram, s);	
						invertedTransTrie.addRule(s, ngram); 
					} else {
						invertedTransTrie.addRule(ngram, s);
						transTrie.addRule(s, ngram); 
					}
					
				}
			}
		}

		return new TransTriePair(transTrie, invertedTransTrie);
	}
}