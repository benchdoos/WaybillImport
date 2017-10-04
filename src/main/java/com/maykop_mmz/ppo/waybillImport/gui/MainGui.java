package com.maykop_mmz.ppo.waybillImport.gui;

import com.maykop_mmz.ppo.waybillImport.core.Main;
import com.maykop_mmz.ppo.waybillImport.utils.FrameUtils;
import com.maykop_mmz.ppo.waybillImport.utils.Logging;
import com.maykop_mmz.ppo.waybillImport.utils.PropertiesNames;
import com.maykop_mmz.ppo.waybillImport.utils.PropertiesUtils;
import org.apache.log4j.Logger;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;

public class MainGui extends JFrame {
    final Color DEFAULT_GREEN_COLOR = new Color(0, 172, 0);
    Logger log = Logger.getLogger(Logging.getCurrentClassName());
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField ostDbfTextField;
    private JTextField prih1DbfTextField;
    private JTextField rash1DbfTextField;
    private JTextField prih3DbfTextField;
    private JTextField rash3DbfTextField;
    private JButton ostSearchButton;
    private JButton prih1SearchButton;
    private JButton rash1SearchButton;
    private JButton prih3SearchButton;
    private JButton rash3SearchButton;
    private JButton moreInfoButton;
    private JLabel statusLabel;
    private JTextPane textPane;
    private JLabel ostLabel;
    private JLabel prih1Label;
    private JLabel prih3Label;
    private JLabel rash1Label;
    private JLabel rash3Label;
    private JTextField textField1;


    public MainGui() {
        createGui();
        fillSettingsToFields();


        moreInfoButton.addActionListener(e -> showMoreInfo());
    }

    private void addInfoToTextPane(String text, Level level) {
        Style style;
        StyleContext sc = new StyleContext();
        style = sc.addStyle("Heading2", null);
        switch (level) {
            case INFO:
                statusLabel.setText(text);
                break;
            case WARN:
                style.addAttribute(StyleConstants.Foreground, Color.red);
                statusLabel.setText("<html><font color='red'>" + text + "</font></html>");
                break;
            case SUCCESS:
                style.addAttribute(StyleConstants.Foreground, DEFAULT_GREEN_COLOR);
                statusLabel.setText("<html><font color='green'>" + text + "</font></html>");
                break;
            default:
                break;
        }


        try {
            Document doc = textPane.getStyledDocument();
            doc.insertString(doc.getLength(), text + "\n", style);
        } catch (BadLocationException exc) {
            exc.printStackTrace();
        }
    }

    private void checkFile(JLabel label, String path) throws FileNotFoundException {
        if (checkFileExistence(path)) {
            label.setForeground(DEFAULT_GREEN_COLOR);
            addInfoToTextPane("Все ок", Level.SUCCESS);
        } else {
            label.setForeground(Color.red);
            throw new FileNotFoundException("Can not find ost file: " + path);
        }
    }

    private boolean checkFileExistence(String path) {
        return new File(path).exists();
    }

    private void checkFilesLocations() throws FileNotFoundException {
        addInfoToTextPane("Проверяю путь к файлу остатков", Level.INFO);
        checkFile(ostLabel, ostDbfTextField.getText());

        addInfoToTextPane("Проверяю путь к файлу прихода СЗ", Level.INFO);
        checkFile(prih1Label, prih1DbfTextField.getText());

        addInfoToTextPane("Проверяю путь к файлу расхода СЗ", Level.INFO);
        checkFile(rash1Label, rash1DbfTextField.getText());

        addInfoToTextPane("Проверяю путь к файлу прихода СГ", Level.INFO);
        checkFile(prih3Label, prih3DbfTextField.getText());

        addInfoToTextPane("Проверяю путь к файлу расхода СГ", Level.INFO);
        checkFile(rash3Label, rash3DbfTextField.getText());
    }

    private void createGui() {
        setContentPane(contentPane);
        setTitle("Импорт накладных в БД DBase3 - ООО \"ММЗ\"");
        setIconImage(Toolkit.getDefaultToolkit().getImage(MainGui.class.getResource("/img/logo.png")));
        getRootPane().setDefaultButton(buttonOK);

        textPane.setContentType("text/html");
        textPane.setText("<html>");

        buttonOK.addActionListener(e -> onOK());

        buttonCancel.addActionListener(e -> onCancel());

        ostSearchButton.addActionListener(e -> ostDbfTextField.setText(openFileBrowser()));
        prih1SearchButton.addActionListener(e -> prih1DbfTextField.setText(openFileBrowser()));
        rash1SearchButton.addActionListener(e -> rash1DbfTextField.setText(openFileBrowser()));
        prih3SearchButton.addActionListener(e -> prih3DbfTextField.setText(openFileBrowser()));

        rash3SearchButton.addActionListener(e -> rash3DbfTextField.setText(openFileBrowser()));

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        textPane.setEditorKit(JEditorPane.createEditorKitForContentType("text/html"));
        textPane.setEditable(false);

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        pack();
        setLocation(FrameUtils.getFrameOnCenterLocationPoint(this));
    }

    private void fillSettingsToFields() {
        log.info("Loading settings to panel");
        ostDbfTextField.setText(PropertiesUtils.getProperty(PropertiesNames.OST_DBF_NAME));
        prih1DbfTextField.setText(PropertiesUtils.getProperty(PropertiesNames.PRIH1_DBF_NAME));
        rash1DbfTextField.setText(PropertiesUtils.getProperty(PropertiesNames.RASH1_DBF_NAME));
        prih3DbfTextField.setText(PropertiesUtils.getProperty(PropertiesNames.PRIH3_DBF_NAME));
        rash3DbfTextField.setText(PropertiesUtils.getProperty(PropertiesNames.RASH3_DBF_NAME));
    }

    private void onCancel() {
        dispose();
    }

    private void onOK() {
        saveSettings();
        try {
            checkFilesLocations();
        } catch (FileNotFoundException e) {
            final String fileNotFound = "Файл не найден";
            statusLabel.setText(fileNotFound);
            addInfoToTextPane(fileNotFound, Level.WARN);
            log.warn("Could not find a file", e);
        }
        //dispose();
    }

    private String openFileBrowser() {
        log.debug("Opening File Browser");

        FileDialog fd = new FileDialog(this, "Выбери файл", FileDialog.LOAD);
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

    private void saveSettings() {
        log.info("Saving settings");
        PropertiesUtils.setProperty(PropertiesNames.OST_DBF_NAME, ostDbfTextField.getText());
        PropertiesUtils.setProperty(PropertiesNames.PRIH1_DBF_NAME, prih1DbfTextField.getText());
        PropertiesUtils.setProperty(PropertiesNames.RASH1_DBF_NAME, rash1DbfTextField.getText());
        PropertiesUtils.setProperty(PropertiesNames.PRIH3_DBF_NAME, prih3DbfTextField.getText());
        PropertiesUtils.setProperty(PropertiesNames.RASH3_DBF_NAME, rash3DbfTextField.getText());
        PropertiesUtils.saveProperties();
    }

    private void showMoreInfo() {
        LogInfoDialog infoDialog = new LogInfoDialog(textPane);
        infoDialog.setVisible(true);
    }

    enum Level {INFO, SUCCESS, WARN}
}
