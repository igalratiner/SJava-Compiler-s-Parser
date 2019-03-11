package project.parsing;

import project.main.Node;


/**
 * a singleton class representing creator of method scopes out of code lines
 */
public class CreateMethodScope extends CreateScope {


    /**
     * the class field holding its own instance
     */
    private static CreateMethodScope instance = new CreateMethodScope();

    /**
     * getter for the class only instance
     * @return CreateMethodScope only instance
     */
    public static CreateMethodScope getInstance() {
        return instance;
    }

    /**
     * the method creates its method scope with creating the parameters as the newest variables of its scope
     * @param codeLine the inputted string method declaration
     * @param curNode the current node where the method declared
     * @throws VariableInScopeRedeclarationException variable is declared although existing already in current scope
     * @throws NotInitiatedFinalVariableException a variable is both final and not initiated
     * @throws InvalidAssigningToFinalVariableException assigning a value to a final modified variable
     * @throws NotExistingInitiatedVariableInputException a variable is not existing or not initiated in scope and all
     * parent and global scope
     * @throws InvalidTypeAssignmentException assigning a value to variable from two not applicable types
     */
    @Override
    protected void methodDeclaration(String codeLine, Node curNode) throws
            VariableInScopeRedeclarationException, NotInitiatedFinalVariableException, InvalidAssigningToFinalVariableException,
            NotExistingInitiatedVariableInputException, InvalidTypeAssignmentException {
        String[] parameters = splitBracketedParameters(codeLine,",");
        for (String parameter : parameters) {
            createVariable(parameter, curNode, true);
        }
    }

}
