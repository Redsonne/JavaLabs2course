import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DOMParser {
    public List<Student> readProductsFromXML(File file) throws Exception {
        List<Student> products = new ArrayList<>();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        Document document = builder.parse(file);
        NodeList productNodes = document.getElementsByTagName("student");

        for (int i = 0; i < productNodes.getLength(); i++) {
            Node node = productNodes.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;

                int id = Integer.parseInt(element.getElementsByTagName("id").item(0).getTextContent());
                String surname = element.getElementsByTagName("surname").item(0).getTextContent();
                int course = Integer.parseInt(element.getElementsByTagName("arg1").item(0).getTextContent());
                int group = Integer.parseInt(element.getElementsByTagName("arg2").item(0).getTextContent());

                products.add(new Student(id, surname, course,group));
            }
        }
        return products;
    }
}
