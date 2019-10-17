/** Copyright 2017 (c) Schiphol Group */
package nl.schiphol.jaxb.tamino;

import static nl.schiphol.jaxb.tamino.TaminoNamespacePrefixMapper.TAMINO_NAMESPACE;

import java.io.Writer;
import javax.xml.stream.*;
import nl.schiphol.jaxb.util.DelegatingXMLStreamWriter;

@Deprecated
public class TaminoXMLStreamWriter extends DelegatingXMLStreamWriter {

  private TaminoXMLStreamWriter(XMLStreamWriter delegate) {
    super(delegate);
  }

  public static TaminoXMLStreamWriter taminoXMLStreamWriter(Writer writer) {
    try {
      return new TaminoXMLStreamWriter(
          XMLOutputFactory.newInstance().createXMLStreamWriter(writer));
    } catch (XMLStreamException | FactoryConfigurationError e) {
      throw new RuntimeException("error creating XMLStreamWriter", e);
    }
  }

  @Override
  public void writeNamespace(String prefix, String namespaceURI) throws XMLStreamException {
    if (TAMINO_NAMESPACE.equals(namespaceURI)) {
      super.writeNamespace(prefix, namespaceURI);
    }
  }

  @Override
  public void writeStartDocument() throws XMLStreamException {}

  @Override
  public void writeStartDocument(String version) throws XMLStreamException {}

  @Override
  public void writeStartDocument(String encoding, String version) throws XMLStreamException {}
}
