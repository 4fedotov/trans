package trans;

public class TransRule {

	public TransRule(String src, String[] dest) {
		m_src = src;
		m_dest = dest;
	}

	public String getSrc() {
		return m_src;
	}

	public String[] getDest() {
		return m_dest;
	}

	private final String m_src;
	private final String[] m_dest;
}