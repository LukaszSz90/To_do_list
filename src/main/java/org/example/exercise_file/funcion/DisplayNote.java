package org.example.exercise_file.funcion;

import org.example.exercise_file.DataToConn;
import org.example.exercise_file.Note;
import org.example.exercise_file.UserPanel;

import javax.swing.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class DisplayNote {

    public void displayPanel() {
        String choice = "";
        ArrayList<Integer> idList = getIdList();
        while (!choice.equals("0")) {
            choice = displayNotePanel();

            try {
                int selectedId = Integer.parseInt(choice);

                if (selectedId >= 0 && isIdExist(selectedId, idList)) {
                    displayNote(selectedId);
                } else {
                    if (selectedId != 0) {
                        JOptionPane.showMessageDialog(null, "The ID you entered does not exist, try again.");
                    }
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "You entered  something else than number. Please Try again\n" +
                        "Message: " + "[" + ex.getMessage() + "]");
            }
        }

    }

    private boolean isIdExist(int selectedId, ArrayList<Integer> idList) {
        for (int i = 0; i < idList.size(); i++) {
            if (idList.get(i) == selectedId) {
                return true;
            }
        }
        return false;
    }

    private ArrayList<Integer> getIdList() {
        ArrayList<Integer> list = new ArrayList<>();

        try (
                Connection connection = DriverManager.getConnection(DataToConn.getURL(), DataToConn.getDataProperties());
                Statement statement = connection.createStatement();
        ) {

            String query = "SELECT id_list FROM list;";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                list.add(resultSet.getInt("id_list"));
            }

            resultSet.close();
            connection.close();
            statement.close();

            return list;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error when select note element." + "\n" +
                    "Error message: " + ex.getMessage() + "\n" +
                    "SQL state: " + ex.getSQLState());
        }
        return list;
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


