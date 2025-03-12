package org.example.exercise_file.funcion;

import org.example.exercise_file.DataToConn;
import org.example.exercise_file.Note;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UpdateNote {
    public void updatePanel() {
        String choice = "";
        while (!choice.equals("0")) {
            choice = displayUpdatePanel();

            try {
                int choiceNumber = Integer.parseInt(choice);

                if (choiceNumber >= 0 || isEnterCorrectId(choiceNumber)) {
                    String subChoice = whatUpdatePanel();
                    int subChoiceNumber = Integer.parseInt(subChoice);

                    switch (subChoiceNumber) {
                        case 1: {
                            //update title
                        }
                        break;
                        case 2: {
                            //update description
                        }
                        break;
                        case 3: {
                            //update deadline
                        }
                        break;
                        case 4: {
                            //update priority
                        } break;
                        default: {

                        }
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "Entered wrong number of action, please try again.");
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "You entered  something else than number. Please Try again\n" +
                        "Message: " + "[" + ex.getMessage() + "]");
            }
        }

    }

    private String whatUpdatePanel() {
        String choice = JOptionPane.showInputDialog(
                "What you want to update: \n" +
                        "[1] Title\n" +
                        "[2] Description\n" +
                        "[3] Deadline\n" +
                        "[4] Priority\n" +
                        "[0] Back\n"
        );

        return choice;
    }

    private boolean isEnterCorrectId(int choiceNumber) {
        List<Note> list = getListOfNotes();

        for (int i = 0; i < list.size(); i++) {
            if (choiceNumber == list.get(i).getId()) {
                return true;
            }
        }
        return false;
    }

    private String createList() {
        List<Note> list = getListOfNotes();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            builder.append("[").append(list.get(i).getId()).append("]")
                    .append(" - ").append(list.get(i).getTitle())
                    .append("\n");
        }
        return builder.toString();
    }

    private String displayUpdatePanel() {
        String listOfNotes = createList();
        String choice = JOptionPane.showInputDialog(
                listOfNotes +
                        "-----------------------------------\n" +
                        "What you want to do: \n" +
                        "[ID NUMBER] Select the identifier of the note to be updated.\n" +
                        "[0] Back\n"
        );
        return choice;
    }

    private String buildTextFromList() {
        List<Note> titleList = getListOfNotes();
        String text = "";
        int count = 1;

        for (int i = 0; i < titleList.size(); i++) {
            text += "[" + count + "] - " + titleList.get(i) + "\n";
            count++;
        }

        return text;
    }

    private static List<Note> getListOfNotes() {
        List<Note> list = new ArrayList<>();

        try (
                Connection connection = DriverManager.getConnection(DataToConn.getURL(), DataToConn.getDataProperties());
                Statement statement = connection.createStatement();
        ) {
            String query = "SELECT * FROM list;";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int id = resultSet.getInt("id_list");
                String title = resultSet.getString("title");

                Note note = new Note(id, title);
                list.add(note);
            }
            return list;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Problem with display list, sorry for problem ...");
        }
        return list;
    }
}
