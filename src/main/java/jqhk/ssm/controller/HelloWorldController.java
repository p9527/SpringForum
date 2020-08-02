package jqhk.ssm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class HelloWorldController {

    @GetMapping("/demo")
    public ModelAndView demoRoute(@RequestParam(name="name", required=false, defaultValue="默认名字测试") String name) {

        // model 说的是给模板引擎的 model
        // view 说的是模板名字，没有后缀
        // view 可以自动补全，也可以直接跳转
        ModelAndView mv = new ModelAndView("hello");
        mv.addObject("name", name);
        return mv;
    }


    @GetMapping("/demo1")
    public ModelAndView demoRoute1(String name) {

        // model 说的是给模板引擎的 model
        // view 说的是模板名字，没有后缀
        // view 可以自动补全，也可以直接跳转
        ModelAndView mv = new ModelAndView("hello");
        mv.addObject("name", name);
        return mv;
    }

    @GetMapping("/demo2")
    public ModelAndView demoRoute2(HttpServletRequest request) {
        // Utility.log("demo2 request %s", request.);
        String name = request.getParameter("name");
        ModelAndView mv = new ModelAndView("hello");
        mv.addObject("name", name);
        return mv;
    }

    // 动态路由
    // 如果访问 /demo3/bai 那么 bai 就会被当做参数传给路由函数
    @GetMapping("/demo3/{name}")
    public ModelAndView demoRoute3(@PathVariable String name) {
        ModelAndView mv = new ModelAndView("hello");
        mv.addObject("name", name);
        return mv;
    }
}
