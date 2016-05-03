package trans;

public class NameFreq {

	public NameFreq(String name, int freq) {
		m_name = name;
		m_freq = freq;
	}

	public String getName() {
		return m_name;
	}

	public int getFreq() {
		return m_freq;
	}

	private final String m_name;
	private final int m_freq;
}