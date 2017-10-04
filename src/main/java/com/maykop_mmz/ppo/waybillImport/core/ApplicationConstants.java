package com.maykop_mmz.ppo.waybillImport.core;

import java.io.File;

public interface ApplicationConstants {
    String APP_NAME = "WaybillImport";
    String APPLICATION_TMP_FOLDER = System.getProperty("java.io.tmpdir")
            + APP_NAME + File.separator;
    String APP_LOG_FOLDER_PATH = APPLICATION_TMP_FOLDER+ "logs";
    String APP_LOG_PROPERTY = "WaybillImport.log.folder";
    String PROPERTIES_FILE_PATH = APPLICATION_TMP_FOLDER + "settings.prop";
}
