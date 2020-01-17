package com.natasha;

public class MyInt {
    private String myInt;
    private int myIntSign;
    private String myIntWithoutSign;
    private String zero = "0";

    public MyInt(int value){
        myInt = Integer.toString(value);
        myIntSign = getSign(myInt);
        myIntWithoutSign = getStringWithoutSign(myInt, myIntSign);
    }

    public MyInt() {
        myInt = "0";
        myIntSign = getSign(myInt);
        myIntWithoutSign = getStringWithoutSign(myInt, myIntSign);
    }

    public MyInt(String value){
        if (value.matches("-?[\\d]+")) {
            myInt = value;
            myIntSign = getSign(myInt);
            myIntWithoutSign = getStringWithoutSign(myInt, myIntSign);
        } else {
            myInt = makeNumber(value);
            myIntSign = getSign(myInt);
            myIntWithoutSign = getStringWithoutSign(myInt, myIntSign);
            System.out.println("Неверно введено число (" + value + ")! Число преобразовано в " + myInt);
        }
    }

    public MyInt(byte[] value){
        String sign = value[0] == 0 ? "" : "-";
        String array = convertByteArrayToString(value);
        myInt = sign + array;
        myIntSign = getSign(myInt);
        myIntWithoutSign = getStringWithoutSign(myInt, myIntSign);
    }

    public String add(MyInt object){
        String value = object.get();
        int signSecond = getSign(value);
        if (myIntSign == signSecond) {
            String first = getStringWithoutSign(myInt, myIntSign);
            String second = getStringWithoutSign(value, signSecond);
            String firstString = first.length() >= second.length() ? zero + first : zero + second;
            String secondString = first.length() < second.length() ? first : second;
            String format = "%0" + (firstString.length() - secondString.length()) + "d%s";
            secondString = String.format(format,0, secondString);
            StringBuilder result = new StringBuilder();
            int regulator = 0;
            for (int i = firstString.length()-1; i >= 0; i--) {
                int x = Integer.parseInt(firstString.substring(i, i+1)) + Integer.parseInt(secondString.substring(i, i+1)) + regulator;
                regulator = 0;
                if (x >= 10) {
                    regulator = 1;
                    x -=10;
                }
                result.insert(0, x);
            }
            result = new StringBuilder(dropZero(result));
            if (myIntSign == 1 && !String.valueOf(result).equals("0")) result.insert(0, '-');
            return String.valueOf(result);
        } else {
            return subtract(new MyInt(reverseSign(value)));
        }
    }

    public String subtract(MyInt object){
        String value = object.get();
        int signSecond = getSign(value);
        if (myIntSign == signSecond) {
            String first = getStringWithoutSign(myInt, myIntSign);
            String second = getStringWithoutSign(value, signSecond);
            boolean firstGreater = firstGreaterThanSecond(myInt, value);
            int resultSign = firstGreater ? myIntSign : myIntSign == signSecond ? Math.abs(myIntSign - 1) : signSecond;
            String firstString = firstGreater ? zero + first : zero + second;
            String secondString = !firstGreater ? first : second;
            String format = "%0" + (firstString.length() - secondString.length()) + "d%s";
            secondString = String.format(format, 0, secondString);
            StringBuilder result = new StringBuilder();
            int adder = 10;
            int regulator = 0;
            for (int i = firstString.length() - 1; i >= 0; i--) {
                int firstItem = Integer.parseInt(firstString.substring(i, i + 1));
                int secondItem = Integer.parseInt(secondString.substring(i, i + 1));
                int x = firstItem + adder - regulator - secondItem;
                regulator = 1;
                if (x >= adder) {
                    x -= adder;
                    regulator = 0;
                }
                result.insert(0, x);
            }
            result = new StringBuilder(dropZero(result));
            if (resultSign == 1 && !String.valueOf(result).equals("0"))
                result.insert(0, '-');
            return String.valueOf(result);
        } else
            return add(new MyInt(reverseSign(value)));
    }

    public String multiply(MyInt object){
        String value = object.get();
        int signSecond = getSign(value);
        String first = myIntWithoutSign;
        String second = getStringWithoutSign(value, getSign(value));
        String firstString = first.length() >= second.length() ? zero + first : zero + second;
        String secondString = first.length() < second.length() ? first : second;
        StringBuilder result = new StringBuilder();
        int regulator = 0;
        String d = ""; //number of
        for (int i = secondString.length()-1; i >= 0; i--) {
            StringBuilder res = new StringBuilder();
            res.append(d);
            for (int j = firstString.length()-1; j >= 0; j--) {
                int x = Integer.parseInt(firstString.substring(j, j+1)) * Integer.parseInt(secondString.substring(i, i+1)) + regulator;
                res.insert(0,x%10);;
                regulator = x/10;
            }
            result = new StringBuilder(simpleAdd(String.valueOf(res), String.valueOf(result)));
            d += "0";
        }
        result = new StringBuilder(dropZero(result));
        if (myIntSign != signSecond && !String.valueOf(result).equals("0")) result.insert(0, '-');
        return String.valueOf(result);
    }

