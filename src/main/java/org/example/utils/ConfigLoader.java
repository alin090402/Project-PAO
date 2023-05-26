package org.example.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConfigLoader {

    private Properties appProp = null;
    private static ConfigLoader instance;
    private String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();

    private ConfigLoader(){
    }

    private Properties LoadConfig(String path){
        Properties prop = new Properties();
        try {
            prop.load(new FileInputStream(path));
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        return prop;
    }


    public static ConfigLoader getInstance() {
        if (instance == null) {
            instance = new ConfigLoader();
        }
        return instance;
    }

    public Properties getAppProp() {
        if(appProp == null)
        {
            appProp = LoadConfig(rootPath + "application.properties");

        }
        return appProp;
    }
}
