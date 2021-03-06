package com.maykop_mmz.ppo.waybillImport.gui;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import com.linuxense.javadbf.*;
import com.maykop_mmz.ppo.waybillImport.core.ApplicationConstants;
import com.maykop_mmz.ppo.waybillImport.dbase3Dao.Dbase3Dao;
import com.maykop_mmz.ppo.waybillImport.dbase3Dao.OstDetailPosition;
import com.maykop_mmz.ppo.waybillImport.dbase3Dao.Stores;
import com.maykop_mmz.ppo.waybillImport.dbase3Dao.atom.ManipulatorIndex;
import com.maykop_mmz.ppo.waybillImport.dbase3Dao.atom.OstDBValues;
import com.maykop_mmz.ppo.waybillImport.dbase3Dao.structures.ConsumptionWaybillStructure;
import com.maykop_mmz.ppo.waybillImport.dbase3Dao.structures.IncomingWaybillStructure;
import com.maykop_mmz.ppo.waybillImport.dbase3Dao.structures.OstStructure;
import com.maykop_mmz.ppo.waybillImport.dbase3Dao.types.ConsumptionWaybillRecord;
import com.maykop_mmz.ppo.waybillImport.dbase3Dao.types.IncomingWaybillRecord;
import com.maykop_mmz.ppo.waybillImport.utils.*;
import org.apache.log4j.Logger;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.maykop_mmz.ppo.waybillImport.dbase3Dao.Dbase3Dao.manipulatorIndexHashMap;

public class MainFrame extends JFrame {
    private final Color DEFAULT_GREEN_COLOR = new Color(0, 172, 0);
    private final Color DEFAULT_ORANGE_COLOR = new Color(235, 143, 0);
    private static final String TMP_OST_FILE_NAME = "OST_.dbf";

    private Logger log = Logger.getLogger(Logging.getCurrentClassName());
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
    private JLabel statusLabel;
    private JTextPane textPane;
    private JLabel ostLabel;
    private JLabel prih1Label;
    private JLabel prih3Label;
    private JLabel rash1Label;
    private JLabel rash3Label;
    private JScrollPane scrollPane;
    private JTabbedPane tabbedPane;
    private JPanel mainTab;
    private JPanel processTab;
    private boolean filesChecked = false;

    public MainFrame() {
        createGui();
        fillSettingsToFields();
    }

    private void annulFileCheck() {
        filesChecked = false;
        buttonOK.setText("OK");
    }

    private void backupFile(File file, File path) {
        try {
            pushInfoToTextPane("Создаем копию " + file + " в " + path, Level.INFO);
            FileUtils.copyFile(file, path);
            pushInfoToTextPane("Все ок.", Level.SUCCESS);
        } catch (IOException e) {
            final String newFile = ApplicationConstants.APP_BACKUP_FOLDER + file.getName();

            log.warn("Could not copy file " + file + " to " + newFile);
            pushInfoToTextPane("Не удалось скопировать " + file + " в "
                    + newFile, Level.ERROR);
        }
    }

    private void changeLabelColorsToDefault() {
        ostLabel.setForeground(Color.black);
        prih1Label.setForeground(Color.black);
        rash1Label.setForeground(Color.black);
        prih3Label.setForeground(Color.black);
        rash3Label.setForeground(Color.black);
    }

    private void checkFile(JLabel label, String path) throws FileNotFoundException {
        if (checkFileExistence(path)) {
            label.setForeground(DEFAULT_GREEN_COLOR);
            pushInfoToTextPane("Все ок", Level.SUCCESS);
        } else {
            label.setForeground(Color.red);
            throw new FileNotFoundException("Can not find ost file: " + path);
        }
    }

    private boolean checkFileExistence(String path) {
        return new File(path).exists();
    }

    private void checkFilesLocations() throws FileNotFoundException {
        pushInfoToTextPane("Проверяю путь к файлу остатков", Level.INFO);
        checkFile(ostLabel, ostDbfTextField.getText());

        pushInfoToTextPane("Проверяю путь к файлу прихода СЗ", Level.INFO);
        checkFile(prih1Label, prih1DbfTextField.getText());

        pushInfoToTextPane("Проверяю путь к файлу расхода СЗ", Level.INFO);
        checkFile(rash1Label, rash1DbfTextField.getText());

        pushInfoToTextPane("Проверяю путь к файлу прихода СГ", Level.INFO);
        checkFile(prih3Label, prih3DbfTextField.getText());

        pushInfoToTextPane("Проверяю путь к файлу расхода СГ", Level.INFO);
        checkFile(rash3Label, rash3DbfTextField.getText());

        changeLabelColorsToDefault();

    }

