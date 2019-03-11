package project.parsing;

import project.main.Node;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * the class represents the creation of scopes and spotting non valid sjava code
 */
public abstract class CreateScope {
    private final static String VARIABLE_CREATION = "VARIABLE_CREATION";
    private final static String METHOD_CREATION = "METHOD_CREATION";
    private final static String RETURN_STATEMENT = "RETURN_STATEMENT";
    private final static String BRACKET = "BRACKET";
    private final static String ASSIGNMENT = "ASSIGNMENT";
    private final static String CONDITION_HANDLING = "CONDITION_HANDLING";
    private final static String METHOD_USE = "METHOD_USE";

    /**
     * the method creates the declared objects relevant to current scope
     * @param codeLine the inputted string method declaration
     * @param curNode the current node where the method declared
     * @throws VariableInScopeRedeclarationException variable is declared although existing already in current scope
     * @throws NotInitiatedFinalVariableException a variable is both final and not initiated
     * @throws InvalidAssigningToFinalVariableException assigning a value to a final modified variable
     * @throws NotExistingInitiatedVariableInputException a variable is not existing or not initiated in scope and all
     * parent and global scope
     * @throws InvalidTypeAssignmentException assigning a value to variable from two not applicable types
     * @throws MethodDeclarationFromInnerScopeException method declaration encountered in the an inner scope's code
     * lines
     */
    protected abstract void methodDeclaration(String codeLine, Node curNode) throws
            VariableInScopeRedeclarationException, NotInitiatedFinalVariableException, InvalidAssigningToFinalVariableException,
            NotExistingInitiatedVariableInputException, InvalidTypeAssignmentException,MethodDeclarationFromInnerScopeException;


    /**
     * the method represents the reaction to the closing bracket to the current scope
     */
    private void bracketLine() {
    }

    /**
     * the method represents the reaction to return statement in the current scope
     * @throws ReturnStatementFromGlobalScopeException return statement encountered in the global scope's code lines
     */
    protected void returnLine() throws ReturnStatementFromGlobalScopeException {
    }

    /**
     * the method checks if the condition is valid (all the parameters are either int, double or boolean,
     * if they are variables also have to be initiated)
     * @param codeLine the inputted string of the condition
     * @param curNode the current node of the condition
     * @throws NotExistingInitiatedVariableInputException a variable is not existing or not initiated in scope and all
     * parent and global scope
     * @throws InvalidParameterTypeException invalid type value is inputted to a condition
     * @throws ConditionStatementFromGlobalScopeException condition statement encountered in the global scope's code
     * lines
     */
    protected void conditionHandling(String codeLine, Node curNode) throws NotExistingInitiatedVariableInputException,
            InvalidParameterTypeException, ConditionStatementFromGlobalScopeException {
        String[] parametersChecked =  splitBracketedParameters(codeLine, RegExp.CONDITIONAL_VALID_SEPARATORS);
        for (String parameter: parametersChecked) {
            String typeOfParameter = typeOfValue(parameter, curNode);
            if (typeOfParameter == null) throw new NotExistingInitiatedVariableInputException(); // if the assigning value is
                // not a recognized type and doesn't exist in the relevant scopes as variable an error is
                // thrown
            else if (!typeOfParameter.matches(RegExp.CONDITIONAL_VALID_VALUES)) throw new
                    InvalidParameterTypeException();
        }
    }

