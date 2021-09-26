package Utils;
import java.io.FileInputStream;
import java.util.Properties;

public class PropertiesManager {

    private static PropertiesManager instance = null;
    private final Properties config = new Properties();

    private PropertiesManager(){
        try{
           config.load(new FileInputStream("src/test/resources/config.properties"));
        }catch (Exception ex){
            System.out.println("Error when opening config.properties!!!");
            ex.printStackTrace();
        }
    }

    /**
     * Return Properties instance.
     * @return
     */
    public static PropertiesManager getInstance(){
        if(instance == null){
            instance = new PropertiesManager();
        }
        return instance;
    }

    /**
     * Returns the value from the config file.
     * @param propertyName
     * @return
     */
    public String getConfig(EPropertiesNames propertyName) {
        return this.config.getProperty(propertyName.name());
    }
}
