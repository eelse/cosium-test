package nl.schiphol.jaxb.adapters;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import javax.xml.bind.annotation.adapters.XmlAdapter;

public class LocalTimeAdapter extends XmlAdapter<String, LocalTime> {
  @Override
  public String marshal(LocalTime time) {
    if (time == null) {
      return null;
    }
    DateTimeFormatter formatter;
    if (time.getNano() > 0) {
      formatter = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");
    } else {
      formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    }
    return formatter.format(time);
  }

  @Override
  public LocalTime unmarshal(String time) {
    if (time == null) {
      return null;
    }
    return LocalTime.parse(time);
  }
}
