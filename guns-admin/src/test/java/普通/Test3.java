package 普通;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class Test3 {

    @Test
    public void test1() {
        List<String> list = new ArrayList<>();
        list.add("b");
        list.add("e");
        list.add("b");
        list.add("c");
        for (int i = 0; i < list.size(); i++) {
            System.out.println("=====");
            if ("b".equals(list.get(i))) {
                list.remove(i);
            }
        }
        System.out.println(list);
    }


    @Test
    public void test2() {
        List<String> list = new ArrayList<>();
        list.add("b");
        list.add("b");
        list.add("e");
        list.add("c");
        for (int i = 0; i< list.size(); i++) {
            System.out.println("=====");
            if ("b".equals(list.get(i))) {
                list.remove(i);
            }
        }
        System.out.println(list);
    }


    @Test
    public void test3() {
        List<String> list = new ArrayList<>();
        list.add("b");
        list.add("b");
        list.add("e");
        list.add("c");
        for (int i = list.size()-1; i>= 0; i--) {
            System.out.println("=====");
            if ("b".equals(list.get(i))) {
                list.remove(i);
            }
        }
        System.out.println(list);
    }

    @Test
    public void test4() {
        List<String> list = new ArrayList<>();
        list.add("b");
        list.add("b");
        list.add("e");
        list.add("c");
        for (int i = list.size()-1; i>= 0; i--) {
            System.out.println("=====");
            if ("b".equals(list.get(i))) {
                list.remove(i);
            }
        }
        System.out.println(list);
    }
}
