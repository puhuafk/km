package java8.Lambda表达式.test2;

/**
 * lambda 表达式只能引用标记了 final 的外层局部变量，
 * 这就是说不能在 lambda 内部修改定义在域外的局部变量，否则会编译错误。
 */
public class Java8Tester {
    final static String salutation = "Hello! ";
    static int i = 0;

    public static void main(String args[]) {
        GreetingService greetService1 = message -> {
            i = i + 1;
            System.out.println("=="+i);
            System.out.println(salutation + message);
        };

        System.out.println("==="+i);
        greetService1.sayMessage("Runoob");
        System.out.println("===="+i);
    }

    interface GreetingService {
        void sayMessage(String message);
    }
}
