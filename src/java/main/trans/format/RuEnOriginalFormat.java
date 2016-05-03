package trans.format;

import trans.TransRule;

public class RuEnOriginalFormat implements TransFileFormat {

	public TransRule parse(String line) throws Exception {

		String[] parts = line.split("=");
		if (parts.length != 2) {
			throw new Exception(ErrorMsg);
		}

		return new TransRule(parts[0], parts[1].split(","));
	} 

	private static final String ErrorMsg = "Broken file format";

}