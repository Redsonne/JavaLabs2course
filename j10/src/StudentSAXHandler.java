import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

public class StudentSAXHandler extends DefaultHandler {
    private List<Student> students;
    private String currentElement;
    private String surname;
    private int id, group, course;

    public StudentSAXHandler() {
        students = new ArrayList<>();
    }

    public List<Student> getStudents() {
        return students;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        currentElement = qName;
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        String content = new String(ch, start, length).trim();
        if (content.isEmpty()) return;

        switch (currentElement) {
            case "id":
                id = Integer.parseInt(content);
                break;
                case "surname":
                surname = content;
                break;

            case "arg1":
                course = Integer.parseInt(content);
                break;
                case "arg2":
                group = Integer.parseInt(content);
                break;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        if ("student".equals(qName)) {
            students.add(new Student(id, surname, course, group));
        }
        currentElement = null;
    }
}