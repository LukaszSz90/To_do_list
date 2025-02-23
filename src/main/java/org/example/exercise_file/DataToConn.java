package org.example.exercise_file;

import java.util.Properties;

public class DataToConn {
        final static String USER = "postgres";
        final static String PASSWORD = "postgres";
        final static String URL = "jdbc:postgresql://localhost:5432/to_do_list";


public static Properties getDataProperties() {
    Properties info = new Properties();
    info.put("user", USER);
    info.put("password", PASSWORD);
    return info;
}

    public static String getURL() {
        return URL;
    }
}
