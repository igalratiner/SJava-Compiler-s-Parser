package project.parsing;

import project.main.ParsingException;

/**
 * class representing error occurs when assigning the method with different type parameter than declared
 * before
 */
public class InvalidParameterTypeException extends ParsingException {

    private String MSG = " : assigning the method with different type parameter than declared before";

    /**
     * InvalidParameterTypeException default constructor
     */
    InvalidParameterTypeException() {
        this.messageError = MSG;
    }
}
