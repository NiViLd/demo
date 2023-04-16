import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        String input = "";
        System.out.println(calc(input));
    }
    public static String calc(String input) {

        ArrayList<String> numATest = new ArrayList<>();
        Collections.addAll(numATest, "0", "1", "2", "3", "4", "5", "6", "7", "8", "9");
        ArrayList<String> numRTest = new ArrayList<>();
        Collections.addAll(numRTest, "I", "V", "X"/*, "L", "C", "D", "M"*/);
        ArrayList<String> res = new ArrayList<>();
        ArrayList<String> mathTest = new ArrayList<>();
        Collections.addAll(mathTest, "+", "-", "*", "/");
        String resultStr = "";
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            while ((input = reader.readLine()) != null) {
                input = input.replaceAll(" ", "");
                //если первый сивол арб число
                if (numATest.contains(String.valueOf(input.charAt(0)))) {
                            numberRead(input, res);
                            resultStr = (String.valueOf(numberMath(res)));
                            break;
                        //если первый символ рим число
                    } else if (numRTest.contains(String.valueOf(input.charAt(0)))) {
                            letterRead(input, res);
                            resultStr = (letterMathCheck(numberMath(res)));
                            break;
                        } else throw new NumberFormatException();
                    }
            } catch (IOException e) {
            e.printStackTrace();
        }

        return resultStr;
    }
    static ArrayList<String> numberRead(String input, ArrayList<String> res) throws IOException {
        String num1 = "";
        String num2 = "";
        String math = "";

        ArrayList<String> numATest = new ArrayList<>();
        Collections.addAll(numATest, "0", "1", "2", "3", "4", "5", "6", "7", "8", "9");
        ArrayList<String> mathTest = new ArrayList<>();
        Collections.addAll(mathTest, "+", "-", "*", "/");

            for (int i = 0; i < input.length(); i++) {
                //собираем первое число (если знака мат действ не было)
                if (((numATest.contains(String.valueOf(input.charAt(i)))) && math.isEmpty())) {
                    num1 = num1 + input.charAt(i);
                    //если знак мат действия был собираем второе
                } else
                    //собираем второе число
                if (numATest.contains(String.valueOf(input.charAt(i)))) {
                    num2 = num2 + input.charAt(i);
                    //забираем знак мат действ
                } else
                //знак мат опер
                if (mathTest.contains(String.valueOf(input.charAt(i)))) {
                    math = math + input.charAt(i);
                    //проверяем символы после 2 числа
                    //знак мат действ не пуст и мы встречаем еще знак мат
                } else if (((!math.isEmpty()) && mathTest.contains(String.valueOf(input.charAt(i)))) ||
                        //знак мат действ не пуст и мы встречаем символ не относящийся к араб цифр
                        ((!math.isEmpty()) && !numATest.contains(String.valueOf(input.charAt(i))))) {
                    throw new IOException("введено что-то после второго числа");
                }
            }

        res.add(num1);
        res.add(num2);
        res.add(math);
        return res;
    }
    static Integer numberMath(ArrayList<String> res) {
        Boolean isError = false;

        Integer n1Int = 0;
        Integer n2Int = 0;
        Integer result = 0;

        try {
            n1Int = Integer.parseInt(res.get(0));
        } catch (NumberFormatException e) {
            isError = true;
            e.printStackTrace();
        }

        try {
            n2Int = Integer.parseInt(res.get(1));
        } catch (NumberFormatException e) {
            isError = true;
            e.printStackTrace();
        }
        if(!isError) {
            switch (res.get(2)) {
                case "+":
                    result = n1Int + n2Int;
                    break;
                case "-":
                    result = n1Int - n2Int;
                    break;
                case "*":
                    result = n1Int * n2Int;
                    break;
                case "/":
                    result = n1Int / n2Int;
                    break;
            }
        }
        return result;
    }
    static ArrayList<String> letterRead(String input, ArrayList<String> res) throws IOException {
        String num1 = "";
        String num2 = "";
        String math = "";

        ArrayList<String> numRTest = new ArrayList<>();
        Collections.addAll(numRTest, "I", "V", "X"/*, "L", "C", "D", "M"*/);
        ArrayList<String> mathTest = new ArrayList<>();
        Collections.addAll(mathTest, "+", "-", "*", "/");

        for (int i = 0; i < input.length(); i++) {
            //собираем первое число (если знака мат действ не было)
            if ((numRTest.contains(String.valueOf(input.charAt(i)))) && math.isEmpty()) {
                num1 = num1 + input.charAt(i);
                //если знак мат действия был собираем второе
            } else if (numRTest.contains(String.valueOf(input.charAt(i)))) {
                num2 = num2 + input.charAt(i);
                //забираем знак мат действ
            } else if (mathTest.contains(String.valueOf(input.charAt(i)))) {
                math = math + input.charAt(i);
                //проверяем символы после 2 числа
                //знак мат действ не пуст и мы встречаем еще знак мат
            } else if (!math.isEmpty() && mathTest.contains(String.valueOf(input.charAt(i))) ||
                    //знак мат действ не пуст и мы встречаем символ не относящийся к римск цифр
                    (!math.isEmpty()) && !numRTest.contains(String.valueOf(input.charAt(i)))) {
                throw new IOException("символы после второго числа");
            } else break;
        }

        //conv
        Map<String, String> rim2arb19 = new HashMap<>();
        rim2arb19.put("I", String.valueOf(1));
        rim2arb19.put("II", String.valueOf(2));
        rim2arb19.put("III", String.valueOf(3));
        rim2arb19.put("IV", String.valueOf(4));
        rim2arb19.put("V", String.valueOf(5));
        rim2arb19.put("VI", String.valueOf(6));
        rim2arb19.put("VII", String.valueOf(7));
        rim2arb19.put("VIII", String.valueOf(8));
        rim2arb19.put("IX", String.valueOf(9));
        rim2arb19.put("X", String.valueOf(10));

        num1 = rim2arb19.get(num1);
        num2 = rim2arb19.get(num2);

        res.add(num1);
        res.add(num2);
        res.add(math);

        return res;
    }
    private static String letterMathCheck(Integer tempRimResult) throws IOException {
        String rimResultFinal = "";

        if (tempRimResult < 1) {
            //rimResultFinal = "Результат вычислений меньше единицы";
            throw new NumberFormatException();
        } else {
            rimResultFinal = rimResConv(String.valueOf(tempRimResult));
        }
        return rimResultFinal;
    }
    static String rimResConv(String rimResult) {
        String tempRim = "";

        Map<String, String> arb2Rim19 = new HashMap<>();
        arb2Rim19.put(String.valueOf(1), "I");
        arb2Rim19.put(String.valueOf(2), "II");
        arb2Rim19.put(String.valueOf(3), "III");
        arb2Rim19.put(String.valueOf(4), "IV");
        arb2Rim19.put(String.valueOf(5), "V");
        arb2Rim19.put(String.valueOf(6), "VI");
        arb2Rim19.put(String.valueOf(7), "VII");
        arb2Rim19.put(String.valueOf(8), "VIII");
        arb2Rim19.put(String.valueOf(9), "IX");

        Map<String, String> arb2Rim = new HashMap<>();
        arb2Rim.put(String.valueOf(10), "X");
        arb2Rim.put(String.valueOf(40), "XL");
        arb2Rim.put(String.valueOf(50), "L");
        arb2Rim.put(String.valueOf(90), "XC");

        switch (rimResult.length()) {
            case 1:
                tempRim = arb2Rim19.get(rimResult);
                break;
            case 2:
                try {
                    int x = Integer.parseInt(rimResult);
                    if((x == 10) || (x == 40) || (x == 50 || (x == 90))) {
                        tempRim = arb2Rim.get(rimResult);
                        } else if ((x > 10) && (x < 40)){
                            for (int i = 0; i < Integer.parseInt(String.valueOf(rimResult.charAt(0))); i++) {
                                tempRim = tempRim + "X";
                            }
                            if (!String.valueOf(rimResult.charAt(1)).equals("0")) {
                                tempRim = tempRim + arb2Rim19.get(String.valueOf(rimResult.charAt(1)));
                            } else
                                break;
                        } else if ((x > 40 ) && (x < 50)){
                            tempRim = tempRim + "XL";
                            tempRim = tempRim + arb2Rim19.get(String.valueOf(rimResult.charAt(1)));
                        } else  if ((x > 50) && (x < 90)) {
                        tempRim = tempRim + "L";
                        int e = Integer.parseInt(String.valueOf(rimResult.charAt(0))) - 5;
                        for (int i = 0; i < e; i++) {
                            tempRim = tempRim + "X";
                        }
                        tempRim = tempRim + arb2Rim19.get(String.valueOf(rimResult.charAt(1)));
                    }
                } catch (NumberFormatException e){
                    e.printStackTrace();
                }
                break;
            case 3:
                tempRim = "C";
                break;
        }
        rimResult = tempRim;
        return rimResult;
    }
}