package 面试;

public class Test3 {

    int j;


    {
        j++;
    }

    void f1() {

    }

    public static void main(String[] args) {
//        Test3 test3 = new Test3();
//        test3.f1();
//        test3.f1();
//        System.out.println(test3.j);

        int i = 0;
        i = i++;

        int tmp=i;
        i = i + 1;
        i = tmp;

        System.out.println(i);
    }
}
