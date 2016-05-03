package trans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Collections;

import java.io.File;
import java.io.IOException;
import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.charset.Charset;

public class LetterNGramStat {

	public LetterNGramStat(String file, int n) {
		calcStat(file, n);
	}

	public void calcStat(String file, int n) {
		// assume tsv file

		m_stat = new ArrayList<HashMap<String, Long>>(n);
		for (int i = 0; i < n; ++i) {
			m_stat.add(new HashMap<String, Long>());
		}
	
		try (BufferedReader reader = Files.newBufferedReader(Paths.get(file), Charset.forName("UTF-8"))) {
		    String line = null;
		    while ((line = reader.readLine()) != null) {
		    	String[] parts = line.split("\t");
		    	if (parts.length != 2) {
		    		throw new Exception("Broken names statistic format");
		    	}
		    	String name = parts[0].trim();
		    	if (LangDetector.detect(name) == -1) {
		    		continue;
		    	}
		    	int freq = Integer.parseInt(parts[1]);
		    	if (freq < 2) {
		    		continue; //?? avoid low freq words (tail with trash)
		    	}
		    	
		    	for (int i = 1; i <= n; ++i) {
		    		int j = 0;
		    		while (j + i <= name.length()) {
		    			String ngram = name.substring(j, j + i);
		    			Long oldFreq = m_stat.get(i - 1).get(ngram);
		    			m_stat.get(i - 1).put(ngram, (oldFreq == null) ? freq : oldFreq + freq);
		    			++j;	
		    		} 
		    	}
		    }
		} catch (IOException exc) {
		    System.err.format("IOException: %s%n", exc);
		}
		catch (Exception exc) {
			System.err.println(exc);
		}
	}

	public long check(String ngram) {
		
		// todo fixme strange isEmpty check
		if (ngram.isEmpty() || ngram.length() > m_stat.size()) {
			return 0;
		}
		Long res = m_stat.get(ngram.length() - 1).get(ngram);
		return ((res == null) ? 0 : res);
	}

	public HashMap<String, Long> getNGrams(int n) {
		return m_stat.get(n - 1);
	}

	private ArrayList<HashMap<String, Long>> m_stat;

}