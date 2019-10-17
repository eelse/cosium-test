package eu.eelse.test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/** Utility class for JAXB. */
public class TestMe {

  public static final String NAMESPACE_PREFIX_MAPPER_PROPERTY =
      "com.sun.xml.bind.namespacePrefixMapper";
  /** Cache the XMLInputFactory, it is thread safe after initialization. */
  private static final XMLInputFactory INPUT_FACTORY = XMLInputFactory.newInstance();

  /** Cache JAXBContext in ThreadLocal storage. Creating JAXBContext is very expensive. */
  private static final ThreadLocal<Map<String, JAXBContext>> CONTEXTS =
      new ThreadLocal<Map<String, JAXBContext>>() {
        @Override
        protected Map<String, JAXBContext> initialValue() {
          return new HashMap<String, JAXBContext>();
        }
      };
  /**
   * Cache Marshaller in ThreadLocal storage. Marshaller can be reused if thread safety is ensured.
   */
  private static final ThreadLocal<Map<String, Marshaller>> MARSHALLERS =
      new ThreadLocal<Map<String, Marshaller>>() {
        @Override
        protected Map<String, Marshaller> initialValue() {
          return new HashMap<String, Marshaller>();
        }
      };

  /**
   * Cache Unmarshaller in ThreadLocal storage. Unmarshaller can be reused if thread safety is
   * ensured.
   */
  private static final ThreadLocal<Map<String, Unmarshaller>> UNMARSHALLERS =
      new ThreadLocal<Map<String, Unmarshaller>>() {
        @Override
        protected Map<String, Unmarshaller> initialValue() {
          return new HashMap<String, Unmarshaller>();
        }
      };

  /**
   * Utility method for unmarshalling XML to any given type. Schema parameter is optional, used for
   * validation of the XML input. StAX is used for reading the input stream, this is proven to be xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx xxxxxxxxxxxxxxxxxxxxxx x xx xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx x xxxxxxxx
   * the fastest approach.
   *
   * @param inputStream InputStream containing the XML to be unmarshalled
   * @param clazz Type of the resulting JAXB unmarshalled object
   * @return Unmarshalled JAXB object of the given type
   * @throws JAXBException
   */
  public static <T> T unmarshal(InputStream inputStream, Class<T> clazz) throws JAXBException {
    T jaxbObject = null;
    XMLStreamReader reader = null;
    try {
      Unmarshaller unmarshaller = getUnmarshaller(clazz);
      jaxbObject = unmarshal(unmarshaller, inputStream, clazz, false, null);
    } catch (SAXException exception) {
      throw new JAXBException(exception);
    } finally {
      if (reader != null) {
        try {
          reader.close();
        } catch (XMLStreamException e) {
          throw new JAXBException("Cannot close xml stream reader", e);
        }
      }
    }
    return jaxbObject;
  }

  /**
   * Utility method for unmarshalling XML to any given type.
   *
   * @param xml string representing the object
   * @param clazz Type of the resulting JAXB unmarshalled object
   * @return Unmarshalled JAXB object of the given type
   * @throws JAXBException
   */
  public static <T> T unmarshal(String xml, Class<T> clazz) throws JAXBException {
    InputStream inputStream = new ByteArrayInputStream(xml.getBytes());
    return unmarshal(inputStream, clazz);
  }

  /**
   * Unmarshall node element into java object
   *
   * @param node
   * @param clazz
   * @return
   * @throws JAXBException
   */
  public static <T> T unmarshal(Node node, Class<T> clazz) throws JAXBException {
    try {
      return unmarshal(node, clazz, false, null);
    } catch (SAXException exception) {
      throw new JAXBException(exception);
    }
  }

  /**
   * Utility method for unmarshalling XML to any given type. Schema parameter is optional, used for
   * validation of the XML input. StAX is used for reading the input stream, this is proven to be
   * the fastest approach.
   *
   * @param inputStream InputStream containing the XML to be unmarshalled
   * @param clazz Type of the resulting JAXB unmarshalled object
   * @param isValidation true if we validate the XML to the schema.
   * @param directory The directory where the schema resides, only used when isValidation is true.
   * @return Unmarshalled JAXB object of the given type
   * @throws JAXBException
   * @throws SAXException When trying to validate or the validation itself fails for the XML
   *     document.
   */
  public static <T> T unmarshal(
      InputStream inputStream, Class<T> clazz, boolean isValidation, String directory)
      throws JAXBException, SAXException {
    Unmarshaller unmarshaller = getUnmarshaller(clazz);
    return unmarshal(unmarshaller, inputStream, clazz, isValidation, directory);
  }

