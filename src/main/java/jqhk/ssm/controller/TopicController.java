package jqhk.ssm.controller;

import jqhk.ssm.Utility;
import jqhk.ssm.model.CommentModel;
import jqhk.ssm.model.TopicModel;
import jqhk.ssm.service.CommentService;
import jqhk.ssm.service.UserService;
import jqhk.ssm.model.UserModel;
import jqhk.ssm.service.TopicService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class TopicController {

    private TopicService topicService;
    private UserService userService;
    private CommentService commentService;

    public TopicController(TopicService topicService, UserService userService, CommentService commentService) {
        this.topicService = topicService;
        this.userService = userService;
        this.commentService = commentService;
    }

    @GetMapping("/topic/new")
    public ModelAndView addView() {
        Utility.log("topic new");
        ModelAndView m = new ModelAndView("topic/topic_add");
        return m;
    }

    @PostMapping("/topic/add")
    public ModelAndView add(String title, String content, String tab, HttpServletRequest request) {
        // Utility.log("content: %s", content);
        title = Utility.escapeScript(title);
        content = Utility.escapeScript(content);
        UserModel currentUser = userService.currentUser(request);
        topicService.add(title, content, currentUser.getId(), tab);
        ModelAndView m = new ModelAndView("redirect:/topic");
        return m;
    }

    @GetMapping("/topic/detail/{id}")
    public ModelAndView detail(@PathVariable Integer id, HttpServletRequest request) {
        Utility.log("topic detail id %s", id);
        TopicModel topic = topicService.selectOneWithComment(id);
        Utility.log("topic detail topic %s", topic);
        this.topicService.updateViewCount(topic.getId());
        UserModel currentUser = userService.currentUser(request);
        UserModel author = userService.findById(topic.getUserId());
        Boolean collected = this.topicService.collected(currentUser.getId(), topic.getId());
        ModelAndView mv = new ModelAndView("topic/topic_detail");
        mv.addObject("topic", topic);
        mv.addObject("currentUser", currentUser);
        mv.addObject("author", author);
        mv.addObject("collected", collected);
        // Utility.log("topic detail collected %s", collected);
        List<CommentModel> comments = commentService.all(id);
        mv.addObject("comments", comments);
        return mv;
    }

    @GetMapping("/topic/edit")
    public ModelAndView edit(Integer id) {
        TopicModel topic = topicService.selectOneWithComment(id);
        ModelAndView m = new ModelAndView("topic/topic_edit");
        m.addObject("t", topic);
        return m;
    }

    @PostMapping("/topic/update")
    public ModelAndView update(Integer id, String title ,String content, String tab) {
        title = Utility.escapeScript(title);
        content = Utility.escapeScript(content);
        topicService.update(id, title, content, tab);
        String url = String.format("redirect:/topic/detail/%s", id);
        ModelAndView m = new ModelAndView(url);
        return m;
    }

    @GetMapping("/topic/delete")
    public ModelAndView delete(Integer id) {
        topicService.deleteById(id);
        ModelAndView m = new ModelAndView("redirect:/topic");
        return m;
    }

    @GetMapping("/topic/delete/{topicId}")
    public ModelAndView adminDelete(@PathVariable Integer topicId) {
        topicService.deleteById(topicId);
        ModelAndView m = new ModelAndView("redirect:/topic");
        return m;
    }

    @GetMapping("/topic")
    public ModelAndView index(@RequestParam(name="tab", required=false, defaultValue="all") String tab,
                              @RequestParam(name="page", required=false, defaultValue="1") Integer page,
                              HttpServletRequest request) {
        List<TopicModel> topics = topicService.allNotTop(tab, page);
        ModelAndView m = new ModelAndView("topic/topic_index");
        List<TopicModel> topTopics = topicService.allTop(tab);
        List<TopicModel> noReplyTopics = topicService.noReplyTopics();
        List<UserModel> sourceOrderList = userService.sourceOrderList(10);
        Utility.log("topic index topics %s, topTopics %s", topics, topTopics);
        m.addObject("topTopics", topTopics);
        m.addObject("topics", topics);
        m.addObject("noReplyTopics", noReplyTopics);
        m.addObject("sourceOrderList", sourceOrderList);
        UserModel u = userService.currentUser(request);
        m.addObject("u", u);
        Utility.log("topic index currentUser %s", u);
        m.addObject("tab", tab);
        return m;
    }

    @GetMapping("/topic/top/{topicId}")
    public ModelAndView top(@PathVariable Integer topicId) {
        this.topicService.top(topicId);
        String url = String.format("redirect:/topic/detail/%s", topicId);
        ModelAndView m = new ModelAndView(url);
        return m;
    }

    @GetMapping("/topic/good/{topicId}")
    public ModelAndView good(@PathVariable Integer topicId) {
        this.topicService.good(topicId);
        String url = String.format("redirect:/topic/detail/%s", topicId);
        ModelAndView m = new ModelAndView(url);
        return m;
    }

    @GetMapping("/topic/collection")
    public ModelAndView collection(Integer id, HttpServletRequest request) {
        UserModel u = this.userService.currentUser(request);
        this.topicService.collection(u.getId(), id);
        String url = String.format("redirect:/topic/detail/%s", id);
        ModelAndView m = new ModelAndView(url);
        return m;
    }

    @GetMapping("/topic/cancelCollection")
    public ModelAndView cancelCollection(Integer id, HttpServletRequest request) {
        UserModel u = this.userService.currentUser(request);
        this.topicService.cancelCollection(u.getId(), id);
        String url = String.format("redirect:/topic/detail/%s", id);
        ModelAndView m = new ModelAndView(url);
        return m;
    }

}
