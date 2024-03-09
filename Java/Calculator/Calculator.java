///////////////////////////////////////////////////////////////////////////////
/**
 * Name: Claire Tsui
 * Email: ctsui@ucsd.edu
 * PID: A16920940
 * File name: Calculator.java
 * Sources used: Write-up
 * 
 * This file is used to do the calculation of addtion and multiplication 
 * without recurring decimals and redundancy.
 */
/**
 * This class aims to create a calculator with no recurring decimals and 
 * redundant numbers.
 *
 * Bugs: N/A
 *
 * @author Claire Tsui
 */
public class Calculator{
    private static final String strPeriod=".";
    private static final char chPeriod='.';
    private static final String strZero="0";
    private static final char chZero='0';
    private static final String strZeroInDouble="0.0";
    private static final int ascii=48;
    private static final int carryInCondition=10;
    private static final int index2=2;
    /**
     * This method extracts only the whole number portion of a number.
     * 
     * @param (parameter number) Value to set the instance variable number to
     * @return Whole number portion of number
     */
    public static String extractWholeNumber(String number){
        int test=0;
        String wholeNum="";
        for(int i=0;i<number.length();i++){
            if(number.charAt(i)!=chPeriod){
                test++;
            }
        }
        if(test==number.length()){
            return number;
        }
        if(number.charAt(0)==chPeriod){
            return strZero;
        }
        else if(test!=number.length()){
            String wholeNumber = number.substring
            (0,number.indexOf(strPeriod));
            wholeNum=wholeNumber;
            return wholeNum;
            // for(int i=0;i<wholeNum.length();i++){
            //     if(number.charAt(i)!=chZero){
            //         return wholeNum;
            //     }
            // }
        }
        return strZero;
    }
    /**
     * This method extracts only decimal portion of a number. 
     * 
     * @param (parameter number) Value to set the instance variable number to
     * @return Decimal portion of number
     */
    public static String extractDecimal(String number){
        int test=0;
        String decimalNum="";
        for(int i=0;i<number.length();i++){
            if(number.charAt(i)!=chPeriod){
                test++;
            }
        }
        if(test==number.length()){
            return strZero;
        }
        else{
            int Decimal=number.indexOf(strPeriod)+1;
            number=number.substring(Decimal);
            for(int i=0;i<number.length();i++){
                if(number.charAt(i)!=chPeriod){
                    decimalNum=number;
                    return decimalNum;
                }
            }
        }
        for(int i=0;i<decimalNum.length();i++){
            if(decimalNum.charAt(i)!=chZero){
                return decimalNum;
            }
        }
        return strZero;
        
    }
    /**
     * This method adds zero(s) in front of a number.
     * 
     * @param (parameter number) Value to set the instance variable number to
     * @param (parameter numZeros) Number of zero(s) added
     * @return Number after adding zero(s) at the begining
     */
    public static String prependZeros(String number, int numZeros){
        if(numZeros<=0){
            return number;
        }
        else{
            for(int i=1;i<=numZeros;i++){
                String pre=strZero;
                number=pre+number;
            }
            return number;
        }
    }
    /**
     * This method adds zero(s) at the end of a number.
     * 
     * @param (parameter number) Value to set the instance variable number to
     * @param (parameter numZeros) Number of zero(s) added
     * @return Number after adding zero(s) at the end
     */
    public static String appendZeros(String number, int numZeros){
        if(numZeros<=0){
            return number;
        }
        else{
            for(int i=1;i<=numZeros;i++){
                String app=strZero;
                number=number+app;
            }
            return number;
        }
    }   
    /**
     * This method remove leading zero(s) of a number.
     * 
     * @param (parameter number) Value to set the instance variable number to
     * @return Number without redundant leading zero(s)
     */     
    public static String stripLeadingZeros(String number){
        String num=extractWholeNumber(number);
        if(number.charAt(0)==chPeriod){
            return number;
        }
        else if(num!=strZero){
            double doubleNum;
            for(int i=0;i<num.length();i++){
                char ch=num.charAt(i);
                int newCh=(int)ch; 
                doubleNum=newCh-ascii;
                if(doubleNum!=0){
                    String stripLeadingZeros = num.substring(i);
                    number=stripLeadingZeros;
                    return number;
                }
            }
        }
        int result0=0;
        for(int i=0;i<num.length();i++){
            if(num.charAt(i)==chZero){
                result0++;
            }
        }
        if(result0++==num.length()){
            return strZero;
        }
        return num;
    }
    /**
     * This method remove trailing zero(s) of a number.
     * 
     * @param (parameter number) Value to set the instance variable number to
     * @return Number without redundant trailing zero(s)
     */
    public static String stripTrailingZeros(String number){
        String num=extractDecimal(number);    
        double doubleNum;
        if(number.charAt(number.length()-1)==chPeriod){
            return number;
        }
        for(int i=num.length()-1;i>=0;i--){
            char ch=num.charAt(i);
            int newCh=(int)ch; 
            doubleNum=newCh-ascii;
            if(doubleNum!=0){
                String stripTrailingZeros=num.substring(0,i+1);
                number=stripTrailingZeros;
                return number;
            }
        }
        return strZero;
    }
    /**
     * This method removes leading and trailing zero(s) of a number.
     *      
     * @param (parameter number) Value to set the instance variable number to
     * @return Number without redundant zero(s)
     */       
    public static String stripZeros(String number){
        int test=0;
        int zeros=0;
        String number1=stripLeadingZeros(number);
        String number2=stripTrailingZeros(number);
        for(int i=0;i<number.length();i++){
            if(number.charAt(i)==chZero){
                zeros++;
            }
        }
        for(int i=0;i<number.length();i++){
            if(number.charAt(i)!=chPeriod){
                test++;
            }
        }
        if(number1.charAt(0)==chPeriod){
            number1="";
        }
        if(number2.charAt(number2.length()-1)==chPeriod){
            number2="";
        }
        if(zeros==number.length()){
            return strZero;
        }
        else if(test==number.length()){
            return number1;
        }
        else{   
            number=number1+strPeriod+number2;
            return number;
        }
    }
    /**
     * This method determines if the added result of previous nubmers needs to 
     * be carried out. The result of adding first and second digits is partly 
     * according to the above result.
     * 
     * @param (parameter firstDigit) Value to set the instance variable 
     * firstDigit to
     * @param (parameter secondDigit) Value to set the instance variable 
     * secondDigit to
     * @param (parameter carryIn) True if needed to be carried in, false if 
     * don't
     * @return Sum of firstDigit and secondDigit after carrying in
     */
    public static char addDigits
    (char firstDigit, char secondDigit, boolean carryIn){
        int Digit1=Character.getNumericValue(firstDigit);
        int Digit2=Character.getNumericValue(secondDigit);
        int intResult=Digit1+Digit2;
        if(carryIn==true){
            intResult++;
            if(intResult>=carryInCondition){
                intResult-=carryInCondition;
                char num=(char)(intResult+chZero);
                return num;
            }
            else{
                char num=(char)(intResult+chZero);
                return num;
            }
        }
        else{
            if(intResult>=carryInCondition){
                intResult-=carryInCondition;
                char num=(char)(intResult+chZero);
                return num;
            }
            else{
                char num=(char)(intResult+chZero);
                return num;
            }
        }
    }
    /**
     * This method determines if the added result needs to carry out.
     * 
     * @param (parameter firstDigit) Value to set the instance variable 
     * firstDigit to
     * @param (parameter secondDigit) Value to set the instance variable 
     * secondDigit to
     * @param (parameter carryIn) True if needed to be carried in, false if 
     * don't
     * @return True if needs to carry out, false if don't
     */
    public static boolean carryOut
    (char firstDigit, char secondDigit, boolean carryIn){
        int Digit1=Character.getNumericValue(firstDigit);
        int Digit2=Character.getNumericValue(secondDigit);
        int intResult=Digit1+Digit2;
        if(carryIn==true){
            intResult++;
        }
        if(intResult>=carryInCondition){
            return true;
        }
        else{
            return false;
        }
    }
    /**
     * This method computes the sum of firstNumber and secondNumber.
     * 
     * @param (parameter firstNumber) Value to set the instance variable 
     * firstNumber to
     * @param (parameter secondNumber) Value to set the instance variable 
     * secondNumber to
     * @return Sum of firstNumber and secondNumber
     */
    public static String add(String firstNumber, String secondNumber){
        String firstNum=stripZeros(firstNumber);
        String secondNum=stripZeros(secondNumber);
        String firstNumberDecimal=extractDecimal(firstNum);
        String secondNumberDecimal=extractDecimal(secondNum);
        String firstNumberWhole=extractWholeNumber(firstNum);
        String secondNumberWhole=extractWholeNumber(secondNum);
        
        int num1De=0;
        int num2De=0; 
        int len1De=firstNumberDecimal.length();
        int len2De=secondNumberDecimal.length();
        int lenDe=0;
        int sumDecimal=0;
        if(len1De>len2De){
            for(int i=len2De;i<=len1De;i++){
                secondNumberDecimal=secondNumberDecimal+chZero;
            }
            len2De=len1De;
            lenDe=len1De+1;
        }
        else if(len2De>len1De){
            for(int i=len1De;i<=len2De;i++){
                firstNumberDecimal=firstNumberDecimal+chZero;
            }
            len1De=len2De;
            lenDe=len2De+1;
        }
        else if(len2De==len1De){
            lenDe=len1De+1;
        }
        for(int i=0;i<len1De;i++){
            num1De=num1De*carryInCondition+
            ((int)firstNumberDecimal.charAt(i)-ascii); 
        }
        for(int i=0;i<len2De;i++){
            num2De=num2De*carryInCondition+
            ((int)secondNumberDecimal.charAt(i)-ascii); 
        } 
        if(firstNumberDecimal==strZero&&secondNumberDecimal==strZero){
            sumDecimal=0;
        }
        else if(num1De+num2De==0){
            sumDecimal=0;
        }
        else{
            sumDecimal=num1De+num2De;
        }
        String sumDe=Integer.toString(sumDecimal);

        int num1Wh=0;
        int num2Wh=0;
        int len1Wh=firstNumberWhole.length();
        int len2Wh=secondNumberWhole.length();
        int sumWhole=0;
        for(int i=0;i<len1Wh;i++){
            num1Wh=num1Wh*carryInCondition+
            ((int)firstNumberWhole.charAt(i)-ascii); 
        }
        for(int i=0;i<len2Wh;i++){
            num2Wh=num2Wh*carryInCondition+
            ((int)secondNumberWhole.charAt(i)-ascii); 
        }

        if(lenDe==sumDe.length()){
            sumWhole=num1Wh+num2Wh+1;
        }
        else{
            sumWhole=num1Wh+num2Wh;
            int add=lenDe-sumDe.length();
            if(sumDe.length()!=lenDe){
                sumDe=prependZeros(sumDe,add);
            }
        }
        if(num1De==0){
            sumDe=secondNumberDecimal;
        }
        else if(num2De==0){
            sumDe=firstNumberDecimal;
        }
        else if(lenDe==sumDe.length()){
            sumDe=sumDe.substring(1);
        }
        String sumWh=Integer.toString(sumWhole);
        
        String result=sumWh+strPeriod+sumDe;
        result=stripZeros(result);
        return result;
    }
   /**
     * This method computes the product of number and numTimes.
     * 
     * @param (parameter number) Value to set the instance variable number to
     * @param (parameter numTimes) Value to set the instance variable numTimes 
     * to
     * @return Product of number and numTimes
     */
    public static String multiply(String number, int numTimes){
        String result="";
        if(numTimes>0){
            result=add(number,number);
            for(int i=0;i<numTimes-index2;i++){
                result=add(result,number);
            }
            return result;
        }
        else if(numTimes<0){
            return number;
        }
        if(numTimes==0){
            return strZeroInDouble;
        }
        return null;
    }
/**
 * Adding and multiplying two numbers using above methods.
 *
 * @param (parameter args) String array
 */ 
public static void main(String[] args){  
        System.out.println(extractWholeNumber(".002"));
        // System.out.print(Calculator.add
        // ("4.02", "0.0050")); 
        // System.out.print(Calculator.add
        // ("4.02", ".005"));   
        // System.out.print(Calculator.add
        // ("100", "200"));   
        // System.out.print(Calculator.multiply
        // ("100", 3));    
    }
}
