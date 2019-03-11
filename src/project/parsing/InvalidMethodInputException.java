package project.parsing;

import project.main.ParsingException;

/**
 * class represents error occurs when assigning the method a different number of argument than declared
 * before
 */
public class InvalidMethodInputException extends ParsingException {
    private String MSG = " : assigning the method a different number of argument than declared before";

    /**
     * InvalidMethodInputException default constructor
     */
    InvalidMethodInputException() {
        this.messageError = MSG;
    }
}
