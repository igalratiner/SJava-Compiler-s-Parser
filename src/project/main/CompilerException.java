package project.main;

/**
 * the class represents an error occurs when using the compiler on inputted code
 */
class CompilerException extends Exception {
    protected String messageError; // the message error presented to the user when the exception occurs

    /**
     * the message error getter
     * @return exception's message error
     */
    public String getMessageError() {
        return messageError;
    }

}
