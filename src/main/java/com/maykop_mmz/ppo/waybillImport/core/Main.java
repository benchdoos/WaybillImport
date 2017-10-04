package com.maykop_mmz.ppo.waybillImport.core;

import com.maykop_mmz.ppo.waybillImport.gui.MainGui;
import com.maykop_mmz.ppo.waybillImport.utils.Logging;

import java.util.logging.Logger;

public class Main {
    private static Logger log;
    public static void main(String[] args) {
        initLogging();
        MainGui gui = new MainGui();
        gui.setVisible(true);
    }

    private static void initLogging() {
        new Logging(ApplicationConstants.APP_NAME);
        log = Logger.getLogger(Logging.getCurrentClassName());

    }
}
