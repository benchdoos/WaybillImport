package com.maykop_mmz.ppo.waybillImport.dbase3Dao.types;

import com.maykop_mmz.ppo.waybillImport.dbase3Dao.atom.ManipulatorIndex;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Eugene Zrazhevsky on 006 06.10.2017.
 */
public class ConsumptionWaybillRecord {
    private int recordId;
    private ManipulatorIndex manipulatorIndex;
    private Date date; //тут только месяц и число (mes,dat)
    private int store; //склад
    private BigDecimal count; //kol

    public ConsumptionWaybillRecord(int recordId, ManipulatorIndex manipulatorIndex, Date date, int store, BigDecimal count) {

        this.recordId = recordId;
        this.manipulatorIndex = manipulatorIndex;
        this.date = date;
        this.store = store;
        this.count = count;
    }

    public ConsumptionWaybillRecord() {
    }

    public BigDecimal getCount() {
        return count;
    }

    public void setCount(BigDecimal count) {
        this.count = count;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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

    public int getStore() {
        return store;
    }

    public void setStore(int store) {
        this.store = store;
    }

    @Override
    public String toString() {
        return "ConsumptionWaybillRecord{" +
                "recordId=" + recordId +
                ", manipulatorIndex=" + manipulatorIndex +
                ", date=" + date +
                ", store=" + store +
                ", count=" + count +
                '}';
    }
}
