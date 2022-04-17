package project.home;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import project.databases.DatabaseRelatedMethods;
import project.model.ViewInformationObject;

import java.io.*;

import java.time.LocalDateTime;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Controller
class HomeController {

    @RequestMapping("/")
    public String index(
            @ModelAttribute ViewInformationObject viewInformationObject,
            Model model
    ) throws ClassNotFoundException {
        Double doubleValueFrom = 0.0;
        Double doubleValueTo = 0.0;
        String innitialCurrencyName = "Dollar";

        Class.forName("org.sqlite.JDBC");

        Connection connection = null;
        try
        {
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:src\\main\\resources\\database\\currencyDB.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            ResultSet rs = statement.executeQuery("select * from currency");
            while(rs.next())
            {
                if(rs.getString("currencyName").equals("Dollar")) {
                    model.addAttribute("currencyNameDollar", rs.getString("currencyName"));
                    model.addAttribute("valueDollar", rs.getDouble("value"));
                    doubleValueFrom = rs.getDouble("value");
                    doubleValueTo = rs.getDouble("value");
                }
                if(rs.getString("currencyName").equals("Euro")) {
                    model.addAttribute("currencyNameEuro", rs.getString("currencyName"));
                    model.addAttribute("valueEuro", rs.getDouble("value"));
                }
                if(rs.getString("currencyName").equals("Pound sterling")) {
                    model.addAttribute("currencyNamePoundsterling", rs.getString("currencyName"));
                    model.addAttribute("valuePoundsterling", rs.getDouble("value"));
                }
                if(rs.getString("currencyName").equals("Japanese Yen")) {
                    model.addAttribute("currencyNameJapaneseYen", rs.getString("currencyName"));
                    model.addAttribute("valueJapaneseYen", rs.getDouble("value"));
                }
            }
        }
        catch(SQLException e)
        {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println(e.getMessage());
        }
        finally
        {
            try
            {
                if(connection != null)
                    connection.close();
            }
            catch(SQLException e)
            {
                // connection close failed.
                System.err.println(e);
            }
        }


        model.addAttribute("currencyFromValue", doubleValueFrom);
        model.addAttribute("currencyToValue", doubleValueTo);
        model.addAttribute("input", new ViewInformationObject());

        return "index2";
    }

