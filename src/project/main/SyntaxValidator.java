package project.main;

import project.parsing.RegExp;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Enum collection that contains all the possible regular expressions that identify valid lines of code
 */
public enum SyntaxValidator {

    INT_DECLARATION("[\\s]*((final[\\s]+int[\\s]+"+ RegExp.VARIABLE_NAME+"[\\s]*\\=[\\s]*(\\-?[\\d]+|"
            +RegExp.VARIABLE_NAME+")[\\s]*(\\;[\\s]*|(,[\\s]*"+RegExp.VARIABLE_NAME+"[\\s]*)*[\\s]*" +
            "(\\;[\\s]*|\\=[\\s]*(\\-?[\\d]+|"+RegExp.VARIABLE_NAME+")[\\s]*\\;[\\s]*)))|int[\\s]+"
            +RegExp.VARIABLE_NAME+"[\\s]*(\\;|(,[\\s]*"+RegExp.VARIABLE_NAME+"[\\s]*)*[\\s]*(\\;[\\s]*|\\=[\\s]*" +
            "(\\-?[\\d]+|"+RegExp.VARIABLE_NAME+")[\\s]*((,[\\s]*"+RegExp.VARIABLE_NAME+"[\\s]*)*[\\s]*" +
            "(\\;[\\s]*|\\=[\\s]*(\\-?[\\d]+|"+RegExp.VARIABLE_NAME+")[\\s]*\\;[\\s]*)))))"),
    DOUBLE_DECLARATION("[\\s]*((final[\\s]+double[\\s]+"+RegExp.VARIABLE_NAME+"[\\s]*\\=[\\s]*" +
            "("+RegExp.DOUBLE_VALUE+"|"+RegExp.VARIABLE_NAME+")[\\s]*(\\;[\\s]*|(,[\\s]*"+RegExp.VARIABLE_NAME+
            "[\\s]*)*[\\s]*(\\;[\\s]*|\\=[\\s]*("+RegExp.DOUBLE_VALUE+"|"+RegExp.VARIABLE_NAME+
            ")[\\s]*\\;[\\s]*)))|double[\\s]+"+RegExp.VARIABLE_NAME+"[\\s]*(\\;[\\s]*|(,[\\s]*"+RegExp.VARIABLE_NAME+
            "[\\s]*)*[\\s]*(\\;[\\s]*|\\=[\\s]*("+RegExp.DOUBLE_VALUE+"|"+RegExp.VARIABLE_NAME+")[\\s]*((,[\\s]*"+
            ""+RegExp.VARIABLE_NAME+"[\\s]*)*[\\s]*(\\;[\\s]*|\\=[\\s]*" + "("+RegExp.DOUBLE_VALUE+"|"
            +RegExp.VARIABLE_NAME+")[\\s]*\\;[\\s]*)))))"),
    CHAR_DECLARATION("[\\s]*((final[\\s]+char[\\s]+"+RegExp.VARIABLE_NAME+"[\\s]*\\=[\\s]*('.'|"+RegExp.VARIABLE_NAME+
            ")[\\s]*(\\;|(,[\\s]*"+RegExp.VARIABLE_NAME+"[\\s]*)*[\\s]*(\\;[\\s]*|\\=[\\s]*('.'|"
            +RegExp.VARIABLE_NAME+")[\\s]*\\;[\\s]*)))|char[\\s]+"+RegExp.VARIABLE_NAME+"[\\s]*(\\;|(,[\\s]*"
            +RegExp.VARIABLE_NAME+"[\\s]*)*[\\s]*(\\;[\\s]*|\\=[\\s]*('.'|"+RegExp.VARIABLE_NAME+")[\\s]*((,[\\s]*"
            +RegExp.VARIABLE_NAME+"[\\s]*)*[\\s]*(\\;[\\s]*|\\=[\\s]*('.'|"+RegExp.VARIABLE_NAME+
            ")[\\s]*\\;[\\s]*)))))"),
    STRING_DECLARATION("[\\s]*((final[\\s]+String[\\s]+"+RegExp.VARIABLE_NAME+"[\\s]*\\=[\\s]*(\".*\"|"
            +RegExp.VARIABLE_NAME+")[\\s]*(\\;[\\s]*|(,[\\s]*"+RegExp.VARIABLE_NAME+"[\\s]*)*[\\s]*(\\;[\\s]*|\\=" +
            "[\\s]*(\".*\"|"+RegExp.VARIABLE_NAME+")[\\s]*\\;[\\s]*)))|String[\\s]+"+RegExp.VARIABLE_NAME+"[\\s]*" +
            "(\\;[\\s]*|(,[\\s]*"+RegExp.VARIABLE_NAME+"[\\s]*)*[\\s]*(\\;[\\s]*|\\=[\\s]*(\".*\"|"
            +RegExp.VARIABLE_NAME+")[\\s]*((,[\\s]*"+RegExp.VARIABLE_NAME+"[\\s]*)*[\\s]*(\\;[\\s]*|\\=[\\s]*" +
            "(\".*\"|"+RegExp.VARIABLE_NAME+")[\\s]*\\;[\\s]*)))))"),
    BOOLEAN_DECLARATION("[\\s]*((final[\\s]+boolean[\\s]+"+RegExp.VARIABLE_NAME+"[\\s]*\\=[\\s]*" +
            "(true|false|"+RegExp.DOUBLE_VALUE+"|"+RegExp.VARIABLE_NAME+")[\\s]*(\\;[\\s]*|(," +
            "[\\s]*"+RegExp.VARIABLE_NAME+")*[\\s]*(\\;" +
            "[\\s]*|\\=[\\s]*(true|false|"+RegExp.DOUBLE_VALUE+"|"+RegExp.VARIABLE_NAME+")[\\s]*\\;[\\s]*)))" +
            "|boolean[\\s]+"+RegExp.VARIABLE_NAME+"[\\s]*(\\;[\\s]*|(,[\\s]*"+RegExp.VARIABLE_NAME+"[\\s]*)*[\\s]*" +
            "(\\;[\\s]*|\\=[\\s]*(true|false|"+RegExp.DOUBLE_VALUE+"|"+RegExp.VARIABLE_NAME+")[\\s]*((,[\\s]*"
            +RegExp.VARIABLE_NAME+"[\\s]*)*[\\s]*(\\;[\\s]*|\\=[\\s]*(true|false|"+RegExp.DOUBLE_VALUE+"|"
            +RegExp.VARIABLE_NAME+")[\\s]*\\;[\\s]*)))))"),
    METHOD_DECLARATION("[\\s]*void[\\s]+[a-zA-Z][\\w]*[\\s]*\\([\\s]*(\\)|(final[\\s]+|)"
            +RegExp.VARIABLE_TYPES+"[\\s]+[a-zA-Z][\\w]*[\\s]*(\\)|(,[\\s]+(final[\\s]+|)" +
            ""+RegExp.VARIABLE_TYPES+"[\\s]+[a-zA-Z][\\w]*)*[\\s]*\\)))[\\s]*\\{[\\s]*"),
    METHOD_CALL("[\\s]*[a-zA-Z][\\w]*[\\s]*\\([\\s]*(\\)|("+RegExp.DOUBLE_VALUE+"|"+RegExp.VARIABLE_NAME+
            "|'.'|\".*\"|)[\\s]*([\\s]*,[\\s]*("+RegExp.DOUBLE_VALUE+"|"+RegExp.VARIABLE_NAME+"|'.'|\".*\")" +
            ")*[\\s]*\\))[\\s]*\\;[\\s]*"),
    CONDITION_DECLARATION("[\\s]*(if|while)[\\s]*\\([\\s]*(([\\s]*("+RegExp.DOUBLE_VALUE+"|" +
            ""+RegExp.VARIABLE_NAME+"))[\\s]*(\\)|((\\|\\||\\&\\&)[\\s]*("+RegExp.DOUBLE_VALUE+"|"+
            RegExp.VARIABLE_NAME+")[\\s]*)*)[\\s]*\\))[\\s]*\\{[\\s]*"),
    END_OF_SCOPE("[\\s]*\\}[\\s]*"),RETURN_STATEMENT(RegExp.VALID_RETURN_LINE),COMMENT(RegExp.COMMENT_LINE),
    EMPTY_LINE("[\\s]*"), VARIABLE_NAME_ASSIGNMENT("[\\s]*"+RegExp.VARIABLE_NAME+"[\\s]*\\=[\\s]*("+RegExp
            .VARIABLE_NAME+"|"+RegExp.DOUBLE_VALUE+"|'.'|\".*\")[\\s]*\\;[\\s]*");

    private final String regex;

    SyntaxValidator(String regex){
        this.regex = regex;
    }

    public String getRegex(){return regex;}

    /**
     * The method gets a line from the code and using the enums from this class checks if the line is valid.
     * @param line line from the code given
     * @throws InvalidLineSyntaxException Exception for the case the line is invalid text wise.
     */
    static void validate(String line) throws InvalidLineSyntaxException {
        for (SyntaxValidator regex: SyntaxValidator.values())
        {
            Pattern checkRegex = Pattern.compile(regex.getRegex());
            Matcher regexMatcher = checkRegex.matcher(line);
            if(regexMatcher.matches()){
                return;
            }
        }
        throw new InvalidLineSyntaxException();
    }
}