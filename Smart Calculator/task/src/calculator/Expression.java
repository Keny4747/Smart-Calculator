package calculator;

import java.math.BigInteger;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class Expression {
    private static final Map<String, String> variables = new HashMap<>();
    private String expression;

    public Expression(String expression) {
        this.expression = expression;
    }

    public static boolean isInvalid(String expression) {
        return Pattern.compile("^[+-]?\\d+[+-]+").matcher(expression).find()
                || Pattern.compile("[*/]{2,}").matcher(expression).find();
    }

    public static boolean isValidIdentifier(String identifier) {
        return identifier.matches("[a-zA-Z]+");
    }

    private static boolean isValidAssignment(String assignment) {
        return isValidIdentifier(assignment) || assignment.matches("-?\\d+");
    }

    private static boolean isOperator(char op) {
        return Pattern.matches("[+*\\-/]", String.valueOf(op));
    }

    private static boolean isOperator(String op) {
        if (op.length() > 1) return false;
        return isOperator(op.charAt(0));
    }

    private static int getOpPrecedence(char op) {
        return switch (op) {
            case '+', '-' -> 1;
            case '*', '/' -> 2;
            default -> 0;
        };
    }

    public Map<String, String> getVariables() {
        return variables;
    }

    public boolean isVariableAssignment() {
        return expression.contains("=");
    }

    public void storeVariable() {
        expression = expression.replaceAll("\\s+", " ");
        String identifier = expression.split("=")[0].trim();
        String assignment = expression.split("=")[1].trim();
        if (!isValidIdentifier(identifier)) {
            System.out.println("Invalid identifier");
            return;
        }
        if (!isValidAssignment(assignment)) {
            System.out.println("Invalid assignment");
            return;
        }
        if (isValidIdentifier(assignment) && !variables.containsKey(assignment)) {
            System.out.println("Unknown variable");
            return;
        }
        variables.put(identifier, variables.getOrDefault(assignment, assignment));
    }

    public boolean isVariableQuery() {
        return expression.matches("\\w+");
    }

    public Deque<String> convertInfixToPostfix() throws Exception {
        Deque<Character> opStack = new ArrayDeque<>();
        Deque<String> output = new ArrayDeque<>();
        for (int i = 0; i < expression.length(); i++) {
            char currentChar = expression.charAt(i);
            if (isOperand(String.valueOf(currentChar))) {
                String buffer = "";
                while (i < expression.length() && isOperand(String.valueOf(expression.charAt(i)))) {
                    buffer += expression.charAt(i);
                    i++;
                }
                output.addLast(buffer);
                i--;
            } else if (currentChar == '(') {
                opStack.push(currentChar);
            } else if (currentChar == ')') {
                boolean foundEquivalent = false;
                while (!opStack.isEmpty()) {
                    char pop = opStack.pop();
                    if (isOperator(pop)) {
                        output.addLast(String.valueOf(pop));
                    } else if (pop == '(') {
                        foundEquivalent = true;
                        break;
                    }
                }
                if (!foundEquivalent) {
                    throw new Exception("Invalid expression");
                }
            } else if (isOperator(currentChar)) {
                while (!opStack.isEmpty() && getOpPrecedence(opStack.peek()) >= getOpPrecedence(currentChar)) {
                    output.addLast(String.valueOf(opStack.pop()));
                }
                opStack.push(currentChar);
            }
        }
        while (!opStack.isEmpty() && opStack.peek() != '(') {
            output.addLast(String.valueOf(opStack.pop()));
        }
        if (!opStack.isEmpty()) {
            throw new Exception("Invalid expression");
        }
        return output;
    }

    private boolean isOperand(String string) {
        return isValidAssignment(string);
    }

    private BigInteger calculate(BigInteger a, BigInteger b, char op) {
        return switch (op) {
            case '+' -> a.add(b);
            case '-' -> a.subtract(b);
            case '*' -> a.multiply(b);
            case '/' -> a.divide(b);
            default -> throw new ArithmeticException("Invalid Operator!");
        };
    }

    private BigInteger evaluatePostfix(Deque<String> postfix) {
        Deque<BigInteger> operandStack = new ArrayDeque<>();
        for (String token : postfix) {
            if (isOperand(token)) {
                operandStack.push(new BigInteger(variables.getOrDefault(token, token)));
            } else if (isOperator(token)) {
                BigInteger op2 =new BigInteger(String.valueOf(operandStack.pop()));
                BigInteger op1 = new BigInteger(String.valueOf(operandStack.pop()));
                operandStack.push(calculate(op1, op2, token.charAt(0)));
            }
        }
        return operandStack.pop();
    }

    public BigInteger evaluate() throws Exception {
        expression = expression.replaceAll("\\s+", " ")
                .replaceAll("\\++", "+")
                .replaceAll("-{3}", "-")
                .replaceAll("-{2}", "+")
                .replaceAll("-\\+|\\+-", "-");
        Deque<String> postfix = convertInfixToPostfix();
        return evaluatePostfix(postfix);
    }
}
