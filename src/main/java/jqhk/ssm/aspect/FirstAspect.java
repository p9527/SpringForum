package jqhk.ssm.aspect;


import jqhk.ssm.Utility;
import jqhk.ssm.model.CommentModel;
import jqhk.ssm.model.TopicModel;
import jqhk.ssm.model.UserModel;
import jqhk.ssm.model.UserRole;
import jqhk.ssm.service.CommentService;
import jqhk.ssm.service.TopicService;
import jqhk.ssm.service.UserService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

// 会被注册成切面，这样方法才会在事件发生的时候执行
@Aspect
// 自动注册成 spring bean，这样 spring 就能认得出这个类
@Component
// 第一层AOP
@Order(0)
// Aspect 切面：注册事件，在某类事情发生的时候调用
public class FirstAspect {
    private UserService userService;
    private TopicService topicService;
    private HttpServletRequest request;
    private CommentService commentService;

    public FirstAspect(UserService service, HttpServletRequest request,
                       TopicService topicService, CommentService commentService) {
        this.request = request;
        this.userService = service;
        this.topicService = topicService;
        this.commentService = commentService;
    }

    @Around("execution(* jqhk.ssm.controller.TopicController.detail(..)) || " +
            "execution(* jqhk.ssm.controller.TopicController.add(..)) || " +
            "execution(* jqhk.ssm.controller.TopicController.addView(..)) || " +
            "within(jqhk.ssm.controller.CommentController) || " +
            "execution(* jqhk.ssm.controller.UserController.settingView(..)) || " +
            "execution(* jqhk.ssm.controller.UserController.messageView(..)) || " +
            "execution(* jqhk.ssm.controller.UserController.detail(..))")
    public ModelAndView loginRequired(ProceedingJoinPoint joint) throws Throwable {
        Utility.log("loginRequired 正在访问的 url %s", request.getRequestURI());
        Utility.log("loginRequired 正在执行的方法 %s %s", joint.getSignature(), joint.getArgs());
        Integer userID = (Integer) request.getSession().getAttribute("user_id");
        if (userID == null) {
            // 跳转回登陆页面
            Utility.log("loginRequired 没有 session");
            return new ModelAndView("redirect:/login");
        } else {
            UserModel u = userService.findById(userID);
            if (u == null || u.getRole().equals(UserRole.guest)) {
                // 跳转回登陆页面
                Utility.log("loginRequired 用户不存在 %s", userID);
                return new ModelAndView("redirect:/login");
            } else {
                // 执行被插入的方法
                return (ModelAndView) joint.proceed();
            }
        }
    }

    @Around("execution(* jqhk.ssm.controller.FileUploadController.singleFileUpload(..))")
    public String loginRequired_String(ProceedingJoinPoint joint) throws Throwable {
        Integer userID = (Integer) request.getSession().getAttribute("user_id");
        if (userID == null) {
            // 跳转回登陆页面
            Utility.log("loginRequired 没有 session");
            return "请登录";
        } else {
            UserModel u = userService.findById(userID);
            if (u == null || u.getRole().equals(UserRole.guest)) {
                // 跳转回登陆页面
                Utility.log("loginRequired 用户不存在 %s", userID);
                return "请登录";
            } else {
                // 执行被插入的方法
                return (String) joint.proceed();
            }
        }
    }


    @Around("execution(* jqhk.ssm.controller.TopicController.edit(..)) || " +
            "execution(* jqhk.ssm.controller.TopicController.delete(..)) || " +
            "execution(* jqhk.ssm.controller.TopicController.update(..))")
    public ModelAndView ownerRequired(ProceedingJoinPoint joint) throws Throwable {
        Utility.log("ownerRequird 正在访问的 url %s", request.getRequestURI());
        Utility.log("ownerRequird 正在执行的方法 %s %s", joint.getSignature(), joint.getArgs());
        Integer userID = (Integer) request.getSession().getAttribute("user_id");
        Integer topicId = Integer.valueOf(request.getParameter("id"));
        if (userID == null) {
            // 跳转回登陆页面
            Utility.log("loginRequired 没有 session");
            return new ModelAndView("redirect:/login");
        } else {
            UserModel u = userService.findById(userID);
            Utility.log("user after find", u);
            if (u == null || u.getRole().equals(UserRole.guest)) {
                // 跳转回登陆页面
                Utility.log("loginRequired 用户不存在 %s", userID);
                return new ModelAndView("redirect:/login");
            } else {
                TopicModel topic = topicService.selectOneWithComment(topicId);
                Utility.log("topic before route: %s", topic);
                Utility.log("user before route: %s", u);
                if (u.getUserName().equals(topic.getUserName())) {
                    return (ModelAndView) joint.proceed();
                } else {
                    return new ModelAndView("redirect:/login");
                }
            }
        }
    }

