package nl.schiphol.jaxb.adapters;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.xml.bind.annotation.adapters.XmlAdapter;

public class LocalDateAdapter extends XmlAdapter<String, LocalDate> {
  @Override
  public String marshal(LocalDate ldate) {
    if (ldate == null) {
      return null;
    }
    return DateTimeFormatter.ofPattern("yyyy-MM-dd").format(ldate);
  }

  @Override
  public LocalDate unmarshal(String ldate) {
    if (ldate == null) {
      return null;
    }
    return LocalDate.parse(ldate);
  }
}
