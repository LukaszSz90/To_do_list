package org.example.exercise_file.funcion;

import org.example.exercise_file.DataToConn;

import javax.swing.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CreateNote {
    public void createPanel() {
        DataToConn.Note note = dataToCreateNote();


    }


    private DataToConn.Note dataToCreateNote() {
        String title = JOptionPane.showInputDialog("Enter a title for the note: ");
        String description = JOptionPane.showInputDialog("Enter a description for the note: ");
        String deadlineString = enterDateWithFormatAndCheckIsCorrect();



        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate deadline = LocalDate.parse(deadlineString, formatter);

        Integer priority = 1;

        return new DataToConn.Note(title, description, deadline, priority);
    }

    private String enterDateWithFormatAndCheckIsCorrect() {
        String deadlineString = JOptionPane.showInputDialog("Enter deadline for completion of task (date format: year-month-day).");
        String[] date = deadlineString.split("-");
        int monthCheck = Integer.parseInt(date[1]);
        int dayCheck = Integer.parseInt(date[2]);

        boolean isFormatTrue = deadlineString.matches("([0-9]{4})-([0-9]{2})-([0-9]{2})");
        boolean isDateTrue = checkDate(monthCheck, dayCheck);

        //todo sprawdzić to pod kątek korekty daty czy jest poprawna
        while (isFormatTrue && isDateTrue) {
            if (isDateTrue) {
                deadlineString = JOptionPane.showInputDialog("Entered incorrect month or day number!\n" +
                        "Enter deadline for completion of task again (date format: year-month-day).");
                date = deadlineString.split("-");
                monthCheck = Integer.parseInt(date[1]);
                dayCheck = Integer.parseInt(date[2]);
                if (checkDate(monthCheck, dayCheck)) {

                }
            } else {
                deadlineString = JOptionPane.showInputDialog("A date was entered in the wrong format!\n" +
                        "Enter deadline for completion of task again (date format: year-month-day).");
            }
        }
        return deadlineString;
    }

    private boolean checkDate(int monthCheck, int dayCheck) {
        return (monthCheck > 12 || monthCheck < 1) && (dayCheck > 31 || dayCheck < 1);
    }


}
