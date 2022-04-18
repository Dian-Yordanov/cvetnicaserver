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
    public String index( @ModelAttribute ViewInformationObject viewInformationObject, Model model ) throws ClassNotFoundException {

        return "index2";
    }

    @PostMapping("/")
    public String indexInputSubmit(@ModelAttribute ViewInformationObject viewInformationObject, Model model) throws ClassNotFoundException {


//        model.addAttribute("input", new ViewInformationObject());
//        model.addAttribute("currencyFromValue", doubleValueFrom);
//        model.addAttribute("currencyToValue", doubleValueTo);
//        model.addAttribute("result", calculationResult);

        return "index2";
    }

    @GetMapping("/currencyDataInputURL")
    public String indexInput(@ModelAttribute ViewInformationObject viewInformationObject, Model model){

//        System.out.println(viewInformationObject.getId());
//        System.out.println(viewInformationObject.getContent());

        return "layouts/currencyDataInputURL";
    }

    @RequestMapping(value = "/result")
    String doStuffMethod(Model model) throws ClassNotFoundException, IOException {

        return "layouts/showdata";
    }

    @RequestMapping(value = "/convertXMLtoDB")
    String convertXMLtoDB(Model model) throws ClassNotFoundException, IOException {

        return "redirect:/showdata";
    }

    @GetMapping("/security/user_page")
    String userPage(Model model) {

        return "security/user_page";
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

}
