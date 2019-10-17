package nl.schiphol.jaxb.util;

import java.io.IOException;
import java.util.Properties;

public class PropertiesUtil {
  public static Properties fromFile(String fileName) throws IOException {
    Properties properties = new Properties();
    properties.load(PropertiesUtil.class.getClassLoader().getResourceAsStream(fileName));
    return properties;
  }
}
