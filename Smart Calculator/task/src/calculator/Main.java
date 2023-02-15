package calculator;

import java.util.*;

public class Main {
    static final String REGEX="[a-zA-Z]";
    public static void main(String[] args) {
        Map<String,Integer> listVariables = new HashMap<>();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("");
            String input = scanner.nextLine();


            switch (input) {
                case "/exit" -> {
                    System.out.println("Bye!");
                    System.exit(0);
                }
                case "/help" -> {
                    System.out.println("The program calculates the sum of numbers");
                    continue;
                }
                case "/go"->{
                    System.out.println("Unknown command");
                    continue;
                }
                case "" -> {
                    continue;
                }
            }
            if(input.contains("=")){
              String [] result = Test.addVariable(input).split(" ");
                if(result.length>1){
                    listVariables.put(result[0],Integer.parseInt(result[1]));
                }
              continue;
            }
            if(REGEX.matches(input)){
                System.out.println(listVariables.get(input));
            }
            try {
                int result = eval(input);
                System.out.println(result);
            } catch (Exception e) {
                System.out.println("Invalid expression");
            }
        }
    }


    public static int eval(String expr) {
        String regExSum = "[+]+|[-]{2}|-{4}";
        String regEsSub = "[-]{3}";

        String cambiar = expr.replaceAll(regEsSub, "-")
                .replaceAll(regExSum, "+")
                .replaceAll(" ", "")
                .replaceAll("-{4,}", "+")
                .replaceAll("\\+{4,}", "+")
                .replaceAll("-\\+{1}", "-")
                .replaceAll("\\+-{1}", "+");

        List<String> lista = addContent(cambiar);
        String[] tokens = lista.toArray(new String[0]);
        int result = Integer.parseInt(tokens[0]);
        for (int i = 1; i < tokens.length; i += 2) {
            String op = tokens[i];
            int value = Integer.parseInt(tokens[i + 1]);
            switch (op) {
                case "+" -> result += value;
                case "-" -> result -= value;
                default -> throw new IllegalArgumentException("Invalid expression");
            }
        }
        return result;
    }

    public static List<String> addContent(String inputUser) {

        List<String> elements = new ArrayList<>();
        StringBuilder currentNumber = new StringBuilder();
        int j = 0;
        if (inputUser.charAt(0) == '+' || inputUser.charAt(0) == '-') {
            elements.add("0");
            elements.add(String.valueOf(inputUser.charAt(0)));
            j = elements.size() - 1;
        }
        for (int i = j; i < inputUser.length(); i++) {
            char c = inputUser.charAt(i);
            if (c == '+' || c == '-') {
                elements.add(currentNumber.toString());
                elements.add(String.valueOf(c));
                currentNumber = new StringBuilder();
            } else {
                currentNumber.append(c);
            }
        }
        elements.add(currentNumber.toString());

        return elements;
    }
}
