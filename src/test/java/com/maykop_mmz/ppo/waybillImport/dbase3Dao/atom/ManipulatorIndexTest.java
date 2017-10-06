package com.maykop_mmz.ppo.waybillImport.dbase3Dao.atom;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Eugene Zrazhevsky on 006 06.10.2017.
 */
public class ManipulatorIndexTest {
    @Test
    public void equals() throws Exception {
        ManipulatorIndex original = new ManipulatorIndex("1","2");
        ManipulatorIndex test1 = new ManipulatorIndex("1","2");
        ManipulatorIndex test2 = new ManipulatorIndex("3","1");
        assertEquals(original.equals(test1), true);
        assertEquals(original.equals(test2), false);
    }

}