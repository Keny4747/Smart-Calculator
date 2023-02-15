package calculator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Test {
   static  Map<String, Integer> variables = new HashMap<>();
    public static void main(String[] args) {
        String text = "nmn = a2a";
        addVariable(text);

    }
    public static void addVariable(String inputUser) {
        String[] varValue = inputUser
                .replaceAll(" ", "")
                .split("");
        List<String> lista =addIdentifierValue(varValue);
        String regIdentifier = "[a-zA-Z]{" + lista.get(0).length() + "}";
        String regValue = "[0-9]{" + lista.get(2).length() + "}";

        if(!lista.get(0).matches(regIdentifier)){
            System.out.println("Invalid identifier");
        }else if(!lista.get(2).matches(regValue)){
            System.out.println("Invalid assignment");
        }else {
            variables.put(lista.get(0),Integer.parseInt(lista.get(2)));
        }

    }

    public static List<String> addIdentifierValue(String[] inputUser) {
        List<String> result = new ArrayList<>();
        StringBuilder text = new StringBuilder();
        for (String s : inputUser) {
            char c = s.charAt(0);
            if (c == '=') {
                result.add(String.valueOf(text));
                text = new StringBuilder();
                result.add(String.valueOf(c));

            } else {
                text.append(c);
            }
        }
        result.add(String.valueOf(text));
        return result;
    }
}
