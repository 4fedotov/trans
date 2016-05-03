package trans;

import java.util.HashSet;
import java.util.HashMap;
import java.util.Map;

public class Transliterator {

	public Transliterator(TransTrie tt) {
		m_transTrie = tt;
	}

	public HashSet<String> process(String word) {
		HashSet<String> result = new HashSet<String>();
		processReq(word.toLowerCase(), 0, new String(), result);
		return result;
	}

	private void processReq(String word, int index, String curr, HashSet<String> result) {
		if (index == word.length()) {
			result.add(curr);	
			// ? check by rules
			return;
		}

		HashMap<Integer, HashSet<String>> options = m_transTrie.lookup(word, index);
		for (Map.Entry<Integer, HashSet<String>> entry: options.entrySet()) {
			int len = entry.getKey();
			assert(index + len <= word.length());
			HashSet<String> optionsForLen = entry.getValue();

			for (String option: optionsForLen) {
				processReq(word, index + len, curr + option, result);	
			}
		}
	}

	TransTrie m_transTrie;
}