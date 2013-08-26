package org.polyglotted.graphonomy.schemaweb.controllers;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.polyglotted.graphonomy.schemaweb.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Maps;

@Controller
@RequestMapping(value = "/api/auth", produces = "application/json")
public class AuthController {

    @RequestMapping(value = "/session", method = RequestMethod.GET)
    @ResponseBody    
    public Map<String, Object> getSession() {
        Map<String, Object> result = Maps.newHashMap();
        result.put("user", new User("shankar", "Shankar Vasudevan", "IT", "UK", "London", true));
        return result;
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public void logout(HttpServletResponse resp) throws IOException {
        resp.sendRedirect("/login.html");
    }
}
