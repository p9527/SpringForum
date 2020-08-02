package jqhk.ssm.controller;

import jqhk.ssm.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PublicController {

    private UserService userService;

    public PublicController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/")
    public ModelAndView indexView(HttpServletRequest request) {
        // UserModel current = userService.currentUser(request);
        ModelAndView m = new ModelAndView("redirect:/topic");
        // m.addObject("currentUser", current);
        return m;
    }
}
