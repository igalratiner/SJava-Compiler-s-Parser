package project.main;

/**
 * the class represents an error occurs when there is no return statement in the end of a method
 */
public class NoReturnAtEndOfMethodException extends ParsingException {
    private String MSG = " : there is no return statement in the end of a method";

    /**
     * NoReturnAtEndOfMethodException default constructor
     */
    NoReturnAtEndOfMethodException() {
        this.messageError = MSG;
    }
}
