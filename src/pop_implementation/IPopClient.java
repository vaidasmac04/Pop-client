package pop_implementation;

import java.io.IOException;

public interface IPopClient {
    IResponse connect(String address, int port) throws IOException;
    IResponse handleCommand(String command) throws IOException;
    boolean isConnected();
}
