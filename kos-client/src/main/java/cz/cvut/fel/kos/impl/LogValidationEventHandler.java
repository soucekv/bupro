package cz.cvut.fel.kos.impl;

import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventHandler;
import javax.xml.bind.ValidationEventLocator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Node;

public class LogValidationEventHandler implements ValidationEventHandler {
	private final Log log = LogFactory.getLog(getClass());

	public boolean handleEvent(ValidationEvent event) {
		log.info("Validation msg " + event.getMessage());
		ValidationEventLocator locator = event.getLocator();
		if (locator != null) {
			log.info("Validation " + locator.getLineNumber() + ":" + locator.getColumnNumber());
			Node node = locator.getNode();
			if (node != null) {
				log.info("Validation node " + node.getNodeName());
			}
		}
		if (event.getLinkedException() != null) {
			log.error("Validation error " + event.getLinkedException());
			return false;
		}
		return true;
	}

}