    private void checkFilesStructure() {
        try {
            pushInfoToTextPane("Проверяем структуру файла остатков", Level.INFO);
            checkOstStructure(ostDbfTextField.getText());
            log.debug("Ost structure: " + Dbase3Dao.getOstStructure());

            ostLabel.setForeground(DEFAULT_GREEN_COLOR);
            pushInfoToTextPane("Все ок.", Level.SUCCESS);

            pushInfoToTextPane("Проверяем структуру файла прихода СЗ", Level.INFO);
            Dbase3Dao.checkPrih1Structure(prih1DbfTextField.getText());
            prih1Label.setForeground(DEFAULT_GREEN_COLOR);
            pushInfoToTextPane("Все ок.", Level.SUCCESS);

            pushInfoToTextPane("Проверяем структуру файла расхода СЗ", Level.INFO);
            Dbase3Dao.checkRash1Structure(rash1DbfTextField.getText());
            rash1Label.setForeground(DEFAULT_GREEN_COLOR);
            pushInfoToTextPane("Все ок.", Level.SUCCESS);

            pushInfoToTextPane("Проверяем структуру файла прихода СГ", Level.INFO);
            Dbase3Dao.checkPrih3Structure(prih3DbfTextField.getText());
            prih3Label.setForeground(DEFAULT_GREEN_COLOR);
            pushInfoToTextPane("Все ок.", Level.SUCCESS);

            pushInfoToTextPane("Проверяем структуру файла расхода СГ", Level.INFO);
            Dbase3Dao.checkRash3Structure(rash3DbfTextField.getText());
            rash3Label.setForeground(DEFAULT_GREEN_COLOR);
            pushInfoToTextPane("Все ок.", Level.SUCCESS);

            changeLabelColorsToDefault();
        } catch (DBFException e) {
            log.warn("Something wrong in dbf's structure", e);
            pushInfoToTextPane("Не удалось прочитать файл: " + e.getMessage(), Level.ERROR);
            pushInfoToTextPane("Проверьте, находятся ли базы на своих местах!", Level.WARN);
            throw e;
        }
    }

    private void checkOstStructure(String path) throws DBFException {
        log.info("Scanning structure of ost in: " + path);
        try (DBFReader reader = new DBFReader(new FileInputStream(path), Charset.forName(Dbase3Dao.DEFAULT_DBF_CHARSET))) {
            Dbase3Dao.generateOstStructure(reader, new File(path));
        } catch (DBFException | IOException e) {
            log.warn("Can not read " + path, e);
            throw new DBFException(e);
        }
    }

