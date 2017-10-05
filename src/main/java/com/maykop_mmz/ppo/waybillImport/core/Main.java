package com.maykop_mmz.ppo.waybillImport.core;

import com.maykop_mmz.ppo.waybillImport.gui.MainFrame;
import com.maykop_mmz.ppo.waybillImport.utils.Logging;
import com.maykop_mmz.ppo.waybillImport.utils.PropertiesUtils;
import org.apache.log4j.Logger;

import javax.swing.*;

public class Main {
    private static Logger log;

    public static void main(String[] args) {
        initLogging();
        PropertiesUtils.loadProperties();

        enableLookAndFeel();

        MainFrame gui = new MainFrame();
        gui.setVisible(true);
    }

    /**
     * Enables LookAndFeel for current OS.
     *
     * @see javax.swing.UIManager.LookAndFeelInfo
     */
    private static void enableLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            log.warn("Can not enable LookAndFeel", e);
        }
    }

    private static void initLogging() {
        new Logging(ApplicationConstants.APP_NAME);
        log = Logger.getLogger(Logging.getCurrentClassName());

    }
}
