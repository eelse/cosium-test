/*
 * Copyright (c) 2016 Schiphol Group
 */
package nl.schiphol.jaxb.tamino;

import com.sun.xml.bind.marshaller.NamespacePrefixMapper;
import java.util.*;
import nl.schiphol.jaxb.ConfigurableNamespacePrefixMapper;

/**
 * This class makes sure that the namespace of tamino wil get the proper ino namespaceprefix,
 * instead of some random ns1. This should be done because Tamino checks for this. Otherwise with
 * e.g. an update you will get the message 'cannot do a update without 'ino:id'.
 *
 * <p>use {@link ConfigurableNamespacePrefixMapper} instead
 */
@Deprecated
public class TaminoNamespacePrefixMapper extends NamespacePrefixMapper {

  public static final String TAMINO_NAMESPACE = "http://namespaces.softwareag.com/tamino/response2";
  public static final String TAMINO_NAMESPACE_PREFIX = "ino";

  private Map<String, String> mapping =
      new HashMap<String, String>() {
        private static final long serialVersionUID = 1L;

        public String get(Object key) {
          String val = super.get(key);
          return val == null ? "" : val;
        }

        {
          put(TAMINO_NAMESPACE, TAMINO_NAMESPACE_PREFIX);
        }
      };

  public TaminoNamespacePrefixMapper() {}

  protected TaminoNamespacePrefixMapper(Map<String, String> additionalMapping) {
    this.mapping.putAll(additionalMapping);
  }

  @Override
  public String getPreferredPrefix(String namespaceUri, String suggestion, boolean requirePrefix) {
    return mapping.get(namespaceUri);
  }
}
