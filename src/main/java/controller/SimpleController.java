package controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
 
@Controller
@RequestMapping("/myc/")
public class SimpleController {
 
    @RequestMapping(method = RequestMethod.GET)
    public String sayMvc(ModelMap model) {
        model.addAttribute("modelValue", "Simple Spring 4 MVC");
        return "output"; //WEB-INF/views/output.jsp
    }
 
    @RequestMapping(value = "/simpleAgain", method = RequestMethod.GET)
    public String sayHelloAgain(ModelMap model) {
        model.addAttribute("modelValue", "Simple again from Spring 4 MVC");
        return "output"; //WEB-INF/views/output.jsp
    }
 
}