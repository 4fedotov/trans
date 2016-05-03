package trans;

import trans.format.*;

import java.io.File;
import java.io.IOException;
import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.charset.Charset;

public class TransLoader {

	public static TransTriePair load(String file, TransFileFormat ff) {

		TransTrie transTrie = new TransTrie();
		TransTrie invertedTransTrie = new TransTrie();

		try (BufferedReader reader = Files.newBufferedReader(Paths.get(file), Charset.forName("UTF-8"))) {
		    String line = null;
		    while ((line = reader.readLine()) != null) {
		    	TransRule tr = ff.parse(line);
		    	transTrie.addRule(tr.getSrc(), tr.getDest());
		    	for (String dest: tr.getDest()) {
		    		invertedTransTrie.addRule(dest, tr.getSrc());
		    	}
		    }
		} catch (IOException exc) {
		    System.err.format("IOException: %s%n", exc);
		}
		catch (Exception exc) {
			System.err.println(exc);
		}

		return new TransTriePair(transTrie, invertedTransTrie);
	}

}