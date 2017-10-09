package com.maykop_mmz.ppo.waybillImport.utils;

import com.maykop_mmz.ppo.waybillImport.core.ApplicationConstants;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.Properties;

/**
 * Created by Eugene Zrazhevsky on 004 04.10.2017.
 */
public class PropertiesUtils {

    private static Properties properties;

    private static Logger log = Logger.getLogger(Logging.getCurrentClassName());

    public static void loadProperties() {
        log.debug("Loading properties from: " + ApplicationConstants.PROPERTIES_FILE_PATH);
        properties = new Properties();
        File file = new File(ApplicationConstants.PROPERTIES_FILE_PATH);
        if (file.exists()) {
            try (FileInputStream fileInputStream = new FileInputStream(file)) {
                log.debug("File " + ApplicationConstants.PROPERTIES_FILE_PATH + " exists");
                properties.loadFromXML(fileInputStream);
            } catch (FileNotFoundException e) {
                log.warn("Can not find settings: " + file.getPath());
            } catch (IOException e) {
                log.warn("Can not load settings: " + file.getPath(), e);
            }
        }
    }

    public static String getProperty(String propertyName) {
        return properties.getProperty(propertyName);
    }

    public static void setProperty(String property, String value) {
        properties.setProperty(property, value);
    }

    public static void saveProperties() {
        File file = new File(ApplicationConstants.PROPERTIES_FILE_PATH);
        try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            properties.storeToXML(fileOutputStream, "", "UTF-8");
        } catch (IOException e) {
            log.warn("Can not write file to: " + file.getPath(), e);
        }
    }
}
