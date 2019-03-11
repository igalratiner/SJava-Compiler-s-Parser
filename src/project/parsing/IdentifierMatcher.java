package project.parsing;

/**
 * the enum identifying code lines by their first word
 */
public enum IdentifierMatcher {

    /**
     * Enum collection that contains all the possible regular expressions that identify the code line types.
     */
    typesMatcher(RegExp.INIT_TYPES_AND_MODIFIERS,"VARIABLE_CREATION"),
    methodTypeMatcher(RegExp.METHOD_TYPE,"METHOD_CREATION"),
    returnMatcher(RegExp.RETURN_LINE,"RETURN_STATEMENT"),
    bracketMatcher(RegExp.CLOSING_SCOPE_BRACKET,"BRACKET"),
    conditionMatcher(RegExp.CONDITIONS,"CONDITION_HANDLING");

    private final String reg;
    private final String type;

    /**
     * constructor for the IdentifierMatcher
     * @param regex the regex capturing the type of code line
     * @param type the type tag
     */
    IdentifierMatcher(String regex, String type){
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
