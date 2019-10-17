/*
 * Copyright (c) 2014 Schiphol Group
 */
package nl.schiphol.jaxb.adapter;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import nl.schiphol.jaxb.util.ConverterUtils;
import org.joda.time.DateTime;

public class DateTimeAdapter extends XmlAdapter<String, DateTime> {

  @Override
  public DateTime unmarshal(String dateTimeStr) {
    return ConverterUtils.toDateTime(dateTimeStr);
  }

  @Override
  public String marshal(DateTime dateTime) {
    return ConverterUtils.fromDateTime(dateTime);
  }
}