    public String divide(MyInt object){
        String value = object.get();
        int secondSign = getSign(value);
        String firstString = myIntWithoutSign;
        String secondString = getStringWithoutSign(value, secondSign);
        if (firstString.equals("0") || (!firstGreaterThanSecond(myInt, value) && !firstEqualsSecond(myInt, value)))
            return "0";
        if (secondString.equals("0"))
            return null;
        int diffLength = Math.abs(firstString.length() - secondString.length());
        if (firstString.length() > secondString.length()) {
            String format = "%0" + (diffLength) + "d%s";
            secondString = String.format(format, 0, secondString);
        }
        if (diffLength >= 4)
            System.out.println("Процесс может занять некоторое время. Пожалуйста, подождите...");
        String count = "";
        boolean restGreater = true;
        while (restGreater) {
            firstString = simpleSub(firstString, secondString);
            restGreater = firstGreaterThanSecond(firstString,secondString) || firstEqualsSecond(firstString,secondString);
            count = simpleAdd(count, "1");
        }
        if (myIntSign != secondSign) return ("-" + count);
        return count;
    }

    public String max(MyInt object){
        String value = object.get();
        int secondSign = getSign(value);
        if (myIntSign == 0 && secondSign == 1) return myInt;
        if (myIntSign == 1 && secondSign == 0) return value;
        String firstString = myIntWithoutSign;
        String secondString = getStringWithoutSign(value, secondSign);
        String[] formatArray = makeFormatting(firstString, secondString);
        firstString = formatArray[0];
        secondString = formatArray[1];
        for (int i = 0; i < firstString.length(); i++) {
            int firstItem = Integer.parseInt(firstString.substring(i, i + 1));
            int secondItem = Integer.parseInt(secondString.substring(i, i + 1));
            if (firstItem > secondItem)
                return myIntSign == 0 ? myInt : value;
            if (firstItem < secondItem)
                return myIntSign == 1 ? myInt : value;
        }
        return value;
    }

    public String min(MyInt object){
        String value = object.get();
        int secondSign = getSign(value);
        if (myIntSign == 0 && secondSign == 1) return value;
        if (myIntSign == 1 && secondSign == 0) return myInt;
        String firstString = getStringWithoutSign(myInt, myIntSign);
        String secondString = getStringWithoutSign(value, secondSign);
        String[] formatArray = makeFormatting(firstString, secondString);
        firstString = formatArray[0];
        secondString = formatArray[1];
        for (int i = 0; i < firstString.length(); i++) {
            int firstItem = Integer.parseInt(firstString.substring(i, i + 1));
            int secondItem = Integer.parseInt(secondString.substring(i, i + 1));
            if (firstItem < secondItem)
                return myIntSign == 0 ? myInt : value;
            if (firstItem > secondItem)
                return myIntSign == 1 ? myInt : value;
        }
        return value;
    }

    public String abs(){
        return myIntWithoutSign;
    }

    public int compareTo(MyInt object){
        String value = object.get();
        int secondSign = getSign(value);
        if (myIntSign == 0 && secondSign == 1) return 1;
        if (myIntSign == 1 && secondSign == 0) return -1;
        String firstString = myIntWithoutSign;
        String secondString = getStringWithoutSign(value, secondSign);
        String[] formatArray = makeFormatting(firstString, secondString);
        firstString = formatArray[0];
        secondString = formatArray[1];
        for (int i = 0; i < firstString.length(); i++) {
            int firstItem = Integer.parseInt(firstString.substring(i, i + 1));
            int secondItem = Integer.parseInt(secondString.substring(i, i + 1));
            if (firstItem > secondItem)
                return (myIntSign == 1 ? -1 : 1);
            else if (firstItem < secondItem)
                return (myIntSign == 1 ? 1 : -1);
        }
        return 0;
    }

    public String gcd(MyInt object){
        String value = object.get();
        String firstString = getStringWithoutSign(myInt, myIntSign);
        String secondString = getStringWithoutSign(value, getSign(value));
        if (firstString.length() >= secondString.length())
            return simpleGcd(firstString, secondString);
        return simpleGcd(secondString, firstString);
    }

    public long longValue(){
        return Long.parseLong(myInt);
    }

    public String toString() {
        return String.valueOf(myInt);
    }

    private String get() {
        return myInt;
    }

    private String simpleGcd(String first, String second) {
        if (second.equals("0"))
            return first;
        return simpleGcd(second, getSurplus(first, second));
    }