    /**
     * the method checks if the function call is valid - if the method exists in the global scope and if
     * the parameters inputted correspond with existing method
     * @param codeLine the inputted string method declaration
     * @param curNode the current node where the method declared
     * @throws NotExistingInitiatedVariableInputException a variable is not existing or not initiated in scope and all
     * parent and global scope
     * @throws NotExistingMethodException the method is declared in the global scope
     * @throws InvalidMethodInputException too many arguments in method call
     * @throws InvalidParameterTypeException invalid type value is inputted to a method
     * @throws MethodCallFromGlobalScopeException method call encountered in the global scope's code lines
     */
    protected void functionCall(String codeLine, Node curNode) throws NotExistingInitiatedVariableInputException,
            NotExistingMethodException, InvalidMethodInputException, InvalidParameterTypeException, MethodCallFromGlobalScopeException {
        Matcher nameMatcher = RegExp.patterMethodName.matcher(codeLine);
        String methodName = findSubString(codeLine,nameMatcher, true);
        if (!curNode.getOuterScopeMethods().containsKey(methodName)) throw new NotExistingMethodException();  // if the
            // method wasn't declared in the global scope an exception thrown
        else {
            String[] parameters = splitBracketedParameters(codeLine, RegExp.COMMA_SEPARATOR);
            String[] listOfParametersInputted = new String[parameters.length];
            for (int parameterIndex = 0; parameterIndex < listOfParametersInputted.length; parameterIndex++){
                String curType = typeOfValue(parameters[parameterIndex], curNode);
                if (curType == null) throw new NotExistingInitiatedVariableInputException(); // if the assigning value
                // is not a recognized type and doesn't exist in the relevant scopes as variable an error
                // is thrown
                listOfParametersInputted[parameterIndex] = curType;
            }
            String[] declaredParameters = curNode.getOuterScopeMethods().get(methodName)
                    .parameterTypes;
            /*
            building a list of types of inputted parameters in the call
             */
            if (listOfParametersInputted.length != declaredParameters.length) throw new InvalidMethodInputException
                    (); // check if the num of parameters called and stored is different - throwing an
                // exception
            else {
                for (int parameterIndex =0; parameterIndex< listOfParametersInputted.length;
                     parameterIndex++) {
                    if (!Objects.equals(listOfParametersInputted[parameterIndex],
                            declaredParameters[parameterIndex]))
                        throw new InvalidParameterTypeException();
                }
            }
            /*
            checking each type inputted by order if corresponded with reserved type
             */
        }

    }

    /**
     * the method creates the scope of the scopeNode inputted with the code lines stored in the curNode.
     * The curNode is the simple code blocks while the scope nodes are conditional and method scope
     * represented by nodes
     * @param curNode the simple block code
     * @throws VariableInScopeRedeclarationException variable is declared although existing already in current scope
     * @throws NotInitiatedFinalVariableException a variable is both final and not initiated
     * @throws InvalidAssigningToFinalVariableException assigning a value to a final modified variable
     * @throws NotExistingInitiatedVariableInputException a variable is not existing or not initiated in scope and all
     * parent and global scope
     * @throws InvalidTypeAssignmentException assigning a value to variable from two not applicable types
     * @throws ReturnStatementFromGlobalScopeException return statement encountered in the global scope's code lines
     * @throws MethodCallFromGlobalScopeException method call encountered in the global scope's code lines
     * @throws InvalidParameterTypeException invalid type value is inputted to a method
     * @throws ConditionStatementFromGlobalScopeException condition statement encountered in the global scope's code
     * lines
     * @throws MethodDeclarationFromInnerScopeException method declaration encountered in the an inner scope's code
     * lines
     * @throws InvalidMethodInputException too many arguments in method call
     * @throws NotExistingMethodException the method is declared in the global scope
     */
    public void creating(Node curNode) throws VariableInScopeRedeclarationException, NotInitiatedFinalVariableException,
            InvalidAssigningToFinalVariableException,
            NotExistingInitiatedVariableInputException, InvalidTypeAssignmentException, ReturnStatementFromGlobalScopeException,
            MethodCallFromGlobalScopeException, InvalidParameterTypeException,ConditionStatementFromGlobalScopeException,
            MethodDeclarationFromInnerScopeException,InvalidMethodInputException, NotExistingMethodException {
        creating(curNode, curNode);
    }

    /**
     * the method creates the scope of the scopeNode inputted with the code lines stored in the curNode.
     * The curNode is the simple code blocks while the scope nodes are conditional and method scope
     * represented by nodes
     * @param curNode the simple block code
     * @param scopeNode the conditional/method node that its scope is built
     * @throws VariableInScopeRedeclarationException variable is declared although existing already in current scope
     * @throws NotInitiatedFinalVariableException a variable is both final and not initiated
     * @throws InvalidAssigningToFinalVariableException assigning a value to a final modified variable
     * @throws NotExistingInitiatedVariableInputException a variable is not existing or not initiated in scope and all
     * parent and global scope
     * @throws InvalidTypeAssignmentException assigning a value to variable from two not applicable types
     * @throws ReturnStatementFromGlobalScopeException return statement encountered in the global scope's code lines
     * @throws MethodCallFromGlobalScopeException method call encountered in the global scope's code lines
     * @throws InvalidParameterTypeException invalid type value is inputted to a method
     * @throws ConditionStatementFromGlobalScopeException condition statement encountered in the global scope's code
     * lines
     * @throws MethodDeclarationFromInnerScopeException method declaration encountered in the an inner scope's code
     * lines
     * @throws InvalidMethodInputException too many arguments in method call
     * @throws NotExistingMethodException the method is declared in the global scope
     */
    public void creating(Node curNode, Node scopeNode) throws
            VariableInScopeRedeclarationException, NotInitiatedFinalVariableException, InvalidAssigningToFinalVariableException,
            NotExistingInitiatedVariableInputException, InvalidTypeAssignmentException, ReturnStatementFromGlobalScopeException,
            MethodCallFromGlobalScopeException, InvalidParameterTypeException,ConditionStatementFromGlobalScopeException,
            MethodDeclarationFromInnerScopeException,InvalidMethodInputException, NotExistingMethodException
             {
        for (String line: curNode.getData()) {
            String lineType = identifier(line); // each code line is identified by type of code line
            if (Objects.equals(lineType, VARIABLE_CREATION)) {
                createVariable(line, scopeNode, false);
            }
            else if (Objects.equals(lineType, METHOD_CREATION)) {
                methodDeclaration(line,scopeNode);
            }
            else if (Objects.equals(lineType, RETURN_STATEMENT)) {
                returnLine();
            }
            else if (Objects.equals(lineType, BRACKET)) {
                bracketLine();
            }
            else if (Objects.equals(lineType, ASSIGNMENT)) {
                assignValue(line,scopeNode);
            }
            else if (Objects.equals(lineType, CONDITION_HANDLING)) {
                conditionHandling(line, scopeNode);
            }
            else functionCall(line,scopeNode);
        }
        /*
        iterates over each code line
         */
    }


