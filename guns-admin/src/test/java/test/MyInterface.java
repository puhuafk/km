package test;


public interface MyInterface {

    default String getName(){
        return "我是父类接口";
    }

}