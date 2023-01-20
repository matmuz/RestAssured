package config;

import org.aeonbits.owner.ConfigCache;

public class ConfigurationRetriever {

    public static Configuration getConfiguration() {
        return ConfigCache.getOrCreate(Configuration.class, System.getProperties());
    }
}
