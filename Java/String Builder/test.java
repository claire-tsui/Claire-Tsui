public class test {
    public static void main(String args[]){
        System.out.println(method1(8));
    }
    public static int method1(int var){
        var+=2;
        System.out.println(var);
        System.out.println(method2(var++));
        System.out.println(method3(var++));
        System.out.println(var);
        return (int)'8';
    }
    public static char method2(int var){
        var-=3;
        System.out.println(var);
        System.out.println(method3(++var));
        System.out.println(var);
        return 'b';
    }
    public static double method3(int var){
        var+=2;
        System.out.println(var--);
        return var+1.0;
    }
}
