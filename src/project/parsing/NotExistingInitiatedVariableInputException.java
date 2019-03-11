package project.parsing;

import project.main.ParsingException;

/**
 * the class representing an error occurs when trying to assign a variable with a variable that is either
 * don't exist or not initiated
 */
public class NotExistingInitiatedVariableInputException extends ParsingException {
    private String MSG = " : assigning a variable with a variable that either\n" +
            " doesn't exist or is not initiated";

    /**
     * NotExistingInitiatedVariableInputException default constructor
     */
    NotExistingInitiatedVariableInputException() {
        this.messageError = MSG;
    }
}
