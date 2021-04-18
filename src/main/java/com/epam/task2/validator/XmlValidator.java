package com.epam.task2.validator;

import com.epam.task2.exception.XmlException;
import com.epam.task2.util.FileResourcesUtil;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

public class XmlValidator {
    private final static Logger LOGGER = LogManager.getLogger(XmlValidator.class);
    private final static String SCHEMA_NAME = "data/xsd.xsd";

    public static boolean validate(String filePath) throws XmlException {
        LOGGER.log(Level.INFO, "run func with filePath :" + filePath);
        SchemaFactory schemaFactory = SchemaFactory.newDefaultInstance();
        try {
            File schemaFile = FileResourcesUtil.getFileFromResource(SCHEMA_NAME);
            Schema schema = schemaFactory.newSchema(schemaFile);
            Validator validator = schema.newValidator();
            Source source = new StreamSource(filePath);
            validator.validate(source);
        } catch (IOException e) {
            throw new XmlException("cannot open file" + filePath, e);
        } catch (URISyntaxException e) {
            throw new XmlException("cannot open resource file" + filePath, e);
        } catch (SAXException e) {
            LOGGER.log(Level.WARN, filePath + " is not valid, details " + e.getMessage());
            return false;
        }
        return true;
    }
}
