package com.maykop_mmz.ppo.waybillImport.utils;

import org.apache.log4j.Logger;

import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Created by Eugene Zrazhevsky on 007 07.10.2017.
 */
public class FileUtils {
    static Logger log = Logger.getLogger(Logging.getCurrentClassName());
    public static void openFolder(File file) {
        try {
            Desktop.getDesktop().browse(file.toURI());
        } catch (IOException e) {
            log.warn("Could not open external file / folder: " + file);
        }
    }
}
