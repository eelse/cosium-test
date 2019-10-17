/*
 * Copyright (c) 2015 Schiphol Group
 */
package nl.schiphol.jaxb.adapter;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/** Adapter class for marshalling and unmarshalling Integer values. */
public class IntegerAdapter extends XmlAdapter<String, Integer> {
  /**
   * Unmarshals the value to a Integer.
   *
   * @param value The value to unmarshal.
   */
  public Integer unmarshal(String value) {
    return new Integer(value);
  }

  /**
   * Marshals the value to a String.
   *
   * @param value The value to marshal.
   */
  public String marshal(Integer value) {
    if (value != null) {
      return value.toString();
    }
    return null;
  }
}
