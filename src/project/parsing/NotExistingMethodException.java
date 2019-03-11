package project.parsing;

import project.main.ParsingException;

/**
 * class represents an error occurs when there is a method call to non-existing method
 */
public class NotExistingMethodException extends ParsingException {
    private String MSG = " : method call to non-existing method";

    /**
     * NotExistingMethodException default constructor
     */
    NotExistingMethodException() {
        this.messageError = MSG;
    }
}
