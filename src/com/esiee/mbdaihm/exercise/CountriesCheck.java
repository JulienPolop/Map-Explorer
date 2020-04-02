package com.esiee.mbdaihm.exercise;

import com.esiee.mbdaihm.Launch;
import com.esiee.mbdaihm.datamodel.DataManager;
import com.esiee.mbdaihm.datamodel.countries.Country;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Class used to check if natural earth file is properly loaded.
 */
public class CountriesCheck
{
    private static final Logger LOGGER = LoggerFactory.getLogger(CountriesCheck.class);

    public static void main(String[] args)
    {
        Launch.initData();

        // Query the world regions
        String regions = DataManager.INSTANCE.getWorldRegions().getRegionValues().stream().collect(Collectors.joining(",","[","]"));

        //System.err.println("regions ="+ regions);
        System.out.println("regions ="+ regions);
        //LOGGER.info(regions);
        

		// What are the "sub regions" composing regions
        // DataManager.INSTANCE.getWorldRegions().getSubRegionsMap()

        // Query the "FRA" country code
    }
}
