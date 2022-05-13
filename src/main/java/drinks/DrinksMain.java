package drinks;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class DrinksMain {

    private static final Scanner scn = new Scanner(System.in);

    public static void main(String[] args) {
        ArrayList<Drink> drinks = readDrinksFromXml("src/main/resources/source.xml");

        int choice = -1;
        while (choice != 0){
            switch (choice){
                case 1 -> listDrinks(drinks);
                case 2 -> addNewDrink(drinks);
                case 3 -> modifyDrink(drinks);
                case 4 -> deleteDrink(drinks);
            }

            System.out.println("1 - List drinks\r\n2 - Add new drink\r\n3 - Modify a drink\r\n4 - Delete a drink\r\n0 - Exit");
            try{
                choice = scn.nextInt();
                scn.nextLine();
                if (choice < 0 || choice > 5) {
                    System.out.println("Not valid option.");
                }
            }
            catch (InputMismatchException e){
                e.getStackTrace();
                e.getMessage();
                e.printStackTrace();
                scn.nextLine();
            }
            catch (Exception e){
                e.getStackTrace();
                e.getMessage();
                e.printStackTrace();
                scn.nextLine();
            }
        }
        saveDrinksToXml(drinks, "src/main/resources/source.xml");
    }

    private static void deleteDrink(ArrayList<Drink> drinks){
        System.out.print("Enter the type of the drink you want to delete: ");
        String type = scn.nextLine();
        System.out.print("Enter the brand: ");
        String brand = scn.nextLine();
        for (Drink drink : drinks) {
            if (drink.getType().equals(type) && drink.getBrand().equals(brand)){
                drinks.remove(drink);
                System.out.println("The drink was successfully deleted.");
                return;
            }
        }
    }

    private static void modifyDrink(ArrayList<Drink> drinks) {
        System.out.print("Enter the type of drink you want to modify: ");
        String type = scn.nextLine();
        System.out.print("Enter the brand of the drink what you want to modify: ");
        String brand = scn.nextLine();
        for (Drink drink : drinks) {
            if (drink.getBrand().equals(brand) && drink.getType().equals(type)) {
                int percentage = inputPercentage();
                int age = inputAge();
                String origin = inputOrigin();
                String manufacturer = inputManufacturer();
                drinks.add(new Drink(type, brand, percentage, age, origin, manufacturer));
                System.out.println("The drink was successfully modified");
                return;
            }
        }
        System.out.println("There is no drink with this name.");
    }

    private static void listDrinks(ArrayList<Drink> drinks) {
        for (int i = 0; i < drinks.size(); i++){
            System.out.println(i+1 + ". drink:");
            System.out.println("\tType: " + drinks.get(i).getType());
            System.out.println("\tBrand: " + drinks.get(i).getBrand());
            System.out.println("\tPercentage of alcohol: " + drinks.get(i).getPercentage());
            System.out.println("\tAge: " + drinks.get(i).getAge());
            System.out.println("\tOrigin: " + drinks.get(i).getOrigin());
            System.out.println("\tManufacturer: " + drinks.get(i).getManufacturer() + "\n");
        }
        return;
    }

    private static void addNewDrink(ArrayList<Drink> drinks) {
        System.out.print("Enter the type of the drink: ");
        String type = scn.nextLine();
        System.out.print("Enter the brand of the drink: ");
        String brand = scn.nextLine();
        for (Drink drink: drinks) {
            if (drink.getBrand().equals(brand)){
                System.out.println("A drink with this brand name is already in the list.");
                return;
            }
        }
        int percentage = inputPercentage();
        int age = inputAge();
        System.out.print("Enter the origin of the drink: ");
        String origin = scn.nextLine();
        System.out.print("Enter the manufacturer of the drink: ");
        String manufacturer = scn.nextLine();
        drinks.add(new Drink(type, brand, percentage, age, origin, manufacturer));
        System.out.println("The new drink is added to the list.");
    }

    private static int inputAge() {
        int age = -1;
        while (age < 0){
            try{
                System.out.print("Enter the age of the drink: ");
                age = scn.nextInt();
                scn.nextLine();
                if (age < 0){
                    System.out.println("Not valid age.");
                }
            }
            catch (Exception e){
                System.out.println("Not valid percentage.");
                System.out.println(e.getMessage());
                scn.nextLine();
            }
        }
        return  age;
    }

    private static int inputPercentage() {
        int percentage = -1;
        while (percentage < 0 || percentage > 96){
            try{
                System.out.print("Enter the percentage of alcohol in the drink: ");
                percentage = scn.nextInt();
                scn.nextLine();
                if (percentage < 0 || percentage > 96){
                    System.out.println("Not valid percentage.");
                }
            }
            catch (InputMismatchException e){
                System.out.println("Not valid percentage.");
                System.out.println(e.getMessage());
                scn.nextLine();
            }
        }
        return  percentage;
    }

    private static String inputOrigin(){
        System.out.print("Enter the origin of the drink: ");
        String origin = scn.nextLine();
        return origin;
    }

    private static String inputManufacturer(){
        System.out.print("Enter the manufacturer of the drink: ");
        String manufacturer = scn.nextLine();
        return manufacturer;
    }

    private static ArrayList<Drink> readDrinksFromXml(String filepath) {
        ArrayList<Drink> drinks = new ArrayList<>();
        try{
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = dbf.newDocumentBuilder();
            Document document = documentBuilder.parse(filepath);
            Element rootElement = document.getDocumentElement();
            NodeList childNodesList = rootElement.getChildNodes();
            Node node;
            for (int i = 0; i < childNodesList.getLength(); i++){
                node = childNodesList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    NodeList childNodesOfDrinkTag = node.getChildNodes();
                    String type = "", brand = "", percentage = "", age = "", origin = "", manufacturer = "";
                    for (int j = 0; j < childNodesOfDrinkTag.getLength(); j++){
                        if (childNodesOfDrinkTag.item(j).getNodeType() == Node.ELEMENT_NODE) {
                            switch (childNodesOfDrinkTag.item(j).getNodeName()) {
                                case "type" -> type = childNodesOfDrinkTag.item(j).getTextContent();
                                case "brand" -> brand = childNodesOfDrinkTag.item(j).getTextContent();
                                case "percentage" -> percentage = childNodesOfDrinkTag.item(j).getTextContent();
                                case "age" -> age = childNodesOfDrinkTag.item(j).getTextContent();
                                case "origin" -> origin = childNodesOfDrinkTag.item(j).getTextContent();
                                case "manufacturer" -> manufacturer = childNodesOfDrinkTag.item(j).getTextContent();

                            }
                        }
                    }
                    drinks.add(new Drink(type, brand, Integer.parseInt(percentage), Integer.parseInt(age), origin, manufacturer));
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
            e.getMessage();
            e.getStackTrace();
        }
        return drinks;
    }

    public static void saveDrinksToXml(ArrayList<Drink> drinks, String filepath) {
        try {
            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            Element rootElement = document.createElement("drinks");
            document.appendChild(rootElement);

            for (Drink drink : drinks) {
                Element userElement = document.createElement("drink");
                rootElement.appendChild(userElement);
                createChildElement(document, userElement, "type", drink.getType());
                createChildElement(document, userElement, "brand", drink.getBrand());
                createChildElement(document, userElement, "percentage", String.valueOf(drink.getPercentage()));
                createChildElement(document, userElement, "age", String.valueOf(drink.getAge()));
                createChildElement(document, userElement, "origin", drink.getOrigin());
                createChildElement(document, userElement, "manufacturer", drink.getManufacturer());
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new FileOutputStream(filepath));

            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(source, result);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void createChildElement(Document document, Element parent, String tagName, String value) {
        Element element = document.createElement(tagName);
        element.setTextContent(value);
        parent.appendChild(element);
    }
}