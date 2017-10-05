package com.maykop_mmz.ppo.waybillImport.dbase3Dao;

import com.linuxense.javadbf.DBFException;
import com.linuxense.javadbf.DBFReader;
import com.maykop_mmz.ppo.waybillImport.dbase3Dao.structures.*;
import com.maykop_mmz.ppo.waybillImport.utils.Logging;
import org.apache.log4j.Logger;

import java.io.File;

/**
 * Created by Eugene Zrazhevsky on 005 05.10.2017.
 */
public class DBase3Dao {
    public static final String DEFAULT_DBF_CHARSET = "cp866";

    private static OstStructure ostStructure;
    private static Prih1Structure prih1Structure;
    private static Rash1Structure rash1Structure;
    private static Logger log = Logger.getLogger(Logging.getCurrentClassName());
    private static Prih3Structure prih3Structure;
    private static Rash3Structure rash3Structure;

    public static OstStructure getOstStructure() {
        return ostStructure;
    }

    public static void setOstStructure(OstStructure ostStructure) {
        DBase3Dao.ostStructure = ostStructure;
    }

    public static Prih1Structure getPrih1Structure() {
        return prih1Structure;
    }

    public static void setPrih1Structure(Prih1Structure prih1Structure) {
        DBase3Dao.prih1Structure = prih1Structure;
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
        DBase3Dao.setOstStructure(structure);
    }


    public static int getFieldNumberByName(DBFReader reader, String fieldName) {
        fieldName = fieldName.toUpperCase();
        for (int i = 0; i < reader.getFieldCount(); i++) {
            if (reader.getField(i).getName().equals(fieldName)) {
                return i;
            }
        }
        throw new DBFException("Field not found exception :" + fieldName);
    }

    public static void generatePrih1Structure(DBFReader reader, File file) {
        log.debug("Checking prih1 fields: " + reader.getFieldCount());

        int manIndex = getFieldNumberByName(reader, Prih1Structure.MAN_FIELD_NAME);
        int kodIndex = getFieldNumberByName(reader, Prih1Structure.KOD_FIELD_NAME);
        int datIndex = getFieldNumberByName(reader, Prih1Structure.DAT_FIELD_NAME);
        int kolIndex = getFieldNumberByName(reader, Prih1Structure.KOL_FIELD_NAME);

        Prih1Structure prih1Structure = new Prih1Structure(manIndex, kodIndex, datIndex, kolIndex, file);
        log.info("Checked structure prih1: " + prih1Structure);
        DBase3Dao.setPrih1Structure(prih1Structure);
    }


    public static void generateRash1Structure(DBFReader reader, File file) {
        log.debug("Checking rash1 fields: " + reader.getFieldCount());

        int manIndex = getFieldNumberByName(reader, Rash1Structure.MAN_FIELD_NAME);
        int kodIndex = getFieldNumberByName(reader, Rash1Structure.KOD_FIELD_NAME);
        int mesIndex = getFieldNumberByName(reader, Rash1Structure.MES_FIELD_NAME);
        int datIndex = getFieldNumberByName(reader, Rash1Structure.DAT_FIELD_NAME);
        int skIndex = getFieldNumberByName(reader, Rash1Structure.SK_FIELD_NAME);
        int kolIndex = getFieldNumberByName(reader, Rash1Structure.KOL_FIELD_NAME);

        Rash1Structure rash1Structure = new Rash1Structure(manIndex, kodIndex, mesIndex, datIndex, skIndex, kolIndex, file);
        log.info("Checked structure rash1: " + rash1Structure);
        DBase3Dao.setRash1Structure(rash1Structure);
    }

    public static Rash1Structure getRash1Structure() {
        return rash1Structure;
    }

    public static void setRash1Structure(Rash1Structure rash1Structure) {
        DBase3Dao.rash1Structure = rash1Structure;
    }

    public static void generatePrih3Structure(DBFReader reader, File file) {
        log.debug("Checking prih1 fields: " + reader.getFieldCount());

        int manIndex = getFieldNumberByName(reader, Rash1Structure.MAN_FIELD_NAME);
        int kodIndex = getFieldNumberByName(reader, Rash1Structure.KOD_FIELD_NAME);
        int datIndex = getFieldNumberByName(reader, Rash1Structure.DAT_FIELD_NAME);
        int kolIndex = getFieldNumberByName(reader, Rash1Structure.KOL_FIELD_NAME);

        Prih3Structure prih3Structure = new Prih3Structure(manIndex, kodIndex, datIndex, kolIndex, file);
        log.info("Checked structure rash1: " + prih3Structure);
        DBase3Dao.setPrih3Structure(prih3Structure);
    }

    public static Prih3Structure getPrih3Structure() {
        return prih3Structure;
    }

    public static void setPrih3Structure(Prih3Structure prih3Structure) {
        DBase3Dao.prih3Structure = prih3Structure;
    }

    public static void generateRash3Structure(DBFReader reader, File file) {
        log.debug("Checking rash3 fields: " + reader.getFieldCount());

        int manIndex = getFieldNumberByName(reader, Rash3Structure.MAN_FIELD_NAME);
        int kodIndex = getFieldNumberByName(reader, Rash3Structure.KOD_FIELD_NAME);
        int mesIndex = getFieldNumberByName(reader, Rash3Structure.MES_FIELD_NAME);
        int datIndex = getFieldNumberByName(reader, Rash3Structure.DAT_FIELD_NAME);
        int skIndex = getFieldNumberByName(reader, Rash3Structure.SK_FIELD_NAME);
        int kolIndex = getFieldNumberByName(reader, Rash3Structure.KOL_FIELD_NAME);

        Rash3Structure rash3Structure = new Rash3Structure(manIndex, kodIndex, mesIndex, datIndex, skIndex, kolIndex, file);
        log.info("Checked structure rash1: " + rash3Structure);
        DBase3Dao.setRash3Structure(rash3Structure);
    }

    public static Rash3Structure getRash3Structure() {
        return rash3Structure;
    }

    public static void setRash3Structure(Rash3Structure rash3Structure) {
        DBase3Dao.rash3Structure = rash3Structure;
    }
}
