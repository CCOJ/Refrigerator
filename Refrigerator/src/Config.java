import java.util.Properties;

/**
 * @author Noah, Randy
 * This reads in "properties.cfg" and set up all default values for refrigerator
 */
public class Config {

    private Properties configFile;

    /**
     * Constructor obtains file and reads in data
     */
    public Config() {
        configFile = new java.util.Properties();
        try {
            configFile.load(this.getClass().getClassLoader().
                    getResourceAsStream("properties.cfg"));
        } catch (Exception eta) {
            eta.printStackTrace();
        }
    }

    /**
     * Returns associated property of a key value pair
     * @param key
     * @return
     */
    public int getProperty(String key) {
        return Integer.parseInt(this.configFile.getProperty(key));
    }
}
