package project.parsing;

import project.main.Node;
import java.util.regex.Matcher;

/**
 * a singleton class representing creator of global scopes out of code lines
 */
public class CreateGlobalScope extends CreateScope {

    /**
     * the class field holding its own instance
     */
    private static CreateGlobalScope instance = new CreateGlobalScope();

    /**
     * getter for the class only instance
     * @return CreateGlobalScope only instance
     */
    public static CreateGlobalScope getInstance() {
        return instance;
    }

    /**
     * the method creates the global scope's method set from the method declaration code lines
     * @param codeLine the inputted string method declaration
     * @param curNode the current node where the method declared
     */
    @Override
    protected void methodDeclaration(String codeLine, Node curNode)  {
        String typeOfMethod = findSubString(codeLine, RegExp.patternMethodType.matcher(codeLine),true);
        String subCodeLine = findSubString(codeLine,RegExp.patternMethodType.matcher(codeLine),false);
        Matcher methodNameMatcher = RegExp.patterMethodName.matcher(subCodeLine);
        String methodName = findSubString(subCodeLine, methodNameMatcher, true);

        /*
        isolation of method name and type and finding the list (by order) of valid input parameter types
         */
        curNode.getOuterScopeMethods().put(methodName,new MethodToken(methodName,typeOfMethod,
                splitMethodParameters
                (codeLine)));
    }

    /**
     * the method isolates the types of variables declared in the method declaration
     * @param codeLine the code line representing the method declaration
     * @return a string array of the input variables' types valid to this method
     */
    private String[] splitMethodParameters(String codeLine) {
        String[] parameters = splitBracketedParameters(codeLine,"\\s*,\\s*"); // split the bracked sub
        // string to each variable sub-segment containing its type and its bame
                    String[] methodParameterTypes = new String[parameters.length];
            for (int parameterIndex = 0; parameterIndex<methodParameterTypes.length ; parameterIndex++) {
                Matcher parameterMatcher = RegExp.patternTypes.matcher(parameters[parameterIndex]);
                String parameterType = findSubString(parameters[parameterIndex], parameterMatcher,true);
            /* isolates the type of each variable segment*/
            methodParameterTypes[parameterIndex] = parameterType;
        }
        return methodParameterTypes;
    }

    /**
     * the method throws exception if encountered a method call in global scope code lines
     * @param codeLine the inputted string method declaration
     * @param curNode the current node where the method declared
     * @throws MethodCallFromGlobalScopeException method call encountered in the global scope's code lines
     */
    @Override
    protected void functionCall(String codeLine, Node curNode) throws MethodCallFromGlobalScopeException {
        throw new MethodCallFromGlobalScopeException();
    }

    /**
     * the method throws exception if encountered a return statement in global scope code lines
     * @throws ReturnStatementFromGlobalScopeException return statement encountered in the global scope's code lines
     */
    @Override
    protected void returnLine() throws ReturnStatementFromGlobalScopeException {
        throw new ReturnStatementFromGlobalScopeException();
    }

    /**
     * the method throws exception if encountered a condition statement in global scope code lines
     * @param codeLine the inputted string of the condition
     * @param curNode the current node of the condition
     * @throws ConditionStatementFromGlobalScopeException condition statement encountered in the global scope's code
     * lines     */
    @Override
    protected void conditionHandling(String codeLine, Node curNode) throws ConditionStatementFromGlobalScopeException {
        throw new ConditionStatementFromGlobalScopeException();
    }



}
