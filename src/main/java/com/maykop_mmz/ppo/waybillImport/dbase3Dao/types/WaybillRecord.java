package com.maykop_mmz.ppo.waybillImport.dbase3Dao.types;

import com.maykop_mmz.ppo.waybillImport.dbase3Dao.atom.ManipulatorIndex;

import java.math.BigDecimal;
import java.util.Date;

public class WaybillRecord {

    private int recordId;
    private ManipulatorIndex manipulatorIndex;
    private BigDecimal count; //kol

    public WaybillRecord(int recordId, ManipulatorIndex manipulatorIndex, BigDecimal count) {

        this.recordId = recordId;
        this.manipulatorIndex = manipulatorIndex;
        this.count = count;
    }

    public WaybillRecord() {
    }

    public BigDecimal getCount() {
        return count;
    }

    public void setCount(BigDecimal count) {
        this.count = count;
    }


    public ManipulatorIndex getManipulatorIndex() {
        return manipulatorIndex;
    }

    public void setManipulatorIndex(ManipulatorIndex manipulatorIndex) {
        this.manipulatorIndex = manipulatorIndex;
    }

    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }


    @Override
    public boolean equals(Object o) {
        if (o instanceof WaybillRecord) {
            WaybillRecord wb = (WaybillRecord) o;
            boolean recordIdsEqual = wb.getRecordId() == recordId;
            boolean countEqual = wb.getCount().equals(count);
            boolean manipulatorIndexEqual = wb.getManipulatorIndex().equals(manipulatorIndex);

            return recordIdsEqual && countEqual && manipulatorIndexEqual;

        } else return false;
    }

    @Override
    public int hashCode() {
        int result = recordId;
        result = 31 * result + (manipulatorIndex != null ? manipulatorIndex.hashCode() : 0);
        result = 31 * result + (count != null ? count.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "WaybillRecord{" +
                "id=" + recordId +
                ", manipulatorIndex=" + manipulatorIndex.getShortString() +
                ", count=" + count +
                '}';
    }
}
