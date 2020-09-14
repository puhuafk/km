package 设计模式.责任链;

/**
 * 辅导员
 */
public class Instructor extends Leader{

    public Instructor(String name){
        super(name);
    }

    public void handleRequest(LeaveNode LeaveNode) {
        if(LeaveNode.getNumber() <= 3){   //小于3天辅导员审批
            System.out.println("辅导员" + name + "审批" +LeaveNode.getPerson() + "同学的请假条,请假天数为" + LeaveNode.getNumber() + "天。");
        }
        else{     //否则传递给系主任
            if(this.successor != null){
                this.successor.handleRequest(LeaveNode);
            }
        }
    }

}