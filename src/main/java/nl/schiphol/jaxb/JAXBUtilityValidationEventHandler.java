/*
 * Copyright (c) 2017 Schiphol Group
 */
package nl.schiphol.jaxb;

import javax.xml.bind.*;
import org.slf4j.*;

class JAXBUtilityValidationEventHandler implements ValidationEventHandler {

  private static final Logger LOG =
      LoggerFactory.getLogger(JAXBUtilityValidationEventHandler.class);

  public boolean handleEvent(ValidationEvent event) {
    StringBuilder builder = new StringBuilder().append(this.toString());

    builder
        .append("message")
        .append(event.getMessage()) //
        .append("lineNumber")
        .append(event.getLocator().getLineNumber()) //
        .append("columnNumber")
        .append(event.getLocator().getColumnNumber()) //
        .append("object")
        .append(event.getLocator().getObject());

    boolean proceed = false;
    if (proceed) {
      LOG.warn(builder.toString());
    } else {
      LOG.error(builder.toString());
    }
    return proceed;
  }
}
