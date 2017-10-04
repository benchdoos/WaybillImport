package com.maykop_mmz.ppo.waybillImport.gui;

import com.maykop_mmz.ppo.waybillImport.utils.FrameUtils;

import javax.swing.*;
import java.awt.event.*;

public class MainGui extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField ostDbfTextField;
    private JTextField prih1DbfTextField;
    private JTextField rash1DbfTextField;
    private JTextField prih3DbfTextField;
    private JTextField rash3DbfTextField;

    public MainGui() {
        createGui();
    }

    private void createGui() {
        setContentPane(contentPane);
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
    }

    private void onOK() {
        // add your code here
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
}
