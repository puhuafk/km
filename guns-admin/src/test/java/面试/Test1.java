package 面试;

public class Test1 {

    static {
        System.out.println(222);
    }

    public static int mf(){
        System.out.println(111);
        return 1;
    }

    private static int i=mf();

    public static void main(String[] args) {

        Integer i = 10;
        Integer j = new Integer(10);

        System.out.println(i==j);




    }


}
