package project.parsing;

import project.main.ParsingException;

/**
 * the class representing an error when method declaration is within an inner scope
 */
public class MethodDeclarationFromInnerScopeException extends ParsingException {

    private String MSG = " : method declaration is within an inner scope";

    /**
     * MethodDeclarationFromInnerScopeException default constructor
     */
    MethodDeclarationFromInnerScopeException() {
        this.messageError = MSG;
    }
}