    /**
     * the identifier checks each code line for its type of line using regex.
     * @param codeLine inputted code line to check
     * @return the type of line represented by String
     */
    private String identifier(String codeLine) {

        Matcher firstWordMatcher = RegExp.patternValidNonBlankCodeLine.matcher(codeLine);
        String firstWord = findSubString(codeLine, firstWordMatcher, true); // isolate first word of the
        // code line
        Matcher methodMatcher = RegExp.patternMethodDeclaration.matcher(codeLine);
        //Matcher variableMatcher = RegExp.patternVariableName.matcher(firstWord);
        for (IdentifierMatcher regex: IdentifierMatcher.values()) {
            Pattern checkRegex = Pattern.compile(regex.getReg());
            Matcher regexMatcher = checkRegex.matcher(firstWord);
            if(regexMatcher.matches()) return regex.getType();
        }
        if (methodMatcher.find()) return METHOD_USE;
        else  return ASSIGNMENT;

    }


    /**
     * the method transfers a new variable code line to a variable in the scope. it isolates variables final
     * modifier (if exists), type, checks if exists already in the current scope and checks if it
     * correspond with the input (variable of constant).
     * @param codeLine the code line to transfer
     * @param curNode current scope's node
     * @param fromMethod boolean value if the creation of the variable is from method declaration line or
     *                   a simple code block
     * @throws VariableInScopeRedeclarationException variable is declared although existing already in current scope
     * @throws NotInitiatedFinalVariableException a variable is both final and not initiated
     * @throws InvalidAssigningToFinalVariableException assigning a value to a final modified variable
     * @throws NotExistingInitiatedVariableInputException a variable is not existing or not initiated in scope and all
     * parent and global scope
     * @throws InvalidTypeAssignmentException assigning a value to variable from two not applicable types
     */
    protected void createVariable (String codeLine, Node curNode, boolean fromMethod) throws
            VariableInScopeRedeclarationException, NotInitiatedFinalVariableException, InvalidAssigningToFinalVariableException,
            NotExistingInitiatedVariableInputException, InvalidTypeAssignmentException {
        Matcher typeOrModifierMatcher = RegExp.patternInitTypesOrModifiers.matcher(codeLine);
        String typeOfNewVariable = findSubString(codeLine, typeOrModifierMatcher, true);
        boolean modifierOfNewVariable = false;
        if (Objects.equals(typeOfNewVariable, RegExp.FINAL_MODIFIER)) {
            modifierOfNewVariable = true;
            typeOfNewVariable = findSubString(codeLine, typeOrModifierMatcher, true);
        }
        /*
        isolation of the type and modifier (by isolation of first word) - if the first word is a final
        modifier it isolates next the type.
         */
        String variables = sliceString(codeLine, typeOrModifierMatcher, false); // slice the final
        // modifier and type from the code line
        String[] difVariables = variables.split(RegExp.COMMA_SEPARATOR); // splits the remaining code line
        // to an array consisting of each variable segment
        for (String newVarSegment: difVariables) {
            //Matcher varMatcher = RegExp.patternVariableName.matcher(newVarSegment);
            String newVariable = findSubString(newVarSegment,  RegExp.patternVariableName.matcher
                    (newVarSegment),true); // isolates the new variable name
            if (curNode.getScopeTokens().containsKey(newVariable)) throw new VariableInScopeRedeclarationException()
                    ;  //if the variable already exists in the scope and exception is throw
            Token newToken = new Token(newVariable, typeOfNewVariable, false, fromMethod);
            curNode.getScopeTokens().put(newToken.tokenName,newToken); // the new token is created and put to
            // the token hashmap representing the variable set of the current scope
            if (newVarSegment.contains(RegExp.EQUATION_OPERATOR)) assignValue(newVarSegment, curNode); //
            // if the equal operator is present in this segment the assigning take place to the new
            // variable.
            newToken.isFinal = modifierOfNewVariable;
            if (!newToken.isInitiated && newToken.isFinal) throw new NotInitiatedFinalVariableException(); // if the
            // new variable is
            // both final and not initiated an exception is thrown
        }
        /*
        iterates over each variable declared in the code line
         */
    }


