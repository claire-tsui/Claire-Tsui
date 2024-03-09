/*
 * Name: Claire Tsui
 * Email: ctsui@ucsd.edu
 * PID: A16920940
 * Sources used: Write-up
 * 
 * This file is used to construct a method that could append a charater 
 * or a string to a MyStringBuilder
 */
/**
 * This class creates a string builder method of our own.
 * 
 * Bugs:N/A
 * 
 * @author Claire Tsui
 */
public class MyStringBuilder {
    private CharNode start;
    private CharNode end;
    private int length;
    private static final String EMPTY="";

    /**
     * Default constructor - creates a MyStringBuilder of a char
     */
    public MyStringBuilder(char ch) {
        CharNode newChar=new CharNode(ch);
        start=newChar;
        end=newChar;
        length=1;
    }

    /**
     * Default constructor - creates a MyStringBuilder of a string
     */
    public MyStringBuilder(String str) {
        this.append(str);
    }

    /**
     * Default constructor - creates a MyStringBuilder of another 
     * MyStringBuilder
     */
    public MyStringBuilder(MyStringBuilder other) {
        if(other==null){
            throw new NullPointerException();
        }
        else{
            int cnt=0;
            CharNode newChar=other.start;
            while(cnt<other.length){
                cnt++;
                this.append(newChar.getData());
                newChar=newChar.getNext();
            }
        }
    }
    
    /**
     * Returns the length of MyStringBuilder
     * 
     * @return length
     */
    public int length() {
        return this.length;
    }

    /**
     * Append a character to MyStringBuilder
     * 
     * @param ch character that is appended
     * @return MyStringBuilder after appending ch
     */
    public MyStringBuilder append(char ch) {
        if(end==null){
            CharNode newChar=new CharNode(ch);
            start=newChar;
            end=start;
            length++;
        }
        else{
            CharNode newChar=new CharNode(ch);
            end.setNext(newChar);
            end=newChar;
            length++;
        }
        return this;
    }

    /**
     * Append a string to MyStringBuilder
     * 
     * @param str string that is appended
     * @return MyStringBuilder after appending str
     */
    public MyStringBuilder append(String str) {
        if(str==null){
            throw new NullPointerException();
        }
        else{
            for(int i=0;i<str.length();i++){
                this.append(str.charAt(i));
            }
            return this;
        }         
    }

    /**
     * Return MyStringBuilder to string type
     * 
     * @return a string
     */
    public String toString() {
        String str="";
        if(start==null){
            return str;
        }
        else{
            int cnt=0;
            CharNode newChar=start;
            while(cnt<length){
                str+=newChar.getData(); 
                newChar=newChar.getNext();
                cnt++;
            }
            return str;
        }
    }

    /**
     * Returns a substring starting from startIdx to the end
     * 
     * @param startIdx starting index of the string (inlcusive)
     * @return a substring
     */
    public String subString(int startIdx) {
        String str=this.toString();
        if(startIdx<0||startIdx>=str.length()){
            throw new IndexOutOfBoundsException();
        }
        else if(startIdx==0){
            return str;
        }
        else{
            str=str.substring(startIdx);
            return str;
        }    
    }

    /**
     * Returns a substring starting from startIdx to endIdx
     * 
     * @param startIdx starting index of the string (inlcusive)
     * @param endIdx ending index of the string (exclusive)
     * @return a substring
     */
    public String subString(int startIdx, int endIdx) {
        String str=this.toString();
        if(startIdx<0||startIdx>=str.length()||endIdx>str.length()||
           startIdx>endIdx){
            throw new IndexOutOfBoundsException();
        }    
        else if(startIdx==endIdx){
            return EMPTY;
        }
        else if(startIdx==0&&endIdx==str.length()){
            return str;
        }    
        else{
            str=str.substring(startIdx,endIdx);
            return str;
        } 
    }
}
