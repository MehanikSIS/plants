package dao;

import entity.Catalog;
import entity.Plant;
import org.xml.sax.SAXException;
import xml.ParsingXML;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.util.List;


public class DAO {

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException{
        for (String fileName : args) {
            File file = new File(fileName);
            ParsingXML parsingXML = new ParsingXML();
            Catalog catalog = new Catalog();
            List<Plant> plants = parsingXML.parsing(file, catalog);
            Connection c;
            Statement stmt;
            try {

                c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/plant", "postgres", "root");
                System.out.println("Opened database successfully");
                stmt = c.createStatement();
                stmt.executeUpdate("INSERT INTO d_cat_catalog (delivery_date,company,uuid) VALUES ('"
                        + catalog.getDate()
                        + "+00', '"
                        + catalog.getCompany() + "', '"
                        + catalog.getUuid() + "' );");
                for (Plant plant : plants) {
                    ResultSet resultSet = stmt.executeQuery("Select id from d_cat_catalog where uuid='" + catalog.getUuid() + "'");
                    resultSet.next();
                    String catalog_id = resultSet.getString("id");
                    stmt.executeUpdate("INSERT INTO f_cat_plants (common,botanical,zone,light,price,availability,catalog_id) VALUES ('"
                            + plant.getCommon() + "', '"
                            + plant.getBotanical() + "', '"
                            + plant.getZone() + "', '"
                            + plant.getLight() + "', '"
                            + plant.getPrice() + "', '"
                            + plant.getAvailability() + "', '"
                            + catalog_id + "');");

                }
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
                System.exit(0);

            }
        }

    }
}
