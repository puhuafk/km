package java8.Lambda表达式.test3;

public class Java8Tester {
    public static void main(String args[]) {
        int num = 1;
        Converter<Integer, String> s = (param) -> System.out.println(String.valueOf(param + num));
        s.convert(2);  // 输出结果为 3
//        num = 5;
    }

    public interface Converter<T1, T2> {
        void convert(int i);
    }
}