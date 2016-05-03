package trans;

import java.util.HashMap;
import java.util.HashSet;

//														[0]
//														 |
//					---------------------------------------------------------------------
//					|               |                |               
//				   [s*]            [t*]             [u*]
//				    |               
//		    -----------------
//			|		|       |
//		   [c*]	   [s*]    [t]
//		   					|
//		   				   [s]
//							|
//						   [c]
//							|
//						   [h*]
//
//	* means this sequence has interpretations
//	i.e. "s" can be interpreted as {[с, з]}
//	     "stsch" can be interpreted as {щ}
//	     "st" doesn't have any interpretation	   				   

public class TransTrie {

	public boolean addRule(String src, HashSet<String> interpretations) {

		if(src.isEmpty() || interpretations.isEmpty()) {
			return false;
		}

		boolean res = m_root.insert(src, 0, interpretations);
		if (res) {
			++m_size;
		}
		return res;
	}

	public boolean addRule(String src, String ... interpretations) {
		HashSet<String> hs = new HashSet<String>();
		for (String s: interpretations) {
			hs.add(s);
		}
		return addRule(src, hs);
	}
/*
	public boolean addRule(String src, String interpretation) {
		
		if(interpretation.isEmpty()) {
			return false;
		}

		return addRule(src, new HashSet<String>().put(interpretation));
   	}
*/
   	public HashMap<Integer, HashSet<String>> lookup(String word, int index) {

   		HashMap<Integer, HashSet<String>> result = new HashMap<Integer, HashSet<String>>();
   		m_root.lookup(word, index, result);

   		return result;
   	}

   	public int size() {
   		return m_size;
   	}

   	public void print() {
   		HashMap<Character, TransTrieNode> rootChildren = m_root.getChildren();
   		for (Character c: rootChildren.keySet()) {
   			System.out.print(Character.toString(c) + " ");
   		}
   		System.out.println();
   	}

   	private int m_size;

	private TransTrieNode m_root = new TransTrieNode((char)0, 0);
}

class TransTrieNode {

	public TransTrieNode(char letter, int depth) {
		m_letter = letter;
		m_depth = depth;
	}

	/**
 	* insert rule recursively in trie
 	* @return false if rule is already presented in trie
 	*/
	public boolean insert(String src, int index, HashSet<String> interpretations) {
		
		assert(index < src.length());

		if (null == m_children) {
			m_children = new HashMap<Character, TransTrieNode>();
		}

		char letter = src.charAt(index);

		TransTrieNode node = m_children.get(letter);

		if (null == node) {
			node = new TransTrieNode(letter, m_depth + 1);	
			m_children.put(letter, node);
		}

		if (index == src.length() - 1) {
			/*if (m_children.get(letter).hasInterpretations()) {
				return false;
			} else {
				m_children.get(letter).setInterpretations(interpretations);
				return true;	
			}*/
			m_children.get(letter).addInterpretations(interpretations);
			return true;
		} else {
			return node.insert(src, index + 1, interpretations);
		}
	}

	public void lookup(String word, int index, HashMap<Integer, HashSet<String>> result) {
		assert(index < word.length());

		if (isLeaf()) {
			return;
		}

		TransTrieNode node = m_children.get(word.charAt(index));
		if (null == node) {
			return;
		}

		if (node.hasInterpretations()) {
			result.put(node.depth(), node.getInterpretations());
		}

		if (index < word.length() - 1) {
			node.lookup(word, index + 1, result);
		}
	}

	public char letter() {
		return m_letter;
	}

	public int depth() {
		return m_depth;
	}

	public HashMap<Character, TransTrieNode> getChildren() {
		return m_children;
	}

	public void setInterpretations(HashSet<String> interpretations) {
		m_interpretations = interpretations;
	}

	public void addInterpretations(HashSet<String> interpretations) {
		if (null == m_interpretations) {
			m_interpretations = interpretations;
		} else {
			m_interpretations.addAll(interpretations);
		}
	}

	public HashSet<String> getInterpretations() {
		return m_interpretations;
	}

	public boolean hasInterpretations() {
		return (null != m_interpretations && !m_interpretations.isEmpty());
	}

	public boolean isAuxiliary() {
		return (!hasInterpretations());
	}

	public boolean isLeaf() {
		return (null == m_children || m_children.isEmpty());
	}

	// todo
	public String toString() {
		return "";
	}

	private char m_letter;
	private int m_depth;

	private HashSet<String> m_interpretations;

	private HashMap<Character, TransTrieNode> m_children;
}