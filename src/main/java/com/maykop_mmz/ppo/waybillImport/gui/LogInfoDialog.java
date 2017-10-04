package com.maykop_mmz.ppo.waybillImport.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LogInfoDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JTextPane textPane;

    public LogInfoDialog(JTextPane pane) {
        textPane = pane;
        createGui();
    }

    private void createGui() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });


        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onOK();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        setSize(Toolkit.getDefaultToolkit().getScreenSize().width-20,(int)Toolkit.getDefaultToolkit().getScreenSize().height-40);
    }

    private void createUIComponents() {}

    private void onOK() {
        dispose();
    }

}
