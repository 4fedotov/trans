package trans;

public class TransTriePair{

	public TransTriePair(TransTrie trie, TransTrie invertedTrie) {
		m_trie = trie;
		m_invertedTrie = invertedTrie;
	}

	public TransTrie getTrie() {
		return m_trie;
	}

	public TransTrie getInvertedTrie() {
		return m_invertedTrie;
	}

	private final TransTrie m_trie;
	private final TransTrie m_invertedTrie;
}