    /**
     * the method returns a substring sliced (right from the matched substring or the matching substring
     * itself) by the non initiated matcher inputted
     * @param str inputted string
     * @param matcher the inputted matcher
     * @param sliceRight boolean value for slicing the string to the right of the matching substring
     * @return the sliced string
     */
    protected String findSubString(String str, Matcher matcher, boolean sliceRight) {
        if (matcher.find()) return sliceString(str, matcher, sliceRight);
        else return null;
    }

    /**
     * the method slices the string inputted (right from the matched substring or the matching substring
     * itself) based on already initiated matcher
     * @param str inputted string
     * @param matcher the inputted matcher
     * @param sliceRight boolean value for slicing the string to the right of the matching substring
     * @return the sliced string
     */
    protected String sliceString(String str, Matcher matcher, boolean sliceRight) {
        int startOfStrIndex = matcher.start();
        int endOfStrIndex = matcher.end();
        if (sliceRight) return str.substring(startOfStrIndex, endOfStrIndex);
        else return str.substring(endOfStrIndex);
    }

    /**
     * the method checks if the the assigning of value to a variable in the inputted code line is valid
     * @param codeLine the inputted string method declaration
     * @param curNode the current node where the method declared
     * @throws NotExistingInitiatedVariableInputException a variable is not existing or not initiated in scope and all
     * parent and global scope
     * @throws InvalidTypeAssignmentException assigning a value to variable from two not applicable types
     * @throws InvalidAssigningToFinalVariableException assigning a value to a final modified variable
     */
    private void assignValue(String codeLine, Node curNode) throws NotExistingInitiatedVariableInputException,
            InvalidTypeAssignmentException,InvalidAssigningToFinalVariableException
             {
        Matcher valMatcher = Pattern.compile(RegExp.ASSIGNING).matcher(codeLine);
        String assignedVarName = findSubString(codeLine, valMatcher, true);
        Token assignedVar = findTokenInRelevantScopes(assignedVarName, curNode, true); // finds the
        // variable in relevant scopes, if its from the global scope it wasn't initiated, it's initiated
        // in the current method scope
        if (assignedVar == null) throw new NotExistingInitiatedVariableInputException();
        if (assignedVar.isFinal) throw new InvalidAssigningToFinalVariableException();
        /*
        if the variable to be assigned doesn't exists or exist but is already final - an exception is thrown
         */
        String assigningValueName = findSubString(codeLine, valMatcher, true);
        initVar(assigningValueName, curNode, assignedVar); // the method checks if the assigning value is
        // valid and corresponds with the assigned variable
    }


    /**
     * the method initiates the variable in the input token if the value name assigned to it is valid and
     * corresponds with the variable type.
     * @param assigningValue the value to be assigned to the variable in the token
     * @param curNode the current scope's node
     * @param curToken the token holding the assigned variable
     * @throws NotExistingInitiatedVariableInputException a variable is not existing or not initiated in scope and all
     * parent and global scope
     * @throws InvalidTypeAssignmentException assigning a value to variable from two not applicable types
     */
    private void initVar(String assigningValue, Node curNode, Token curToken) throws
            NotExistingInitiatedVariableInputException, InvalidTypeAssignmentException {
        String valType = typeOfValue(assigningValue, curNode);
        if (valType == null) throw new NotExistingInitiatedVariableInputException(); // if the assigning value is
        // not  a recognized type and doesn't exist in the relevant scopes as variable an error is thrown
            if (!(Objects.equals(curToken.type,RegExp.DOUBLE_TYPE) && Objects.equals(valType,RegExp
                    .INT_TYPE))
                    && !(Objects.equals(curToken.type,RegExp.BOOLEAN_TYPE) && (Objects.equals
                    (valType,RegExp.INT_TYPE) || Objects.equals
                    (valType,RegExp.DOUBLE_TYPE) )) &&
                    !Objects.equals(valType,curToken.type)) throw new
                    InvalidTypeAssignmentException(); // if the assigning value and the assigned variable types don't
        // correspond an exception thrown
        curToken.isInitiated = true;
        curNode.getScopeTokens().put(curToken.tokenName, curToken);
    }

