package jqhk.ssm.aspect;

import jqhk.ssm.Utility;
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
import java.util.HashMap;
import java.util.UUID;

// Aspect 切面：注册事件，在某类事情发生的时候调用
@Aspect
// 自动注册成 spring bean，这样 spring 就能认得出这个类
@Component
// 第二层AOP
@Order(1)
public class SecondAspect {
	private HttpServletRequest request;
	private HashMap<String, String> tokenMap = new HashMap<>();

	public SecondAspect(HttpServletRequest request) {
		this.request = request;
	}

	@Around("execution(* jqhk.ssm.controller.TopicController.detail(..)) || " +
			"execution(* jqhk.ssm.controller.TopicController.addView(..)) || " +
			"execution(* jqhk.ssm.controller.UserController.settingView(..))")
	public ModelAndView addToken(ProceedingJoinPoint joint) throws Throwable {
		Utility.log("aop add token");
		String token = UUID.randomUUID().toString();
		ModelAndView mv = (ModelAndView) joint.proceed();
		String userID = request.getSession().getAttribute("user_id").toString();
		tokenMap.put(userID, token);
		mv.addObject("token", token);
		return mv;
	}

	@Around("execution(* jqhk.ssm.controller.CommentController.add(..)) || " +
			"execution(* jqhk.ssm.controller.CommentController.delete(..)) || " +
			"execution(* jqhk.ssm.controller.TopicController.delete(..)) || " +
			"execution(* jqhk.ssm.controller.TopicController.add(..)) || " +
			"execution(* jqhk.ssm.controller.TopicController.collection(..)) || " +
			"execution(* jqhk.ssm.controller.TopicController.cancelCollection(..)) || " +
			"execution(* jqhk.ssm.controller.UserController.update(..)) || " +
			"execution(* jqhk.ssm.controller.UserController.setting(..))")
	public ModelAndView validToken(ProceedingJoinPoint joint) throws Throwable {
		Utility.log("aop valid token");
		String token = request.getParameter("token");
		String userID = request.getSession().getAttribute("user_id").toString();
		String tokenInTokenMap = tokenMap.get(userID);
		if (tokenInTokenMap != null && tokenInTokenMap.equals(token)) {
			Utility.log("aop valid token success");
			tokenMap.remove(userID);
			return (ModelAndView) joint.proceed();
		} else {
			return new ModelAndView("redirect:/topic");
		}
	}

}
