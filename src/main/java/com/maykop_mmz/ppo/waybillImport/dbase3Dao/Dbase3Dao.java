package com.maykop_mmz.ppo.waybillImport.dbase3Dao;

import com.linuxense.javadbf.*;
import com.maykop_mmz.ppo.waybillImport.dbase3Dao.atom.ManipulatorIndex;
import com.maykop_mmz.ppo.waybillImport.dbase3Dao.atom.OstDBValues;
import com.maykop_mmz.ppo.waybillImport.dbase3Dao.structures.ConsumptionWaybillStructure;
import com.maykop_mmz.ppo.waybillImport.dbase3Dao.structures.IncomingWaybillStructure;
import com.maykop_mmz.ppo.waybillImport.dbase3Dao.structures.OstStructure;
import com.maykop_mmz.ppo.waybillImport.dbase3Dao.types.ConsumptionWaybillRecord;
import com.maykop_mmz.ppo.waybillImport.dbase3Dao.types.IncomingWaybillRecord;
import com.maykop_mmz.ppo.waybillImport.utils.Logging;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.*;

/**
 * Created by Eugene Zrazhevsky on 005 05.10.2017.
 */
public class Dbase3Dao {
    public static final String DEFAULT_DBF_CHARSET = "cp866";
    public static final HashMap<ManipulatorIndex, OstDBValues> manipulatorIndexHashMap = new HashMap<>();
    public static File backupFolder;
    private static OstStructure ostStructure;
    private static IncomingWaybillStructure prih1Structure;
    private static ConsumptionWaybillStructure rash1Structure;
    private static Logger log = Logger.getLogger(Logging.getCurrentClassName());
    private static IncomingWaybillStructure prih3Structure;
    private static ConsumptionWaybillStructure rash3Structure;

    public static OstStructure getOstStructure() {
        return ostStructure;
    }

    private static void setOstStructure(OstStructure ostStructure) {
        Dbase3Dao.ostStructure = ostStructure;
    }

    public static IncomingWaybillStructure getPrih1Structure() {
        return prih1Structure;
    }

    public static void setPrih1Structure(IncomingWaybillStructure prih1Structure) {
        Dbase3Dao.prih1Structure = prih1Structure;
    }

    public static void generateOstStructure(DBFReader reader, File file) {
        log.debug("Checking ost fields: " + reader.getFieldCount());

        int szIndex = getFieldNumberByName(reader, OstStructure.SZ1_FIELD_NAME);
        int mzIndex = getFieldNumberByName(reader, OstStructure.MZ1_FIELD_NAME);
        int sgIndex = getFieldNumberByName(reader, OstStructure.SG1_FIELD_NAME);
        int pzIndex = getFieldNumberByName(reader, OstStructure.PZ1_FIELD_NAME);
        int manIndex = getFieldNumberByName(reader, OstStructure.MAN_FIELD_NAME);
        int kodIndex = getFieldNumberByName(reader, OstStructure.KOD_FIELD_NAME);

        OstStructure structure = new OstStructure(szIndex, mzIndex, sgIndex, pzIndex, manIndex, kodIndex, file);
        log.info("Checked structure ost: " + structure);
        Dbase3Dao.setOstStructure(structure);
    }

    private static int getFieldNumberByName(DBFReader reader, String fieldName) {
        fieldName = fieldName.toUpperCase();
        for (int i = 0; i < reader.getFieldCount(); i++) {
            if (reader.getField(i).getName().equals(fieldName)) {
                return i;
            }
        }
        throw new DBFException("Field not found exception :" + fieldName);
    }

