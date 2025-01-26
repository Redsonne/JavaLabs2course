import java.io.File;
import java.util.List;
public interface ParseStrategy {

    List<Student> parse(File file) throws Exception;
}
class Strategy1 implements ParseStrategy {
    @Override
    public List<Student>   parse(File file) throws Exception {
         DOMParser p=new DOMParser();
        return p.readProductsFromXML(file);
    }
}

class Strategy2 implements ParseStrategy {
    @Override
    public List<Student>  parse(File file) throws Exception {
SAXParsr s=new SAXParsr();

        return s.readProductsFromXML(file);
    }
}
