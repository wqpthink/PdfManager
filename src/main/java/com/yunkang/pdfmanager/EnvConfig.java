package com.yunkang.pdfmanager;
import lombok.Data;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;


public class EnvConfig {
//    private String InputRootDir;
//    private Integer OutputPictureDensity;
//    private String OutputStorageModel;

    public static final String PROPERTY_FILE ="d:\\config.properties";
    private static Map<String,String> propertyMap = new HashMap<String,String>();;

    static {
        Properties prop = new Properties();
        try {
            File file = new File(PROPERTY_FILE);
            InputStream is = new FileInputStream(file);
            prop.load(is);
            propertyMap.put("input.root.dir",prop.getProperty("input.root.dir"));
            propertyMap.put("output.picture.density",prop.getProperty("output.picture.density"));
            propertyMap.put("output.storage.model",prop.getProperty("output.storage.model"));
            propertyMap.put("output.picture.dir",prop.getProperty("output.picture.dir"));
            propertyMap.put("output.picture.suffix",prop.getProperty("output.picture.suffix"));
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

//        ResourceBundle bundle = ResourceBundle.getBundle("d:\\application.properties");
//        Enumeration<String> keys = bundle.getKeys();
//        while (keys.hasMoreElements()){
//            String k = keys.nextElement();
//            String v = bundle.getString(k);
//            propertyMap.put(k,v);
//        }
    }

    public static String getProperty(String key){
        return propertyMap.get(key);
    }
}
