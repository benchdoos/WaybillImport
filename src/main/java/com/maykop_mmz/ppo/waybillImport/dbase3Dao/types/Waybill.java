package com.maykop_mmz.ppo.waybillImport.dbase3Dao.types;

import com.maykop_mmz.ppo.waybillImport.dbase3Dao.atom.ManipulatorIndex;

import java.util.ArrayList;
import java.util.Date;

public class Waybill {
    // Why string??? because some waybills have no number,
    // and furthermore - they have "number" like '3СВ', '15К' and else.
    // Don't use waybill number to detect waybill
    private String waybillNumber;
    private Date date;
    private int sendFromStore;
    private int sendToStore;
    private ArrayList<WaybillRecord> waybillRecords;

    public int getSendToStore() {
        return sendToStore;
    }

    public void setSendToStore(int sendToStore) {
        this.sendToStore = sendToStore;
    }

    public Date getDate() {
        return new Date(date.getTime());
    }

    public void setDate(Date date) {
        this.date = new Date(date.getTime());
    }


    public ArrayList<WaybillRecord> getWaybillRecords() {
        return waybillRecords;
    }

    public void setWaybillRecords(ArrayList<WaybillRecord> waybillRecords) { //Does it needed????
        this.waybillRecords = waybillRecords;
    }

    public void addRecord(WaybillRecord record) {
        waybillRecords.add(record);
    }

    public void removeRecord(WaybillRecord record) {
        if (waybillRecords.contains(record)) {
            waybillRecords.remove(record);
        }
    }

    public String getWaybillNumber() {
        return waybillNumber;
    }

    public void setWaybillNumber(String waybillNumber) {
        this.waybillNumber = waybillNumber;
    }

    @Override
    public boolean equals(Object o) { //WTF is going on???
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Waybill waybill = (Waybill) o;

        if (sendToStore != waybill.sendToStore) return false;
        if (waybillNumber != null ? !waybillNumber.equals(waybill.waybillNumber) : waybill.waybillNumber != null)
            return false;
        if (date != null ? !date.equals(waybill.date) : waybill.date != null) return false;
        return waybillRecords != null ? waybillRecords.equals(waybill.waybillRecords) : waybill.waybillRecords == null;
    }

    @Override
    public int hashCode() {
        int result = waybillNumber != null ? waybillNumber.hashCode() : 0;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + sendToStore;
        result = 31 * result + (waybillRecords != null ? waybillRecords.hashCode() : 0);
        return result;
    }

    public int getSendFromStore() {
        return sendFromStore;
    }

    public void setSendFromStore(int sendFromStore) {
        this.sendFromStore = sendFromStore;
    }

    public boolean contains(ManipulatorIndex index) {
        for (WaybillRecord record : waybillRecords) {
            if (record.getManipulatorIndex().equals(index)) {
                return true;
            }
        }
        return false;
    }
}
