package org.example.exercise_file.funcion;

import org.example.exercise_file.DataToConn;
import org.example.exercise_file.Note;
import org.example.exercise_file.UserPanel;

import javax.swing.*;
import javax.xml.crypto.Data;
import java.sql.*;
import java.time.LocalDate;

public class DisplayNote {

    public void displayPanel() {
        String choice = "";
        while (!choice.equals("0")) {
            choice = displayNotePanel();

            try {
                int selectedId = Integer.parseInt(choice);
// todo zrobić zabezpieczenie do przed wprowadzeniem id nieistniejącego
                if (selectedId > 0) {
                    displayNote(selectedId);
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "You entered  something else than number. Please Try again\n" +
                        "Message: " + "[" + ex.getMessage() + "]");
            }
        }

    }

    private String displayNotePanel() {
        String choice = JOptionPane.showInputDialog(
                "Notes in database: \n" +
                        UserPanel.buildTextFromList() +
                        "=-------------------------------------\n" +
                        "Command: \n" +
                        "[1] Enter ID of note to view details\n" +
                        "[0] Back"
        );
        return choice;
    }


    private void displayNote(int selectedId) {
        String query = "SELECT * FROM list WHERE id_list = " + selectedId + ";";

        try (
                Connection connection = DriverManager.getConnection(DataToConn.getURL(), DataToConn.getDataProperties());
                Statement statement = connection.createStatement();
        ) {
            ResultSet resultSet = statement.executeQuery(query);

            Note note = null;

            while (resultSet.next()) {
                int id = resultSet.getInt("id_list");
                String title = resultSet.getString("title");
                String description = resultSet.getString("description");
                LocalDate deadline = resultSet.getDate("deadline").toLocalDate();
                int priority = resultSet.getInt("priority");
                note = new Note(id, title, description, deadline, priority);
            }

            resultSet.close();
            connection.close();
            statement.close();

            StringBuilder buildText = new StringBuilder();

            buildText.append("[").append(note.getId()).append("] -> ")
                    .append(note.getTitle()).append("---").append(note.getDescription()).append("\n")
                    .append("Deadline: ").append(note.getDeadLine()).append("\n")
                    .append("Priority: ").append(note.getPriority()).append("\n");

            JOptionPane.showMessageDialog(null, buildText.toString());

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error when select note element." + "\n" +
                    "Error message: " + ex.getMessage() + "\n" +
                    "SQL state: " + ex.getSQLState());
        }
    }


}


