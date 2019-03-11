package project.main;

/**
 * the class represents an error occurs when there is non valid number of parenthesis in the program code
 */
class ExceedingParenthesesException extends ParsingException {
    private String MSG = " : non valid number of parenthesis in the program code";

    /**
     * ExceedingParenthesesException default constructor
     */
    ExceedingParenthesesException() {
        this.messageError = MSG;
    }
}
