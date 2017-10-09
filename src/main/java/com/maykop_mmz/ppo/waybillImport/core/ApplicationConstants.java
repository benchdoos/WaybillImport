package com.maykop_mmz.ppo.waybillImport.core;

import java.io.File;

public class ApplicationConstants {
    public static final String APP_NAME = "WaybillImport";
    public static final String APPLICATION_TMP_FOLDER = System.getProperty("java.io.tmpdir")
            + APP_NAME + File.separator;
    public static final String APP_LOG_FOLDER_PATH = APPLICATION_TMP_FOLDER+ "logs";
    public static final String APP_LOG_PROPERTY = "WaybillImport.log.folder";
    public static final String PROPERTIES_FILE_PATH = APPLICATION_TMP_FOLDER + "settings.xml";
    public static final String APP_BACKUP_FOLDER = System.getProperty("user.home") + File.separator + APP_NAME + File.separator + "backup";
}
