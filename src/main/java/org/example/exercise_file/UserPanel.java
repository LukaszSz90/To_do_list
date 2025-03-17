package org.example.exercise_file;

import com.sun.source.tree.BreakTree;
import org.example.exercise_file.funcion.CreateNote;
import org.example.exercise_file.funcion.DeleteNote;
import org.example.exercise_file.funcion.DisplayNote;
import org.example.exercise_file.funcion.UpdateNote;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class UserPanel {

    public static void mainPanelStart() {
        String choice = "";
        while (!choice.equals("0")) {
            choice = UserPanel.displayMainPanel();

            try {
                int choiceNumber = Integer.parseInt(choice);

                if (choiceNumber >= 0 && choiceNumber <= 4) {
                    actionPanel(choiceNumber);
                } else {
                    JOptionPane.showMessageDialog(null, "Entered wrong number of action, please try again.");
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "You entered  something else than number. Please Try again\n" +
                        "Message: " + "[" + ex.getMessage() + "]");
            }
        }
    }

    private static void actionPanel(int choice) {
        switch (choice) {
            case 1: {
                DisplayNote displayNote = new DisplayNote();
                displayNote.displayPanel();
            }
            break;
            case 2: {
                CreateNote createNote = new CreateNote();
                createNote.createPanel();
            }
            break;
            case 3: {
                UpdateNote updateNote = new UpdateNote();
                updateNote.updatePanel();
            }
            break;
            case 4: {
                DeleteNote deleteNote = new DeleteNote();
                deleteNote.deletePanel();
            }
            break;
            case 0: {
                JOptionPane.showMessageDialog(null, "Program is closed.");
                System.exit(0);
            }
            break;
            default: {
                JOptionPane.showMessageDialog(null, "Something has gone wrong, the programme is closed");
                System.exit(0);
            }
        }
    }

    public static String displayMainPanel() {
        String choice = JOptionPane.showInputDialog(
                "Actual list to do:\n" +
                        buildTextFromList() +
                        "--------------------------------------------\n" +
                        "What you want to do: \n" +
                        "[1] View details of note\n" +
                        "[2] Add a new note\n" +
                        "[3] Modify a note\n" +
                        "[4] Delete a note\n" +
                        "[0] Exit\n"
        );

        return choice;
    }

    public static String buildTextFromList() {
        List<Note> titleList = getTitleList();

        Comparator<Note> comparator = new Comparator<Note>() {
            @Override
            public int compare(Note o1, Note o2) {
                return o1.getId() - o2.getId();
            }
        };

        titleList.sort(comparator);

        StringBuilder buildList = new StringBuilder();

        for (int i = 0; i < titleList.size(); i++) {
           buildList.append("[").append(titleList.get(i).getId()).append("] ")
                   .append(titleList.get(i).getTitle()).append("\n");
        }

        return buildList.toString();
    }

        private static List<Note> getTitleList() {
        List<Note> list = new ArrayList<>();
        try (
                Connection connection = DriverManager.getConnection(DataToConn.getURL(), DataToConn.getDataProperties());
                Statement statement = connection.createStatement();
        ) {
            String query = "SELECT id_list, title FROM list;";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                Note note = new Note(resultSet.getInt("id_list"), resultSet.getString("title"));
                list.add(note);
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

}
