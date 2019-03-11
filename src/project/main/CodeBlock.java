package project.main;


import java.util.ArrayList;

/**
 * the class tags lists of code lines by its sort for any to the the scope
 */
class CodeBlock {
    final static String SIMPLE_BLOCK_TYPE = "SIMPLE_BLOCK"; // code lines without any scopes within
    final static String CONDITION_BLOCK_TYPE = "CONDITION_BLOCK"; // code lines representing inner scope
    ArrayList<String> codeLines;  // list of code lines
    String blockTag; // the sort of the list of code lines

    /**
     *
     * @param codeLines list of code lines
     * @param blockTag the sort of the list of code lines
     */
    CodeBlock(ArrayList<String> codeLines, String blockTag) {
        this.codeLines = codeLines;
        this.blockTag = blockTag;
    }
}
