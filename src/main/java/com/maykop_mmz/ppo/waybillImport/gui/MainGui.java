package com.maykop_mmz.ppo.waybillImport.gui;

import com.maykop_mmz.ppo.waybillImport.utils.FrameUtils;
import com.maykop_mmz.ppo.waybillImport.utils.PropertiesNames;
import com.maykop_mmz.ppo.waybillImport.utils.PropertiesUtils;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainGui extends JDialog {
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

    public MainGui() {
        createGui();
        fillSettingsToFields();
    }

    private void fillSettingsToFields() {
        ostDbfTextField.setText(PropertiesUtils.getProperty(PropertiesNames.OST_DBF_NAME));
        prih1DbfTextField.setText(PropertiesUtils.getProperty(PropertiesNames.PRIH1_DBF_NAME));
        rash1DbfTextField.setText(PropertiesUtils.getProperty(PropertiesNames.RASH1_DBF_NAME));
        prih3DbfTextField.setText(PropertiesUtils.getProperty(PropertiesNames.PRIH3_DBF_NAME));
        rash3DbfTextField.setText(PropertiesUtils.getProperty(PropertiesNames.RASH3_DBF_NAME));
    }

    private void createGui() {
        setContentPane(contentPane);
        setTitle("Импорт накладных в базу данных DBase3");
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(e -> onOK());

        buttonCancel.addActionListener(e -> onCancel());

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        pack();
        setLocation(FrameUtils.getFrameOnCenterLocationPoint(this));
        FrameUtils.showOnTop(this);
    }

    private void onOK() {
        saveSettings();
        dispose();
    }

    private void saveSettings() {
        PropertiesUtils.setProperty(PropertiesNames.OST_DBF_NAME,ostDbfTextField.getText());
        PropertiesUtils.setProperty(PropertiesNames.PRIH1_DBF_NAME,prih1DbfTextField.getText());
        PropertiesUtils.setProperty(PropertiesNames.RASH1_DBF_NAME,rash1DbfTextField.getText());
        PropertiesUtils.setProperty(PropertiesNames.PRIH3_DBF_NAME,prih3DbfTextField.getText());
        PropertiesUtils.setProperty(PropertiesNames.RASH3_DBF_NAME,rash3DbfTextField.getText());
        PropertiesUtils.saveProperties();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
}
