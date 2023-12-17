package service;

import app.IslandConfiguration;
import constant.Constants;
import model.EatingProperties;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import utilize.ConfigReader;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ConfigManager {

    static volatile ConfigManager configManager;

    EatingProperties eatingProperties;
    IslandConfiguration islandConfiguration;

    public ConfigManager(EatingProperties eatingProperties, IslandConfiguration islandConfiguration) {
        this.eatingProperties = eatingProperties;
        this.islandConfiguration = islandConfiguration;
    }

    public static ConfigManager getConfigManager() {
        if (configManager == null) {
            synchronized (ConfigManager.class) {
                if (configManager == null) {
                    configManager = new ConfigManager(
                        ConfigReader.readConfig(Constants.EATING_PROPERTIES_PATH,EatingProperties.class),
                        ConfigReader.readConfig(Constants.ISLAND_CONFIG_PATH, IslandConfiguration.class)
                    );
                }
            }
        }
        return configManager;
    }

}
