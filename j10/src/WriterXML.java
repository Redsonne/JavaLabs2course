import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.List;

public class WriterXML {
    public void writeToFile(List<Student> stu, File file) throws Exception {
        // Создание фабрики и построителя для документа
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.newDocument();

        Element root = document.createElement("students");
        document.appendChild(root);

        for (Student student : stu) {
            Element stuElement = document.createElement("student");
            Element id = document.createElement("id");
            id.appendChild(document.createTextNode(String.valueOf(student.getId())));
            stuElement.appendChild(id);
            Element surname = document.createElement("surname");
            surname.appendChild(document.createTextNode(student.getSurname()));
            stuElement.appendChild(surname);

            Element course = document.createElement("arg1");
            course.appendChild(document.createTextNode(String.valueOf(student.getCourse())));
            stuElement.appendChild(course);

            Element group = document.createElement("arg2");
            group.appendChild(document.createTextNode(String.valueOf(student.getGroup())));
            stuElement.appendChild(group);

            root.appendChild(stuElement);
        }

        // Настройка трансформера для записи документа в файл
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes"); // Форматирование с отступами

        DOMSource source = new DOMSource(document);
        StreamResult result = new StreamResult(file);

        transformer.transform(source, result);
    }
}