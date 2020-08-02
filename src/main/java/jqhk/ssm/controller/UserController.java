package jqhk.ssm.controller;

import jqhk.ssm.Utility;
import jqhk.ssm.model.MessageModel;
import jqhk.ssm.model.TopicModel;
import jqhk.ssm.model.UserModel;
import jqhk.ssm.service.MessageService;
import jqhk.ssm.service.TopicService;
import jqhk.ssm.service.UserService;
import org.apache.catalina.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@Controller
public class UserController {

    private UserService userService;
    private TopicService topicService;
    private MessageService messageService;

    public UserController(UserService userService,
                          TopicService topicService,
                          MessageService messageService) {
        this.userService = userService;
        this.topicService = topicService;
        this.messageService = messageService;
    }

    @GetMapping("/register")
    public ModelAndView registerView() {
        ModelAndView m = new ModelAndView("user/register");
        return m;
    }

    @RequestMapping("/user/ajax/register")
    @ResponseBody
    public HashMap<String, String> ajaxRegister(@RequestBody HashMap<String, String> elements) {
        String username = elements.get("username");
        String password = elements.get("password");
        Utility.log("user ajax register username %s, password %s", username, password);
        boolean success =  userService.validRegister(username, password);
        HashMap<String, String> result = new HashMap<>();
        String successStr = "false";
        if (success) {
            successStr = "true";
            userService.add(username, password);
        }
        result.put("success", successStr);
        result.put("results", "");
        return result;
    }

    @GetMapping("/login")
    public ModelAndView loginView(HttpServletRequest request) {
        UserModel currentUser = userService.currentUser(request);
        ModelAndView m = new ModelAndView("user/login");
        m.addObject("currentUser", currentUser);
        return m;
    }

    @PostMapping("/user/login")
    public ModelAndView login(HttpServletRequest request) {
        String username = request.getParameter("username");
        String password=  request.getParameter("password");
        if (userService.validateLogin(username, password)) {
            UserModel user = userService.findByUsername(username);
            // Utility.log("登录成功");
            request.getSession().setAttribute("user_id", user.getId());
            // request.getSession().setAttribute("test", "test");
            return new ModelAndView("redirect:/topic");
        } else {
            request.getSession().removeAttribute("user_id");
            ModelAndView m = new ModelAndView("redirect:/login");
            return m;
        }
    }

    @GetMapping("/setting")
    public ModelAndView settingView(HttpServletRequest request) {
        UserModel currentUser = userService.currentUser(request);
        ModelAndView m = new ModelAndView("user/setting");
        m.addObject("u", currentUser);
        return m;
    }

    @PostMapping("/user/setting")
    public ModelAndView setting(HttpServletRequest request) {
        String userName = request.getParameter("username");
        String note = request.getParameter("note");
        this.userService.updateSetting(userName, note);
        ModelAndView m = new ModelAndView("redirect:/setting");
        return m;
    }

    @PostMapping("/user/update")
    public ModelAndView update(HttpServletRequest request) {
        String userName = request.getParameter("username");
        String oldPassWord = request.getParameter("old_pass");
        String newPassWord = request.getParameter("new_pass");
        if (userService.validateLogin(userName, oldPassWord)) {
            userService.update(userName, newPassWord);
        }
        ModelAndView m = new ModelAndView("redirect:/setting");
        return m;
    }

    @GetMapping("/user/{userName}")
    public ModelAndView detail(@PathVariable String userName) {
        UserModel u = this.userService.findByUsername(userName);
        ModelAndView m = new ModelAndView("user/detail");
        m.addObject("u", u);
        List<TopicModel> createTopics = this.topicService.topicsByUserId(u.getId(), 5);
        List<TopicModel> attendTopics = this.topicService.currentAttendTopics(u.getId(), 5);
        m.addObject("createTopics", createTopics);
        m.addObject("attendTopics", attendTopics);
        return m;
    }

    @GetMapping("/user/{userName}/topics")
    public ModelAndView userTopicsView(@PathVariable String userName) {
        UserModel u = this.userService.findByUsername(userName);
        ModelAndView m = new ModelAndView("user/topics");
        m.addObject("u", u);
        List<TopicModel> createTopics = this.topicService.topicsByUserId(u.getId(), 5);
        m.addObject("topics", createTopics);
        return m;
    }

    @GetMapping("/user/{userName}/replies")
    public ModelAndView userRespliesView(@PathVariable String userName) {
        UserModel u = this.userService.findByUsername(userName);
        ModelAndView m = new ModelAndView("user/replies");
        m.addObject("u", u);
        List<TopicModel> attendTopics = this.topicService.currentAttendTopics(u.getId(), 5);
        m.addObject("topics", attendTopics);
        return m;
    }

    @GetMapping("/message")
    public ModelAndView messageView(HttpServletRequest request) {
        UserModel currentUser = userService.currentUser(request);
        ModelAndView m = new ModelAndView("user/message");
        m.addObject("u", currentUser);
        List<MessageModel> notRead = this.messageService.notReadAll(currentUser.getId());
        List<MessageModel> read = this.messageService.readAll(currentUser.getId());
        m.addObject("read", read);
        // Utility.log("message read %s", read);
        m.addObject("notRead", notRead);

        this.messageService.readAll(notRead);
        return m;
    }

    @GetMapping("/user/{userName}/collection")
    public ModelAndView collectionView(@PathVariable String userName) {
        UserModel u = this.userService.findByUsername(userName);
        ModelAndView m = new ModelAndView("user/collection");
        m.addObject("u", u);
        List<TopicModel> topics = this.topicService.collectionTopics(u.getId());
        m.addObject("topics", topics);
        Utility.log("user %s collection topic %s", u.getUserName(), topics);
        return m;
    }

    @GetMapping("/users/top100")
    public ModelAndView sourceTopView(HttpServletRequest request) {
        UserModel currenUser = this.userService.currentUser(request);
        List<UserModel> top100User = this.userService.sourceTop100();
        ModelAndView m = new ModelAndView("user/top100");
        m.addObject("currentUser", currenUser);
        m.addObject("users", top100User);
        return m;
    }
    
}
