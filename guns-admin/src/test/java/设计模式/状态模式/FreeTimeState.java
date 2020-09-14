package 设计模式.状态模式;

/**
 * @project: design_state
 * @author chenssy
 * @date 2013-8-24
 * @Description: 空闲状态只能预订和入住
 */
public class FreeTimeState implements State {

    Room hotelManagement;

    public FreeTimeState(Room hotelManagement){
        this.hotelManagement = hotelManagement;
    }


    public void bookRoom() {
        System.out.println("您已经成功预订了...");
        hotelManagement.setState(hotelManagement.getBookedState());   //状态变成已经预订
    }

    public void checkInRoom() {
        System.out.println("您已经成功入住了...");
        hotelManagement.setState(hotelManagement.getCheckInState());   //状态变成已经入住
    }

    public void checkOutRoom() {
        System.out.println("您已经成功退房了...");
        //不需要做操作
    }

    public void unsubscribeRoom() {
        System.out.println("您已经成功退订了...");
        //不需要做操作
    }

}