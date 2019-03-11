package project.parsing;

/**
 * enum for all types of primitive types
 */
public enum VariablesRegex {
    /**
     * Enum collection that contains all the possible regular expressions that identify the assignment type
     * a variable assigned with.
     */
    STRING_VALUE("(\".*\")", "String"),CHAR_VALUE("('.')", "char"),INT_VALUE("-?[\\d]+","int"),
    DOUBLE_VALUE ("-?[\\d]+\\.[\\d]+", "double"),BOOLEAN_VALUE("(true|false)", "boolean");

    private final String reg; // the regex capturing the type
    private final String type; // the type tag


    /**
     * constructor for the VariablesRegex
     * @param regex the regex capturing the type
     * @param type the type tag
     */
    VariablesRegex(String regex, String type){
        this.reg = regex;
        this.type = type;
    }

    /**
     * type getter
     * @return the regex type
     */
    public String getType(){return this.type;}

    /**
     * regex getter
     * @return the regex to identify the value type
     */
    public String getReg(){return this.reg;}

}
