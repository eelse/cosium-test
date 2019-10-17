/*
 * Copyright (c) 2014 Schiphol Group
 */
package nl.schiphol.jaxb.adapter;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import nl.schiphol.jaxb.util.ConverterUtils;
import org.joda.time.LocalTime;

public class TimeAdapter extends XmlAdapter<String, LocalTime> {
  @Override
  public LocalTime unmarshal(String timeStr) {
    return ConverterUtils.toTime(timeStr);
  }

  @Override
  public String marshal(LocalTime time) {
    return ConverterUtils.fromTime(time);
  }
}
