package 设计模式.责任链;

/**
 *     系主任
 */
public class DepartmentHead extends Leader{

    public DepartmentHead(String name) {
        super(name);
    }

    public void handleRequest(LeaveNode LeaveNode) {
        if(LeaveNode.getNumber() <= 7){   //小于7天系主任审批
            System.out.println("系主任" + name + "审批" +LeaveNode.getPerson() + "同学的请假条,请假天数为" + LeaveNode.getNumber() + "天。");
        }
        else{     //否则传递给院长
            if(this.successor != null){
                this.successor.handleRequest(LeaveNode);
            }
        }
    }
}