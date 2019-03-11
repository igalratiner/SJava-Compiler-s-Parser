package project.parsing;

import project.main.ParsingException;

/**
 * the class representing an error when return statement call is within global scope
 */
public class ReturnStatementFromGlobalScopeException extends ParsingException {

    private String MSG = " : return statement call is within global scope";

    /**
     * ReturnStatementFromGlobalScopeException default constructor
     */
    ReturnStatementFromGlobalScopeException() {
        this.messageError = MSG;
    }
}
