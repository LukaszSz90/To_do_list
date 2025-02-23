package org.example.exercise_file;

import org.example.exercise_file.funcion.CreateNote;
import org.example.exercise_file.funcion.DeleteNote;
import org.example.exercise_file.funcion.DisplayNote;
import org.example.exercise_file.funcion.UpdateNote;

import javax.swing.*;
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
        String textList = buildTextFromList();

        String choice = JOptionPane.showInputDialog(
                "Actual list to do:\n" +
                        textList + "\n" +
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

    private static String buildTextFromList() {
        List<String> titleList = DisplayNote.getTitleList();
        String text = "";
        int count = 1;

        for (int i = 0; i < titleList.size(); i++) {
            text += "[" + count + "] - " + titleList.get(i) + "\n";
            count++;
        }

        return text;
    }

}
