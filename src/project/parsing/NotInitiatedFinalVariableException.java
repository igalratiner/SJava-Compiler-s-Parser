package project.parsing;

import project.main.ParsingException;

/**
 * class represents an error occurs when creating a final non-initiated variable
 */
public class NotInitiatedFinalVariableException extends ParsingException {

    private String MSG = " : creating a final non-initiated variable";

    /**
     * NotInitiatedFinalVariableException default constructor
     */
    NotInitiatedFinalVariableException() {
        this.messageError = MSG;
    }
}
