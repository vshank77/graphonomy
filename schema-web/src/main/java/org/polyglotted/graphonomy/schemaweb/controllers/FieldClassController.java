package org.polyglotted.graphonomy.schemaweb.controllers;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/api/fieldClasses", produces = "application/json")
public class FieldClassController {

    @RequestMapping(method = GET)
    @ResponseBody
    public List<String> list() {
        return Arrays.asList("hello", "word");
    }
}