    @PostMapping("/")
    public String indexInputSubmit(@ModelAttribute ViewInformationObject viewInformationObject, Model model) throws ClassNotFoundException {
        Double doubleValueFrom = 0.0;
        Double doubleValueTo = 0.0;

        model.addAttribute("currencyDataInputURL", new ViewInformationObject());


//        System.out.println("getId()" + viewInformationObject.getId());
//        System.out.println("getContent() " + viewInformationObject.getContent());

        String str = viewInformationObject.getContent();
        String[] arrOfStr = str.split(",", 2);

        String currencyFrom = arrOfStr[0];
        String currencyTo = arrOfStr[1];

        // load the sqlite-JDBC driver using the current class loader
        Class.forName("org.sqlite.JDBC");

        Connection connection = null;
        try
        {
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:src\\main\\resources\\database\\currencyDB.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            ResultSet rs = statement.executeQuery("select * from currency");
            while(rs.next())
            {
                if(rs.getString("currencyName").equals("Dollar")) {
                    model.addAttribute("currencyNameDollar", rs.getString("currencyName"));
                    model.addAttribute("valueDollar", rs.getDouble("value"));
                    doubleValueFrom = rs.getDouble("value");
                    doubleValueTo = rs.getDouble("value");
                }
                if(rs.getString("currencyName").equals("Euro")) {
                    model.addAttribute("currencyNameEuro", rs.getString("currencyName"));
                    model.addAttribute("valueEuro", rs.getDouble("value"));
                }
                if(rs.getString("currencyName").equals("Pound sterling")) {
                    model.addAttribute("currencyNamePoundsterling", rs.getString("currencyName"));
                    model.addAttribute("valuePoundsterling", rs.getDouble("value"));
                }
                if(rs.getString("currencyName").equals("Japanese Yen")) {
                    model.addAttribute("currencyNameJapaneseYen", rs.getString("currencyName"));
                    model.addAttribute("valueJapaneseYen", rs.getDouble("value"));
                }
            }
        }
        catch(SQLException e)
        {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println(e.getMessage());
        }
        finally
        {
            try
            {
                if(connection != null)
                    connection.close();
            }
            catch(SQLException e)
            {
                // connection close failed.
                System.err.println(e);
            }
        }

        connection = null;

        try
        {
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:src\\main\\resources\\database\\currencyDB.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            ResultSet rs = statement.executeQuery("select value from currency WHERE currencyName = '" + currencyFrom +"';");
            while(rs.next())
            {
                System.out.println("currencyFrom " + currencyFrom);
                System.out.println("rs.getDouble(\"value\") " + rs.getDouble("value"));
                doubleValueFrom = rs.getDouble("value");
                
            }
            ResultSet rs1 = statement.executeQuery("select value from currency WHERE currencyName = '" + currencyTo +"';");
            while(rs1.next())
            {
                System.out.println("currencyTo " + currencyTo);
                System.out.println("rs.getDouble(\"value\") " + rs1.getDouble("value"));
                doubleValueTo = rs.getDouble("value");
                
            }

        }
        catch(SQLException e)
        {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println(e.getMessage());
        }
        finally
        {
            try
            {
                if(connection != null)
                    connection.close();
            }
            catch(SQLException e)
            {
                // connection close failed.
                System.err.println(e);
            }
        }

        double calculationResult = Double.parseDouble(viewInformationObject.getId()) * doubleValueFrom /doubleValueTo;

        model.addAttribute("input", new ViewInformationObject());
        model.addAttribute("currencyFromValue", doubleValueFrom);
        model.addAttribute("currencyToValue", doubleValueTo);
        model.addAttribute("result", calculationResult);

        return "index2";
    }

    @GetMapping("/currencyDataInputURL")
    public String indexInput(@ModelAttribute ViewInformationObject viewInformationObject, Model model){

        System.out.println(viewInformationObject.getId());
        System.out.println(viewInformationObject.getContent());

        return "layouts/currencyDataInputURL";
    }

    @RequestMapping(value = "/result")
    String doStuffMethod(Model model) throws ClassNotFoundException, IOException {
        System.out.println("Success");

//        Application.downloadInitializer();

        return "layouts/showdata";
    }

