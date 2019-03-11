package project.parsing;

import project.main.ParsingException;

/**
 * the class represents an error occurs when the same variable is declared in the same scope more than once
 */
public class VariableInScopeRedeclarationException extends ParsingException {

    private String MSG = " : the same variable is declared in the same scope more than once";

    /**
     * VariableInScopeRedeclarationException default constructor
     */
    VariableInScopeRedeclarationException() {
        this.messageError = MSG;
    }
}