  public static <T> T unmarshal(Unmarshaller unmarshaller, InputStream inputStream, Class<T> clazz)
      throws JAXBException, SAXException {
    return unmarshal(unmarshaller, inputStream, clazz, false, null);
  }
  /**
   * Same as above. Only you cna give your own unmarshaller if you have multiple contexts. This can
   * be handy if you have an unmarshaller with multiple contexts.
   */
  public static <T> T unmarshal(
      Unmarshaller unmarshaller,
      InputStream inputStream,
      Class<T> clazz,
      boolean isValidation,
      String directory)
      throws JAXBException, SAXException {
    T jaxbObject = null;
    XMLStreamReader reader = null;
    try {
      reader = INPUT_FACTORY.createXMLStreamReader(inputStream);
      if (isValidation) {
        validate(clazz, unmarshaller, directory);
      }
      jaxbObject = unmarshaller.unmarshal(reader, clazz).getValue();
    } catch (XMLStreamException exception) {
      throw new JAXBException("Cannot open xml stream reader", exception);
    } finally {
      if (reader != null) {
        try {
          reader.close();
        } catch (XMLStreamException e) {
          throw new JAXBException("Cannot close xml stream reader", e);
        }
      }
    }
    return jaxbObject;
  }

  /**
   * Utility method for unmarshalling XML to any given type.
   *
   * @param xml string representing the object
   * @param clazz Type of the resulting JAXB unmarshalled object
   * @param isValidation true if we validate the XML to the schema.
   * @param directory The directory where the schema resides.
   * @return Unmarshalled JAXB object of the given type
   * @throws JAXBException
   * @throws SAXException When trying to validate or the validation itself fails for the XML
   *     document.
   */
  public static <T> T unmarshal(String xml, Class<T> clazz, boolean isValidation, String directory)
      throws JAXBException, SAXException {
    InputStream inputStream = new ByteArrayInputStream(xml.getBytes());
    Unmarshaller unmarshaller = getUnmarshaller(clazz);
    return unmarshal(unmarshaller, inputStream, clazz, isValidation, directory);
  }

  /**
   * Unmarshall node element into java object
   *
   * @param node
   * @param clazz
   * @param isValidation true if we validate the XML to the schema.
   * @param directory The directory where the schema resides.
   * @return
   * @throws JAXBException
   * @throws SAXException When trying to validate or the validation itself fails for the XML
   *     document.
   */
  public static <T> T unmarshal(Node node, Class<T> clazz, boolean isValidation, String directory)
      throws JAXBException, SAXException {
    T result;

    Unmarshaller unmarshaller = getUnmarshaller(clazz);

    if (isValidation) {
      validate(clazz, unmarshaller, directory);
    }

    result = unmarshaller.unmarshal(node, clazz).getValue();

    return result;
  }

  private static <T> void validate(Class<T> clazz, Unmarshaller unmarshaller, String directory)
      throws SAXException, JAXBException {
    SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
    InputStream inputStream =
        clazz
            .getClassLoader()
            .getResourceAsStream(directory + "/" + clazz.getSimpleName() + ".xsd");
    Source source = new StreamSource(inputStream, null);
    Schema schema = schemaFactory.newSchema(source);
    unmarshaller.setSchema(schema);
  }

  /**
   * Returns cached Unmarshaller instance. New instance is created and cached in case that it is not
   * cached already.
   *
   * @param classes Types for which the Unmarshaller is being created
   * @return Cached Unmarshaller instance
   * @throws JAXBException
   */
  public static Unmarshaller getUnmarshaller(Class<?>... classes) throws JAXBException {
    String contextString = getContextString(classes);
    Unmarshaller unmarshaller = UNMARSHALLERS.get().get(contextString);
    if (unmarshaller == null) {
      unmarshaller = getContext(contextString).createUnmarshaller();
      UNMARSHALLERS.get().put(contextString, unmarshaller);
    }
    return unmarshaller;
  }