    @RequestMapping(value = "/convertXMLtoDB")
    String convertXMLtoDB(Model model) throws ClassNotFoundException, IOException {
//        System.out.println("Succggess");

//        Application.downloadInitializerForXML();

        String filePath = "src\\main\\resources\\downloads\\";
//        String fileName = "XMLFile.xml";
        String fileName = "Exchange_Rates.xml";

        try
        {

            File file = new File(filePath+fileName);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);
            doc.getDocumentElement().normalize();
            System.out.println("Root element: " + doc.getDocumentElement().getNodeName());
            NodeList nodeList = doc.getElementsByTagName("ROW");

            DatabaseRelatedMethods methods = new DatabaseRelatedMethods();

            methods.createDB();

            for (int itr = 0; itr < nodeList.getLength(); itr++)
            {
                Node node = nodeList.item(itr);
                System.out.println("\nNode Name :" + node.getNodeName());
                try {
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        Element eElement = (Element) node;


//                        System.out.println("NAME_: " + eElement.getElementsByTagName("NAME_").item(0).getTextContent());
//                        System.out.println("RATE: " + eElement.getElementsByTagName("RATE").item(0).getTextContent());

                        methods.writeIntoDB(eElement.getElementsByTagName("NAME_").item(0).getTextContent(),
                                eElement.getElementsByTagName("RATE").item(0).getTextContent());

                    }
                } catch (NullPointerException e) {
                    System.out.println("There was a transfer exception");
                    e.printStackTrace();
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return "redirect:/showdata";
    }

    @GetMapping("/security/user_page")
    String userPage(Model model) {
        model.addAttribute("now", LocalDateTime.now());

        return "security/user_page";
    }

    @GetMapping("/showdata")
    String showdata(Model model){

        return "layouts/showdata";
    }

    @GetMapping("properties")
    @ResponseBody
    java.util.Properties properties() {
        return System.getProperties();
    }

    @GetMapping("/change-currency-rates")
    public String inputForm(@ModelAttribute ViewInformationObject viewInformationObject, Model model)  throws ClassNotFoundException{

        // load the sqlite-JDBC driver using the current class loader
        Class.forName("org.sqlite.JDBC");

        Connection connection = null;
        try
        {
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:src\\main\\resources\\database\\currencyDB.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            ResultSet rs = statement.executeQuery("select * from currency");
            while(rs.next())
            {
                if(rs.getString("currencyName").equals("Dollar")) {
                    model.addAttribute("currencyNameDollar", rs.getString("currencyName"));
                    model.addAttribute("valueDollar", rs.getDouble("value"));
                }
                if(rs.getString("currencyName").equals("Euro")) {
                    model.addAttribute("currencyNameEuro", rs.getString("currencyName"));
                    model.addAttribute("valueEuro", rs.getDouble("value"));
                }
                if(rs.getString("currencyName").equals("Pound sterling")) {
                    model.addAttribute("currencyNamePoundsterling", rs.getString("currencyName"));
                    model.addAttribute("valuePoundsterling", rs.getDouble("value"));
                }
                if(rs.getString("currencyName").equals("Japanese Yen")) {
                    model.addAttribute("currencyNameJapaneseYen", rs.getString("currencyName"));
                    model.addAttribute("valueJapaneseYen", rs.getDouble("value"));
                }
            }
        }
        catch(SQLException e)
        {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println(e.getMessage());
        }
        finally
        {
            try
            {
                if(connection != null)
                    connection.close();
            }
            catch(SQLException e)
            {
                // connection close failed.
                System.err.println(e);
            }
        }
        return "layouts/change-currency-rates";
    }

    @PostMapping("/change-currency-rates")
    public String inputSubmit(@ModelAttribute ViewInformationObject viewInformationObject, Model model) throws ClassNotFoundException{
        model.addAttribute("change-currency-rates", viewInformationObject);

        System.out.println("UPDATE currency SET value = " + viewInformationObject.getId() + " WHERE currencyName = '" + viewInformationObject.getContent() + "';");

//        // load the sqlite-JDBC driver using the current class loader
        Class.forName("org.sqlite.JDBC");

        Connection connection = null;
        try
            {
                // create a database connection
                connection = DriverManager.getConnection("jdbc:sqlite:src\\main\\resources\\database\\currencyDB.db");
                Statement statement = connection.createStatement();
                statement.setQueryTimeout(30);  // set timeout to 30 sec.

                statement.executeUpdate("UPDATE currency SET value = " + viewInformationObject.getId() + " WHERE currencyName = '" + viewInformationObject.getContent() + "';");

            }
            catch(SQLException e)
            {
                // if the error message is "out of memory",
                // it probably means no database file is found
                System.err.println(e.getMessage());
            }
            finally
            {
                try
                {
                    if(connection != null)
                        connection.close();
                }
                catch(SQLException e)
                {
                    // connection close failed.
                    System.err.println(e);
                }
        }

        return "redirect:/";
    }

    @RequestMapping(value="/security/logout", method=RequestMethod.GET)
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/";
    }

    @GetMapping("/access-denied")
    public String getAccessDenied() {
        return "/security/access-denied";
    }

    @GetMapping("/downloads/Exchange_Rates.pdf")
    public ResponseEntity<InputStreamResource> getTermsConditions() throws FileNotFoundException {

        String filePath = "src\\main\\resources\\downloads\\";
        String fileName = "Exchange_Rates.pdf";
        File file = new File(filePath+fileName);
        HttpHeaders headers = new HttpHeaders();
        headers.add("content-disposition", "inline;filename=" +fileName);

        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/pdf"))
                .body(resource);
    }
}
