package 设计模式.状态模式;

public interface State {
    /**
     * @desc 预订房间
     * @return void
     */
    public void bookRoom();

    /**
     * @desc 退订房间
     * @return void
     */
    public void unsubscribeRoom();

    /**
     * @desc 入住
     * @return void
     */
    public void checkInRoom();

    /**
     * @desc 退房
     * @return void
     */
    public void checkOutRoom();

}