    private void createGui() {
        setContentPane(contentPane);
        setTitle("Импорт накладных в БД DBase3 - ООО \"ММЗ\"");
        setIconImage(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/img/logo.png")));
        getRootPane().setDefaultButton(buttonOK);

        textPane.setContentType("text/html");
        textPane.setText("<html>");

        buttonOK.addActionListener(e -> onOK());

        buttonCancel.addActionListener(e -> onCancel());

        DocumentListener documentListener = new DocumentListener() {
            @Override
            public void changedUpdate(DocumentEvent e) {
                annulFileCheck();
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                annulFileCheck();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                annulFileCheck();
            }
        };

        ostDbfTextField.getDocument().addDocumentListener(documentListener);
        prih1DbfTextField.getDocument().addDocumentListener(documentListener);
        rash1DbfTextField.getDocument().addDocumentListener(documentListener);
        prih3DbfTextField.getDocument().addDocumentListener(documentListener);
        rash3DbfTextField.getDocument().addDocumentListener(documentListener);

        ostSearchButton.addActionListener(e -> {
            String path = FileUtils.openFileBrowser(this);
            if (path != null) {
                ostDbfTextField.setText(path);
                waybillAutoDetect(path);
            }
        });
        prih1SearchButton.addActionListener(e -> {
            String path = FileUtils.openFileBrowser(this);
            if (path != null) {
                prih1DbfTextField.setText(path);
            }
        });
        rash1SearchButton.addActionListener(e -> {
            String path = FileUtils.openFileBrowser(this);
            if (path != null) {
                rash1DbfTextField.setText(path);
            }
        });
        prih3SearchButton.addActionListener(e -> {
            String path = FileUtils.openFileBrowser(this);
            if (path != null) {
                prih3DbfTextField.setText(path);
            }
        });
        rash3SearchButton.addActionListener(e -> {
            String path = FileUtils.openFileBrowser(this);
            if (path != null) {
                rash3DbfTextField.setText(path);
            }
        });

        textPane.setEditorKit(JEditorPane.createEditorKitForContentType("text/html"));
        textPane.setEditable(false);
        textPane.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void changedUpdate(DocumentEvent e) {
                textPane.setCaretPosition(textPane.getDocument().getLength());
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                textPane.setCaretPosition(textPane.getDocument().getLength());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                textPane.setCaretPosition(textPane.getDocument().getLength());
            }
        });


        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        pack();
        setMinimumSize(getSize());
        setLocation(FrameUtils.getFrameOnCenterLocationPoint(this));
    }

    private void waybillAutoDetect(String path) {
        File folder = new File(path).getParentFile();
        if (folder.exists() && folder.isDirectory()) {
            final String PRIH1_NAME = "prih1.dbf";
            final String PRIH3_NAME = "prih3.dbf";
            final String RASH1_NAME = "rash1.dbf";
            final String RASH3_NAME = "rash3.dbf";
            File prih1_file = new File(folder.getPath() + File.separator + PRIH1_NAME);
            if (prih1_file.exists()) {
                prih1DbfTextField.setText(prih1_file.getAbsolutePath());
            }
            File prih3_file = new File(folder.getPath() + File.separator + PRIH3_NAME);
            if (prih3_file.exists()) {
                prih3DbfTextField.setText(prih3_file.getAbsolutePath());
            }
            File rash1_file = new File(folder.getPath() + File.separator + RASH1_NAME);
            if (rash1_file.exists()) {
                rash1DbfTextField.setText(rash1_file.getAbsolutePath());
            }
            File rash3_file = new File(folder.getPath() + File.separator + RASH3_NAME);
            if (rash3_file.exists()) {
                rash3DbfTextField.setText(rash3_file.getAbsolutePath());
            }
        }
    }

    private void exportListToOst(ArrayList<OstDetailPosition> ostDetailPositions, HashMap<ManipulatorIndex, OstDBValues> manipulatorIndexHashMap, OstStructure structure) {
        final String pathname = structure.getFile().getParent() + File.separator + TMP_OST_FILE_NAME;
        File file = new File(pathname);

        try (DBFReader reader = new DBFReader(new FileInputStream(structure.getFile()),
                Charset.forName(Dbase3Dao.DEFAULT_DBF_CHARSET));
             DBFWriter writer = new DBFWriter(file, Charset.forName(Dbase3Dao.DEFAULT_DBF_CHARSET))) {
            boolean isFileDeleted = file.delete();
            if (!isFileDeleted) {
                log.warn("Couldn't delete file: " + file);
            }


            DBFField[] fields = generateFields(reader);

            try {
                writer.setFields(fields);
            } catch (DBFException e) {
                log.debug("Fields already exist, nevermind");
            }


            ArrayList<Integer> searchingRecordIdsArrayList = Dbase3Dao.getFullListRecordIds(ostDetailPositions);

            for (int i = 0; i < reader.getRecordCount(); i++) {
                Object record[] = reader.nextRecord();
                if (searchingRecordIdsArrayList.contains(i)) {
                    ManipulatorIndex current = new ManipulatorIndex((String) record[structure.getManIndex()], (String) record[structure.getKodIndex()]);
                    if (manipulatorIndexHashMap.containsKey(current)) {
                        OstDBValues values = manipulatorIndexHashMap.get(current);
                        Object[] customRecord = new Object[reader.getFieldCount()];
                        for (int j = 0; j < reader.getFieldCount(); j++) {
                            if (j == structure.getSzIndex()) {
                                customRecord[j] = Dbase3Dao.sumValues((BigDecimal) record[j], values.getSz1());
                            } else if (j == structure.getMzIndex()) {
                                customRecord[j] = Dbase3Dao.sumValues((BigDecimal) record[j], values.getMz1());
                            } else if (j == structure.getSgIndex()) {
                                customRecord[j] = Dbase3Dao.sumValues((BigDecimal) record[j], values.getSg1());
                            } else if (j == structure.getPzIndex()) {
                                customRecord[j] = Dbase3Dao.sumValues((BigDecimal) record[j], values.getPz1());
                            } else {
                                customRecord[j] = record[j];
                            }
                        }
                        writer.addRecord(customRecord);
                        pushInfoToTextPane("Успешно добавлена деталь " + current + "\t" + values + "\tНа строке: " + i, Level.SUCCESS);
                        log.info("Successfully added detail " + current + " " + values + " on id: " + i);
                    } else {
                        log.warn("Detail discrepancy. Wanted: " + current + " on id:" + i);
                        pushInfoToTextPane("Несоответсвие деталей, что-то пошло не так... Ожидалась" + current + " на id: " + i, Level.ERROR);
                    }
                } else {
                    try {
                        writer.addRecord(record);
                    } catch (DBFException e) {
                        log.warn("Could not add record: " + i + " " + Arrays.toString(record));
                    }
                }
            }
        } catch (FileNotFoundException e) {
            log.warn("Could not write file to " + pathname, e);
        }
    }

    private void fillSettingsToFields() {
        log.info("Loading settings to panel");
        ostDbfTextField.setText(PropertiesUtils.getProperty(PropertiesNames.OST_DBF_NAME));
        ostDbfTextField.setCaretPosition(ostDbfTextField.getText().length());

        prih1DbfTextField.setText(PropertiesUtils.getProperty(PropertiesNames.PRIH1_DBF_NAME));
        prih1DbfTextField.setCaretPosition(prih1DbfTextField.getText().length());

        rash1DbfTextField.setText(PropertiesUtils.getProperty(PropertiesNames.RASH1_DBF_NAME));
        rash1DbfTextField.setCaretPosition(rash1DbfTextField.getText().length());

        prih3DbfTextField.setText(PropertiesUtils.getProperty(PropertiesNames.PRIH3_DBF_NAME));
        prih3DbfTextField.setCaretPosition(prih3DbfTextField.getText().length());

        rash3DbfTextField.setText(PropertiesUtils.getProperty(PropertiesNames.RASH3_DBF_NAME));
        rash3DbfTextField.setCaretPosition(rash3DbfTextField.getText().length());
    }

    private ArrayList<OstDetailPosition> findRecords(OstStructure ostStructure, HashMap<ManipulatorIndex, OstDBValues> manipulatorIndexHashMap) throws IOException {
        log.info("Starting collecting all records");
        pushInfoToTextPane("Начинаем готовить список записей, которые нужно изменить в файле остатков.", Level.INFO);
        pushInfoToTextPane("Количество записей, которые нужно найти: " + manipulatorIndexHashMap.size(), Level.INFO);
        return Dbase3Dao.getRecordsList(manipulatorIndexHashMap, ostStructure);
    }

    private DBFField[] generateFields(DBFReader reader) {
        DBFField[] fields = new DBFField[reader.getFieldCount()];

        for (int i = 0; i < reader.getFieldCount(); i++) {
            fields[i] = reader.getField(i);
        }
        return fields;
    }

    private void importPrih1(IncomingWaybillStructure prih1Structure, final Date date) throws IOException {
        log.info("Starting import prih1 since date: " + date);
        pushInfoToTextPane("Начинаем составлять списки изменений для прихода СЗ начиная с " + date, Level.INFO);
        final ArrayList<IncomingWaybillRecord> prih1List = Dbase3Dao.getIncomingWaybillsAfterDateArrayList(prih1Structure, date);
        log.debug("prih1 suitable waybills count: " + prih1List.size());
        pushInfoToTextPane("Необходимо произвести изменения по " + prih1List.size() + " записям", Level.INFO);

        for (IncomingWaybillRecord record : prih1List) {
            ManipulatorIndex manipulatorIndex = record.getManipulatorIndex();

            OstDBValues values = new OstDBValues(record.getCount(), new BigDecimal(0),
                    new BigDecimal(0), new BigDecimal(0));

            if (manipulatorIndexHashMap.containsKey(manipulatorIndex)) {
                OstDBValues summed = manipulatorIndexHashMap.get(manipulatorIndex).add(values);
                manipulatorIndexHashMap.put(manipulatorIndex, summed);
            } else {
                manipulatorIndexHashMap.put(manipulatorIndex, values);
            }
        }

        //DBase3Dao.printMap(manipulatorIndexHashMap);
        log.info("Checked records: " + prih1List.size());
        pushInfoToTextPane("Все ок.", Level.SUCCESS);
    }

    private void importPrih3(IncomingWaybillStructure prih3Structure, Date date) throws IOException {
        log.info("Starting import prih3 since date: " + date);
        pushInfoToTextPane("Начинаем составлять списки изменений для прихода СГ начиная с " + date, Level.INFO);
        ArrayList<IncomingWaybillRecord> prih1List = Dbase3Dao.getIncomingWaybillsAfterDateArrayList(prih3Structure, date);
        log.debug("prih3 suitable waybills count: " + prih1List.size());
        pushInfoToTextPane("Необходимо произвести изменения по " + prih1List.size() + " записям", Level.INFO);
        for (IncomingWaybillRecord record : prih1List) {
            ManipulatorIndex manipulatorIndex = record.getManipulatorIndex();

            OstDBValues values = new OstDBValues(new BigDecimal(0), new BigDecimal(-record.getCount().intValue()),
                    record.getCount(), new BigDecimal(0));

            if (manipulatorIndexHashMap.containsKey(manipulatorIndex)) {
                OstDBValues summed = manipulatorIndexHashMap.get(manipulatorIndex).add(values);
                manipulatorIndexHashMap.put(manipulatorIndex, summed);
            } else {
                manipulatorIndexHashMap.put(manipulatorIndex, values);
            }
        }
        //Dbase3Dao.printMap(manipulatorIndexHashMap);
        log.info("Checked records: " + prih1List.size());
        pushInfoToTextPane("Все ок.", Level.SUCCESS);
    }

    private void importRash1(ConsumptionWaybillStructure rash1Structure, Date date) throws IOException {
        log.info("Starting import rash1 since date: " + date);
        pushInfoToTextPane("Начинаем составлять списки изменений для расхода СЗ начиная с " + date, Level.INFO);
        ArrayList<ConsumptionWaybillRecord> rash1List = Dbase3Dao.getConsumptionWaybillsAfterDateArrayList(rash1Structure, date);
        log.debug("prih1 suitable waybills count: " + rash1List.size());
        pushInfoToTextPane("Необходимо произвести изменения по " + rash1List.size() + " записям", Level.INFO);
        for (ConsumptionWaybillRecord record : rash1List) {
            ManipulatorIndex manipulatorIndex = record.getManipulatorIndex();
            OstDBValues values = null;
            if (record.getStore() == Stores.MZ1) {
                values = new OstDBValues(new BigDecimal(-record.getCount().intValue()), record.getCount(),
                        new BigDecimal(0), new BigDecimal(0));
            } else if (record.getStore() == Stores.PZ1) {
                values = new OstDBValues(new BigDecimal(-record.getCount().intValue()), new BigDecimal(0),
                        new BigDecimal(0), record.getCount());
            } else {
                log.warn("Can not parse record: " + record);
            }

            if (values != null) {
                if (manipulatorIndexHashMap.containsKey(manipulatorIndex)) {
                    OstDBValues summed = manipulatorIndexHashMap.get(manipulatorIndex).add(values);
                    manipulatorIndexHashMap.put(manipulatorIndex, summed);
                } else {
                    manipulatorIndexHashMap.put(manipulatorIndex, values);
                }
            }
        }
        //Dbase3Dao.printMap(manipulatorIndexHashMap);
        log.info("Checked records: " + rash1List.size());
        pushInfoToTextPane("Все ок.", Level.SUCCESS);
    }

    private void importRash3(ConsumptionWaybillStructure rash3Structure, Date date) throws IOException {
        log.info("Starting import rash3 since date: " + date);
        pushInfoToTextPane("Начинаем составлять списки изменений для расхода СГ начиная с " + date, Level.INFO);
        ArrayList<ConsumptionWaybillRecord> rash1List = Dbase3Dao.getConsumptionWaybillsAfterDateArrayList(rash3Structure, date);
        log.debug("prih1 suitable waybills count: " + rash1List.size());
        pushInfoToTextPane("Необходимо произвести изменения по " + rash1List.size() + " записям", Level.INFO);
        for (ConsumptionWaybillRecord record : rash1List) {
            ManipulatorIndex manipulatorIndex = record.getManipulatorIndex();
            OstDBValues values = null;
            if (record.getStore() == Stores.MZ1) {
                values = new OstDBValues(new BigDecimal(0), record.getCount(),
                        new BigDecimal(-record.getCount().intValue()), new BigDecimal(0));
            } else if (record.getStore() == Stores.PZ1) {
                values = new OstDBValues(new BigDecimal(0), new BigDecimal(0),
                        new BigDecimal(-record.getCount().intValue()), record.getCount());
            } else {
                log.warn("Can not parse record: " + record);
            }

            if (values != null) {
                if (manipulatorIndexHashMap.containsKey(manipulatorIndex)) {
                    OstDBValues summed = manipulatorIndexHashMap.get(manipulatorIndex).add(values);
                    manipulatorIndexHashMap.put(manipulatorIndex, summed);
                } else {
                    manipulatorIndexHashMap.put(manipulatorIndex, values);
                }
            }
        }
        //Dbase3Dao.printMap(manipulatorIndexHashMap);
        log.info("Checked records: " + rash1List.size());
        pushInfoToTextPane("Все ок.", Level.SUCCESS);
    }

    private void makeBackup() {
        final Date date = Calendar.getInstance().getTime();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH.mm.ss");
        String fixedDate = df.format(date);
        final String path = ApplicationConstants.APP_BACKUP_FOLDER + File.separator + fixedDate + File.separator;
        Dbase3Dao.backupFolder = new File(path);


        String oldFile = ostDbfTextField.getText();
        backupFile(new File(oldFile), Dbase3Dao.backupFolder);

        oldFile = prih1DbfTextField.getText();
        backupFile(new File(oldFile), Dbase3Dao.backupFolder);

        oldFile = rash1DbfTextField.getText();
        backupFile(new File(oldFile), Dbase3Dao.backupFolder);


        oldFile = prih3DbfTextField.getText();
        backupFile(new File(oldFile), Dbase3Dao.backupFolder);

        oldFile = rash3DbfTextField.getText();
        backupFile(new File(oldFile), Dbase3Dao.backupFolder);
    }

    private void onCancel() {
        dispose();
    }

    private void onOK() {
        if (!filesChecked) {
            updateSettings();
            try {
                tabbedPane.setSelectedComponent(processTab);
                checkFilesLocations();
                checkFilesStructure();
                makeBackup();
                pushInfoToTextPane("Все готово к проведению импорта", Level.SUCCESS);
                filesChecked = true;
            } catch (FileNotFoundException e) {
                final String fileNotFound = "Файл не найден";
                statusLabel.setText(fileNotFound);
                pushInfoToTextPane(fileNotFound, Level.ERROR);
                log.warn("Could not find a file", e);
            } catch (DBFException e) {
                log.warn("Can not read file", e);
                pushInfoToTextPane("Не удалось загрузить файл: " + e.getMessage(), Level.ERROR);
                filesChecked = false;
            }
            if (filesChecked) {
                buttonOK.setText("Начать");
            }
        } else {
            final AcceptingDialog acceptingDialog = new AcceptingDialog();
            acceptingDialog.setVisible(true);
            final boolean result = acceptingDialog.result();
            log.info("Accepted: " + result);
            if (result) {
                pushInfoToTextPane("===========================================", Level.WARN);
                SwingUtilities.invokeLater(() -> startDataTransfer(acceptingDialog));

            }
        }
    }

    private void pushInfoToTextPane(final String text, final Level level) {

        MutableAttributeSet black = new SimpleAttributeSet();
        MutableAttributeSet orange = new SimpleAttributeSet();
        MutableAttributeSet red = new SimpleAttributeSet();
        MutableAttributeSet green = new SimpleAttributeSet();

        StyleConstants.setForeground(black, Color.black);
        StyleConstants.setForeground(orange, DEFAULT_ORANGE_COLOR);
        StyleConstants.setForeground(red, Color.red);
        StyleConstants.setForeground(green, DEFAULT_GREEN_COLOR);

        AttributeSet attribute;

        switch (level) {
            case INFO:
                attribute = black;
                statusLabel.setForeground(Color.black);
                statusLabel.setText(text);
                break;
            case WARN:
                attribute = orange;
                statusLabel.setForeground(DEFAULT_ORANGE_COLOR);
                statusLabel.setText(text);
                break;
            case ERROR:
                attribute = red;
                statusLabel.setForeground(Color.red);
                statusLabel.setText(text);
                break;
            case SUCCESS:
                attribute = green;
                statusLabel.setForeground(DEFAULT_GREEN_COLOR);
                statusLabel.setText(text);
                break;
            default:
                attribute = black;
                statusLabel.setForeground(Color.black);
                statusLabel.setText(text);
                break;
        }

        Runnable runnable = () -> {
            try {
                Document doc = textPane.getStyledDocument();
                doc.insertString(doc.getLength(), text + "\n", attribute);
            } catch (BadLocationException e) {
                log.warn("Could not add line to document", e);
            }
        };
        SwingUtilities.invokeLater(runnable);
    }

    private void updateSettings() {
        log.info("Updating settings");
        PropertiesUtils.setProperty(PropertiesNames.OST_DBF_NAME, ostDbfTextField.getText());
        PropertiesUtils.setProperty(PropertiesNames.PRIH1_DBF_NAME, prih1DbfTextField.getText());
        PropertiesUtils.setProperty(PropertiesNames.RASH1_DBF_NAME, rash1DbfTextField.getText());
        PropertiesUtils.setProperty(PropertiesNames.PRIH3_DBF_NAME, prih3DbfTextField.getText());
        PropertiesUtils.setProperty(PropertiesNames.RASH3_DBF_NAME, rash3DbfTextField.getText());
        PropertiesUtils.saveProperties();
    }

    private void scanList(ArrayList<OstDetailPosition> list) {
        pushInfoToTextPane("Выполняем поиск id в базе остатков по индексу деталей. Всего деталей для изменения: " + list.size(), Level.INFO);

        int succeed = 0;
        int failed = 0;
        for (OstDetailPosition detail : list) {
            if (detail.getRecordsId().size() == 0) {
                log.warn("Could not find " +
                        detail.getIndex() + "\t" +
                        Dbase3Dao.manipulatorIndexHashMap.get(detail.getIndex()) +
                        " at ost db");
                pushInfoToTextPane("Не удалось найти деталь "
                                + detail.getIndex() + "\t" + Dbase3Dao.manipulatorIndexHashMap.get(detail.getIndex()),
                        Level.ERROR);
                failed++;
            } else {
                succeed++;
            }
        }
        pushInfoToTextPane("Всего найдено деталей в базе: " + succeed + " не найдено:" + failed, Level.WARN);
    }

    private void setEnabledUIElements(boolean b) {
        if (b) {
            tabbedPane.setEnabledAt(tabbedPane.indexOfTab("Главная"), true);
        } else {
            tabbedPane.setSelectedComponent(processTab);
            tabbedPane.setEnabledAt(tabbedPane.indexOfTab("Главная"), false);
        }
        buttonOK.setEnabled(b);
        buttonCancel.setEnabled(b);
    }

    @Override
    public void setVisible(boolean b) {
        super.setVisible(b);
        pushInfoToTextPane("Логирование в " + System.getProperty(ApplicationConstants.APP_LOG_PROPERTY), Level.INFO);
    }

    private void startDataTransfer(AcceptingDialog acceptingDialog) {
        if (acceptingDialog.isDateSelected()) {
            final Date selectedDate = acceptingDialog.getSelectedDate();
            log.info("Selected date from: " + selectedDate);
            setEnabledUIElements(false);

            new Thread(new ThreadDispatcher(selectedDate)).start();

        } else {
            pushInfoToTextPane("Дата, с которой нужно добавлять накладные - не указана", Level.ERROR);
        }
    }

    private void startImport(final Date date) {
        try {
            manipulatorIndexHashMap.clear();

            startImportPrih1(date);

            startImportRash1(date);

            startImportPrih3(date);

            startImportRash3(date);


            startExportToOst();

            pushInfoToTextPane("===========================================", Level.WARN);
            pushInfoToTextPane("Импорт накладных в базу остатков успешно завершен", Level.SUCCESS);
            log.info("Import stopped successfully!");


        } catch (IOException e1) {
            log.error("Could not load some waybills, rolling back all operations");
            pushInfoToTextPane(
                    "Не удалось загрузить 1 или более типа накладных " +
                            "в базу остатков. Открываем папку с резервными копиями.", Level.ERROR);
            FileUtils.openFolder(Dbase3Dao.backupFolder);
        }

        setEnabledUIElements(true);
        buttonOK.setEnabled(false);

    }

    private void startExportToOst() throws IOException {
        try {
            pushInfoToTextPane("Собираем список полей, которые необходимо изменить", Level.INFO);
            final ArrayList<OstDetailPosition> list = findRecords(Dbase3Dao.getOstStructure(), Dbase3Dao.manipulatorIndexHashMap);
            pushInfoToTextPane("Всего полей для изменения в базе: " + list.size(), Level.INFO);

            scanList(list);

            String temp_file_path = Dbase3Dao.getOstStructure().getFile().getParent() + File.separator + TMP_OST_FILE_NAME;
            File temp_ost_file = new File(temp_file_path);
            temp_ost_file.delete();

            exportListToOst(list, Dbase3Dao.manipulatorIndexHashMap, Dbase3Dao.getOstStructure());
        } catch (IOException e) {
            log.error("Could not prepare list of changing details or push them to base ost dbf");
            pushInfoToTextPane("Не удалось подготовить список изменяемых деталей или сохранить их в базе остатков", Level.ERROR);
            throw new IOException(e);
        }
    }

    private void startImportRash3(Date date) throws IOException {
        try {
            importRash3(Dbase3Dao.getRash3Structure(), date);
            pushInfoToTextPane("Всего подготовлено к импорту записей: " + manipulatorIndexHashMap.size(), Level.SUCCESS);
        } catch (IOException e) {
            log.error("Can not load consumption waybills from stock of finished parts (rash3)", e);
            pushInfoToTextPane("Не удалось импортировать расход склада готовых деталей (rash3)", Level.ERROR);
            throw new IOException(e);
        }
    }

    private void startImportPrih3(Date date) throws IOException {
        try {
            importPrih3(Dbase3Dao.getPrih3Structure(), date);
            pushInfoToTextPane("Всего подготовлено к импорту записей: " + manipulatorIndexHashMap.size(), Level.SUCCESS);
        } catch (IOException e) {
            log.error("Can not load incoming waybills to stock of finished parts (prih3)", e);
            pushInfoToTextPane("Не удалось импортировать приход склада готовых деталей (prih3)", Level.ERROR);
            throw new IOException(e);
        }
    }

    private void startImportRash1(Date date) throws IOException {
        try {
            importRash1(Dbase3Dao.getRash1Structure(), date);
            pushInfoToTextPane("Всего подготовлено к импорту записей: " + manipulatorIndexHashMap.size(), Level.SUCCESS);
        } catch (IOException e) {
            log.error("Can not load consumption waybills from stock of blanks (rash1)", e);
            pushInfoToTextPane("Не удалось импортировать расход склада заготовок (rash1)", Level.ERROR);
            throw new IOException(e);
        }
    }

    private void startImportPrih1(Date date) throws IOException {
        try {
            importPrih1(Dbase3Dao.getPrih1Structure(), date);
            pushInfoToTextPane("Всего подготовлено к импорту записей: " + manipulatorIndexHashMap.size(), Level.SUCCESS);
        } catch (IOException e) {
            log.error("Can not load incoming waybills to stock of blanks (prih1)", e);
            pushInfoToTextPane("Не удалось импортировать приход склада заготовок (prih1)", Level.ERROR);
            throw new IOException(e);
        }
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        contentPane = new JPanel();
        contentPane.setLayout(new GridLayoutManager(2, 1, new Insets(10, 10, 10, 10), -1, -1));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        contentPane.add(panel1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        panel1.add(spacer1, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1, true, false));
        panel1.add(panel2, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        buttonOK = new JButton();
        buttonOK.setText("ОК");
        panel2.add(buttonOK, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        buttonCancel = new JButton();
        buttonCancel.setText("Отмена");
        panel2.add(buttonCancel, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panel3, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, 1, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        statusLabel = new JLabel();
        statusLabel.setText("");
        statusLabel.setVisible(true);
        panel3.add(statusLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, 1, GridConstraints.SIZEPOLICY_FIXED, new Dimension(0, -1), null, null, 0, false));
        tabbedPane = new JTabbedPane();
        contentPane.add(tabbedPane, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(200, 200), null, 0, false));
        mainTab = new JPanel();
        mainTab.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPane.addTab("Главная", mainTab);
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayoutManager(5, 3, new Insets(0, 0, 0, 0), -1, -1));
        panel4.setName("Hello");
        mainTab.add(panel4, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        ostLabel = new JLabel();
        ostLabel.setText("База остатков (ost.dbf)");
        panel4.add(ostLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        ostDbfTextField = new JTextField();
        panel4.add(ostDbfTextField, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        ostSearchButton = new JButton();
        ostSearchButton.setText("Обзор");
        panel4.add(ostSearchButton, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        prih1Label = new JLabel();
        prih1Label.setText("База прихода СЗ (prih1.dbf)");
        panel4.add(prih1Label, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        prih1DbfTextField = new JTextField();
        panel4.add(prih1DbfTextField, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        prih1SearchButton = new JButton();
        prih1SearchButton.setText("Обзор");
        panel4.add(prih1SearchButton, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        rash1Label = new JLabel();
        rash1Label.setText("База расхода СЗ (rash1.dbf)");
        panel4.add(rash1Label, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        rash1DbfTextField = new JTextField();
        panel4.add(rash1DbfTextField, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        rash1SearchButton = new JButton();
        rash1SearchButton.setText("Обзор");
        panel4.add(rash1SearchButton, new GridConstraints(2, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        prih3Label = new JLabel();
        prih3Label.setText("База прихода СГ (prih3.dbf)");
        panel4.add(prih3Label, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        prih3DbfTextField = new JTextField();
        panel4.add(prih3DbfTextField, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        prih3SearchButton = new JButton();
        prih3SearchButton.setText("Обзор");
        panel4.add(prih3SearchButton, new GridConstraints(3, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        rash3Label = new JLabel();
        rash3Label.setText("База расхода СГ (rash3.dbf)");
        panel4.add(rash3Label, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        rash3DbfTextField = new JTextField();
        panel4.add(rash3DbfTextField, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        rash3SearchButton = new JButton();
        rash3SearchButton.setText("Обзор");
        panel4.add(rash3SearchButton, new GridConstraints(4, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        mainTab.add(spacer2, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        processTab = new JPanel();
        processTab.setLayout(new GridLayoutManager(2, 2, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPane.addTab("Процесс", processTab);
        scrollPane = new JScrollPane();
        scrollPane.putClientProperty("html.disable", Boolean.FALSE);
        processTab.add(scrollPane, new GridConstraints(0, 0, 2, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        textPane = new JTextPane();
        textPane.setContentType("text/html");
        scrollPane.setViewportView(textPane);
        ostLabel.setLabelFor(ostDbfTextField);
        prih1Label.setLabelFor(prih1DbfTextField);
        rash1Label.setLabelFor(rash1DbfTextField);
        prih3Label.setLabelFor(prih3DbfTextField);
        rash3Label.setLabelFor(rash3DbfTextField);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return contentPane;
    }

    enum Level {INFO, SUCCESS, WARN, ERROR}

    class ThreadDispatcher implements Runnable {
        private Date date;

        ThreadDispatcher(Date date) {
            this.date = date;
        }

        public void run() {
            startImport(date);

        }

    }
}