    public static IncomingWaybillStructure generateIncomingWaybillStructure(DBFReader reader, File file) {
        log.debug("Checking prih1 fields: " + reader.getFieldCount());

        int manIndex = getFieldNumberByName(reader, IncomingWaybillStructure.MAN_FIELD_NAME);
        int kodIndex = getFieldNumberByName(reader, IncomingWaybillStructure.KOD_FIELD_NAME);
        int datIndex = getFieldNumberByName(reader, IncomingWaybillStructure.DAT_FIELD_NAME);
        int kolIndex = getFieldNumberByName(reader, IncomingWaybillStructure.KOL_FIELD_NAME);

        for (int i = 0; i < reader.getFieldCount(); i++) {
            DBFField field = reader.getField(i);
            if (i == manIndex) {
                DBFDataType dbfDataType = field.getType();
                char ch = dbfDataType.getCharCode();
                if (ch != 'C') {
                    throw new DBFException("Field MAN is not a String: " + dbfDataType);
                }
            }
            if (i == kodIndex) {
                DBFDataType dbfDataType = field.getType();
                char ch = dbfDataType.getCharCode();
                if (ch != 'C') {
                    throw new DBFException("Field KOD is not a String: " + dbfDataType);
                }
            }

            if (i == datIndex) {
                DBFDataType dbfDataType = field.getType();
                char ch = dbfDataType.getCharCode();
                if (ch != 'D') {
                    throw new DBFException("Field DAT is not a Date: " + dbfDataType);
                }
            }

            if (i == kolIndex) {
                DBFDataType dbfDataType = field.getType();
                char ch = dbfDataType.getCharCode();
                if (ch != 'N') {
                    throw new DBFException("Field KOL is not a String: " + dbfDataType);
                }
            }
        }


        return new IncomingWaybillStructure(manIndex, kodIndex, datIndex, kolIndex, file);
    }

    public static ConsumptionWaybillStructure generateConsumptionWaybillStructure(DBFReader reader, File file) {
        log.debug("Checking rash1 fields: " + reader.getFieldCount());

        int manIndex = getFieldNumberByName(reader, ConsumptionWaybillStructure.MAN_FIELD_NAME);
        int kodIndex = getFieldNumberByName(reader, ConsumptionWaybillStructure.KOD_FIELD_NAME);
        int mesIndex = getFieldNumberByName(reader, ConsumptionWaybillStructure.MES_FIELD_NAME);
        int datIndex = getFieldNumberByName(reader, ConsumptionWaybillStructure.DAT_FIELD_NAME);
        int skIndex = getFieldNumberByName(reader, ConsumptionWaybillStructure.SK_FIELD_NAME);
        int kolIndex = getFieldNumberByName(reader, ConsumptionWaybillStructure.KOL_FIELD_NAME);

        return new ConsumptionWaybillStructure(manIndex, kodIndex, mesIndex, datIndex, skIndex, kolIndex, file);
    }

    public static ConsumptionWaybillStructure getRash1Structure() {
        return rash1Structure;
    }

    public static void setRash1Structure(ConsumptionWaybillStructure rash1Structure) {
        Dbase3Dao.rash1Structure = rash1Structure;
    }

    public static IncomingWaybillStructure getPrih3Structure() {
        return prih3Structure;
    }

    public static void setPrih3Structure(IncomingWaybillStructure prih3Structure) {
        Dbase3Dao.prih3Structure = prih3Structure;
    }

    public static ConsumptionWaybillStructure getRash3Structure() {
        return rash3Structure;
    }

    public static void setRash3Structure(ConsumptionWaybillStructure rash3Structure) {
        Dbase3Dao.rash3Structure = rash3Structure;
    }

