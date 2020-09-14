package 设计模式.中介者;

public abstract class Person {
    protected String name;
    protected Mediator mediator;

    Person(String name,Mediator mediator){
        this.name = name;
        this.mediator = mediator;
    }

}