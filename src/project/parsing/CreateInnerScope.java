package project.parsing;

import project.main.Node;


/**
 * a singleton class representing creator of inner scopes out of code lines
 */
public class CreateInnerScope extends CreateScope{


    /**
     * the class field holding its own instance
     */
    private static CreateInnerScope instance = new CreateInnerScope();

    /**
     * getter for the class only instance
     * @return CreateInnerScope only instance
     */
    public static CreateInnerScope getInstance() {
        return instance;
    }

    /**
     * the method throws an exception if encounters a method declaration in an inner scope
     * @param codeLine the inputted string method declaration
     * @param curNode the current node where the method declared
     * @throws MethodDeclarationFromInnerScopeException method declaration encountered in the an inner scope's code
     * lines
     */
    @Override
    protected void methodDeclaration(String codeLine, Node curNode) throws MethodDeclarationFromInnerScopeException {
        throw new MethodDeclarationFromInnerScopeException();
    }


}
