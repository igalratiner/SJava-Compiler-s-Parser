package project.parsing;

import java.util.regex.Pattern;

/**
 * the class holds the regular expressions and patterns used by the program classes
 */
public class RegExp {

        final static String ASSIGNING = RegExp.VARIABLE_NAME+"|"+RegExp.DOUBLE_VALUE+"|'.'|\".*\"";
        public final static String VARIABLE_NAME = "(\\_[A-Za-z_][\\w]*|[A-Za-z][\\w]*)";
        final static String METHOD_TYPE = "void";
        final static String STRING_TYPE = "String";
        final static String CHAR_TYPE = "char";
        final static String DOUBLE_TYPE = "double";
        final static String INT_TYPE = "int";
        final static String BOOLEAN_TYPE = "boolean";
        final static String FINAL_MODIFIER = "final";
        final static String RETURN_LINE = "[\\s]*return[\\s]*";
        public final static String VARIABLE_TYPES = "(int|double|char|boolean|String)";
        final static String INIT_TYPES_AND_MODIFIERS = "final|int|double|char|boolean|String";
        final static String CONDITIONS = "if|while";
        final static String CLOSING_SCOPE_BRACKET = "[\\s]*}[\\s]*";
        final static String OPENING_SCOPE_BRACKET = "[\\s]*\\{[\\s]*";
        final static String STRING_VALUE = "(\".*\")";
        final static String CHAR_VALUE = "(\\'.\\')";
        public final static String DOUBLE_VALUE = "(-?[\\d]+\\.?[\\d]+|-?[\\d]+)";
        final static String INT_VALUE = "(-?[0-9]+)";
        final static String BOOLEAN_VALUE = "(true|false)";
        final static String PARAMETERS_BRACKETS = "\\(.*\\)";
        final static String METHOD_NAME = "[a-zA-Z]+[\\w]*";
        public final static String VALID_RETURN_LINE = "[\\s]*return[\\s]*;[\\s]*";
        final static String LEFT_BRACKET = "\\s*\\(\\s*";
        final static String RIGHT_BRACKET = "\\s*\\)\\s*";
        public final static String BLANK_LINE = "[\\s]*";
        public final static String COMMENT_LINE = "//.*";
        final static String VALID_NON_BLANK_CODE_LINE = "[\\w]+|}";
        final static String CONDITIONAL_VALID_VALUES = "int|double|boolean";
        final static String CONDITIONAL_VALID_SEPARATORS = "\\s*&&\\s*|\\s*\\|\\|\\s*";
        final static String COMMA_SEPARATOR = "\\s*,\\s*";
        final static String EQUATION_OPERATOR = "=";
        final static String EMPTY_BRACKETS = "\\(\\s*\\)";

        final static Pattern patternEmptyBrackets = Pattern.compile(EMPTY_BRACKETS);
        final static Pattern patternValidNonBlankCodeLine = Pattern.compile(VALID_NON_BLANK_CODE_LINE);
        final static Pattern patternCommentLine = Pattern.compile(COMMENT_LINE);
        final static Pattern patternBlankLine = Pattern.compile(BLANK_LINE);
        final static Pattern patternLeftBracket = Pattern.compile(LEFT_BRACKET);
        final static Pattern patternRightBracket = Pattern.compile(RIGHT_BRACKET);
        public final static Pattern patternValidReturnLine = Pattern.compile(VALID_RETURN_LINE);
        final static Pattern patternParametersBrackets = Pattern.compile(PARAMETERS_BRACKETS);
        final static Pattern patternConditions = Pattern.compile(CONDITIONS);
        final static Pattern patternInitTypesOrModifiers = Pattern.compile(INIT_TYPES_AND_MODIFIERS);
        final static Pattern patterMethodName = Pattern.compile(METHOD_NAME);
        final static Pattern patternMethodDeclaration = Pattern.compile(METHOD_NAME+"[\\s]*\\(.*\\)[\\s]*");
        final static Pattern patternVariableName = Pattern.compile(VARIABLE_NAME);
        final static Pattern patternTypes = Pattern.compile(VARIABLE_TYPES);
        final static Pattern patternReturn = Pattern.compile(RETURN_LINE);
        public final static Pattern patternOpeningScopeBracket = Pattern.compile(OPENING_SCOPE_BRACKET);
        public final static Pattern patternClosingScopeBracket = Pattern.compile(CLOSING_SCOPE_BRACKET);
        final static Pattern patternMethodType = Pattern.compile(METHOD_TYPE);



}
