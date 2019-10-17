/*
 * Copyright (c) 2017 Schiphol Group
 */
package nl.schiphol.jaxb;

import javax.xml.bind.*;
import nl.schiphol.publicflights.commons.util.*;
import org.slf4j.*;

class JAXBUtilityValidationEventHandler implements ValidationEventHandler {

  private static final Logger LOG =
      LoggerFactory.getLogger(JAXBUtilityValidationEventHandler.class);

  public boolean handleEvent(ValidationEvent event) {
    ToStringBuilder builder = new ToStringBuilder(this);

    builder
        .append("message", event.getMessage()) //
        .append("lineNumber", event.getLocator().getLineNumber()) //
        .append("columnNumber", event.getLocator().getColumnNumber()) //
        .append("object", event.getLocator().getObject());

    boolean proceed = (event.getSeverity() == ValidationEvent.WARNING);
    if (proceed) {
      LOG.warn(builder.build());
    } else {
      LOG.error(builder.build());
    }
    return proceed;
  }
}
