package project.parsing;

/**
 * the class represents the vital information of an object of sjava
 */
public class Token {
    /**
     * Constructor for an object to contain variable information
     */
    String tokenName; // the variable name
    String type; // the variable type
    boolean isFinal; // does the variable has final deceleration.
    boolean isInitiated; // did the variable initialized with a value.

    public Token (String tokenName, String type, boolean isFinal, boolean isInitiated) {
        this. tokenName = tokenName;
        this.type = type;
        this.isFinal = isFinal;
        this.isInitiated = isInitiated;
    }


}
