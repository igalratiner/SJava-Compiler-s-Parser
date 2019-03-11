package project.main;

import project.parsing.RegExp;

import java.io.*;
import java.util.ArrayList;
import java.util.regex.Matcher;

/**
 * the class reads the code and responsible for segmentation to code line lists
 */
class Reader {
    static ArrayList<String> globalScope = new ArrayList<>(); // the global scope string line codes of the
    // sjavac file.
    static ArrayList<ArrayList<String>> methodsList = new ArrayList<>(); // the list of methods, each of
    // them holds its list of string line codes.


    /**
     * The read method creating the global scopes lines of codes and a list of methods consists of each
     * method's scope line of codes
     * @param codeFile the path of the appropriate simple java code
     * @throws IOException exception occurs while reading from file
     * @throws InvalidLineSyntaxException the code line's form is not valid by the javac syntax
     * @throws ExceedingParenthesesException there are more closing parentheses than opening ones in some point or
     * there different number of closing and opening nes at the end of file.
     * @throws NoReturnAtEndOfMethodException the last valid code line of a method before closing parentheses is not
     * a return code line
     */
    static void readFromFile(String codeFile)throws IOException,InvalidLineSyntaxException, ExceedingParenthesesException,
            NoReturnAtEndOfMethodException {
        FileReader fileReader = new FileReader(codeFile);
        BufferedReader bf = new BufferedReader(fileReader);
        String newLine = bf.readLine();
        int parenthesisCounter = 0;
        ArrayList<String> methodArray = new ArrayList<>();
        String lastLine = newLine;
        while (newLine != null) {
            if (!(newLine.matches(RegExp.BLANK_LINE) || newLine.matches(RegExp.COMMENT_LINE))){ // blank and
                // comments lines don't enter the scopes lists
                SyntaxValidator.validate(newLine); // if there is a syntax violation an exception is thrown
                Matcher openParenthesisMatcher = RegExp.patternOpeningScopeBracket.matcher(newLine);
                Matcher closedParenthesisMatcher = RegExp.patternClosingScopeBracket.matcher(newLine);
                if (parenthesisCounter == 0) {
                    globalScope.add(newLine);
                    if (openParenthesisMatcher.find()) {
                        methodArray = new ArrayList<>();
                        methodArray.add(newLine);
                        parenthesisCounter += 1;
                    }
                    /*
                    if an open parenthesis is noticed the global scope gets the method declaration line and
                     also a new method list is initiated and gets the current line too, the global scope is
                     not updated till the parenthesis counter is back to 0.
                     */
                }
                /*
                as long as the parenthesis counted shows 0 the lines of code are written to the global scope
                 */
                else if (parenthesisCounter > 0){
                    methodArray.add(newLine);
                    if (openParenthesisMatcher.find()) {
                        parenthesisCounter += 1;
                    }
                    if (closedParenthesisMatcher.matches()) {
                        parenthesisCounter -= 1;
                        if (parenthesisCounter == 0) {
                            if (!RegExp.patternValidReturnLine.matcher(lastLine).matches())
                                throw new NoReturnAtEndOfMethodException();
                            methodsList.add(methodArray);
                        }
                        /*
                        if a method closing parenthesis is encountered but the last code line before it
                        wasn't a return statement - an exception is thrown
                         */
                    }
                }
                /*
                if the parenthesis counter is positive the lines of code are written to the current method
                scope list
                 */
                else throw new ExceedingParenthesesException();
                lastLine = newLine;
            }
            newLine = bf.readLine();
        }
        if (parenthesisCounter != 0) throw new ExceedingParenthesesException();
    }




    /**
     * getter for global scope code lines
     * @return arraylist of strings representing the list of line codes of the global scope
     */
    static ArrayList<String> globalScopeSegmentation() {
        return globalScope;
    }

    /**
     * getter for method list of each method's scope code lines
     * @return arraylist of method scopes, while each method scope represented the list of line codes
     */
    static ArrayList<ArrayList<String>> outerMethodScopeSegmentation() {
        return methodsList;
    }

    /**
     * the method transfers an method/conditional scope represented by its code lines to new code blocks,
     * each block is either simple code belongs to the scope or inner scopes
     * @param innerCode the current scope's code lines list
     * @return the list of divided blocks of codes
     */
    static ArrayList<CodeBlock> innerMethodScopeSegmentation(ArrayList<String> innerCode) {
        boolean isMethodLine = true;
        ArrayList<CodeBlock> codeBlocks = new ArrayList<>();
        int parenthesisCounter = 1; // each scope's first line starts either with condition or method
        // declaration therefore the counter is set to 1
        ArrayList<String> conditionBlock = new ArrayList<>();
        ArrayList<String> simpleCodeBlock = new ArrayList<>();
        for (String lineCode : innerCode) {
            Matcher openParenthesisMatcher = RegExp.patternOpeningScopeBracket.matcher(lineCode);
            Matcher closedParenthesisMatcher = RegExp.patternClosingScopeBracket.matcher(lineCode);
            if (parenthesisCounter == 1) {
                if (openParenthesisMatcher.find() && !isMethodLine) {
                    codeBlocks.add(new CodeBlock(simpleCodeBlock, CodeBlock.SIMPLE_BLOCK_TYPE));
                    conditionBlock = new ArrayList<>();
                    conditionBlock.add(lineCode);
                    parenthesisCounter += 1;
                }
                /*
                as long as its not the method declaration (which starts as simple block), each open
                parenthesis encountered, the simple block ends and condition block starts
                 */
                else {
                    isMethodLine = false;
                    simpleCodeBlock.add(lineCode);
                    if (closedParenthesisMatcher.find())  codeBlocks.add(new CodeBlock(simpleCodeBlock,
                            CodeBlock.SIMPLE_BLOCK_TYPE));
                }
                /*
                if the a close parenthesis encountered and the counter set to 1 - the method ends and the
                last simple block added
                 */
            }
            else {
                conditionBlock.add(lineCode);
                if (closedParenthesisMatcher.matches()) {
                    parenthesisCounter -= 1;
                    if (parenthesisCounter == 1) {
                        codeBlocks.add(new CodeBlock(conditionBlock, CodeBlock.CONDITION_BLOCK_TYPE));
                        simpleCodeBlock = new ArrayList<>();
                    }
                }
                /*
                if the counter is bigger than 1, then the code i written to condition block until it returns
                 to 1 and then the condition block ends and a new simple code block initiated
                 */
            }
        }
        return codeBlocks;
    }

}
