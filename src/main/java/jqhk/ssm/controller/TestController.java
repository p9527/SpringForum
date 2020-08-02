package jqhk.ssm.controller;

import jqhk.ssm.Utility;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;

@Controller
public class TestController {
    // @RequestMapping(value = "/ajax/test", produces = "application/json;charset=UTF-8")
    @RequestMapping("/test")
    public ModelAndView test() {
        ModelAndView mv = new ModelAndView("test");
        return mv;
    }
}
