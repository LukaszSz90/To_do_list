package org.example.exercise_file.funcion;

import javax.swing.*;

public class DeleteNote {
    public void deletePanel() {
        int choice = choiceDeletePanel();

        switch (choice) {
            case 1: {

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
