package trans;

import java.util.TreeSet;
import java.util.HashSet;

public class NameFreqChecker {
	public NameFreqChecker(NamesStatistic ns) {
		m_namesStat = ns;
	}

	public TreeSet<NameFreq> check(HashSet<String> names) {

		TreeSet<NameFreq> result = new TreeSet<NameFreq>(new FreqComparator());

		for (String name: names) {
			int freq = m_namesStat.check(name);
			if (freq > 0) {
				result.add(new NameFreq(name, freq));	
			}
		}

		return result;
	}

	private NamesStatistic m_namesStat;
}