    @Around("execution(* jqhk.ssm.controller.CommentController.edit(..)) || " +
            "execution(* jqhk.ssm.controller.CommentController.delete(..)) || " +
            "execution(* jqhk.ssm.controller.CommentController.update(..))")
    public ModelAndView ownerCommentRequired(ProceedingJoinPoint joint) throws Throwable {
        Utility.log("ownerRequird 正在访问的 url %s", request.getRequestURI());
        Utility.log("ownerRequird 正在执行的方法 %s %s", joint.getSignature(), joint.getArgs());
        Integer userID = (Integer) request.getSession().getAttribute("user_id");
        Integer commentId = Integer.valueOf(request.getParameter("id"));
        if (userID == null) {
            // 跳转回登陆页面
            Utility.log("loginRequired 没有 session");
            return new ModelAndView("redirect:/login");
        } else {
            UserModel u = userService.findById(userID);
            Utility.log("user after find", u);
            if (u == null || u.getRole().equals(UserRole.guest)) {
                // 跳转回登陆页面
                Utility.log("loginRequired 用户不存在 %s", userID);
                return new ModelAndView("redirect:/login");
            } else {
                CommentModel comment = commentService.findById(commentId);
                Utility.log("comment owner required u.name %s comment.userName %s", u.getUserName(), comment.getUserName());
                if (u.getUserName().equals(comment.getUserName())) {
                    return (ModelAndView) joint.proceed();
                } else {
                    return new ModelAndView("redirect:/login");
                }
            }
        }
    }

    @Around("execution(* jqhk.ssm.controller.TopicController.top(..)) || " +
            "execution(* jqhk.ssm.controller.TopicController.good(..)) || " +
            "execution(* jqhk.ssm.controller.TopicController.adminDelete(..))")
    public ModelAndView adminRequired(ProceedingJoinPoint joint) throws Throwable {
        Utility.log("adminRequired 正在访问的 url %s", request.getRequestURI());
        Utility.log("adminRequired 正在执行的方法 %s %s", joint.getSignature(), joint.getArgs());
        Integer userID = (Integer) request.getSession().getAttribute("user_id");
        if (userID == null) {
            // 跳转回登陆页面
            Utility.log("adminRequired 没有 session");
            return new ModelAndView("redirect:/login");
        } else {
            UserModel u = userService.findById(userID);
            Utility.log("user after find", u);
            if (u == null || u.getRole().equals(UserRole.guest)) {
                // 跳转回登陆页面
                Utility.log("adminRequired 用户不存在 %s", userID);
                return new ModelAndView("redirect:/login");
            } else {
                if (u.getRole().equals(UserRole.admin)) {
                    return (ModelAndView) joint.proceed();
                } else {
                    return new ModelAndView("redirect:/login");
                }
            }
        }
    }

    @Around("execution(* jqhk.ssm.controller.UserController.update(..)) || " +
            "execution(* jqhk.ssm.controller.UserController.setting(..))")
    public ModelAndView sameUser(ProceedingJoinPoint joint) throws Throwable {
        Utility.log("adminRequired 正在访问的 url %s", request.getRequestURI());
        Utility.log("adminRequired 正在执行的方法 %s %s", joint.getSignature(), joint.getArgs());
        Integer userID = (Integer) request.getSession().getAttribute("user_id");
        String userName = request.getParameter("username");
        if (userID == null) {
            // 跳转回登陆页面
            Utility.log("adminRequired 没有 session");
            return new ModelAndView("redirect:/login");
        } else {
            UserModel u = userService.findById(userID);
            Utility.log("user after find", u);
            if (u == null || u.getRole().equals(UserRole.guest)) {
                // 跳转回登陆页面
                Utility.log("adminRequired 用户不存在 %s", userID);
                return new ModelAndView("redirect:/login");
            } else {
                if (u.getUserName().equals(userName)) {
                    return (ModelAndView) joint.proceed();
                } else {
                    return new ModelAndView("redirect:/login");
                }
            }
        }
    }

}
