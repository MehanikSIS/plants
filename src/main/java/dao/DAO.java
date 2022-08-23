package dao;

import entity.Catalog;
import entity.Plant;
import org.xml.sax.SAXException;
import xml.ParsingXML;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.List;
import java.util.Properties;


public class DAO {

    public static void main(String[] args) throws ParserConfigurationException, SAXException {
        Properties prop = new Properties();
        try {
            InputStream input = new FileInputStream("src/main/resources/config.properties");
            prop.load(input);
            Connection connection = DriverManager.getConnection(prop.getProperty("urlDataBase"), prop.getProperty("user"), prop.getProperty("password"));
            Statement stmt = connection.createStatement();
            System.out.println("Opened database successfully");
            for (String fileName : args) {
                File file = new File(fileName);
                ParsingXML parsingXML = new ParsingXML();
                Catalog catalog = new Catalog();
                List<Plant> plants = parsingXML.parsing(file, catalog);
                stmt.executeUpdate("INSERT INTO d_cat_catalog (delivery_date,company,uuid) VALUES ('"
                        + catalog.getDate()
                        + "', '"
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
                System.out.println(file.getName() + " uploaded to database");
            }
            stmt.close();
            connection.close();
            input.close();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }
}
