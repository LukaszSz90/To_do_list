package org.example.exercise_file;

import java.time.LocalDate;
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

    public static class Note {
        private  int id;

        private String title;
        private String description;
        private LocalDate deadLine;
        private int priority;

        public Note(String title, String description, LocalDate deadLine, int priority) {
            this.title = title;
            this.description = description;
            this.deadLine = deadLine;
            this.priority = priority;
        }

        public Note(int id, String title, String description, LocalDate deadLine, int priority) {
            this.id = id;
            this.title = title;
            this.description = description;
            this.deadLine = deadLine;
            this.priority = priority;
        }

        public int getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }

        public LocalDate getDeadLine() {
            return deadLine;
        }

        public int getPriority() {
            return priority;
        }
    }
}
