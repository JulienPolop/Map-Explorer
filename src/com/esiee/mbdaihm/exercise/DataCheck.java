package com.esiee.mbdaihm.exercise;

import com.esiee.mbdaihm.Launch;
import com.esiee.mbdaihm.dataaccess.wdi.RawWDIData;
import com.esiee.mbdaihm.dataaccess.wdi.WDIDataDecoder;
import com.esiee.mbdaihm.datamodel.DataManager;
import com.esiee.mbdaihm.datamodel.countries.Country;
import com.esiee.mbdaihm.datamodel.indicators.Indicator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Optional;

/**
 * Class used to demonstrate how to parse a specific indicator.
 */
public class DataCheck
{
    private static final Logger LOGGER = LoggerFactory.getLogger(DataCheck.class);

    public static void main(String[] args)
    {
        Launch.initData();

        LOGGER.info("------------- LIFE EXPECTANCY FEMALE ITALY -----------------------");

        List<RawWDIData> lifeExpectancyWomen
                = WDIDataDecoder.decode(Launch.WDI_FOLDER, "SP.DYN.LE00.FE.IN");

        // Print the life expectancy for 1982
        Optional<Double> ita = lifeExpectancyWomen.stream().filter(rd -> rd.countryCode.equals("ITA")).map(rd -> rd.getValueForYear(1982)).findFirst();
        ita.ifPresent(System.out::println);
       
        // Print the life expectancy for all years
      
        LOGGER.info("------------------------------------------------------------------");

        LOGGER.info("-------- COUNTRY WITH HIGHEST POPULATION DENSITY 2014 ------------");
        // http://data.worldbank.org/indicator/EN.POP.DNST?locations=MO

        List<RawWDIData> density = WDIDataDecoder.decode(Launch.WDI_FOLDER, "EN.POP.DNST");

        LOGGER.info("------------------------------------------------------------------");
    }
}
