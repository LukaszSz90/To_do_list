package org.example.exercise_file.funcion;

import org.example.exercise_file.DataToConn;
import org.example.exercise_file.Note;

import javax.swing.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UpdateNote {
    public void updatePanel() {
        String choice = "";

        while (!choice.equals("0")) {
            choice = displayUpdatePanel();

            if (!choice.equals("0")) {
            try {
                int selectedId = Integer.parseInt(choice);
                if (selectedId >= 0 || isEnterCorrectId(selectedId)){

                    String subChoice = whatUpdatePanel();
                    int selectedElementToUpdate = Integer.parseInt(subChoice);

                    int isUpdated = updateElementWithId(selectedId, selectedElementToUpdate);

                    if (isUpdated == 1) {
                        JOptionPane.showMessageDialog(null, "Data update successful :)");
                    } else {
                        JOptionPane.showMessageDialog(null, "No update");
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

    }

    private int updateElementWithId(int selectedId, int selectedElementToUpdate) {
        switch (selectedElementToUpdate) {
            case 1:
            case 2: {
                String query = "";
                if (selectedElementToUpdate == 1) {
                    query = "UPDATE list SET title = ? WHERE id_list = ?;";
                } else {
                    query = "UPDATE list SET description = ? WHERE id_list = ?;";
                }

                try(
                        Connection connection = DriverManager.getConnection(DataToConn.getURL(), DataToConn.getDataProperties());
                        PreparedStatement preparedStatement = connection.prepareStatement(query);
                        ) {
                    String titleOrDescription = "";
                    if (selectedElementToUpdate == 1) {
                        titleOrDescription = JOptionPane.showInputDialog("Enter new title:");
                    } else {
                        titleOrDescription = JOptionPane.showInputDialog("Enter new description:");
                    }

                    preparedStatement.setString(1, titleOrDescription);
                    preparedStatement.setInt(2, selectedId);
                    int isUpdated = preparedStatement.executeUpdate();

                    connection.close();
                    preparedStatement.close();
                    return isUpdated;

                } catch (SQLException ex){
                    JOptionPane.showMessageDialog(null, "Error when update note element." + "\n" +
                            "Error message: " + ex.getMessage() + "\n" +
                            "SQL state: " + ex.getSQLState());
                }
            } break;
            case 3: {
                //todo napisać zabezpieczenie przed wpisaniem złej daty
                String query = "UPDATE list SET deadline = ? WHERE id_list = ?;";
                try(
                        Connection connection = DriverManager.getConnection(DataToConn.getURL(), DataToConn.getDataProperties());
                        PreparedStatement preparedStatement = connection.prepareStatement(query);
                ) {
                    String newDeadline = "";
                    newDeadline = JOptionPane.showInputDialog("Enter new deadline (year-month-day):");
                    LocalDate updateDeadline = LocalDate.parse(newDeadline);

                    preparedStatement.setDate(1, Date.valueOf(updateDeadline));
                    preparedStatement.setInt(2, selectedId);
                    int isUpdated = preparedStatement.executeUpdate();

                    connection.close();
                    preparedStatement.close();
                    return isUpdated;

                } catch (SQLException ex){
                    JOptionPane.showMessageDialog(null, "Error when update note element." + "\n" +
                            "Error message: " + ex.getMessage() + "\n" +
                            "SQL state: " + ex.getSQLState());
                }
            } break;
            case 4: {
                String query = "UPDATE list SET priority = ? WHERE id_list = ?;";
                try(
                        Connection connection = DriverManager.getConnection(DataToConn.getURL(), DataToConn.getDataProperties());
                        PreparedStatement preparedStatement = connection.prepareStatement(query);
                ) {
                    String newPriority = "";
                    newPriority = JOptionPane.showInputDialog("Enter new priority:");
                    int updatePriority = Integer.parseInt(newPriority);

                    preparedStatement.setInt(1, updatePriority);
                    preparedStatement.setInt(2, selectedId);
                    int isUpdated = preparedStatement.executeUpdate();

                    connection.close();
                    preparedStatement.close();
                    return isUpdated;

                } catch (SQLException ex){
                    JOptionPane.showMessageDialog(null, "Error when update note element." + "\n" +
                            "Error message: " + ex.getMessage() + "\n" +
                            "SQL state: " + ex.getSQLState());
                } catch (NumberFormatException exc) {
                    JOptionPane.showMessageDialog(null, "You entered  something else than number. Please Try again\n" +
                            "Message: " + "[" + exc.getMessage() + "]");
                }
            } break;
            default: {

            }

        }

        return 0;
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
