import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public interface Strategy {

   List<Student> search(List<Student> studList,int group);
}
class StrategyB implements Strategy {
   @Override
   public List<Student>  search(List<Student>studList,int group) {
     return MyFrame.sortStudent(studList, group);
   }
}

class StrategyA implements Strategy {
   @Override
   public List<Student> search(List<Student> studList,int group) {
      List<Student> groupList = (List)studList.stream().filter((xx) -> {
         return xx.group == group;
      }).collect(Collectors.toList());
      Collections.sort(groupList, Comparator.comparingInt(Student::getId));
      return groupList;
   }
}
