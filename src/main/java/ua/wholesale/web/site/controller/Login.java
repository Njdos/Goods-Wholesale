package ua.wholesale.web.site.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class Login extends HttpServlet {


/*    @GetMapping("/login")
    public String gesCookies(HttpServletRequest httpServletRequest,
                             HttpServletResponse httpServletResponse,
                             Model model) {


        Cookie cookie_name = new Cookie("user_username", "godd");
        Cookie cookie_password = new Cookie("user_password", "123");

        cookie_name.setMaxAge(0);
        cookie_password.setMaxAge(0);

        httpServletResponse.addCookie(cookie_name);
        httpServletResponse.addCookie(cookie_password);

        Cookie[] cookies = httpServletRequest.getCookies();

        if (cookies != null) {
            for (Cookie cookie1 : cookies) {
                model.addAttribute(cookie1.getName(), cookie1.getValue());
            }
        }
        return "login";
    }*/
}


