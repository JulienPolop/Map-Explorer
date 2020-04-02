package com.esiee.mbdaihm.exercise;

import com.esiee.mbdaihm.Launch;
import com.esiee.mbdaihm.datamodel.DataManager;
import com.esiee.mbdaihm.datamodel.indicators.Indicator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class used to check if WDI Data SeriesCheck is properly loaded.
 */
public class IndicatorsCheck
{
    private static final Logger LOGGER = LoggerFactory.getLogger(IndicatorsCheck.class);

    public static void main(String[] args)
    {
        Launch.initData();

        // Print the indicators related to agriculture
         DataManager.INSTANCE.getIndicators().map(Indicator::getName).filter(n-> n.contains("agriculture")).forEach(System.out::println);
        // Print the indicators related to the Topic List
         DataManager.INSTANCE.getIndicators().map(Indicator::getTopic).distinct().sorted().forEach(System.out::println);
    }
}
