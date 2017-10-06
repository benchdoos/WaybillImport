package com.maykop_mmz.ppo.waybillImport.dbase3Dao.atom;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

/**
 * Created by Eugene Zrazhevsky on 006 06.10.2017.
 */
public class OstDBValuesTest {
    @org.junit.Test
    public void add() throws Exception {
        OstDBValues original = new OstDBValues(
                new BigDecimal(1), new BigDecimal(1),
                new BigDecimal(1), new BigDecimal(1));
        OstDBValues adding = new OstDBValues(
                new BigDecimal(1), new BigDecimal(-11),
                new BigDecimal(9), new BigDecimal(0));

        OstDBValues expecting = new OstDBValues(
                new BigDecimal(2), new BigDecimal(-10),
                new BigDecimal(10), new BigDecimal(1));


        OstDBValues result = original.add(adding);

        assertEquals(expecting, result);
    }

}