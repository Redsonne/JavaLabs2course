import javax.swing.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;

public class PriorityQQ<T> implements Agregate<T> {
    private final ArrayList<QQElement<T>> lst = new ArrayList<>();


    boolean isEmpty() {
        return lst.isEmpty();
    }

    int size() {
        return lst.size();
    }

    void clear() {
        lst.clear();

    }
//    public JList<T> toJList() {
//        JList<T> jlist = new JList<>();
//        DefaultListModel<T> listModel = new DefaultListModel<>();
//        Iterator<T> iterator = createIterator();
//        while (!iterator.is_done()) {
//            listModel.addElement(iterator.current_item());
//        }
//        jlist.setModel(listModel);
//        return jlist;
//    }


    void insert(T item, int priority) {
        QQElement<T> newElement = new QQElement<>(item, priority);
        lst.add(newElement);
        sortElements();
    }

    public QQElement<T> extractMinimum() {
        if (isEmpty()) return null;
        QQElement<T> res= lst.remove(0);
        return res;
    }

    public QQElement<T> getMinimum() {
        return isEmpty() ? null : lst.get(0);
    }


    public void insertAll(ArrayList<QQElement<T>> newElements) {
        for (QQElement<T> element : newElements) {
            insert(element.item, element.priority);
        }

    }
    private void sortElements() {
        lst.sort(Comparator.comparingInt(e -> ((QQElement<T>) e).getPriority()));
    }


    @Override
    public String toString() {
        StringBuilder st = new StringBuilder();
        Iterator<T> iterator = createIterator();
        while (!iterator.is_done()) {
            st.append(iterator.current_item().toString() + ",");
            iterator.next();
        }
        if (st.length() > 0) {
            st.setCharAt(st.length() - 1, '.');
        }
        return st.toString();
    }



    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        PriorityQQ<T> other = (PriorityQQ<T>) obj;
        return lst.equals(other.lst);
    }

    @Override
    public int hashCode() {
        return lst.hashCode();
    }


    @Override
    public  Iterator<T> createIterator() {
        return new Iterator() {
            int index = 0;


            @Override
            public void next() {
                index += 1;
            }

            @Override
            public boolean is_done() {
                return (index > size() - 1);
            }

            @Override
            public QQElement<T> current_item() {
                return lst.get(index);
            }

            @Override
            public void first() {
                index = 0;
            }
        };
    }


}
