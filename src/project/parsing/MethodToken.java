package project.parsing;

/**
 * Class representing all vital info oon method objects in sjava
 */
public class MethodToken {
    /**
     * Constructor for an object to contain method information
     */
    String methodName; // the name of the method
    String returnType; // the type the method returns
    String[] parameterTypes; // the inputs of the method

    public MethodToken (String methodName, String returnType, String[] parameterTypes) {
        this.returnType = returnType;
        this.methodName = methodName;
        this.parameterTypes = parameterTypes;
    }
}
