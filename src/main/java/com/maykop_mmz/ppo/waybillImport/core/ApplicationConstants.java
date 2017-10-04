package com.maykop_mmz.ppo.waybillImport.core;

import java.io.File;

public interface ApplicationConstants {

    String APP_NAME = "WaybillImport";
    String APP_LOG_FOLDER_PATH = System.getProperty("java.io.tmpdir")
            + APP_NAME + File.separator + "logs";
    String APP_LOG_PROPERTY = "WaybillImport.log.folder";
}
