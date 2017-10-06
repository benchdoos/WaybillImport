package com.maykop_mmz.ppo.waybillImport.dbase3Dao.atom;

/**
 * Created by Eugene Zrazhevsky on 006 06.10.2017.
 */
@SuppressWarnings("ALL")
public class ManipulatorIndex {
    private String serial;
    private String code;

    public ManipulatorIndex(String serial, String code) {
        this.serial = serial;
        this.code = code;
    }

    public ManipulatorIndex() {
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ManipulatorIndex) {
            ManipulatorIndex compare = (ManipulatorIndex) obj;
            return serial.equals(compare.getSerial()) && code.equals(compare.getCode());
        } else return false;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    @Override
    public int hashCode() {

        final int prime = getClass().hashCode();
        int result = 1;
        result = prime * result + ((serial == null) ? 0 : serial.hashCode());
        result = prime * result + ((code == null) ? 0 : code.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "ManipulatorIndex{" +
                "serial='" + serial + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
