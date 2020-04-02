package com.esiee.mbdaihm.datamodel.indicators;

import java.util.DoubleSummaryStatistics;

/**
 * Data model class representing a World Data Index indicator.
 */
public class Indicator
{
    // --------------------------------------------
    // ATTRIBUTES
    // --------------------------------------------
    private String topic;

    private String subTopic;

    private String name;

    private String code;

    private EIndicatorType type;

    private DoubleSummaryStatistics stats;

    // --------------------------------------------
    // GETTERS AND SETTERS
    // --------------------------------------------
    public String getTopic()
    {
        return topic;
    }

    public String getSubTopic()
    {
        return subTopic;
    }

    public String getName()
    {
        return name;
    }

    public String getCode()
    {
        return code;
    }

    public EIndicatorType getType()
    {
        return type;
    }

    /**
     * Retrieve statistics related to this indicator.
     *
     * @return statistics related to this indicator
     */
    public DoubleSummaryStatistics getStats()
    {
        return stats;
    }

    public void setStats(DoubleSummaryStatistics stats)
    {
        this.stats = stats;
    }

    // --------------------------------------------
    // METHODS
    // --------------------------------------------
    @Override
    public String toString()
    {
        return code + " : " + name;
    }

    // --------------------------------------------
    // INNER CLASSES
    // --------------------------------------------
    public static final class Builder
    {
        private String topic;

        private String subTopic;

        private String name;

        private String code;

        private EIndicatorType type;

        public Builder()
        {
        }

        public Builder withTopic(final String topic)
        {
            this.topic = topic;
            return this;
        }

        public Builder withSubTopic(final String subTopic)
        {
            this.subTopic = subTopic;
            return this;
        }

        public Builder withName(final String name)
        {
            this.name = name;
            return this;
        }

        public Builder withCode(final String code)
        {
            this.code = code;
            return this;
        }

        public Builder withType(final EIndicatorType type)
        {
            this.type = type;
            return this;
        }

        public Indicator build()
        {
            final Indicator result = new Indicator();
            result.topic = topic;
            result.subTopic = subTopic;
            result.name = name;
            result.code = code;
            result.type = type;
            return result;
        }

        public Builder mergeFrom(final Indicator src)
        {
            this.topic = src.topic;
            this.subTopic = src.subTopic;
            this.name = src.name;
            this.code = src.code;
            return this;
        }
    }
}
