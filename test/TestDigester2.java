import org.apache.tomcat.util.digester.Digester;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;

public class TestDigester2 {

    public static void main(String[] args) throws IOException, SAXException {
        Digester digester = new Digester();
        digester.setValidating(false);
        digester.setRulesValidation(true);

        digester.addObjectCreate("department", "Department");

        digester.addSetProperties("department");

        digester.addObjectCreate("department/user", "User");

        digester.addSetProperties("department/user");

        digester.addSetNext("department/user", "addUser", "User");

        digester.addCallMethod("department/extension", "putExtension", 2);

        digester.addCallParam("department/extension/property-name", 0);

        digester.addCallParam("department/extension/property-value", 1);

        Department department = (Department) digester.parse(new File("test.xml"));
        System.out.println(department);


    }

}
