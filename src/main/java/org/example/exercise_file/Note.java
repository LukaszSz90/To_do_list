package org.example.exercise_file;

import java.time.LocalDate;

public class Note {
    private  int id;
    private String title;
    private String description;
    private LocalDate deadLine;
    private int priority;

    public Note(int id, String title) {
        this.id = id;
        this.title = title;
    }

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