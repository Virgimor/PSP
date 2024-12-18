package es.iesjandula.psp.spring_security_thymeleaf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SecurityController
{
    @RequestMapping("/")
    public String indexDefault()
    {
        return "secured/index.html";
    }
	
    @RequestMapping("/index.html")
    public String index()
    {
        return "secured/index.html";
    }

    @RequestMapping("/login.html")
    public String login()
    {
        return "secured/login.html";
    }

    @RequestMapping("/admin/index.html")
    public String adminIndex()
    {
        return "secured/admin/index.html";
    }

    @RequestMapping("/user/index.html")
    public String userIndex()
    {
        return "secured/user/index.html";
    }

    @RequestMapping("/login-error.html")
    public String loginError(Model model)
    {
        model.addAttribute("loginError", true);
        return "secured/login.html";
    }

    @RequestMapping("/forbidden.html")
    public String forbidden()
    {
        return "secured/forbidden.html";
    }

    @RequestMapping("/css/main.css")
    public String getCss()
    {
        return "secured/css/main.css";
    }
    
    @RequestMapping("/not-found.html")
    public String notFound()
    {
        return "secured/not-found.html";
    }
}

