import javax.swing.*;

public class toListVisitor<T> implements Visitor<T> {
    private DefaultListModel<String> listModel = new DefaultListModel<>();
    @Override
    public void visit(QQElement<T> el) {
            listModel.addElement(el.toString());

        }
    public JList<String> getJList() {
        return new JList<>(listModel);
    }

}