  /**
   * Try to obtain JAXBContext from cache, create a new one if it does not exist and cache it for
   * later use. Instance is created using the same class loader which loads the given type. This is
   * important for cases where JAXB mapping classes reside in a different bundle than the code that
   * is using them.
   *
   * @param context
   * @return
   * @throws JAXBException
   */
  private static JAXBContext getContext(String context) throws JAXBException {
    JAXBContext jaxbContext = CONTEXTS.get().get(context);
    if (jaxbContext == null) {
      // note: in case of classloading problems try newInstance with the classloader as second
      // parameter
      jaxbContext = JAXBContext.newInstance(context);
      CONTEXTS.get().put(context, jaxbContext);
    }
    return jaxbContext;
  }

  /**
   * Returns cached Unmarshaller instance. New instance is created and cached in case that it is not
   * cached already.
   *
   * @param classes Types for which the Unmarshaller is being created
   * @return Cached Unmarshaller instance
   * @throws JAXBException
   */
  public static Marshaller getMarshaller(Class<?>... classes) throws JAXBException {
    String contextString = getContextString(classes);
    Marshaller marshaller = MARSHALLERS.get().get(contextString);
    if (marshaller == null) {
      marshaller = getContext(contextString).createMarshaller();
      // use this to inject default namespacePrefixMappers
      // marshaller.setProperty("com.sun.xml.bind.namespacePrefixMapper",namespacePrefixMapper);
      MARSHALLERS.get().put(contextString, marshaller);
    }
    return marshaller;
  }

  /**
   * Unmarshall payload
   *
   * @param <T>
   * @param clazz Class that we wnat to unmarshall
   * @param node Node that is unmarshalling
   * @param stripNamespace should we strip namespace or not
   * @return
   * @throws JAXBException
   */
  public static <T> T unmarshalPayload(Class<T> clazz, Node node, boolean stripNamespace)
      throws JAXBException {
    if (!node.getNodeName().equals(clazz.getSimpleName())) {
      throw new JAXBException(
          String.format(
              "Node not a document of type %s found %s",
              clazz.getSimpleName(), node.getNodeName()));
    }
    Unmarshaller unmarshaller = getUnmarshaller(clazz);
    if (stripNamespace) {
      stripDefaultNamespace(node);
    }
    return (T) unmarshaller.unmarshal(node, clazz).getValue();
  }

  /**
   * Utility method for removing default namespace declarations from a given node and all its
   * children. Typical use case is removing ASBMessage namespace from payload elements.
   *
   * @param node DOM Node representation of input XML.
   */
  public static void stripDefaultNamespace(Node node) {
    Document document = node.getOwnerDocument();
    if (node.getNodeType() == Node.ELEMENT_NODE) {
      document.renameNode(
          node, null, node.getLocalName() == null ? node.getNodeName() : node.getLocalName());
    }
    NodeList list = node.getChildNodes();
    for (int i = 0; i < list.getLength(); ++i) {
      stripDefaultNamespace(list.item(i));
    }
  }

  /**
   * Utility method for extracting org.w3c.Element from ASBMessage payload. It returns the first
   * instance of Element contained in object list provided as argument.
   *
   * @param objects
   * @return
   */
  public static Element getFirstPayloadContentNode(List<Object> objects) {
    Element payloadContentNode = null;
    for (Object content : objects) {
      if (content instanceof Element) {
        payloadContentNode = (Element) content;
        break;
      }
    }
    return payloadContentNode;
  }

  /**
   * marshal element in XML
   *
   * @param clazz
   * @param element
   * @return
   * @throws JAXBException
   */
  public static <T> String marshal(Class<?> clazz, Object element) throws JAXBException {
    StringWriter writer = new StringWriter();
    marshal(clazz, element, writer);
    return writer.toString();
  }

  /**
   * @param clazz
   * @param element
   * @throws JAXBException
   */
  public static void marshal(Class<?> clazz, Object element, Writer writer) throws JAXBException {
    Marshaller m = getMarshaller(clazz);
    m.marshal(element, writer);
  }

  /**
   * @param clazz
   * @param element
   * @throws JAXBException
   */
  public static void marshal(Class<?> clazz, Object element, XMLStreamWriter writer)
      throws JAXBException {
    Marshaller m = getMarshaller(clazz);
    m.marshal(element, writer);
  }

  /**
   * Creates a context string that can be used to create a (un)marshaller.
   *
   * @param classes to create the context for.
   * @return string that represents the context.
   */
  private static String getContextString(Class<?>... classes) {
    StringBuilder contexts = new StringBuilder();
    for (Class<?> clazz : classes) {
      contexts.append(clazz.getPackage().getName()).append(":");
    }
    contexts.deleteCharAt(contexts.length() - 1);
    return contexts.toString();
  };
}
