package Client;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;

import java.io.File;
import java.io.StringWriter;

public class SaveAsXML {
    public static String getWriter(){
        // Create an instance of the object you want to save as XML
        Person person = new Person("John Doe", 30);

        try {
            // Create a JAXBContext for the Person class
            JAXBContext jaxbContext = JAXBContext.newInstance(Person.class);

            // Create a Marshaller to convert the object to XML
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            StringWriter writer = new StringWriter();

            // Marshal the object to XML and save it to the file
            marshaller.marshal(person, writer);
            return writer.toString();


        } catch (JAXBException e) {
            e.printStackTrace();
        };
        return null;
    }


    public static void main(String[] args) {
        // Create an instance of the object you want to save as XML
        Person person = new Person("John Doe", 30);

        // Specify the file path where you want to save the XML
        // String filePath = "path/to/save/file.xml";

        try {
            // Create a JAXBContext for the Person class
            JAXBContext jaxbContext = JAXBContext.newInstance(Person.class);

            // Create a Marshaller to convert the object to XML
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            // Marshal the object to XML and save it to the file

            marshaller.marshal(person, new File("/Users/Radin/School/AP/project-graphics/test.xml"));
//            String xmlData = writer.toString();
//
//            System.out.println(xmlData);
//
//            JAXBContext context = JAXBContext.newInstance(PersonTest.class);
//            Unmarshaller unmarshaller = context.createUnmarshaller();
//            StringReader reader = new StringReader(xmlData);
//            PersonTest person1 = (PersonTest) unmarshaller.unmarshal(reader);
//
//            System.out.println(person1.getAge());


        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}
