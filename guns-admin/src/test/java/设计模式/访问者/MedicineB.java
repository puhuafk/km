package 设计模式.访问者;

/**
 * 具体元素：MedicineB.java
 */
public class MedicineB extends Medicine{

    public MedicineB(String name, double price) {
        super(name, price);
    }

    public void accept(Visitor visitor) {
        visitor.visitor(this);
    }
}