/*
 * Copyright (c) 2015 Schiphol Group
 */
package nl.schiphol.jaxb.adapter;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/** Adapter class for marshalling and unmarshalling Long values. */
public class LongAdapter extends XmlAdapter<String, Long> {
  /**
   * Unmarshals the value to a Long.
   *
   * @param value The value to unmarshal.
   */
  public Long unmarshal(String value) {
    return new Long(value);
  }

  /**
   * Marshals the value to a String.
   *
   * @param value The value to marshal.
   */
  public String marshal(Long value) {
    if (value != null) {
      return value.toString();
    }
    return null;
  }
}
