package com.maykop_mmz.ppo.waybillImport.dbase3Dao.structures;

import java.io.File;

/**
 * Created by Eugene Zrazhevsky on 005 05.10.2017.
 */
public class OstStructure {
    public static final String SZ1_FIELD_NAME = "SZ1";
    public static final String MZ1_FIELD_NAME = "MZ1";
    public static final String SG1_FIELD_NAME = "SG1";
    public static final String PZ1_FIELD_NAME = "PZ1";
    public static final String MAN_FIELD_NAME = "MAN";
    public static final String KOD_FIELD_NAME = "KOD";


    private int szIndex;
    private int mzIndex;
    private int sgIndex;
    private int pzIndex;
    private int manIndex;
    private int kodIndex;
    private File file;

    public OstStructure(int szIndex, int mzIndex, int sgIndex, int pzIndex, int manIndex, int kodIndex,File file) {
        this.szIndex = szIndex;
        this.mzIndex = mzIndex;
        this.sgIndex = sgIndex;
        this.pzIndex = pzIndex;
        this.manIndex = manIndex;
        this.kodIndex = kodIndex;
    }

    public File getFile() {
        return file;
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

    public int getManIndex() {
        return manIndex;
    }

    public void setManIndex(int manIndex) {
        this.manIndex = manIndex;
    }

    public int getMzIndex() {
        return mzIndex;
    }

    public void setMzIndex(int mzIndex) {
        this.mzIndex = mzIndex;
    }

    public int getPzIndex() {
        return pzIndex;
    }

    public void setPzIndex(int pzIndex) {
        this.pzIndex = pzIndex;
    }

    public int getSgIndex() {
        return sgIndex;
    }

    public void setSgIndex(int sgIndex) {
        this.sgIndex = sgIndex;
    }

    public int getSzIndex() {
        return szIndex;
    }

    public void setSzIndex(int szIndex) {
        this.szIndex = szIndex;
    }

    @Override
    public String toString() {
        return "OstStructure{" +
                SZ1_FIELD_NAME + ":" + szIndex + ", " +
                MZ1_FIELD_NAME + ":" + mzIndex + ", " +
                SG1_FIELD_NAME + ":" + sgIndex + ", " +
                PZ1_FIELD_NAME + ":" + pzIndex + ", " +
                MAN_FIELD_NAME + ":" + manIndex + ", " +
                KOD_FIELD_NAME + ":" + kodIndex +
                '}';
    }
}
