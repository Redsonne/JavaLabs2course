import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.util.List;

public class SAXParsr {
    public List<Student> readProductsFromXML(File file) throws Exception {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();

        StudentSAXHandler handler = new StudentSAXHandler();
        parser.parse(file, handler);

        return handler.getStudents();
    }
}