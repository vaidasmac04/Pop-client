package user_interface;

import javax.swing.*;
import java.awt.*;

public class LoginPanel {
    private JPanel panel;
    private JTextField popServerNameAndPortText;
    private JTextField clientNameText;
    private JPasswordField clientPasswordText;
    private JLabel popServerNameAndPortLabel;
    private JLabel clientNameLabel;
    private JLabel quitLabel;
    private JLabel clientPasswordLabel;
    private JButton popServerNameAndPortButton;
    private JButton clientNameButton;
    private JButton clientPasswordButton;
    private JButton quitButton;

    public LoginPanel(){
        panel = new JPanel();
        createComponents();
    }


    private void createComponents(){
        popServerNameAndPortLabel = new JLabel("Pop server address and port:");
        clientNameLabel = new JLabel("Client username:");
        clientPasswordLabel = new JLabel("Client password:");
        quitLabel = new JLabel("Click here to disconnect:");

        popServerNameAndPortLabel.setHorizontalAlignment(JLabel.RIGHT);
        clientNameLabel.setHorizontalAlignment(JLabel.RIGHT);
        clientPasswordLabel.setHorizontalAlignment(JLabel.RIGHT);
        quitLabel.setHorizontalAlignment(JLabel.RIGHT);

        popServerNameAndPortText = new JTextField("");
        clientNameText = new JTextField();
        clientPasswordText = new JPasswordField();

        popServerNameAndPortButton = new JButton("Connect");
        clientNameButton = new JButton("Send");
        clientPasswordButton = new JButton("Send");
        quitButton = new JButton("Quit");

        clientNameButton.setEnabled(false);
        clientPasswordButton.setEnabled(false);
        quitButton.setEnabled(false);


        panel.setLayout(new GridLayout(4, 3, 10, 10));

        panel.add(popServerNameAndPortLabel);
        panel.add(popServerNameAndPortText);
        panel.add(popServerNameAndPortButton);

        panel.add(clientNameLabel);
        panel.add(clientNameText);
        panel.add(clientNameButton);

        panel.add(clientPasswordLabel);
        panel.add(clientPasswordText);
        panel.add(clientPasswordButton);

        panel.add(quitLabel);
        panel.add(quitButton);
    }

    public JPanel getPanel() {
        return panel;
    }

    public String[] getAddressAndPort(){
        String[] result = popServerNameAndPortText.getText().split(" ");

        if(result.length != 2){
            throw new IllegalArgumentException("Please provide address and port");
        }

        return result;
    }

    public String getName(){
        String text = clientNameText.getText();

        if(text.isEmpty()){
            throw new IllegalArgumentException("Name cannot be empty");
        }

        return text;
    }

    public String getPassword(){
        String text = new String(clientPasswordText.getPassword());

        if(text.isEmpty()){
            throw new IllegalArgumentException("Password cannot be empty");
        }

        return text;
    }

    public JButton getPopServerNameAndPortButton() {
        return popServerNameAndPortButton;
    }

    public JButton getClientNameButton() {
        return clientNameButton;
    }

    public JButton getClientPasswordButton() {
        return clientPasswordButton;
    }

    public JButton getQuitButton() {
        return quitButton;
    }

    public JPasswordField getClientPasswordText() {
        return clientPasswordText;
    }
}
