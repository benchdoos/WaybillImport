package com.maykop_mmz.ppo.waybillImport.dbase3Dao.structures;

import java.io.File;

/**
 * Created by Eugene Zrazhevsky on 005 05.10.2017.
 */
public class Rash1Structure {
    public static final String MAN_FIELD_NAME = "MAN";
    public static final String KOD_FIELD_NAME = "KOD";
    public static final String MES_FIELD_NAME = "MES";
    public static final String DAT_FIELD_NAME = "DAT";
    public static final String SK_FIELD_NAME = "SK";
    public static final String KOL_FIELD_NAME = "KOL";


    private int manIndex;
    private int kodIndex;
    private int mesIndex;
    private int datIndex;
    private int skIndex;
    private int kolIndex;
    private File file;

    public Rash1Structure(int manIndex, int kodIndex, int mesIndex, int datIndex, int skIndex, int kolIndex, File file) {
        this.manIndex = manIndex;
        this.kodIndex = kodIndex;
        this.mesIndex = mesIndex;
        this.datIndex = datIndex;
        this.skIndex = skIndex;
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

    public int getMesIndex() {
        return mesIndex;
    }

    public void setMesIndex(int mesIndex) {
        this.mesIndex = mesIndex;
    }

    public int getSkIndex() {
        return skIndex;
    }

    public void setSkIndex(int skIndex) {
        this.skIndex = skIndex;
    }


    @Override
    public String toString() {
        return "Rash1Structure{" +
                MAN_FIELD_NAME + ":" + manIndex + ", " +
                KOD_FIELD_NAME + ":" + kodIndex + ", " +
                MES_FIELD_NAME + ":" + mesIndex + ", " +
                DAT_FIELD_NAME + ":" + datIndex + ", " +
                SK_FIELD_NAME + ":" + skIndex + ", " +
                KOL_FIELD_NAME + ":" + kolIndex +
                '}';
    }
}
