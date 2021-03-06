package com.maykop_mmz.ppo.waybillImport.utils;

import com.maykop_mmz.ppo.waybillImport.core.Main;
import org.apache.log4j.Logger;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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

    public static void copyFile(String sourcePath, String targetPath) throws IOException {
        copyFile(new File(sourcePath), new File(targetPath));
    }

    public static void copyFile(File source, File target) throws IOException {
        log.debug("Got to copy " + source + " into " + target);
        Path sourcePath = Paths.get(source.toURI());
        Path targetPath = Paths.get(target.toURI());

        if (!target.exists()) {
            log.debug(target + " does not exist, making directories");
            if (target.mkdirs()) {
                log.debug("Copying " + sourcePath + " to " + targetPath);
                Files.copy(sourcePath, targetPath.resolve(sourcePath.getFileName()));
            } else {
                log.warn("Could not create " + target + " for some reason");
            }

        } else {
            log.debug("Copying " + sourcePath + " to " + targetPath);
            Files.copy(sourcePath, targetPath.resolve(sourcePath.getFileName()));
        }

    }

    public static String openFileBrowser(Frame parent) {
        log.debug("Opening File Browser");

        FileDialog fd = new FileDialog(parent, "Выбери файл", FileDialog.LOAD);
        fd.setIconImage(Toolkit.getDefaultToolkit()
                .getImage(Main.class.getResource("/img/logo.png")));
        fd.setDirectory(System.getProperty("user.dir"));
        fd.setFile("*.dbf");
        fd.setMultipleMode(false);
        fd.setVisible(true);
        String filename = fd.getFile();
        File[] f = fd.getFiles();
        if (f.length > 0) {
            log.debug("Choice: " + fd.getFiles()[0].getAbsolutePath());
            return fd.getFiles()[0].getAbsolutePath();
        } else {
            log.debug("Choice canceled");
            return null;
        }
    }

}
