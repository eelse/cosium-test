/*
 * Copyright (c) 2016 Schiphol Group
 */
package nl.schiphol.jaxb.adapter;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/** Adapter class for marshalling and unmarshalling Double values. */
public class DoubleAdapter extends XmlAdapter<String, Double> {
  /**
   * Unmarshals the value to a Double.
   *
   * @param value The value to unmarshal.
   */
  public Double unmarshal(String value) {
    return new Double(value);
  }

  /**
   * Marshals the value to a String.
   *
   * @param value The value to marshal.
   */
  public String marshal(Double value) {
    if (value != null) {
      return value.toString();
    }
    return null;
  }
}
