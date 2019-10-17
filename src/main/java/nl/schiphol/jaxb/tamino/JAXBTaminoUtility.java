package nl.schiphol.jaxb.tamino;

import java.io.StringWriter;

@Deprecated
public class JAXBTaminoUtility {

  static final String TAMINO_NAMESPACE_DECLARATION =
      " xmlns:ino=\"" + TaminoNamespacePrefixMapper.TAMINO_NAMESPACE + "\"";

  public static void removeTaminoNamespaceDecleration(StringWriter writer) {
    try {
      StringBuffer buffer = writer.getBuffer();
      int indexOf = buffer.indexOf(TAMINO_NAMESPACE_DECLARATION);
      buffer.replace(indexOf, indexOf + TAMINO_NAMESPACE_DECLARATION.length(), "");
    } catch (IndexOutOfBoundsException exception) {
      // No Tamino namespace present, this is non problem.
    }
  }
}