    /**
     * the method returns the type of value inputted, either constant's type or an existing variable type
     * if initiated
     * @param value the value to check
     * @param curNode the current scope's node
     * @return the type of the assigning value, null if not exists and not constant
     */
    private String typeOfValue(String value, Node curNode)  {
        String ifValueNotVariable = typeOfVar(value);
        if (ifValueNotVariable != null) {
            return ifValueNotVariable;
        }
        /*
        returns type of constant if it's any recognized type of constant
         */
        else {
            Token existingVariable = findTokenInRelevantScopes(value, curNode, false); // finds the
            // variable in relevant scopes
            if (existingVariable != null) if (existingVariable.isInitiated) return existingVariable.type;
            return null; // if it's neither an initiated variable form relevant scope or an constant, null
            // is returned
        }
    }

    /**
     * the method returns the token holding the variable with the same name as the inputted string if
     * exists in the current, ancestor scope or global scope. Also if the name looked in this method is of
     * a variable to be assigned exists already in the global scope but not withing the ancestor scope then
     * a deep copy of that global variable is initiated within the node's method scope.
     * @param posVar possible variable name
     * @param curNode current scope's node
     * @param assigned a boolean variable if the possible variable to be assigned
     * @return the token represents an existing variable with the same name as inputted string
     */
    private Token findTokenInRelevantScopes(String posVar, Node curNode, boolean assigned) {
        if (curNode.getParent() != null) {
            if (curNode.getScopeTokens().containsKey(posVar)) return curNode.getScopeTokens().get(posVar);
            else return findTokenInRelevantScopes(posVar, curNode.getParent(), assigned);
        }
        /*
        if its an inner node, it checks the variable in local scope and checks for its parent one
         */
        else {
            if (curNode.getScopeTokens().containsKey(posVar)) return curNode.getScopeTokens().get(posVar);
            else if (curNode.getOuterScope().containsKey(posVar)) {
                Token globalVar = curNode.getOuterScope().get(posVar);
                if (assigned) {
                    Token newToken = new Token(globalVar.tokenName,globalVar.type,globalVar
                            .isFinal,globalVar.isInitiated);
                    curNode.getScopeTokens().put(posVar, newToken);
                    return newToken;
                }
                /*
                if the variable looked is to be assigned in some inner scope a deepcopy of the global
                variable is initiated in the method scope and it is returned
                 */
                else return globalVar;
            }
            /*
            if there is no parent to the node - its the method scope so the variable is checked in the
            method scope and then in the global one
             */
            else return null;
        }
    }


    /**
     * splits the string in brackets (within a code line like condition or method declaration or method use)
     * by the inputted regex
     * @param codeLine the inputted code line
     * @param reg the inputted regex
     * @return a string array with relevant parameters
     */
    protected String[] splitBracketedParameters(String codeLine, String reg) {
        Matcher inputParametersMatcher = RegExp.patternParametersBrackets.matcher(codeLine);
        String parametersInput = findSubString(codeLine, inputParametersMatcher, true);
        /* isolating the bracket and the string withing from the whole code line */
        if (RegExp.patternEmptyBrackets.matcher(parametersInput).matches()) return new String[0]; // if
        // the brackets are empty a zero length array returned
        String sliceLeftBracket = findSubString(parametersInput, RegExp.patternLeftBracket.matcher
                (parametersInput), false);
        Matcher rightBracketMatcher = RegExp.patternRightBracket.matcher(sliceLeftBracket);
        rightBracketMatcher.find();
        String sliceRightBracket  = sliceLeftBracket.substring(0,rightBracketMatcher.start());
        /*
        deleting the brackets and redundant spaces next to them
         */
        return sliceRightBracket.split(reg); // returns the splitted string from withing the brackets
    }

    /**
     * returns type of constant from enum cas if the inputted string is a constant
     * @param str inputted possible constant to check
     * @return the type of the constant, if not a constant then null
     */
    private static String typeOfVar(String str) {
        for(VariablesRegex regVar:VariablesRegex.values())
        {
            if (str.matches(RegExp.BLANK_LINE+regVar.getReg()+ RegExp.BLANK_LINE))
                return regVar.getType();
        }
        return null;
    }

}
