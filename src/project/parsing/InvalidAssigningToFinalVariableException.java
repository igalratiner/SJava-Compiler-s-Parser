package project.parsing;

import project.main.ParsingException;

/**
 * the class represents an error happens if assigning an existing final variable another value
 */
public class InvalidAssigningToFinalVariableException extends ParsingException {
    private String MSG = " : assigning an existing final variable another value";

    /**
     * InvalidAssigningToFinalVariableException default constructor
     */
    InvalidAssigningToFinalVariableException() {
        this.messageError = MSG;
    }
}
