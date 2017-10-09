package com.maykop_mmz.ppo.waybillImport.dbase3Dao.structures;

import com.sun.istack.internal.NotNull;

import java.io.File;
import java.util.Date;

import static com.maykop_mmz.ppo.waybillImport.dbase3Dao.structures.ConsumptionWaybillStructure.DAT_FIELD_NAME;

/**
 * Created by Eugene Zrazhevsky on 006 06.10.2017.
 */
public class IncomingWaybillStructure extends WaybillStructure {
    public static final String DAT_FIELD_NAME = "DAT";

    private int dateIndex;

    public IncomingWaybillStructure(int dateIndex) {
        this.dateIndex = dateIndex;
    }

    public IncomingWaybillStructure(int manIndex, int kodIndex, int datIndex, int kolIndex, File file, int dateIndex) {
        super(manIndex, kodIndex, datIndex, kolIndex, file);
        this.dateIndex = dateIndex;
    }

    @Override
    public String toString() {
        return "IncomingWaybillStructure{" +
                MAN_FIELD_NAME + ":" + super.getManIndex() + ", " +
                KOD_FIELD_NAME + ":" + super.getKodIndex() + ", " +
                DAT_FIELD_NAME + ":" + super.getDatIndex() + ", " +
                KOL_FIELD_NAME + ":" + super.getKolIndex() +
                "}";
    }


    public int getDateIndex() {
        return dateIndex;
    }

    public void setDateIndex(int dateIndex) {
        this.dateIndex = dateIndex;
    }
}
