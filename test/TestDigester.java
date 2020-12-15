import org.apache.tomcat.util.digester.Digester;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;

public class TestDigester {

    public static void main(String[] args) throws IOException, SAXException {
        Digester digester = new Digester();
        digester.setValidating(false);
        digester.setRulesValidation(true);

        // 匹配department节点时，创建Department对象
        digester.addObjectCreate("department", "Department");

        // 匹配department节点时，设置对象属性
        digester.addSetProperties("department");

        // 匹配deparment/user节点时，创建user对象
        digester.addObjectCreate("department/user", "User");

        // 匹配department/user节点时，设置对象属性
        digester.addSetProperties("department/user");

        // 匹配department/user节点时，调用Department对象的addUser方法
        digester.addSetNext("department/user", "addUser", "User");

        // 匹配department/extension节点时，调用Department对象的putExtension方法
        digester.addCallMethod("department/extension", "putExtension", 2);

        // 调用方法的第一个参数为节点department/extension/property-name的内容
        digester.addCallParam("department/extension/property-name", 0);

        // 调用方法的第2个参数为节点department/extension/property-value的内容
        digester.addCallParam("department/extension/property-value", 1);

        Department department = (Department) digester.parse(new File("test.xml"));
        System.out.println(department);


    }

}
