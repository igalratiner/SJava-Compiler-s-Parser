package project.parsing;

import project.main.ParsingException;

/**
 * the class representing an error when condition statement is within global scope
 */
class ConditionStatementFromGlobalScopeException extends ParsingException {
    private String MSG = " : condition statement is within global scope";

    /**
     * ConditionStatementFromGlobalScopeException default constructor
     */
    ConditionStatementFromGlobalScopeException() {
        this.messageError = MSG;
    }
}
