/*
 * Copyright (c) 2017 Schiphol Group
 */
package nl.schiphol.jaxb.util;

import javax.xml.namespace.NamespaceContext;
import javax.xml.stream.*;

public class DelegatingXMLStreamWriter implements XMLStreamWriter {

  private XMLStreamWriter delegate;

  public DelegatingXMLStreamWriter(XMLStreamWriter delegate) {
    this.delegate = delegate;
  }

  /**
   * @param localName
   * @throws XMLStreamException
   * @see javax.xml.stream.XMLStreamWriter#writeStartElement(java.lang.String)
   */
  public void writeStartElement(String localName) throws XMLStreamException {
    delegate.writeStartElement(localName);
  }

  /**
   * @param namespaceURI
   * @param localName
   * @throws XMLStreamException
   * @see javax.xml.stream.XMLStreamWriter#writeStartElement(java.lang.String, java.lang.String)
   */
  public void writeStartElement(String namespaceURI, String localName) throws XMLStreamException {
    delegate.writeStartElement(namespaceURI, localName);
  }

  /**
   * @param prefix
   * @param localName
   * @param namespaceURI
   * @throws XMLStreamException
   * @see javax.xml.stream.XMLStreamWriter#writeStartElement(java.lang.String, java.lang.String,
   *     java.lang.String)
   */
  public void writeStartElement(String prefix, String localName, String namespaceURI)
      throws XMLStreamException {
    delegate.writeStartElement(prefix, localName, namespaceURI);
  }

  /**
   * @param namespaceURI
   * @param localName
   * @throws XMLStreamException
   * @see javax.xml.stream.XMLStreamWriter#writeEmptyElement(java.lang.String, java.lang.String)
   */
  public void writeEmptyElement(String namespaceURI, String localName) throws XMLStreamException {
    delegate.writeEmptyElement(namespaceURI, localName);
  }

  /**
   * @param prefix
   * @param localName
   * @param namespaceURI
   * @throws XMLStreamException
   * @see javax.xml.stream.XMLStreamWriter#writeEmptyElement(java.lang.String, java.lang.String,
   *     java.lang.String)
   */
  public void writeEmptyElement(String prefix, String localName, String namespaceURI)
      throws XMLStreamException {
    delegate.writeEmptyElement(prefix, localName, namespaceURI);
  }

  /**
   * @param localName
   * @throws XMLStreamException
   * @see javax.xml.stream.XMLStreamWriter#writeEmptyElement(java.lang.String)
   */
  public void writeEmptyElement(String localName) throws XMLStreamException {
    delegate.writeEmptyElement(localName);
  }

  /**
   * @throws XMLStreamException
   * @see javax.xml.stream.XMLStreamWriter#writeEndElement()
   */
  public void writeEndElement() throws XMLStreamException {
    delegate.writeEndElement();
  }

  /**
   * @throws XMLStreamException
   * @see javax.xml.stream.XMLStreamWriter#writeEndDocument()
   */
  public void writeEndDocument() throws XMLStreamException {
    delegate.writeEndDocument();
  }

  /**
   * @throws XMLStreamException
   * @see javax.xml.stream.XMLStreamWriter#close()
   */
  public void close() throws XMLStreamException {
    delegate.close();
  }

  /**
   * @throws XMLStreamException
   * @see javax.xml.stream.XMLStreamWriter#flush()
   */
  public void flush() throws XMLStreamException {
    delegate.flush();
  }

  /**
   * @param localName
   * @param value
   * @throws XMLStreamException
   * @see javax.xml.stream.XMLStreamWriter#writeAttribute(java.lang.String, java.lang.String)
   */
  public void writeAttribute(String localName, String value) throws XMLStreamException {
    delegate.writeAttribute(localName, value);
  }

  /**
   * @param prefix
   * @param namespaceURI
   * @param localName
   * @param value
   * @throws XMLStreamException
   * @see javax.xml.stream.XMLStreamWriter#writeAttribute(java.lang.String, java.lang.String,
   *     java.lang.String, java.lang.String)
   */
  public void writeAttribute(String prefix, String namespaceURI, String localName, String value)
      throws XMLStreamException {
    delegate.writeAttribute(prefix, namespaceURI, localName, value);
  }

  /**
   * @param namespaceURI
   * @param localName
   * @param value
   * @throws XMLStreamException
   * @see javax.xml.stream.XMLStreamWriter#writeAttribute(java.lang.String, java.lang.String,
   *     java.lang.String)
   */
  public void writeAttribute(String namespaceURI, String localName, String value)
      throws XMLStreamException {
    delegate.writeAttribute(namespaceURI, localName, value);
  }

  /**
   * @param prefix
   * @param namespaceURI
   * @throws XMLStreamException
   * @see javax.xml.stream.XMLStreamWriter#writeNamespace(java.lang.String, java.lang.String)
   */
  public void writeNamespace(String prefix, String namespaceURI) throws XMLStreamException {
    delegate.writeNamespace(prefix, namespaceURI);
  }

