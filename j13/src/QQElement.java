import java.util.Objects;

public  class QQElement<T> implements Element{
    T item;
    int priority;
    QQElement(T item, int priority) {
        this.item = item;
        this.priority = priority;
    }
    public int getPriority() {
        return priority;
    }
    @Override
    public String toString() {
        return "(" + item + ", " + priority + ")";
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
     QQElement<T> other = (QQElement<T>) obj;

        return priority == other.priority && item.toString().equals(other.item.toString());
    }
    @Override
    public int hashCode() {
        return Objects.hash(item, priority);
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }
}