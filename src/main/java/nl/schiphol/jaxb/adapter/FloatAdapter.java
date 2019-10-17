/*
 * Copyright (c) 2016 Schiphol Group
 */
package nl.schiphol.jaxb.adapter;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/** Adapter class for marshalling and unmarshalling Float values. */
public class FloatAdapter extends XmlAdapter<String, Float> {
  /**
   * Unmarshals the value to a Float.
   *
   * @param value The value to unmarshal.
   */
  public Float unmarshal(String value) {
    return new Float(value);
  }

  /**
   * Marshals the value to a String.
   *
   * @param value The value to marshal.
   */
  public String marshal(Float value) {
    if (value != null) {
      return value.toString();
    }
    return null;
  }
}
