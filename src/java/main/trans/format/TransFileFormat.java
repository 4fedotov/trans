package trans.format;

import trans.TransRule;

public interface TransFileFormat {

	public TransRule parse(String line) throws Exception;

}