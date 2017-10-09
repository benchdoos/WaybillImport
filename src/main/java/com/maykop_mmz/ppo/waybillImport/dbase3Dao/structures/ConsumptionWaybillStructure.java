package com.maykop_mmz.ppo.waybillImport.dbase3Dao.structures;

import java.io.File;

/**
 * Created by Eugene Zrazhevsky on 005 05.10.2017.
 */
public class ConsumptionWaybillStructure extends WaybillStructure {
    public static final String MES_FIELD_NAME = "MES";
    public static final String DAT_FIELD_NAME = "DAT";
    public static final String SK_FIELD_NAME = "SK";


    private int mesIndex;
    private int dayIndex;
    private int skIndex;

    public ConsumptionWaybillStructure(int manIndex, int kodIndex, int mesIndex, int datIndex, int skIndex, int kolIndex, File file) {

    ///TODO WHAAAAT?
    }


    public int getDayIndex() {
        return dayIndex;
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
                MAN_FIELD_NAME + ":" + super.getManIndex() + ", " +
                KOD_FIELD_NAME + ":" + super.getKodIndex() + ", " +
                MES_FIELD_NAME + ":" + mesIndex + ", " +
                DAT_FIELD_NAME + ":" + dayIndex + ", " +
                SK_FIELD_NAME + ":" + skIndex + ", " +
                KOL_FIELD_NAME + ":" + super.getKolIndex() +
                '}';
    }
}
