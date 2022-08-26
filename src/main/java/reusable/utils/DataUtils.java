package reusable.utils;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DataUtils {
	private static Logger logger = LogManager.getLogger(DataUtils.class);

	public static String readPropertiesFile(String filename, String key) throws IOException {

		Properties p = new Properties();
		FileReader prop = new FileReader(System.getProperty("user.dir")+ "/src/main/java/testData/"+filename+".properties");
		p.load(prop);
		String value= p.getProperty(key);
		return value;
	}
}
