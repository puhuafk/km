package java8.方法引用;

import java.util.Arrays;
import java.util.List;

@FunctionalInterface
public interface Supplier<T> {
    T get();
}

class Car {
    //Supplier是jdk1.8的接口，这里和lamda一起使用了
    public static Car create(final Supplier<Car> supplier) {
        return supplier.get();
    }

    public static void collide(final Car car) {
        System.out.println("Collided " + car.toString());
    }

    public void follow(final Car another) {
        System.out.println("Following the " + another.toString());
    }

    public void repair() {
        System.out.println("Repaired " + this.toString());
    }

    public static void main(String[] args) {
        //1、构造器引用：它的语法是Class::new，或者更一般的Class<T>::new实例如下
        final Car car = Car.create( Car::new );
        final List< Car > cars = Arrays.asList(car );
        //2、静态方法引用：它的语法是Class::static_method，实例如下：
        cars.forEach(Car::collide );
        //类似这种,这两种是等价的，上面这个静态方法引用类似返回值，应该是这个道理
        cars.forEach((w)->Car.collide(w));
        //3、特定类的任意对象的方法引用
        cars.forEach(Car::repair );
        //4、特定对象的方法引用：它的语法是instance::method实例如下：
        Car police = Car.create( Car::new );
        cars.forEach( police::follow );


    }

}