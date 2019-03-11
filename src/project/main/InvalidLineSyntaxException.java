package project.main;

/**
 * the class represents errors of non matching code lines to the possible variations of code line in sjava
 */
class InvalidLineSyntaxException extends CompilerException {

    private String MSG = " : The line doesn't match the valid set of code lines in sjava";

    /**
     * the InvalidLineSyntaxException default constructor
     */
    InvalidLineSyntaxException(){
        this.messageError = MSG;
    }


}
