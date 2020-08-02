package jqhk.ssm.controller;

import jqhk.ssm.Utility;
import jqhk.ssm.model.CommentModel;
import jqhk.ssm.service.CommentService;
import jqhk.ssm.service.MessageService;
import jqhk.ssm.service.UserService;
import jqhk.ssm.model.TopicModel;
import jqhk.ssm.model.UserModel;
import jqhk.ssm.service.TopicService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class CommentController {

    private TopicService topicService;
    private UserService userService;
    private CommentService commentService;
    private MessageService messageService;

    public CommentController(TopicService topicService, UserService userService,
                             CommentService commentService, MessageService messageService) {
        this.topicService = topicService;
        this.userService = userService;
        this.commentService = commentService;
        this.messageService = messageService;
    }

    @PostMapping("/comment/add")
    public ModelAndView add(String content, Integer topicId, HttpServletRequest request) {
        // Utility.log("content: %s", content);
        UserModel currentUser = userService.currentUser(request);
        content = Utility.escapeScript(content);
        commentService.add(content, topicId, currentUser.getId());
        this.topicService.updateRelyCount(topicId);
        TopicModel t = this.topicService.selectOneWithComment(topicId);
        if (!currentUser.getId().equals(t.getUserId())) {
            this.userService.sourceAdd(t.getUserName());
            this.messageService.add(currentUser.getId(), t.getUserId(), t.getId());
        }
        String url = String.format("redirect:/topic/detail/%s", topicId);
        ModelAndView m = new ModelAndView(url);
        return m;
    }

    @PostMapping("/comment/reply")
    public ModelAndView reply(String content, Integer topicId, HttpServletRequest request) {
        // TODO @ 功能
        UserModel currentUser = userService.currentUser(request);
        content = Utility.escapeScript(content);

        commentService.add(content, topicId, currentUser.getId());
        this.topicService.updateRelyCount(topicId);
        TopicModel t = this.topicService.selectOneWithComment(topicId);
        if (!currentUser.getId().equals(t.getUserId())) {
            this.userService.sourceAdd(t.getUserName());
            this.messageService.add(currentUser.getId(), t.getUserId(), t.getId());
        }

        String url = String.format("redirect:/topic/detail/%s", topicId);
        ModelAndView m = new ModelAndView(url);
        return m;
    }
    
    @GetMapping("/comment/edit")
    public ModelAndView edit(Integer id) {
        CommentModel c = commentService.findById(id);
        ModelAndView m = new ModelAndView("topic/comment_edit");
        m.addObject("c", c);
        return m;
    }

    @PostMapping("/comment/update")
    public ModelAndView update(Integer id ,String content) {
        // Utility.log("content: %s", content);
        content = Utility.escapeScript(content);
        // todoService.update(id, content);
        commentService.update(id, content);
        CommentModel c = commentService.findById(id);
        String url = String.format("redirect:/topic/detail/%s", c.getTopicId());
        ModelAndView m = new ModelAndView(url);
        return m;
    }
    //
    //
    @GetMapping("/comment/delete")
    public ModelAndView delete(Integer id) {
        CommentModel c = commentService.findById(id);
        commentService.deleteById(id);
        String url = String.format("redirect:/topic/detail/%s", c.getTopicId());
        ModelAndView m = new ModelAndView(url);
        return m;
    }

    public static void main(String[] args) {
    }
}
