package trans;

import java.util.HashMap;

import java.io.File;
import java.io.IOException;
import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.charset.Charset;

public class NamesStatistic {

	public NamesStatistic(String file) {
		load(file);
	}

	public void load(String file) {
		// assume tsv file

		try (BufferedReader reader = Files.newBufferedReader(Paths.get(file), Charset.forName("UTF-8"))) {
		    String line = null;
		    while ((line = reader.readLine()) != null) {
		    	String[] parts = line.split("\t");
		    	if (parts.length != 2) {
		    		throw new Exception("Broken names statistic format");
		    	}
		    	m_stat.put(parts[0], Integer.parseInt(parts[1]));
		    }
		} catch (IOException exc) {
		    System.err.format("IOException: %s%n", exc);
		}
		catch (Exception exc) {
			System.err.println(exc);
		}
	}

	public int check(String name) {
		return (m_stat.containsKey(name)? m_stat.get(name) : 0);
	}

	private HashMap<String, Integer> m_stat = new HashMap<String, Integer>();
}