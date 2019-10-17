package nl.schiphol.jaxb.xmldocument;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.sax.SAXSource;
import javax.xml.validation.Schema;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

public interface XMLDocument {

  static String toXML(XMLDocument doc) {
    if (doc == null) {
      return null;
    }
    return doc.toXML();
  }

  static <T> T fromXML(String xml, Class<T> clazz) {
    if (xml == null || xml.trim().length() == 0) {
      return null;
    }
    return fromXML(new StringReader(xml), clazz);
  }

  static <T> T fromXML(Reader reader, Class<T> clazz) {
    try {
      Unmarshaller unmarshaller = JAXBContext.newInstance(clazz).createUnmarshaller();
      SAXParserFactory sax = SAXParserFactory.newInstance();
      sax.setNamespaceAware(false);
      XMLReader xmlReader = sax.newSAXParser().getXMLReader();
      SAXSource source = new SAXSource(xmlReader, new InputSource(reader));

      return unmarshaller.unmarshal(source, clazz).getValue();
    } catch (JAXBException | SAXException | ParserConfigurationException e) {
      throw new MarshallingException(e);
    } finally {
      try {
        reader.close();
      } catch (IOException e) {
        // ignored
      }
    }
  }

  default String toXML() {
    try {
      JAXBContext jaxbCtx = JAXBContext.newInstance(getClass());
      StringWriter stringWriter = new StringWriter();
      if (this instanceof WithDefaultValues) {
        ((WithDefaultValues) this).updateWithDefaultValues();
      }
      Marshaller marshaller = jaxbCtx.createMarshaller();
      marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, isJaxbFormattedOutput());
      marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
      Schema schema = SchemaUtility.getSchema(this.getClass().getSimpleName());
      if (schema != null) {
        marshaller.setSchema(schema);
      }
      marshaller.marshal(this, stringWriter);
      return stringWriter.toString();
    } catch (JAXBException e) {
      throw new MarshallingException(e);
    }
  }

  default boolean isJaxbFormattedOutput() {
    return false;
  }
}
