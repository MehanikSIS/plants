package xml;

import entity.Catalog;
import entity.Plant;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ParsingXML {

    public List<Plant> parsing(File file, Catalog catalog) throws ParserConfigurationException, IOException, SAXException {

        List<Plant> list = new ArrayList<>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(file);
        Element catalogElement = document.getDocumentElement();
        catalog.setCompany(catalogElement.getAttribute("company"));
        catalog.setDate(LocalDate.parse(catalogElement.getAttribute("date"), DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        catalog.setUuid(catalogElement.getAttribute("uuid"));
        NodeList plantNodeList = document.getElementsByTagName("PLANT");
        for (int i = 0; i < plantNodeList.getLength(); i++) {
            if (plantNodeList.item(i).getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) plantNodeList.item(i);
                Plant plant = new Plant();
                NodeList parsPlant = element.getChildNodes();
                for (int j = 0; j < parsPlant.getLength(); j++) {
                    if (parsPlant.item(j).getNodeType() == Node.ELEMENT_NODE) {
                        Element elementChild = (Element) parsPlant.item(j);

                        switch (elementChild.getNodeName()) {
                            case "COMMON" -> plant.setCommon(elementChild.getTextContent());
                            case "BOTANICAL" -> plant.setBotanical(elementChild.getTextContent());
                            case "ZONE" -> {
                                if (elementChild.getTextContent().equalsIgnoreCase("годичный")) {
                                    plant.setZone(1);
                                } else {
                                    plant.setZone(Integer.parseInt(elementChild.getTextContent()));
                                }
                            }
                            case "LIGHT" -> plant.setLight(elementChild.getTextContent());
                            case "PRICE" -> plant.setPrice(new BigDecimal(elementChild.getTextContent().substring(1)));
                            case "AVAILABILITY" ->
                                    plant.setAvailability(Integer.parseInt(elementChild.getTextContent()));
                        }
                    }
                }
                list.add(plant);
            }
        }
        return list;
    }
}
