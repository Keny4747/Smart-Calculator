package calculator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Test {
    /*
    public static void main(String[] args) {

        String text = "n = 2";
        String text2 = "N =3";
        String text3 = "n =6";
        addVariable(text);
        addVariable(text2);
        addVariable(text3);
        for(var entry:variables.entrySet()){
            System.out.println(entry.getKey()+" "+entry.getValue());
        }
        }
*/
    public static String addVariable(String inputUser,Map<String, Integer>listVariables) {
        String[] varValue = inputUser
                .replaceAll(" ", "")
                .split("");
        List<String> lista =addIdentifierValue(varValue);
        String regIdentifier = "[a-zA-Z]{" + lista.get(0).length() + "}";
        String regValue = "[0-9]{" + lista.get(2).length() + "}";
        String result="";
        if(!lista.get(0).matches(regIdentifier)){
            System.out.println("Invalid identifier");
        }else if(!lista.get(2).matches(regValue)){
            if(listVariables.containsKey(lista.get(2))){
                result=lista.get(0)+" "+listVariables.get(lista.get(2));
            }else {
                System.out.println("Invalid assignment");
            }

        }else if(countCharacter(inputUser)){
            System.out.println("Invalid assignment");
        }else{

           result=lista.get(0)+" "+lista.get(2);
        }
        return result;
    }
    public static boolean countCharacter(String input){
        int count = 0;
        for(int i=0; i<input.length(); i++){
            if(input.charAt(i)=='='){
                count++;
            }
        }
        return count>1;
    }

    public static List<String> addIdentifierValue(String[] inputUser) {
        List<String> result = new ArrayList<>();
        StringBuilder text = new StringBuilder();
        for (String s : inputUser) {
            char c = s.charAt(0);
            if (c == '='||c == '+'||c == '-') {
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
    public static String obtainValue(String input,Map<String, Integer> listVariables){
        String [] arrayInput=input.replaceAll(" ","").split("");
        List<String> list = addIdentifierValue(arrayInput);
        StringBuilder result= new StringBuilder();
        for (String s : list) {
            if (listVariables.containsKey(s)) {
                result.append(listVariables.get(s));
            }else{
                result.append(s);
            }

        }
        return String.valueOf(result);
    }
}
