package com.maykop_mmz.ppo.waybillImport.gui;

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
    final Color DEFAULT_GREEN_COLOR = new Color(0, 172, 0);


    public MainGui() {
        createGui();
        fillSettingsToFields();
    }

    private void addInfoToTextPane(String text, Level level) {
        Style style;
        StyleContext sc = new StyleContext();
        style = sc.addStyle("Heading2", null);
        switch (level) {
            case INFO:

                break;
            case WARN:
                style.addAttribute(StyleConstants.Foreground, Color.red);
                text = "<font color='red'>" + text + "</font>";
                break;
            case SUCCESS:
                style.addAttribute(StyleConstants.Foreground, DEFAULT_GREEN_COLOR);
                text = "<font color='green'>" + text + "</font>";
                break;
            default:
                break;
        }


        try {
            Document doc = textPane.getStyledDocument();
            doc.insertString(doc.getLength(), text + "<br>", null);
        } catch (BadLocationException exc) {
            exc.printStackTrace();
        }
    }

    private void checkFile(JLabel label, String path) throws FileNotFoundException {
        if (checkFileExistence(path)) {
            label.setForeground(DEFAULT_GREEN_COLOR);
        } else {
            label.setForeground(Color.red);
            throw new FileNotFoundException("Can not find ost file: " + path);
        }
    }

    private boolean checkFileExistence(String path) {
        return new File(path).exists();
    }

    private void checkFilesLocations() throws FileNotFoundException {
        final String ostCheckString = "Проверяю путь к файлу остатков";
        addInfoToTextPane(ostCheckString, Level.INFO);
        statusLabel.setText(ostCheckString);
        checkFile(ostLabel, ostDbfTextField.getText());


        final String prih1CheckString = "Проверяю путь к файлу прихода СЗ";
        addInfoToTextPane(prih1CheckString, Level.INFO);
        statusLabel.setText(prih1CheckString);
        checkFile(prih1Label, prih1DbfTextField.getText());

        statusLabel.setText("Проверяю путь к файлу расхода СЗ");
        checkFile(rash1Label, rash1DbfTextField.getText());
        statusLabel.setText("Проверяю путь к файлу прихода СГ");
        checkFile(prih3Label, prih3DbfTextField.getText());
        statusLabel.setText("Проверяю путь к файлу расхода СГ");
        checkFile(rash3Label, rash3DbfTextField.getText());
    }

    private void createGui() {
        setContentPane(contentPane);
        setTitle("Импорт накладных в базу данных DBase3 - ООО \"ММЗ\"");
        setIconImage(Toolkit.getDefaultToolkit().getImage(MainGui.class.getResource("/img/logo.png")));
        getRootPane().setDefaultButton(buttonOK);

        textPane.setContentType("text/html");
        textPane.setText("<html>");

        buttonOK.addActionListener(e -> onOK());

        buttonCancel.addActionListener(e -> onCancel());

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

    private void saveSettings() {
        log.info("Saving settings");
        PropertiesUtils.setProperty(PropertiesNames.OST_DBF_NAME, ostDbfTextField.getText());
        PropertiesUtils.setProperty(PropertiesNames.PRIH1_DBF_NAME, prih1DbfTextField.getText());
        PropertiesUtils.setProperty(PropertiesNames.RASH1_DBF_NAME, rash1DbfTextField.getText());
        PropertiesUtils.setProperty(PropertiesNames.PRIH3_DBF_NAME, prih3DbfTextField.getText());
        PropertiesUtils.setProperty(PropertiesNames.RASH3_DBF_NAME, rash3DbfTextField.getText());
        PropertiesUtils.saveProperties();
    }

    enum Level {INFO, SUCCESS, WARN}
}
