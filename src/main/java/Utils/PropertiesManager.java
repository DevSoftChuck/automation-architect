package Utils;
import Setup.TestEnvironment;

import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class PropertiesManager {

    private static volatile PropertiesManager instance = null;
    private static final Properties config = new Properties();

    private PropertiesManager(){
        try{
           config.load(Files.newInputStream(Paths.get("src/test/resources/config.properties")));
        }catch (Exception ex){
            TestEnvironment.logger.error("Error when opening config.properties!!!");
            ex.printStackTrace();
        }
    }

    /**
     * Optimized version of thread-safe Singleton version using locking
     * a) Check that the variable is initialized without6 obtaining the lock. If it is initialized return it immediately.
     * b) Obtain the lock.
     * c) Double-check whether the variable has already been initialized.
     *      if another thread acquired the lock first, it may have already dopne the initialization.
     *      If so, return the initialized variable.
*    * d) Otherwise, initialize and return the variable.
     * @return Property instance
     * @author Ivan Andraschko
     */
    public static PropertiesManager createInstance(){
        PropertiesManager localref = instance;
        if(localref == null){
            synchronized (PropertiesManager.class){
                localref = instance;
                if(localref == null){
                    instance = localref = new PropertiesManager();
                }
            }
        }
        return localref;
    }

    /**
     * Returns the value from the config file.
     * @param propertyName property name that we want its value
     * @return value from propertyName
     */
    public static String getConfig(EPropertiesNames propertyName) {
        createInstance();
        return config.getProperty(propertyName.name());
    }
}
