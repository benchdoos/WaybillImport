package com.maykop_mmz.ppo.waybillImport.dbase3Dao.statistics;

public class StatisticsManager {
    private static StatisticsManager manager = new StatisticsManager();

    public static StatisticsManager getInstance() {
        return manager;
    }

    private StatisticsManager() {}


}
