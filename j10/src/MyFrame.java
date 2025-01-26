

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import javax.swing.*;

public class MyFrame extends JFrame {

    JLabel sur;
    JLabel idnum;
    JLabel course;
    JLabel group;
    JLabel filenam;
    JLabel ngroup;
    JLabel list;
    JLabel sortedlist;
    JButton Open;
    JButton Add;
    JButton Savexml;
    JButton Find;
    JButton Find2;
    JTextField aField;
    JTextField bField;
    JTextField cField;
    JTextField dField;
    JTextField fileField;
    JTextField gField;
    JTextArea allList;
    JTextArea answer;
    List<Student> studList;
    List<Student> groupList;
    ParseStrategy strategy;


    private JRadioButton domButton;
    private JRadioButton saxButton;


    public MyFrame(String str) {

        super(str);
        this.setDefaultCloseOperation(3);
        this.setLayout(new FlowLayout());
        this.setLayout(new BoxLayout(this.getContentPane(), 1));
        this.filenam = new JLabel("Имя файла(.txt/.xml):  ");
        this.fileField = new JTextField(10);
        this.Open = new JButton("Открыть");
        this.Savexml = new JButton("Сохранить в .xml");
        this.idnum = new JLabel("Номер зачетки:  ");
        this.sur = new JLabel("Фамилия:  ");
        this.course = new JLabel("Курс:  ");
        this.group = new JLabel("Группа:  ");
        this.aField = new JTextField(5);
        this.bField = new JTextField(10);
        this.cField = new JTextField(5);
        this.dField = new JTextField(5);
        this.Add = new JButton("Добавить");
        this.Find = new JButton("Найти1");
        this.Find2 = new JButton("Найти2");
        this.ngroup = new JLabel("Номер группы:  ");
        this.gField = new JTextField(5);
        this.list = new JLabel("Исходный список:  ");
        this.sortedlist = new JLabel("Список группы:  ");
        this.allList = new JTextArea(34, 30);
        this.answer = new JTextArea(34, 30);
        domButton = new JRadioButton("DOM");
        saxButton = new JRadioButton("SAX");
        domButton.setSelected(true); // Установить DOM как выбранный по умолчанию
        ButtonGroup bgroup = new ButtonGroup();
        bgroup.add(domButton);
        bgroup.add(saxButton);
        this.answer.setEditable(false);
        this.allList.setEditable(false);
        Open.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                allList.setText(""); // Очищаем текстовое поле
                String filename = "src/" + fileField.getText();

                try {
                    if (filename.endsWith(".txt")) {
                        try (Scanner sc = new Scanner(new FileReader(filename))) {
                            studList = getList(sc);
                        }
                    } else if (filename.endsWith(".xml")) {
                        File file = new File(filename);
                        strategy = domButton.isSelected() ? new Strategy1() : new Strategy2();
                        studList = strategy.parse(file);
                    } else {
                        allList.setText("Ошибка: Неподдерживаемый формат файла.");
                        return;
                    }

                    for (Student student : studList) {
                        allList.append(student.toString() + "\n");
                    }
                } catch (Exception ex) {
                    allList.setText("Ошибка: Файл не найден.");
                }
            }
        });



        this.Add.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MyFrame.this.Open.doClick();
                int id = Integer.parseInt(MyFrame.this.aField.getText());
                String surname = MyFrame.this.bField.getText();
                int course = Integer.parseInt(MyFrame.this.cField.getText());
                int group = Integer.parseInt(MyFrame.this.dField.getText());
                Student y = new Student(id, surname, course, group);
                if (!MyFrame.this.allList.getText().equals("Ошибка: Файл не найден .")) {
                    MyFrame.this.studList.add(y);
                    MyFrame.this.allList.append(y.toString() + "\n");
                }

                if (!MyFrame.this.answer.getText().equals("")) {
                    MyFrame.this.Find.doClick();
                }

            }
        });

        Savexml.addActionListener(new ActionListener() {


            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Сохранить файл");
                int userSelection = fileChooser.showSaveDialog(MyFrame.this);

                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    File fileToSave = fileChooser.getSelectedFile();

                    if (!fileToSave.getName().toLowerCase().endsWith(".xml")) {
                        fileToSave = new File(fileToSave.getAbsolutePath() + ".xml");
                    }
                    WriterXML wr=new WriterXML();

                    try {
                        wr.writeToFile(groupList,fileToSave);
                    } catch (Exception ex) {
                        // Отображение всплывающего окна с сообщением об ошибке
                        JOptionPane.showMessageDialog(MyFrame.this,
                                "Ошибка при сохранении файла: " + ex.getMessage(),
                                "Ошибка",
                                JOptionPane.ERROR_MESSAGE);
                    }

                }
            }
        });

        this.Find.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                   MyFrame.this.answer.setText("");
                    if (MyFrame.this.gField.getText().equals("")) {
                        throw new MyEx("Введите группу");
                    }


                    if (MyFrame.this.allList.getText().equals("") || MyFrame.this.allList.getText().equals("Ошибка: Файл не найден .")) {
                       MyFrame.this.Open.doClick();
                    }

                    int group = Integer.parseInt(MyFrame.this.gField.getText());
                    StrategyObj obj=new StrategyObj(studList,group);
                    obj.setStrategy(new StrategyB());
                     groupList = obj.executeStrategy();
                    Iterator var4 = groupList.iterator();

                    while(var4.hasNext()) {
                        Student x = (Student)var4.next();
                        MyFrame.this.answer.append(x.toString() + "\n");
                    }
                } catch (MyEx var6) {
                    MyEx exx = var6;
                    MyFrame.this.answer.setText(exx.getMessage());
                }

            }
        });
        this.Find2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    MyFrame.this.answer.setText("");
                    if (MyFrame.this.gField.getText().equals("")) {
                       throw new MyEx("Введите группу");
                   }

                    if (MyFrame.this.allList.getText().equals("") || MyFrame.this.allList.getText().equals("Ошибка: Файл не найден .")) {
                        MyFrame.this.Open.doClick();
                    }

                    int group = Integer.parseInt(MyFrame.this.gField.getText());
                    StrategyObj obj=new StrategyObj(studList,group);
                    obj.setStrategy(new StrategyA());
                  groupList = obj.executeStrategy();
                    Iterator var4 = groupList.iterator();

                   while(var4.hasNext()) {
                        Student x = (Student)var4.next();
                        MyFrame.this.answer.append(x.toString() + "\n");
                    }
                } catch (MyEx var6) {
                   MyEx exx = var6;
                   MyFrame.this.answer.setText(exx.getMessage());
                }

           }
       });
        JPanel panel0 = new JPanel(new FlowLayout());
        panel0.add(this.filenam);
        panel0.add(this.fileField);
        panel0.add(this.Open);
        panel0.add(domButton);
        panel0.add(saxButton);
        JPanel panel1 = new JPanel(new FlowLayout());
        panel1.add(this.idnum);
        panel1.add(this.aField);
        panel1.add(this.sur);
        panel1.add(this.bField);
        panel1.add(this.course);
        panel1.add(this.cField);
        panel1.add(this.group);
        panel1.add(this.dField);
        panel1.add(this.Add);
        JPanel panel4 = new JPanel(new FlowLayout());
        panel4.add(this.ngroup);
        panel4.add(this.gField);
        panel4.add(this.Find);
        panel4.add(this.Find2);
        panel4.add(this.Savexml);
        JPanel panel5 = new JPanel();
        panel5.setLayout(new BoxLayout(panel5, 1));
        panel5.add(this.list);
        panel5.add(this.allList);
        JPanel panel6 = new JPanel();
        panel6.setLayout(new BoxLayout(panel6, 1));
        panel6.add(this.sortedlist);
        panel6.add(this.answer);
        JPanel panel7 = new JPanel(new FlowLayout(0));
        panel7.add(panel5);
        panel7.add(panel6);
        this.add(panel0);
        this.add(panel1);
        this.add(panel4);
        this.add(panel7);
    }

    public static void main(String[] args) {
        MyFrame frame = new MyFrame("Студенты");
        frame.setSize(700, 740);
        frame.setVisible(true);
    }

    public static List<Student> getList(Scanner sc) {
        List<Student> lst = new ArrayList<>();

        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] parts = line.split(" ");
            if (parts.length == 4) {
                int id = Integer.parseInt(parts[0]);
                String surname = parts[1];
                int course = Integer.parseInt(parts[2]);
                int group = Integer.parseInt(parts[3]);
                Student y = new Student(id, surname, course, group);
                lst.add(y);
            }
        }

        return lst;
    }

    public static List<Student> sortStudent(List<Student> students, int group) {
        List<Student> res = new ArrayList();
        Iterator var3 = students.iterator();

        while(var3.hasNext()) {
            Student x = (Student)var3.next();
            if (x.group == group) {
                res.add(x);
            }
        }

        Collections.sort(res);
        return res;
    }


}
