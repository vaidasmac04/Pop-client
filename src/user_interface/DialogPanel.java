package user_interface;

import pop_implementation.IResponse;

import javax.swing.*;
import java.awt.Component;
import java.awt.FlowLayout;
import java.util.List;

public class DialogPanel {

    private JPanel panel;
    private JScrollPane scrollPane;
    private JTextArea textArea;
    private JTextField commandText;
    private JLabel requestInformationLabel;
    private JButton sendButton;
    private JButton clearPanelButton;

    public DialogPanel(){
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        createComponents();
        addClearButtonAction();
    }

    public JPanel getPanel() {
        return panel;
    }

    public JButton getSendButton() {
        return sendButton;
    }

    private void createComponents(){
        JLabel commandLabel = new JLabel("Enter a command:");
        commandLabel.setHorizontalAlignment(JLabel.RIGHT);
        commandText = new JTextField(30);
        sendButton = new JButton("Send");
        sendButton.setEnabled(false);

        JPanel commandPanel = new JPanel(new FlowLayout());
        commandPanel.add(commandLabel);
        commandPanel.add(commandText);
        commandPanel.add(sendButton);

        requestInformationLabel = new JLabel();
        requestInformationLabel.setText("Last request: no information");
        requestInformationLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        textArea = new JTextArea(30, 50);
        textArea.setEditable(false);
        scrollPane = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        JLabel nameLabel = new JLabel("Response panel");
        nameLabel.setHorizontalAlignment(JLabel.CENTER);
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        clearPanelButton = new JButton("Clear");
        clearPanelButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(commandPanel);
        panel.add(requestInformationLabel);
        panel.add(nameLabel);
        panel.add(scrollPane);
        panel.add(clearPanelButton);

    }

    public String getCommand(){
        String result = commandText.getText();

        if(result.isEmpty()){
            throw new IllegalArgumentException("Command cannot be empty");
        }

        return result;
    }

    public void writeCommand(String command){
        textArea.append("You sent: " + command + '\n');
    }

    public void writeResponse(IResponse response){

        int responseCount = response.getResponse().size();
        List<String> responseList = response.getResponse();

        textArea.append(responseList.get(0)+"\n");

        requestInformationLabel.setText("Last request: status  " + (response.isValid() ? "+OK" : "-ERR") + ", lines - " + responseCount);

        if(responseCount != 1){
            for(String line : responseList.subList(1, responseList.size())){
                textArea.append("   " + line+"\n");
            }
        }
    }

    private void addClearButtonAction(){
        clearPanelButton.addActionListener(e -> {
            textArea.setText("");
        });
    }

    public void clearCommandText(){
        commandText.setText("");
    }
}
