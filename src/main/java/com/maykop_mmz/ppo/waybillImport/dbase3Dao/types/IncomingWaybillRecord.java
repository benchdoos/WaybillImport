package com.maykop_mmz.ppo.waybillImport.dbase3Dao.types;

import com.maykop_mmz.ppo.waybillImport.dbase3Dao.atom.ManipulatorIndex;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Eugene Zrazhevsky on 005 05.10.2017.
 */
public class IncomingWaybillRecord {
    private int recordId;
    private ManipulatorIndex manipulatorIndex;
    private Date date;
    private BigDecimal count;
    public IncomingWaybillRecord() {
    }
    public IncomingWaybillRecord(int recordId, ManipulatorIndex manipulatorIndex, Date date, BigDecimal count) {
        this.recordId = recordId;
        this.manipulatorIndex = manipulatorIndex;
        this.date = new Date(date.getTime());
        this.count = count;
    }

    public Date getDate() {
        return new Date(date.getTime());
    }

    public void setDate(Date date) {
        this.date = new Date(date.getTime());
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
    public String toString() {
        return "IncomingWaybillRecord{" +
                "recordId=" + recordId +
                ", manipulatorIndex=" + manipulatorIndex +
                ", date=" + date +
                ", count=" + count +
                '}';
    }
}
