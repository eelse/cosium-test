/*
 * Copyright (c) 2014 Schiphol Group
 */
package nl.schiphol.jaxb.adapter;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import nl.schiphol.jaxb.util.ConverterUtils;
import org.joda.time.LocalDate;

public class DateAdapter extends XmlAdapter<String, LocalDate> {
  @Override
  public LocalDate unmarshal(String dateStr) {
    return ConverterUtils.toDate(dateStr);
  }

  @Override
  public String marshal(LocalDate date) {
    return ConverterUtils.fromDate(date);
  }
}
