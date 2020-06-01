package pop_implementation;

import java.util.regex.Pattern;

public class Command {
    private final String name;
    private final String pattern;
    private final boolean isMultiLine;

    public Command(String name, String pattern, boolean isMultiLine) {
        this.name = name;
        this.pattern = pattern;
        this.isMultiLine = isMultiLine;
    }

    public String getName() {
        return name;
    }

    public boolean isMultiLine() {
        return isMultiLine;
    }

    public boolean isPatternMatch(String command){
        return Pattern.matches(pattern, command);
    }
}
