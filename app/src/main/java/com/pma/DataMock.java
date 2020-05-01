package com.pma;

import com.pma.model.DailySummary;

import java.util.ArrayList;
import java.util.Date;

public class DataMock {

    public static ArrayList<DailySummary> getDailySummaries() {
        DailySummary ds1 = new DailySummary(new Date(), 2100, 2330);
        DailySummary ds2 = new DailySummary(new Date(), 2200, 2230);
        DailySummary ds3 = new DailySummary(new Date(), 2800, 2390);
        ArrayList<DailySummary> retVal = new ArrayList<>();
        retVal.add(ds1);
        retVal.add(ds2);
        retVal.add(ds3);
        return retVal;
    }
}