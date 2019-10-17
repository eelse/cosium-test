package nl.schiphol.jaxb.adapters;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import javax.xml.bind.annotation.adapters.XmlAdapter;

public class LocalDateTimeAdapter extends XmlAdapter<String, LocalDateTime> {

  private static final String ISO_DATETIME = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";

  @Override
  public String marshal(LocalDateTime date) {
    return (date != null) ? formatXmlDateTime(date) : null;
  }

  @Override
  public LocalDateTime unmarshal(String date) {
    return date != null ? LocalDateTime.parse(date) : null;
  }

  private static String formatXmlDateTime(LocalDateTime dateTime) {
    Date date = Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
    return date != null ? new SimpleDateFormat(ISO_DATETIME).format(date) : null;
  }
}
