/*
 * Copyright (c) 2017 Schiphol Group
 */
package nl.schiphol.jaxb;

import com.sun.xml.bind.marshaller.NamespacePrefixMapper;

public class NoNamespacePrefixMapper extends NamespacePrefixMapper {

  private static final NoNamespacePrefixMapper instance = new NoNamespacePrefixMapper();

  private NoNamespacePrefixMapper() {}

  public String getPreferredPrefix(String uri, String suggest, boolean require) {
    return "";
  }

  @Override
  public String[] getPreDeclaredNamespaceUris() {
    return new String[0];
  }

  public static NoNamespacePrefixMapper noNamespacePrefixMapper() {
    return instance;
  }
}
