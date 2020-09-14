package 设计模式.状态模式;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Room {
    /*
     * 房间的三个状态
     */
    State freeTimeState;    //空闲状态
    State checkInState;     //入住状态
    State bookedState;      //预订状态

    State state ;

    public Room(){
        freeTimeState = new FreeTimeState(this);//空闲
        checkInState = new CheckInState(this);
        bookedState = new BookedState(this);
        state = freeTimeState ;  //初始状态为空闲
    }

    /**
     * @desc 预订房间
     * @return void
     */
    public void bookRoom(){
        state.bookRoom();
    }

    /**
     * @desc 退订房间
     * @return void
     */
    public void unsubscribeRoom(){
        state.unsubscribeRoom();
    }

    /**
     * @desc 入住
     * @return void
     */
    public void checkInRoom(){
        state.checkInRoom();
    }

    /**
     * @desc 退房
     * @return void
     */
    public void checkOutRoom(){
        state.checkOutRoom();
    }

    public String toString(){
        return "该房间的状态是:"+getState().getClass().getName();
    }

}