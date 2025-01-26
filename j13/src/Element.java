import javax.swing.*;

public interface Element {
    void accept(Visitor v);
}
interface Visitor<T> {
    public void visit(QQElement<T>  el);
    JList<String> getJList();
}