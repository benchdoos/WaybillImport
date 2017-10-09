package com.maykop_mmz.ppo.waybillImport.dbase3Dao.structures;

import com.sun.istack.internal.NotNull;

import java.io.File;

public class WaybillStructure {

    public static final String MAN_FIELD_NAME = "MAN";
    public static final String KOD_FIELD_NAME = "KOD";
    public static final String KOL_FIELD_NAME = "KOL";

    private int manIndex;
    private int kodIndex;
    private int datIndex;
    private int kolIndex;
    private File file;

    public WaybillStructure() {
    }

    public WaybillStructure(int manIndex, int kodIndex, int datIndex, int kolIndex,
                                    @NotNull File file) {
        this.manIndex = manIndex;
        this.kodIndex = kodIndex;
        this.datIndex = datIndex;
        this.kolIndex = kolIndex;
        this.file = file;
    }

    public int getDatIndex() {
        return datIndex;
    }

    public void setDatIndex(int datIndex) {
        this.datIndex = datIndex;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public int getKodIndex() {
        return kodIndex;
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
        return "waybillStructure{" +
                MAN_FIELD_NAME + ":" + manIndex + ", " +
                KOD_FIELD_NAME + ":" + kodIndex + ", " +
                KOL_FIELD_NAME + ":" + kolIndex +
                "}";
    }
}
