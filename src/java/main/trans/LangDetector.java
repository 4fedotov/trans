package trans;

public class LangDetector {

	public static int detect(String word) {
		if (null == word || word.isEmpty()) {
			return -1;
		}
		String regexEn = "[a-zA-z']+";
		String regexRu = "[а-яА-ЯёЁ]+";

		char letter = word.toLowerCase().charAt(0);
		if (word.matches(regexEn)) {
			return 0;
		}
		if (word.matches(regexRu)) {
			return 1;
		}
		return -1;
	}
}