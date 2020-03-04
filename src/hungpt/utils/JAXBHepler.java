package hungpt.utils;

import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.bind.*;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.*;

public class JAXBHepler {
    public static Object unmarshall(Class type, ByteArrayOutputStream byteArrayOutputStream, String schemaFilePath)
            throws JAXBException, SAXException {
        Unmarshaller unmarshaller = JAXBContext.newInstance(type).createUnmarshaller();
        unmarshaller.setEventHandler(new ValidationEventHandler() {
            @Override
            public boolean handleEvent(ValidationEvent event) {
                if(event.getSeverity() == ValidationEvent.FATAL_ERROR
                        || event.getSeverity() == ValidationEvent.ERROR) {
                    ValidationEventLocator locator = event.getLocator();

                    System.out.println("Invalid document: " + locator.getURL());
                    System.out.println("Error: " + event.getMessage());
                    System.out.println("Error at column " + locator.getColumnNumber() +
                            ", line " + locator.getLineNumber());
                }
                return true;
            }
        });
        Schema schema = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI).newSchema(new File(schemaFilePath));
        unmarshaller.setSchema(schema);

        return unmarshaller.unmarshal(new ByteArrayInputStream(byteArrayOutputStream.toByteArray()));
    }
}