    public static void printMap(Map mp) {
        Iterator it = mp.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            System.out.println(pair.getKey() + " = " + pair.getValue());
            it.remove(); // avoids a ConcurrentModificationException
        }
    }

    public static ArrayList<IncomingWaybillRecord> getIncomingWaybillsAfterDateArrayList(IncomingWaybillStructure structure, Date date) throws IOException {
        ArrayList<IncomingWaybillRecord> prih1List = new ArrayList<>();
        DBFReader reader = null;
        try {
            reader = new DBFReader(new FileInputStream(structure.getFile()), Charset.forName(Dbase3Dao.DEFAULT_DBF_CHARSET));
            Object[] rowObjects;

            Date lastWaybillDate = date;

            for (int i = 0; i < reader.getRecordCount(); i++) {
                rowObjects = reader.nextRecord();

                IncomingWaybillRecord waybillRecord = new IncomingWaybillRecord();
                waybillRecord.setRecordId(i);

                String serial = (String) rowObjects[structure.getManIndex()];
                String code = (String) rowObjects[structure.getKodIndex()];
                waybillRecord.setManipulatorIndex(new ManipulatorIndex(serial, code));

                waybillRecord.setDate((Date) rowObjects[structure.getDatIndex()]);
                waybillRecord.setCount((BigDecimal) rowObjects[structure.getKolIndex()]);

                if (date != null && waybillRecord.getDate() != null) {
                    if (waybillRecord.getDate().equals(date) || waybillRecord.getDate().after(date)) {
                        prih1List.add(waybillRecord);

                        if (!lastWaybillDate.equals(waybillRecord.getDate())) {
                            lastWaybillDate = waybillRecord.getDate();
                            log.info("Added incoming waybill by date: " + lastWaybillDate);
                        }
                    }
                }
            }
            return prih1List;
        } catch (DBFException | IOException e) {
            throw new IOException("Can not read information from prih1 dbf", e);
        } finally {
            DBFUtils.close(reader);
        }
    }

    public static ArrayList<ConsumptionWaybillRecord> getConsumptionWaybillsAfterDateArrayList(
            ConsumptionWaybillStructure structure, Date date) throws IOException {
        ArrayList<ConsumptionWaybillRecord> consumptionList = new ArrayList<>();
        DBFReader reader = null;
        try {
            reader = new DBFReader(new FileInputStream(structure.getFile()), Charset.forName(Dbase3Dao.DEFAULT_DBF_CHARSET));
            Object[] rowObjects;

            Date lastWaybillDate = date;

            for (int i = 0; i < reader.getRecordCount(); i++) {
                rowObjects = reader.nextRecord();

                ConsumptionWaybillRecord waybillRecord = new ConsumptionWaybillRecord();
                waybillRecord.setRecordId(i);

                String serial = (String) rowObjects[structure.getManIndex()];
                String code = (String) rowObjects[structure.getKodIndex()];
                waybillRecord.setManipulatorIndex(new ManipulatorIndex(serial, code));

                String dayString = (String) rowObjects[structure.getDayIndex()];
                String monthString = (String) rowObjects[structure.getMesIndex()];
                int day;
                int month;
                int store;
                try {
                    day = Integer.parseInt(dayString);
                    month = Integer.parseInt(monthString) - 1;//in java first month is 0, in dbfs - 1

                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(date);
                    calendar.set(Calendar.DAY_OF_MONTH, day);
                    calendar.set(Calendar.MONTH, month);
                    Date waybillDate = calendar.getTime();

                    waybillRecord.setDate(waybillDate);

                    store = Integer.parseInt((String) rowObjects[structure.getSkIndex()]);

                    waybillRecord.setStore(store);
                    waybillRecord.setCount((BigDecimal) rowObjects[structure.getKolIndex()]);

                    if (waybillRecord.getDate() != null) {
                        if (waybillRecord.getDate().equals(date) || waybillRecord.getDate().after(date)) {
                            consumptionList.add(waybillRecord);

                            if (!lastWaybillDate.equals(waybillRecord.getDate())) {
                                lastWaybillDate = waybillRecord.getDate();
                                log.info("Added incoming waybill by date: " + lastWaybillDate);
                            }
                        }
                    }
                } catch (NumberFormatException e) {
                    log.warn("Could not read day and month for record: " + waybillRecord.getRecordId());
                }
            }
            return consumptionList;
        } catch (DBFException | IOException e) {
            throw new IOException("Can not read information from prih1 dbf", e);
        } finally {
            DBFUtils.close(reader);
        }
    }

    public static ArrayList<OstDetailPosition> getRecordsList(HashMap<ManipulatorIndex, OstDBValues> map, OstStructure structure) throws IOException {
        ArrayList<OstDetailPosition> details;

        details = importSearchingDetails(map);


        DBFReader reader = null;
        try {
            reader = new DBFReader(new FileInputStream(structure.getFile()), Charset.forName(DEFAULT_DBF_CHARSET));
            Object[] rowObjects;

            for (int i = 0; i < reader.getRecordCount(); i++) {
                rowObjects = reader.nextRecord();

                String serial = (String) rowObjects[structure.getManIndex()];
                String code = (String) rowObjects[structure.getKodIndex()];
                ManipulatorIndex index = new ManipulatorIndex(serial, code);

                if (map.containsKey(index)) {
                    for (int j = 0; j < details.size(); j++) {
                        OstDetailPosition detail = details.get(j);
                        if (detail.getIndex().equals(index)) {
                            detail.addRecordId(i);
                        }
                        details.set(j, detail);

                    }
                }
            }
            return details;
        } catch (DBFException | IOException e) {
            throw new IOException("Can not read information from prih1 dbf", e);
        } finally {
            DBFUtils.close(reader);
        }
    }

    private static ArrayList<OstDetailPosition> importSearchingDetails(HashMap<ManipulatorIndex, OstDBValues> map) {
        ArrayList<OstDetailPosition> list = new ArrayList<>();

        for (Object o : map.entrySet()) {
            Map.Entry pair = (Map.Entry) o;
            OstDetailPosition position = new OstDetailPosition();
            position.setIndex((ManipulatorIndex) pair.getKey());
            list.add(position);
            //it.remove(); // avoids a ConcurrentModificationException
        }
        return list;
    }

    public static ArrayList<Integer> getFullListRecordIds(ArrayList<OstDetailPosition> ostDetailPositions) {
        ArrayList<Integer> result = new ArrayList<>();
        for (OstDetailPosition detailPosition : ostDetailPositions) {
            final ArrayList<Integer> recordsIdList = detailPosition.getRecordsId();
            if (recordsIdList.size() > 0) {
                result.addAll(recordsIdList);
            }
        }

        return result;
    }

    public static Object sumValues(BigDecimal v1, BigDecimal v2) {
        if (v1 == null) {
            v1 = new BigDecimal(0);
        }

        if (v2 == null) {
            v2 = new BigDecimal(0);
        }
        return v1.add(v2);
    }

    public static void checkPrih1Structure(String path) {
        log.info("Scanning structure of prih1 in: " + path);
        DBFReader reader = null;
        try {
            reader = new DBFReader(new FileInputStream(path), Charset.forName(Dbase3Dao.DEFAULT_DBF_CHARSET));
            IncomingWaybillStructure structure = Dbase3Dao.generateIncomingWaybillStructure(reader, new File(path));
            Dbase3Dao.setPrih1Structure(structure);
        } catch (DBFException | IOException e) {
            log.warn("Can not read " + path, e);
            throw new DBFException(e);
        } finally {
            DBFUtils.close(reader);
        }
    }

    public static void checkPrih3Structure(String path) {
        log.info("Scanning structure of prih3 in: " + path);
        DBFReader reader = null;
        try {
            reader = new DBFReader(new FileInputStream(path), Charset.forName(Dbase3Dao.DEFAULT_DBF_CHARSET));
            IncomingWaybillStructure structure = Dbase3Dao.generateIncomingWaybillStructure(reader, new File(path));
            Dbase3Dao.setPrih3Structure(structure);
        } catch (DBFException | IOException e) {
            log.warn("Can not read " + path, e);
            throw new DBFException(e);
        } finally {
            DBFUtils.close(reader);
        }
    }

    public static void checkRash1Structure(String path) {
        log.info("Scanning structure of rash1 in: " + path);
        DBFReader reader = null;
        try {
            reader = new DBFReader(new FileInputStream(path), Charset.forName(Dbase3Dao.DEFAULT_DBF_CHARSET));
            final ConsumptionWaybillStructure structure = Dbase3Dao.generateConsumptionWaybillStructure(reader, new File(path));
            Dbase3Dao.setRash1Structure(structure);

        } catch (DBFException | IOException e) {
            log.warn("Can not read " + path, e);
            throw new DBFException(e);
        } finally {
            DBFUtils.close(reader);
        }
    }

    public static void checkRash3Structure(String path) {
        log.info("Scanning structure of rash3 in: " + path);
        DBFReader reader = null;
        try {
            reader = new DBFReader(new FileInputStream(path), Charset.forName(Dbase3Dao.DEFAULT_DBF_CHARSET));
            final ConsumptionWaybillStructure structure = Dbase3Dao.generateConsumptionWaybillStructure(reader, new File(path));
            Dbase3Dao.setRash3Structure(structure);

        } catch (DBFException | IOException e) {
            log.warn("Can not read " + path, e);
            throw new DBFException(e);
        } finally {
            DBFUtils.close(reader);
        }
    }
}
