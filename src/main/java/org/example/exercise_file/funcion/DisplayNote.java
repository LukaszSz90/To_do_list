package org.example.exercise_file.funcion;

import org.example.exercise_file.DataToConn;
import org.example.exercise_file.Note;

import javax.swing.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DisplayNote {

    public void displayPanel() {
        String choice = "";
        while (!choice.equals("0")) {
            choice = displayNotePanel();

            try {
                int choiceNumber = Integer.parseInt(choice);

                if (choiceNumber >= 0 && choiceNumber <= 2) {
                    actionDisplayPanel(choiceNumber);
                } else {
                    JOptionPane.showMessageDialog(null, "Entered wrong number of action, please try again.");
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "You entered  something else than number. Please Try again\n" +
                        "Message: " + "[" + ex.getMessage() + "]");
            }
        }

    }

    private void actionDisplayPanel(int choiceNumber) {
        //Panel to display all notes or a selected one
        switch (choiceNumber) {
            case 1: {
                displayNote(getListOfNotes(choiceNumber, 0));
            }
            break;
            case 2: {
                try (
                        Connection connection = DriverManager.getConnection(DataToConn.getURL(), DataToConn.getDataProperties());
                        Statement statement = connection.createStatement();
                ) {
                    String query = "";
                    query += "SELECT id_list FROM list;";
                    ResultSet resultSet = statement.executeQuery(query);
                    List<Integer> idList = new ArrayList<>();

                    while (resultSet.next()) {
                        idList.add(resultSet.getInt("id_list"));
                    }
                    resultSet.close();
                    connection.close();
                    statement.close();

                    int choiceID = Integer.parseInt(JOptionPane.showInputDialog(
                            "Enable note id: " + idList.toString() + "\n" +
                            "Enter id of not witch you want display: "));
                    displayNote(getListOfNotes(choiceNumber, choiceID));
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Problem with display list, sorry for problem ...");
                }
            }
            break;
            case 0: {
                JOptionPane.showMessageDialog(null, "Back to main menu");
            }
            break;
            default: {
                JOptionPane.showMessageDialog(null, "Something has gone wrong, the programme is closed");
                System.exit(0);
            }
        }
    }

    private void displayNote(List<Note> notes) {

        String toDisplay = "";

        for (int i = 0; i < notes.size(); i++) {
            toDisplay += "[" + notes.get(i).getId() + "] -> TITLE: " + notes.get(i).getTitle() + " | PRIORITY [" + notes.get(i).getPriority() + "]\n" +
                    "\t" + notes.get(i).getDescription() + "\n" +
                    "\tMust do to: " + notes.get(i).getDeadLine() + "\n";
        }

        JOptionPane.showMessageDialog(null, toDisplay);
    }

    private List<Note> getListOfNotes(int choiceNumber, int number) {
        List<Note> listOfNote = new ArrayList<>();

        try (
                Connection connection = DriverManager.getConnection(DataToConn.getURL(), DataToConn.getDataProperties());
                Statement statement = connection.createStatement();
        ) {
            String query = "";
            if (choiceNumber == 1) {
                query += "SELECT id_list, title, description, deadline, priority FROM list;";
            } else {
                query += "SELECT id_list, title, description, deadline, priority FROM list WHERE id_list=" + number + ";";
            }

            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                Note note = new Note(
                        Integer.parseInt(resultSet.getString("id_list")),
                        resultSet.getString("title"),
                        resultSet.getString("description"),
                        LocalDate.parse(resultSet.getString("deadline")),
                        Integer.parseInt(resultSet.getString("priority"))
                );
                listOfNote.add(note);
            }
            resultSet.close();
            connection.close();
            statement.close();
            return listOfNote;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Problem with display list, sorry for problem ...");
        }
        return listOfNote;
    }

    public static List<String> getTitleList() {
        List<String> list = new ArrayList<>();
        try (
                Connection connection = DriverManager.getConnection(DataToConn.getURL(), DataToConn.getDataProperties());
                Statement statement = connection.createStatement();
        ) {
            String query = "SELECT title FROM list;";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                list.add(resultSet.getString("title"));
            }
            resultSet.close();
            connection.close();
            statement.close();
            return list;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Problem with display list, sorry for problem ...");
        }
        return list;
    }

    private String displayNotePanel() {
        String choice = JOptionPane.showInputDialog("Enter command: \n" +
                "[1] Display all notes with details\n" +
                "[2] Display one note with details\n" +
                "[0] Back"
        );
        return choice;
    }
}