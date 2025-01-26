import javax.swing.*;
import java.util.ArrayList;


public class View<T> {
   PriorityQQ<T> st;
    DefaultListModel model;


    View(PriorityQQ<T> st, DefaultListModel model) {
        this.model = model;
        this.st=st;
    }

    void updateAll() {
       model.clear();
       var it =  st.createIterator();
        while(!it.is_done()){
            model.addElement(it.current_item());
        it.next();}
    }
    }

class Controller<T> {
    View vi;
    PriorityQQ<T> st;

    Controller(View vi, PriorityQQ<T> st) {
        this.vi = vi;
        this.st = st;
    }

    void insert(T item,int priority ) {
        st.insert(item,priority);
        vi.updateAll();

    }
   QQElement extractMinimum(){
       QQElement el= st.extractMinimum();
       vi.updateAll();
       return el;
   }
    QQElement getMinimum(){
        QQElement el= st.getMinimum();
        vi.updateAll();
        return el;
    }
   void clear(){
        st.clear();
       vi.updateAll();
   }

    public void insertAll(ArrayList <QQElement<T>> d){
        st.insertAll(d);
        vi.updateAll();
    }
}
