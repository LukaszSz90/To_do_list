package org.example.exercise_file.funcion;

import org.example.exercise_file.DataToConn;
import org.example.exercise_file.Note;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DeleteNote {
    public void deletePanel() {
        int choice = choiceDeletePanel();

        switch (choice) {
            case 1: {
                int selectedNoteIdToDelete = displayAndSelectId();
                deleteNoteById(selectedNoteIdToDelete);
                //todo wprowadzić zabezpieczenie przed wpisaniem litery zamiast liczby odnoszącej sie do ID notatki
            }
            break;
            case 2: {
                boolean whetherSurely = areYouSure();


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

    private void deleteNoteById(int selectedNoteIdToDelete) {
        String deleteQuery ="DELETE FROM list WHERE id_list = ?";
        try (
                Connection connection = DriverManager.getConnection(DataToConn.getURL(), DataToConn.getDataProperties());
                PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery);
                ) {
            preparedStatement.setInt(1, selectedNoteIdToDelete);
            preparedStatement.executeUpdate();

            connection.close();
            preparedStatement.close();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error when deleting a note." + "\n" +
                    "Error message: " + ex.getMessage() + "\n" +
                    "SQL state: " + ex.getSQLState());
        }
    }

    private int displayAndSelectId() {
        List<Note> noteList = getNoteListFromDataBase();
        StringBuilder build = new StringBuilder();

        for (int i = 0; i < noteList.size(); i++) {
            build.append("[").append(noteList.get(i).getId()).append("]")
                    .append(" -> ").append(noteList.get(i).getTitle()).append("\n");
        }
        build.append("Select the note ID to delete this note: ");
        int choice = Integer.parseInt(JOptionPane.showInputDialog(build.toString()));
        return choice;
    }

    private List<Note> getNoteListFromDataBase() {
        List<Note> noteList = new ArrayList<>();

        try (
                Connection connection = DriverManager.getConnection(DataToConn.getURL(), DataToConn.getDataProperties());
                Statement statement = connection.createStatement();
        ) {
            String query = "SELECT id_list, title FROM list;";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int id = resultSet.getInt("id_list");
                String title = resultSet.getString("title");

                Note note = new Note(id, title);
                noteList.add(note);
            }

            connection.close();
            statement.close();
            resultSet.close();

            return noteList;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Problem with display list, sorry for problem ...");
        }
        return noteList;
    }

    private int choiceDeletePanel() {
        int choice = Integer.parseInt(JOptionPane.showInputDialog(
                "Select what you want to delete:\n" +
                        "[1] Delete one note.\n" +
                        "[2] Delete all notes.\n" +
                        "[0] Back"
        ));
        return choice;
    }

    private boolean areYouSure() {
        int choice = Integer.parseInt(JOptionPane.showInputDialog(
                "Are you sure you want to delete all the notes?\n" +
                        "[1] YES               [2] NO"
        ));
        while (choice != 1 || choice != 2) {
            choice = Integer.parseInt(JOptionPane.showInputDialog(
                    "-----You entered wrong answer number-----\n" +
                            "Are you sure you want to delete all the notes?\n" +
                            "[1] YES               [2] NO"
            ));
        }

        return choice == 1;
    }
}
