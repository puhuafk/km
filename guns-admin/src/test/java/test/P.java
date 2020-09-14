package test;

import test2.C;
public class P {
    int eat(){
        return 1;
    }

    public static void main(String[] args) {
        P p = new C();
        System.out.println(p.eat());
    }
}
