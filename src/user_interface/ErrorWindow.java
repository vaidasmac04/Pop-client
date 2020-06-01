package user_interface;

import javax.swing.*;

public class ErrorWindow {

    public static void showErrorWindow(JFrame frame, String message){
        JOptionPane.showMessageDialog(frame, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

}