  /**
   * @param namespaceURI
   * @throws XMLStreamException
   * @see javax.xml.stream.XMLStreamWriter#writeDefaultNamespace(java.lang.String)
   */
  public void writeDefaultNamespace(String namespaceURI) throws XMLStreamException {
    delegate.writeDefaultNamespace(namespaceURI);
  }

  /**
   * @param data
   * @throws XMLStreamException
   * @see javax.xml.stream.XMLStreamWriter#writeComment(java.lang.String)
   */
  public void writeComment(String data) throws XMLStreamException {
    delegate.writeComment(data);
  }

  /**
   * @param target
   * @throws XMLStreamException
   * @see javax.xml.stream.XMLStreamWriter#writeProcessingInstruction(java.lang.String)
   */
  public void writeProcessingInstruction(String target) throws XMLStreamException {
    delegate.writeProcessingInstruction(target);
  }

  /**
   * @param target
   * @param data
   * @throws XMLStreamException
   * @see javax.xml.stream.XMLStreamWriter#writeProcessingInstruction(java.lang.String,
   *     java.lang.String)
   */
  public void writeProcessingInstruction(String target, String data) throws XMLStreamException {
    delegate.writeProcessingInstruction(target, data);
  }

  /**
   * @param data
   * @throws XMLStreamException
   * @see javax.xml.stream.XMLStreamWriter#writeCData(java.lang.String)
   */
  public void writeCData(String data) throws XMLStreamException {
    delegate.writeCData(data);
  }

  /**
   * @param dtd
   * @throws XMLStreamException
   * @see javax.xml.stream.XMLStreamWriter#writeDTD(java.lang.String)
   */
  public void writeDTD(String dtd) throws XMLStreamException {
    delegate.writeDTD(dtd);
  }

  /**
   * @param name
   * @throws XMLStreamException
   * @see javax.xml.stream.XMLStreamWriter#writeEntityRef(java.lang.String)
   */
  public void writeEntityRef(String name) throws XMLStreamException {
    delegate.writeEntityRef(name);
  }

  /**
   * @throws XMLStreamException
   * @see javax.xml.stream.XMLStreamWriter#writeStartDocument()
   */
  public void writeStartDocument() throws XMLStreamException {
    delegate.writeStartDocument();
  }

  /**
   * @param version
   * @throws XMLStreamException
   * @see javax.xml.stream.XMLStreamWriter#writeStartDocument(java.lang.String)
   */
  public void writeStartDocument(String version) throws XMLStreamException {
    delegate.writeStartDocument(version);
  }

  /**
   * @param encoding
   * @param version
   * @throws XMLStreamException
   * @see javax.xml.stream.XMLStreamWriter#writeStartDocument(java.lang.String, java.lang.String)
   */
  public void writeStartDocument(String encoding, String version) throws XMLStreamException {
    delegate.writeStartDocument(encoding, version);
  }

  /**
   * @param text
   * @throws XMLStreamException
   * @see javax.xml.stream.XMLStreamWriter#writeCharacters(java.lang.String)
   */
  public void writeCharacters(String text) throws XMLStreamException {
    delegate.writeCharacters(text);
  }

  /**
   * @param text
   * @param start
   * @param len
   * @throws XMLStreamException
   * @see javax.xml.stream.XMLStreamWriter#writeCharacters(char[], int, int)
   */
  public void writeCharacters(char[] text, int start, int len) throws XMLStreamException {
    delegate.writeCharacters(text, start, len);
  }

  /**
   * @param uri
   * @return
   * @throws XMLStreamException
   * @see javax.xml.stream.XMLStreamWriter#getPrefix(java.lang.String)
   */
  public String getPrefix(String uri) throws XMLStreamException {
    return delegate.getPrefix(uri);
  }

  /**
   * @param prefix
   * @param uri
   * @throws XMLStreamException
   * @see javax.xml.stream.XMLStreamWriter#setPrefix(java.lang.String, java.lang.String)
   */
  public void setPrefix(String prefix, String uri) throws XMLStreamException {
    delegate.setPrefix(prefix, uri);
  }

  /**
   * @param uri
   * @throws XMLStreamException
   * @see javax.xml.stream.XMLStreamWriter#setDefaultNamespace(java.lang.String)
   */
  public void setDefaultNamespace(String uri) throws XMLStreamException {
    delegate.setDefaultNamespace(uri);
  }

  /**
   * @param context
   * @throws XMLStreamException
   * @see javax.xml.stream.XMLStreamWriter#setNamespaceContext(javax.xml.namespace.NamespaceContext)
   */
  public void setNamespaceContext(NamespaceContext context) throws XMLStreamException {
    delegate.setNamespaceContext(context);
  }

  /**
   * @return
   * @see javax.xml.stream.XMLStreamWriter#getNamespaceContext()
   */
  public NamespaceContext getNamespaceContext() {
    return delegate.getNamespaceContext();
  }

  /**
   * @param name
   * @return
   * @throws IllegalArgumentException
   * @see javax.xml.stream.XMLStreamWriter#getProperty(java.lang.String)
   */
  public Object getProperty(String name) throws IllegalArgumentException {
    return delegate.getProperty(name);
  }
}
