package com.maykop_mmz.ppo.waybillImport.dbase3Dao.atom;

import java.math.BigDecimal;

/**
 * Created by Eugene Zrazhevsky on 006 06.10.2017.
 */
public class OstDBValues {
    private BigDecimal sz1;
    private BigDecimal mz1;
    private BigDecimal sg1;
    private BigDecimal pz1;

    public OstDBValues(BigDecimal sz1, BigDecimal mz1, BigDecimal sg1, BigDecimal pz1) {
        this.sz1 = sz1;
        this.mz1 = mz1;
        this.sg1 = sg1;
        this.pz1 = pz1;
    }

    public OstDBValues() {

    }

    public OstDBValues add(OstDBValues ostDBValues) {
        OstDBValues result = new OstDBValues();
        result.setSz1(sz1.add(ostDBValues.getSz1()));
        result.setMz1(mz1.add(ostDBValues.getMz1()));
        result.setSg1(sg1.add(ostDBValues.getSg1()));
        result.setPz1(pz1.add(ostDBValues.getPz1()));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof OstDBValues) {
            OstDBValues compare = (OstDBValues) obj;
            return sz1.equals(compare.getSz1()) && mz1.equals(compare.getMz1())
                    && sg1.equals(compare.getSg1()) && pz1.equals(compare.getPz1());
        } else return false;
    }

    public BigDecimal getMz1() {
        return mz1;
    }

    public void setMz1(BigDecimal mz1) {
        this.mz1 = mz1;
    }

    public BigDecimal getPz1() {
        return pz1;
    }

    public void setPz1(BigDecimal pz1) {
        this.pz1 = pz1;
    }

    public BigDecimal getSg1() {
        return sg1;
    }

    public void setSg1(BigDecimal sg1) {
        this.sg1 = sg1;
    }

    public BigDecimal getSz1() {
        return sz1;
    }

    public void setSz1(BigDecimal sz1) {
        this.sz1 = sz1;
    }

    @Override
    public String toString() {
        return "OstDBValues{" +
                "sz1=" + sz1 +
                ", mz1=" + mz1 +
                ", sg1=" + sg1 +
                ", pz1=" + pz1 +
                '}';
    }
}
