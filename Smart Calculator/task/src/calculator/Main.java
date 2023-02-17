package calculator;

import java.util.Scanner;

public class Main {
    private static boolean isUnknownCommand(String command) {
        return command.startsWith("/") && !command.equals("/exit") && !command.equals("/help");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input;
        Expression expression;
        do {
            input = scanner.nextLine();
            if (Expression.isInvalid(input)) {
                System.out.println("Invalid expression");
                continue;
            }
            if (isUnknownCommand(input)) {
                System.out.println("Unknown command");
                continue;
            }
            expression = new Expression(input);
            if (expression.isVariableAssignment()) {
                expression.storeVariable();
                continue;
            }
            if (expression.isVariableQuery()) {
                if (Expression.isValidIdentifier(input)) {
                    String value = expression.getVariables().getOrDefault(input, "Unknown variable");
                    System.out.println(value);
                } else {
                    System.out.println("Invalid identifier");
                }
                continue;
            }
            switch (input) {
                case "":
                    break;
                case "/exit":
                    System.out.println("Bye!");
                    break;
                case "/help":
                    System.out.println("The program calculates the sum/subtraction/multiplication/division of numbers");
                    break;
                default:
                    try {
                        System.out.println(expression.evaluate());
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
            }
        } while (!input.equals("/exit"));
    }
}
