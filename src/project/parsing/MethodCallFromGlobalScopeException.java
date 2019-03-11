package project.parsing;

import project.main.ParsingException;

/**
 * the class representing an error when method call is within global scope
 */
public class MethodCallFromGlobalScopeException extends ParsingException {

    private String MSG = " : method call is within global scope";

    /**
     * MethodCallFromGlobalScopeException default constructor
     */
    MethodCallFromGlobalScopeException() {
        this.messageError = MSG;
    }
}
