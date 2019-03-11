package project.parsing;

import project.main.ParsingException;

/**
 * class representing error occurs when assigning a variable with non-applicable type value than declared before
 */
public class InvalidTypeAssignmentException extends ParsingException {

    private String MSG = " : assigning a variable with non-applicable type value than declared before";

    /**
     * InvalidTypeAssignmentException default constructor
     */
    InvalidTypeAssignmentException() {
        this.messageError = MSG;
    }
}
