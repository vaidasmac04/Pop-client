package pop_implementation;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.util.*;
import java.util.List;

public class PopClient implements IPopClient{

    private SSLSocket socket;
    private PrintStream printStream;
    private BufferedReader bufferedReader;
    private boolean isConnected;
    private List<Command> commands;

    public PopClient() {
        createCommands();
    }

    public void createCommands(){

        commands = new ArrayList<>();
        commands.add(new Command("stat", "[a-z]+", false));
        commands.add(new Command("noop", "[a-z]+", false));
        commands.add(new Command("quit", "[a-z]+", false));
        commands.add(new Command("rset", "[a-z]+", false));
        commands.add(new Command("list", "[a-z]+", true));
        commands.add(new Command("uidl", "[a-z]+", true));
        commands.add(new Command("user", "[a-z]+ .+", false));
        commands.add(new Command("pass", "[a-z]+ .+", false));
        commands.add(new Command("list", "[a-z]+ \\d+", false));
        commands.add(new Command("uidl", "[a-z]+ \\d+", false));
        commands.add(new Command("dele", "[a-z]+ \\d+", false));
        commands.add(new Command("retr", "[a-z]+ \\d+", true));
        commands.add(new Command("top", "[a-z]+ \\d+ \\d+", true));
    }

    @Override
    public IResponse connect(String address, int port ) throws IOException {
        SSLSocketFactory factory = (SSLSocketFactory)SSLSocketFactory.getDefault();
        Response response = new Response();
        socket = (SSLSocket)factory.createSocket(address, port);

        OutputStream outputStream = socket.getOutputStream();
        InputStream inputStream = socket.getInputStream();

        printStream = new PrintStream(outputStream);
        bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        response.appendLineToResponse(bufferedReader.readLine());

        if(response.isValid()){
            isConnected = true;
        }

        return response;
    }

    //if command is found returns IResponse containing response's text, otherwise - throws IllegalArgumentException
    @Override
    public IResponse handleCommand(String command) throws IOException {
        IResponse response = new Response();

        command = command.toLowerCase();
        command = command.trim();

        boolean isFound = false;
        for(Command c : commands){ //goes through all the commands and checks the given command name and pattern
            if(command.startsWith(c.getName()) && c.isPatternMatch(command)){
                response = handleResponse(command, c.isMultiLine());

                if(c.getName().equals("quit")){
                    isConnected = false;
                }

                isFound = true;
                break;
            }
        }

        if(!isFound){
            throw new IllegalArgumentException("Command was not found");
        }

        return response;
    }

    //returns IResponse containing single or multi-line response's text
    private IResponse handleResponse(String command, boolean isMultiLine) throws IOException {
        printStream.println(command); //send command to the server

        String line = bufferedReader.readLine();
        Response response = new Response();
        response.appendLineToResponse(line); //first line indicates if request was successful

        if(response.isValid() && isMultiLine){
            List<String> responseList = new ArrayList<>();

            boolean status = true;
            while(status) {
                line = bufferedReader.readLine();

                if(line.equals(".")) {  //POP protocol: multi-line response ends with '.'
                    status = false;
                }
                else{
                    responseList.add(line);
                }
            }

            response.appendListToResponse(responseList);
        }

        return response;
    }

    @Override
    public boolean isConnected() {
        return isConnected;
    }
}
