package org.example.exercise_file.funcion;

import com.sun.source.tree.CatchTree;
import org.example.exercise_file.DataToConn;
import org.example.exercise_file.Note;

import javax.swing.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CreateNote {
    public void createPanel() {
        Note note = dataToCreateNote();

        String queryToAddNote = "INSERT INTO list (title, description, deadline, priority) VALUES (?, ?, ?, ?)";

        try (
                Connection connection = DriverManager.getConnection(DataToConn.getURL(), DataToConn.getDataProperties());
                PreparedStatement preparedStatement = connection.prepareStatement(queryToAddNote);
        ) {
            preparedStatement.setString(1, note.getTitle());
            preparedStatement.setString(2, note.getDescription());
            preparedStatement.setDate(3, Date.valueOf(note.getDeadLine()));
            preparedStatement.setInt(4, note.getPriority());
            preparedStatement.executeUpdate();

            connection.close();
            preparedStatement.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error when adding a new note." + "\n" +
                    "Error message: " + ex.getMessage() + "\n" +
                    "SQL state: " + ex.getSQLState());
        }

        JOptionPane.showMessageDialog(null,
                "Created node:\n" +
                        "Title: " + note.getTitle() + "\n" +
                        "Description: " + note.getDescription() + "\n" +
                        "Deadline: " + note.getDeadLine() + "\n" +
                        "Priority: " + note.getPriority() + "\n" +
                        "-----New note add with success-----"
        );

    }


    private Note dataToCreateNote() {
        String title = JOptionPane.showInputDialog("Enter a title for the note: ");
        String description = JOptionPane.showInputDialog("Enter a description for the note: ");

        String deadlineString = enterDateWithFormatAndCheckIsCorrect();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate deadline = LocalDate.parse(deadlineString, formatter);

        Integer priority = null;
        boolean isCorrectPriority = false;
        while (!isCorrectPriority) {
            try {
                priority = Integer.parseInt(JOptionPane.showInputDialog("Enter a priority (1-10) for the note: "));
                if (priority > 10 || priority < 1) {
                    JOptionPane.showMessageDialog(null, "The priority number is off the scale of 1-10, try again.");
                    continue;
                }
                isCorrectPriority = true;
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Incorrect priority number entered, try again.");
            }
        }

        return new Note(title, description, deadline, priority);
    }

    private String enterDateWithFormatAndCheckIsCorrect() {
        String deadlineString = JOptionPane.showInputDialog("Enter deadline for completion of task (date format: year-month-day).");

        deadlineString = correctingData(deadlineString);

        while (!checkIsFormatAndDateCorrect(deadlineString)) {
            deadlineString = JOptionPane.showInputDialog("The date entered has an incorrect format or the day/month is incorrect!\n" +
                    "Enter deadline for completion of task again (date format: year-month-day).");
        }
        return deadlineString;
    }

    private String correctingData(String deadlineString) {
        String[] splitDate = deadlineString.split("-");
        StringBuilder buildDate = new StringBuilder();
        buildDate.append(splitDate[0]).append("-");

        if (splitDate[1].length() < 2) {
            buildDate.append("0").append(splitDate[1]).append("-");
        } else {
            buildDate.append(splitDate[1]).append("-");
        }

        if (splitDate[2].length() < 2) {
            buildDate.append("0").append(splitDate[2]);
        } else {
            buildDate.append(splitDate[2]);
        }

        return buildDate.toString();
    }


    private boolean checkIsFormatAndDateCorrect(String deadlineString) {
        boolean isFormatCorrect = deadlineString.matches("([0-9]{4})-([0-9]{2})-([0-9]{2})");
        boolean isDateCorrect;
        String[] date = deadlineString.split("-");

        if (date.length < 3) {
            isDateCorrect = false;
        } else {
            int monthCheck = Integer.parseInt(date[1]);
            int dayCheck = Integer.parseInt(date[2]);
            isDateCorrect = (monthCheck <= 12 && monthCheck >= 1) && (dayCheck <= 31 && dayCheck >= 1);
        }
        return isFormatCorrect && isDateCorrect;
    }


}
