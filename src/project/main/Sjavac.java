package project.main;


import project.parsing.CreateGlobalScope;
import project.parsing.CreateInnerScope;
import project.parsing.CreateMethodScope;
import project.parsing.CreateScope;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

/**
 * the class is responsible for syntax and logical testing of simple java code.
 */
public class Sjavac {
    private static String IOE_EXCEPTION_ERROR_MSG = " : problem occurred reading the file"; // the
    // error explanatory message shown if IOException occurs
    private static String VALID_CODE_MSG = "0"; // message shown if the code is valid
    private static String NON_IOE_EXCEPTION_MSG = "1"; // message shown if non-IOException occurs
    private static String IOE_EXCEPTION_MSG = "2"; // message shown if IOException occurs


    /**
     * The driver of the program responsible for reading and testing simple java code for validation.
     * @param filePath the path of the appropriate simple java code
     * @throws IOException exception occurs while reading from file
     * @throws ParsingException the code line doesn't match the sjavac principles
     * @throws InvalidLineSyntaxException the code line's form is not valid by the javac syntax
     */
    private static void driver(String filePath) throws  IOException,
            ParsingException,InvalidLineSyntaxException {

        Reader.readFromFile(filePath); // reads the javac and creates list of string lines of codes for the
        // global scope and creates a list of all methods, while each method holds its own list of strings
        ArrayList<ArrayList<String>> methods = Reader.outerMethodScopeSegmentation();
        Node globalNode = new Node(Reader.globalScopeSegmentation(),Node.GLOBAL_NODE_TYPE); // node
        // representing the global scope
        CreateGlobalScope.getInstance().creating(globalNode); // creates the global scope consisted of
        // methods and variables out of list of strings.
        ArrayList<Node> subTrees = new ArrayList<>(); // list of all subtrees of methods
        for (ArrayList<String> method: methods) {
            Node methodNode = new Node(method, Node.METHOD_NODE_TYPE, null, globalNode.scopeTokens,
                    globalNode
                    .outerScopeMethods); // creating a node representing method
            methodNode.createChildNodes(); //creates the subtree of each node
            subTrees.add(methodNode);
        }
        for (Node subTree: subTrees) {
            recursiveScopeCreation(subTree, CreateMethodScope.getInstance()); // create all scopes for every
            // subtree of methods.
        }
    }

    /**
     * the recursive function creates an appropriate scope for the inputted node and for every child in the
     * subtree rooted from the inputted node.
     * @param scopeToCreate the current scope to create
     * @param scopeCreator the appropriate scope creator based on identity of each node (either method or
     *                     conditional scope)
     * @throws IOException exception occurs while reading from file
     * @throws ParsingException the code line doesn't match the sjavac principles
     * @throws InvalidLineSyntaxException the code line's form is not valid by the javac syntax
     */
    private static void recursiveScopeCreation(Node scopeToCreate, CreateScope scopeCreator) throws
            ParsingException, InvalidLineSyntaxException, IOException {
        if (scopeToCreate.children.size() == 0) return;
        for (Node childNode : scopeToCreate.children) {
            if (Objects.equals(childNode.type, Node.SIMPLE_CODE_BLOCK_NODE_TYPE)) {
                scopeCreator.creating(childNode, childNode.parent);
            } else {
                recursiveScopeCreation(childNode, CreateInnerScope.getInstance());
            }
            /*
            the scope is created via the "simple" block of codes that are children of their scope's node.
            The simple block are the actual lines of code in each scope (not including the method
            declaration and conditions). Those nodes hold the scopes of their parent method or conditional
            blocks.
             */
        }
    }




    public static void main(String[] args)  {

        Reader.globalScope = new ArrayList<>();
        Reader.methodsList = new ArrayList<>();
        /*
        nullifying the static variables of Reader class to pass the test - otherwise redundant.
         */

        try {
            driver(args[0]); // absolute path of the file
            System.out.println(VALID_CODE_MSG);
        }
        catch (IOException e) {
            System.err.println(e + IOE_EXCEPTION_ERROR_MSG);
            System.out.println(IOE_EXCEPTION_MSG);
        }
        catch (CompilerException e) {
            System.out.println(NON_IOE_EXCEPTION_MSG);
            System.err.println(e+e.getMessageError());
        }

    }


}
