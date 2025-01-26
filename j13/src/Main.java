import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main extends JFrame {
    PriorityQQ<QQElement> qq;
    JList<QQElement> jList;
    Controller controller;
    View view;
    DefaultListModel<QQElement> model;
    JButton visitButton;
    JList<QQElement> field;
    DefaultListModel<QQElement> model1;

    public void createAndShowGUI() {
        JFrame frame = new JFrame("10lab");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 800);
        frame.setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel();
        JPanel lPanel = new JPanel();

        lPanel.setLayout(new GridLayout(1, 2));

        frame.add(buttonPanel, BorderLayout.SOUTH);
        JTextField itemField = new JTextField(10);
        JTextField priorityField = new JTextField(5);
        buttonPanel.add(new JLabel("Item:"));
        buttonPanel.add(itemField);
        buttonPanel.add(new JLabel("Priority:"));
        buttonPanel.add(priorityField);
        JButton addButton = new JButton("Add");
        visitButton = new JButton("Visitor");
        model1 = new DefaultListModel<>();
        field = new JList<>(model1);
        model = new DefaultListModel<>();
        jList = new JList<>(model);
        qq = new PriorityQQ<>();

        view = new View(qq, model);
        controller = new Controller(view, qq);

        JScrollPane scrollPane1 = new JScrollPane(field);
        JScrollPane scrollPane = new JScrollPane(jList);

        lPanel.add(scrollPane);
        lPanel.add(scrollPane1);
        frame.add(lPanel, BorderLayout.CENTER);

        buttonPanel.add(addButton);
        buttonPanel.add(visitButton);
        JButton extractButton = new JButton("Extract Minimum");
        buttonPanel.add(extractButton);
        JButton getMinButton = new JButton("Get Minimum");
        buttonPanel.add(getMinButton);
        JButton clearButton = new JButton("Clear");
        buttonPanel.add(clearButton);

        frame.setVisible(true);

        addButton.addActionListener(e -> {
            String item = itemField.getText();
            String priorityText = priorityField.getText();
            if (!item.isEmpty() && !priorityText.isEmpty()) {
                try {
                    int priority = Integer.parseInt(priorityText);
                    controller.insert(item, priority);
                    itemField.setText("");
                    priorityField.setText("");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Invalid priority!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        extractButton.addActionListener(e -> {
            var el = controller.extractMinimum();
            if (el != null) {
                String minElement = el.toString();
                JOptionPane.showMessageDialog(frame, "Extracted: " + minElement);
            } else {
                JOptionPane.showMessageDialog(frame, "Queue is empty!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        getMinButton.addActionListener(e -> {
            var el = qq.getMinimum();
            if (el != null) {
                String minElement = el.toString();
                JOptionPane.showMessageDialog(frame, "Minimum: " + minElement);
            } else {
                JOptionPane.showMessageDialog(frame, "Queue is empty!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        clearButton.addActionListener(e -> {
            controller.clear();
            JOptionPane.showMessageDialog(frame, "Queue cleared.");
        });

        visitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Visitor<QQElement> v = new toListVisitor<>();
                Iterator<QQElement> iter = qq.createIterator();
                while (!iter.is_done()) {
                    iter.current_item().accept(v);
                    iter.next();
                }
                field.setModel((DefaultListModel) v.getJList().getModel());
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Main app = new Main();
            app.createAndShowGUI();
        });
    }
}