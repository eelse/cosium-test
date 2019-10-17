package nl.schiphol.jaxb;

import com.sun.xml.bind.marshaller.NamespacePrefixMapper;
import java.util.HashMap;
import java.util.Map;

public class ConfigurableNamespacePrefixMapper extends NamespacePrefixMapper {

  private Map<String, String> mapping =
      new HashMap<String, String>() {
        private static final long serialVersionUID = 1L;

        public String get(Object key) {
          String val = super.get(key);
          return val == null ? "" : val;
        }
      };

  public ConfigurableNamespacePrefixMapper() {}

  protected ConfigurableNamespacePrefixMapper(Map<String, String> additionalMapping) {
    this.mapping.putAll(additionalMapping);
  }

  @Override
  public String getPreferredPrefix(String namespaceUri, String suggestion, boolean requirePrefix) {
    return mapping.get(namespaceUri);
  }
}
