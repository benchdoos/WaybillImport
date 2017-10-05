package com.maykop_mmz.ppo.waybillImport.dbase3Dao.structures;

import java.io.File;

/**
 * Created by Eugene Zrazhevsky on 005 05.10.2017.
 */
public class Prih3Structure {
    public static final String MAN_FIELD_NAME = "MAN";
    public static final String KOD_FIELD_NAME = "KOD";
    public static final String DAT_FIELD_NAME = "DAT";
    public static final String KOL_FIELD_NAME = "KOL";

    private int manIndex;
    private int kodIndex;
    private int datIndex;
    private int kolIndex;
    private File file;


    public Prih3Structure() {
    }

    public Prih3Structure(int manIndex, int kodIndex, int datIndex, int kolIndex, File file) {
        this.manIndex = manIndex;
        this.kodIndex = kodIndex;
        this.datIndex = datIndex;
        this.kolIndex = kolIndex;
        this.file = file;
    }

    public int getDatIndex() {
        return datIndex;
    }

    public File getFile() {
        return file;
    }

    public void setDatIndex(int datIndex) {
        this.datIndex = datIndex;
    }

    public int getKodIndex() {
        return kodIndex;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public void setKodIndex(int kodIndex) {
        this.kodIndex = kodIndex;
    }

    public int getKolIndex() {
        return kolIndex;
    }

    public void setKolIndex(int kolIndex) {
        this.kolIndex = kolIndex;
    }

    public int getManIndex() {
        return manIndex;
    }

    public void setManIndex(int manIndex) {
        this.manIndex = manIndex;
    }

    @Override
    public String toString() {
        return "Prih3Structure{" +
                MAN_FIELD_NAME + ":" + manIndex + ", " +
                KOD_FIELD_NAME + ":" + kodIndex + ", " +
                DAT_FIELD_NAME + ":" + datIndex + ", " +
                KOL_FIELD_NAME + ":" + kolIndex +
                "}";
    }
}
