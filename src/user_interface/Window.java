package user_interface;

import javax.swing.*;

public abstract class Window {
    protected JFrame frame;

    public Window(){
        frame = new JFrame();
    }

    public abstract void configureWindow();
}
