package project.main;

import project.parsing.MethodToken;
import project.parsing.Token;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;


/**
 * class representing scopes as nodes in tree
 */
public class Node {
    final static String GLOBAL_NODE_TYPE = "GLOBAL";
    final static String METHOD_NODE_TYPE = "METHOD";
    final static String CONDITION_NODE_TYPE = "CONDITION";
    final static String SIMPLE_CODE_BLOCK_NODE_TYPE = "SIMPLE_BLOCK";
    private HashMap<String, Token> outerScope; // the scope visible to objects in local node's scope
    HashMap<String, Token> scopeTokens; // the current set of objects in the scope
    HashMap<String, MethodToken> outerScopeMethods; // the set of methods from the outer scope visible to
    // this node
    private ArrayList<String> data; // the list of code lines of this scope
    String type; // the type of the node
    Node parent; // node's parent node
    ArrayList<Node> children = new ArrayList<>(); // list of child nodes of the node

    /**
     * the constructor of the global scope node
     * @param data the list of code lines of this scope
     * @param type the type of the node
     */
    Node(ArrayList<String> data, String type) {
        this.data = data;
        this.type = type;
        this.parent = null;
        this.outerScope = new HashMap<>();
        this.scopeTokens = new HashMap<>();
        this.outerScopeMethods = new HashMap<>();
    }

    /**
     * the constructor of the method scope node
     * @param data the list of code lines of this scope
     * @param type the type of the node
     * @param parent the node's parent
     * @param outerScope the global scope
     * @param outerMethodScope the global scope's methods
     */
    Node(ArrayList<String> data, String type, Node parent, HashMap<String,Token> outerScope,
         HashMap<String,MethodToken> outerMethodScope) {
        this.data = data;
        this.type = type;
        this.parent = parent;
        this.outerScope = outerScope;
        this.outerScopeMethods = outerMethodScope;
        this.scopeTokens = new HashMap<>();
    }

    /**
     * the constructor of an inner scope node
     * @param data the list of code lines of this scope
     * @param type the type of the node
     * @param parent the node's parent
     * @param outerMethodScope the global scope's methods
     */
    Node(ArrayList<String> data, String type, Node parent,
         HashMap<String,MethodToken> outerMethodScope) {
        this.data = data;
        this.type = type;
        this.parent = parent;
        this.outerScope = new HashMap<>();
        this.outerScopeMethods = outerMethodScope;
        this.scopeTokens = new HashMap<>();
    }

    /**
     * the method creates recursively each node it's childs nodes (as a subtree)
     */
    public void createChildNodes() {
        ArrayList<CodeBlock> codeBlocks = Reader.innerMethodScopeSegmentation(data); // the scope's code
        // lines are segmented to code blocks
        for (CodeBlock codeBlock : codeBlocks) {
            Node newNode;
            if (Objects.equals(codeBlock.blockTag,CodeBlock.SIMPLE_BLOCK_TYPE)) {
                newNode = new Node(codeBlock.codeLines, SIMPLE_CODE_BLOCK_NODE_TYPE, this,    this
                        .outerScopeMethods);
            }
            else {
               newNode = new Node(codeBlock.codeLines, CONDITION_NODE_TYPE, this, this
                        .outerScopeMethods);
                newNode.createChildNodes();
            }
            this.children.add(newNode);
        }
        /*
        each code block is converted to a node, while the non simple blocks are recursively repeat the
        process till their subtree is created
         */
    }

    /**
     * outer scope getter
     * @return outer scope
     */
    public HashMap<String, Token> getOuterScope() {
        return outerScope;
    }

    /**
     * scope token getter
     * @return hashmap of tokens of the scope
     */
    public HashMap<String, Token> getScopeTokens() {
        return scopeTokens;
    }

    /**
     * node's parent getter
     * @return node's parent
     */
    public Node getParent() {
        return parent;
    }

    /**
     * getter for the node's code lines list
     * @return the node's code lines list
     */
    public ArrayList<String> getData() {
        return data;
    }

    /**
     * scope's method-tokens getter
     * @return hashmap of method-tokens of the scope
     */
    public HashMap<String, MethodToken> getOuterScopeMethods() {
        return outerScopeMethods;
    }
}
