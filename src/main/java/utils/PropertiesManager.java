package utils;

import org.aeonbits.owner.Accessible;
import org.aeonbits.owner.Config.Sources;
import org.aeonbits.owner.ConfigCache;

@Sources("file:src/test/resources/config.properties")
public interface PropertiesManager extends Accessible{

    @Key("BASE_URL")
    String BASE_URL();

    @Key("PASSWORD")
    String PASSWORD();

    /**
     * Returns the value from the config file
     * @param propertyName property name that we want its value
     * @return value from propertyName
     */
    static String getConfig(String propertyName){
        return ConfigCache.getOrCreate(PropertiesManager.class).getProperty(propertyName);
    }

    static PropertiesManager getConfig(){
        return ConfigCache.getOrCreate(PropertiesManager.class);
    }
}
