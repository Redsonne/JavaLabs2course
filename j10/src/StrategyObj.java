import javax.swing.*;
import java.util.List;


public class StrategyObj {
    List<Student> studList;
    int group;
    StrategyObj( List<Student>groupList,int group){
        this.studList=groupList;
        this.group=group;
    }
    Strategy strategy;
    public void setStrategy(Strategy strategy){
        this.strategy=strategy;
    }
    public List<Student> executeStrategy(){
       return strategy.search(studList,group);
    }
}
