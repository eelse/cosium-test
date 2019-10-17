package nl.schiphol.jaxb.adapters;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.xml.bind.annotation.adapters.XmlAdapter;

public class DateAdapter extends XmlAdapter<String, Date> {

  private static final String ISO_DATETIME = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";

  private static final ThreadLocal<DateFormat> dateFormat =
      ThreadLocal.withInitial(() -> new SimpleDateFormat(ISO_DATETIME));

  @Override
  public Date unmarshal(String v) throws Exception {
    return dateFormat.get().parse(v);
  }

  @Override
  public String marshal(Date v) throws Exception {
    return dateFormat.get().format(v);
  }
}
