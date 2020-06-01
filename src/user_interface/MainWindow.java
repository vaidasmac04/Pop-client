package user_interface;

import pop_implementation.IPopClient;
import pop_implementation.IResponse;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class MainWindow extends Window {

    private LoginPanel loginPanel;
    private DialogPanel dialogPanel;
    private IPopClient popClient;

    public MainWindow(IPopClient popClient){
        super();
        this.popClient = popClient;
        configureWindow();
        handleLoginPanel();
        handleDialogPanel();
    }

    @Override
    public void configureWindow() {
        frame.setTitle("Main Window");
        frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        createPanels();
        frame.add(Box.createRigidArea(new Dimension(0,20)));
        frame.add(loginPanel.getPanel());
        frame.add(Box.createRigidArea(new Dimension(0,20)));
        frame.add(dialogPanel.getPanel());
        frame.setVisible(true);
        frame.pack();
    }

    private void createPanels(){
        loginPanel = new LoginPanel();
        dialogPanel = new DialogPanel();
    }

    private void handleLoginPanel(){
        loginPanel.getPopServerNameAndPortButton().addActionListener(e -> {
            try {
                String[] result = loginPanel.getAddressAndPort();
                IResponse response = popClient.connect(result[0], Integer.parseInt(result[1]));
                dialogPanel.writeCommand(result[0] + " " + result[1]);
                dialogPanel.writeResponse(response);

                if (response.isValid()) {
                    loginPanel.getClientNameButton().setEnabled(true);
                    loginPanel.getPopServerNameAndPortButton().setEnabled(false);
                    loginPanel.getQuitButton().setEnabled(true);
                }
            }catch(IOException iOException){
                ErrorWindow.showErrorWindow(frame, iOException.getMessage());
            }catch(NumberFormatException numberFormatException){
                ErrorWindow.showErrorWindow(frame, "Port must be an integer");
            }catch(IllegalArgumentException illegalArgumentException){
                ErrorWindow.showErrorWindow(frame, illegalArgumentException.getMessage());
            }
        });

        loginPanel.getClientNameButton().addActionListener(e -> {
            try {
                String result = loginPanel.getName();
                dialogPanel.writeCommand("user " + result);
                IResponse response = popClient.handleCommand("user " + result);
                dialogPanel.writeResponse(response);

                if(response.isValid()){
                    loginPanel.getClientPasswordButton().setEnabled(true);
                    loginPanel.getClientNameButton().setEnabled(false);
                }
            }catch(IOException iOException){
                ErrorWindow.showErrorWindow(frame, iOException.getMessage());
            }catch (IllegalArgumentException illegalArgumentException){
                ErrorWindow.showErrorWindow(frame, illegalArgumentException.getMessage());
            }
        });

        loginPanel.getClientPasswordButton().addActionListener(e -> {
            try {
                String result = loginPanel.getPassword();
                dialogPanel.writeCommand("pass " + "*****");
                IResponse response = popClient.handleCommand("pass " + result);
                dialogPanel.writeResponse(response);

                if(response.isValid()){
                    loginPanel.getClientPasswordButton().setEnabled(false);
                    dialogPanel.getSendButton().setEnabled(true);
                }
                else{
                    loginPanel.getClientNameButton().setEnabled(true);
                    loginPanel.getClientPasswordButton().setEnabled(false);
                    loginPanel.getClientPasswordText().setText("");
                }
            }catch(IOException iOException){
                ErrorWindow.showErrorWindow(frame, iOException.getMessage());
            }catch (IllegalArgumentException illegalArgumentException){
                ErrorWindow.showErrorWindow(frame, illegalArgumentException.getMessage());
            }
        });

        loginPanel.getQuitButton().addActionListener(e -> {
            try {
            IResponse response = popClient.handleCommand("quit");
            dialogPanel.writeCommand("quit");
            dialogPanel.writeResponse(response);

            if(response.isValid()){
                loginPanel.getPopServerNameAndPortButton().setEnabled(true);
                loginPanel.getClientNameButton().setEnabled(false);
                loginPanel.getClientPasswordButton().setEnabled(false);
                loginPanel.getQuitButton().setEnabled(false);
                dialogPanel.getSendButton().setEnabled(false);
            }}
            catch(IOException iOException){
                ErrorWindow.showErrorWindow(frame, iOException.getMessage());
            }
        });
    }

    public void handleDialogPanel(){
        dialogPanel.getSendButton().addActionListener(e -> {
            try {
                String command = dialogPanel.getCommand();
                IResponse response = popClient.handleCommand(command);
                dialogPanel.writeCommand(command);
                dialogPanel.writeResponse(response);

                if (!popClient.isConnected()) {
                    dialogPanel.getSendButton().setEnabled(false);
                    loginPanel.getPopServerNameAndPortButton().setEnabled(true);
                    loginPanel.getQuitButton().setEnabled(false);
                }

                if(response.isValid()){
                    dialogPanel.clearCommandText();
                }

            }catch (IOException iOException){
                ErrorWindow.showErrorWindow(frame, iOException.getMessage());
            }catch (IllegalArgumentException illegalArgumentException){
                ErrorWindow.showErrorWindow(frame, illegalArgumentException.getMessage());
            }
        });
    }
}