    private String getSurplus(String firstString, String secondString) {
        boolean restGreater = true;
        while (restGreater) {
            firstString = simpleSub(firstString, secondString);
            restGreater = firstGreaterThanSecond(firstString,secondString);
        }
        return firstString;
    }

    private String simpleAdd(String firstNumber, String secondNumber) {
        String firstString = firstNumber.length() >= secondNumber.length() ? zero + firstNumber : zero + secondNumber;
        String secondString = firstNumber.length() < secondNumber.length() ? firstNumber : secondNumber;
        String format = "%0" + (firstString.length() - secondString.length()) + "d%s";
        secondString = String.format(format,0, secondString);
        StringBuilder result = new StringBuilder();
        int regulator = 0;
        for (int i = firstString.length()-1; i >= 0; i--) {
            int x = Integer.parseInt(firstString.substring(i, i+1)) + Integer.parseInt(secondString.substring(i, i+1)) + regulator;
            regulator = 0;
            if (x >= 10) {
                regulator = 1;
                x -=10;
            }
            result.insert(0,x);
        }
        result = new StringBuilder(dropZero(result));
        return String.valueOf(result);
    }

    private String simpleSub(String firstNumber, String secondNumber) {
        String firstString = zero + firstNumber;
        String secondString = secondNumber;
        String[] formatArray = makeFormatting(firstString, secondString);
        firstString = formatArray[0];
        secondString = formatArray[1];
        StringBuilder result = new StringBuilder();
        int adder = 10;
        int regulator = 0;
        for (int i = firstString.length() - 1; i >= 0; i--) {
            int firstItem = Integer.parseInt(firstString.substring(i, i + 1));
            int secondItem = Integer.parseInt(secondString.substring(i, i + 1));
            int x = firstItem + adder - regulator - secondItem;
            regulator = 1;
            if (x >= adder) {
                x -= adder;
                regulator = 0;
            }
            result.insert(0, x);
        }
        result = new StringBuilder(dropZero(result));
        return String.valueOf(result);
    }

    private String[] makeFormatting(String first, String second) {
        String[] array = new String[2];
        if (first.length() > second.length()) {
            String format = "%0" + (first.length() - second.length()) + "d%s";
            second = String.format(format,0, second);
        } else if (first.length() < second.length()) {
            String format = "%0" + (second.length() - first.length()) + "d%s";
            first = String.format(format,0, first);
        }
        array[0] = first;
        array[1] = second;
        return array;
    }

    private StringBuilder dropZero(StringBuilder number){
        while (number.charAt(0) == '0' && number.length() > 1) {
            number.deleteCharAt(0);
        }
        return number;
    }

    private int getSign(String value) {
        return (value).charAt(0) == '-' ? 1 : 0;
    }
    private String getStringWithoutSign(String value, int startIndex) {
        return (value).substring(startIndex);
    }

    private String reverseSign(String value) {
        if (getSign(value) == 1)
            return (value).substring(1);
        return ("-" + value);
    }

    private String makeNumber(String value) {
        String result = "";
        for (int i=0;i<value.length();i++) {
            if (i==0 && value.charAt(i) == '-')
                result += value.substring(i,i+1);
            if(Character.isDigit(value.charAt(i)))
                result += value.substring(i,i+1);
        }
        if (result.equals(""))
            return "0";
        return result;
    }

    private static String convertByteArrayToString(byte[] array) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 1; i < array.length; i++) {
            stringBuilder.append(array[i]);
        }
        return stringBuilder.toString();
    }

    private boolean firstGreaterThanSecond(String first, String second) {
        String firstString = getStringWithoutSign(first,  getSign(first));
        String secondString = getStringWithoutSign(second, getSign(second));
        String[] formatArray = makeFormatting(firstString, secondString);
        firstString = formatArray[0];
        secondString = formatArray[1];
        for (int i = 0; i < firstString.length(); i++) {
            int firstItem = Integer.parseInt(firstString.substring(i, i + 1));
            int secondItem = Integer.parseInt(secondString.substring(i, i + 1));
            if (firstItem > secondItem)
                return true;
             else if (firstItem < secondItem)
                return false;
        }
        return false;
    }
    private boolean firstEqualsSecond(String first, String second) {
        first = getStringWithoutSign(first,  getSign(first));
        second = getStringWithoutSign(second, getSign(second));
        String firstString = first.length() >= second.length() ?  first : second;
        String secondString = first.length() < second.length() ? first : second;
        String format = "%0" + (firstString.length() - secondString.length()) + "d%s";
        if (first.length() != second.length())
            secondString = String.format(format,0, secondString);
        for (int i = 0; i < firstString.length(); i++) {
            int firstItem = Integer.parseInt(firstString.substring(i, i + 1));
            int secondItem = Integer.parseInt(secondString.substring(i, i + 1));
            if (firstItem != secondItem)
                return false;
        }
        return true;
    }
}
