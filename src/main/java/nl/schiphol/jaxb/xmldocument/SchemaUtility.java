package nl.schiphol.jaxb.xmldocument;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.xml.XMLConstants;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import nl.schiphol.jaxb.util.FileSearch;
import nl.schiphol.jaxb.util.PropertiesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * read from jaxb.properties <br>
 * validate.jaxb: true or false<br>
 * schema.path: the path where the schema are stored<br>
 */
public class SchemaUtility {

  private static final Logger LOG = LoggerFactory.getLogger(SchemaUtility.class);
  private static final Map<String, Schema> schemas = new HashMap<>();
  private static String rootPath;
  private static boolean validateJaxb;

  static {
    rootPath = "schemas";
    validateJaxb = false;
    try {
      Properties properties = PropertiesUtil.fromFile("jaxb.properties");
      validateJaxb = Boolean.valueOf(properties.getProperty("validate.jaxb"));
      String temp = properties.getProperty("schema.path");
      if (temp != null) {
        rootPath = temp;
      }
    } catch (Exception e) {
      LOG.warn(
          "Can not read validate.jaxb setting from config because of: {}. Ignore the jaxb validation.",
          e);
    }
  }

  public static Schema getSchema(String simpleName) {
    if (!validateJaxb) {
      return null;
    }
    String fileName = String.format("%s.xsd", simpleName);
    Schema schema = schemas.get(fileName);
    if (!schemas.containsKey(fileName)) {
      FileSearch fileSearch = new FileSearch();
      String path = SchemaUtility.class.getClassLoader().getResource(rootPath).getPath();
      fileSearch.searchDirectory(new File(path), fileName);
      if (fileSearch.getResult().size() > 0) {
        try {
          File file = new File(fileSearch.getResult().get(0));
          SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
          schema = sf.newSchema(file);
        } catch (Exception e) {
          LOG.warn("Can not generate schema {} because of {}.", fileName, e);
        }
      } else {
        LOG.warn("Can not read {} from classpath.", fileName);
      }
      // if schema is not found, put {fileName, null} into map to avoid unnecessary file search.
      schemas.put(fileName, schema);
    }
    return schema;
  }
}
