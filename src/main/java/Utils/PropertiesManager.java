package Utils;

import org.aeonbits.owner.Accessible;
import org.aeonbits.owner.Config.Sources;
import org.aeonbits.owner.ConfigCache;

@Sources("file:src/test/resources/config.properties")
public interface PropertiesManager extends Accessible{

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
