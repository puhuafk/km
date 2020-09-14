package 设计模式.访问者;

/**
 *   具体访问者：划价员
 */
public class Charger extends Visitor{

    public void visitor(MedicineA a) {
        System.out.println("划价员：" + name +"给药" + a.getName() +"划价:" + a.getPrice());
    }

    public void visitor(MedicineB b) {
        System.out.println("划价员：" + name +"给药" + b.getName() +"划价:" + b.getPrice());
    }

}