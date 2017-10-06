package com.maykop_mmz.ppo.waybillImport.dbase3Dao.structures;

import java.io.File;

/**
 * Created by Eugene Zrazhevsky on 005 05.10.2017.
 */
public class ConsumptionWaybillStructure {
    public static final String MAN_FIELD_NAME = "MAN";
    public static final String KOD_FIELD_NAME = "KOD";
    public static final String MES_FIELD_NAME = "MES";
    public static final String DAT_FIELD_NAME = "DAT";
    public static final String SK_FIELD_NAME = "SK";
    public static final String KOL_FIELD_NAME = "KOL";


    private int manIndex;
    private int kodIndex;
    private int mesIndex;
    private int dayIndex;
    private int skIndex;
    private int kolIndex;
    private File file;

    public ConsumptionWaybillStructure(int manIndex, int kodIndex, int mesIndex, int dayIndex, int skIndex, int kolIndex, File file) {
        this.manIndex = manIndex;
        this.kodIndex = kodIndex;
        this.mesIndex = mesIndex;
        this.dayIndex = dayIndex;
        this.skIndex = skIndex;
        this.kolIndex = kolIndex;
        this.file = file;
    }

    public int getDayIndex() {
        return dayIndex;
    }

    public File getFile() {
        return file;
    }

    public void setDayIndex(int dayIndex) {
        this.dayIndex = dayIndex;
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
        return "ConsumptionWaybillStructure{" +
                MAN_FIELD_NAME + ":" + manIndex + ", " +
                KOD_FIELD_NAME + ":" + kodIndex + ", " +
                MES_FIELD_NAME + ":" + mesIndex + ", " +
                DAT_FIELD_NAME + ":" + dayIndex + ", " +
                SK_FIELD_NAME + ":" + skIndex + ", " +
                KOL_FIELD_NAME + ":" + kolIndex +
                '}';
    }
}
