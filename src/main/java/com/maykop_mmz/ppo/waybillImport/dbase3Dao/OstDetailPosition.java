package com.maykop_mmz.ppo.waybillImport.dbase3Dao;

import com.maykop_mmz.ppo.waybillImport.dbase3Dao.atom.ManipulatorIndex;

import java.util.ArrayList;

/**
 * Created by Eugene Zrazhevsky on 006 06.10.2017.
 */
public class OstDetailPosition {
    private ManipulatorIndex index;
    private ArrayList<Integer> recordsId = new ArrayList<>();

    private int currentID = 0;

    public OstDetailPosition() {
    }

    public OstDetailPosition(ManipulatorIndex index, ArrayList<Integer> recordsId) {

        this.index = index;
        this.recordsId = recordsId;
    }

    public void addRecordId(int i) {
        recordsId.add(i);
    }

    public ManipulatorIndex getIndex() {

        return index;
    }

    public void setIndex(ManipulatorIndex index) {
        this.index = index;
    }

    public ArrayList<Integer> getRecordsId() {
        return recordsId;
    }

    public void setRecordsId(ArrayList<Integer> recordsId) {
        this.recordsId = recordsId;
    }

    public boolean hasNext() {
        return currentID != this.recordsId.size();
    }

    public Integer hasNextInt() {
        if (currentID != this.recordsId.size()) {
            Integer result = this.recordsId.get(currentID);
            currentID++;
            return result;
        } else {
            return null;
        }
    }

    @Override
    public String toString() {
        return "OstDetailPosition{" +
                "index=" + index +
                ", recordsId=" + recordsId +
                '}';
    }
}
