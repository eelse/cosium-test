package nl.schiphol.jaxb.xmldocument;

public class MarshallingException extends RuntimeException {

  private static final long serialVersionUID = -8832595614345729412L;

  public MarshallingException(Exception e) {
    super(e);
  }

  public MarshallingException(String id, Exception e) {
    super(id, e);
  }
}
