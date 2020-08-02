package jqhk.ssm.controller;

import com.alibaba.fastjson.JSON;
import jqhk.ssm.Utility;
import jqhk.ssm.model.TodoModel;
import jqhk.ssm.model.UserModel;
import jqhk.ssm.service.TodoService;
import jqhk.ssm.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

@Controller
public class AjaxTodoController {
	private TodoService todoService;
	private UserService userService;

	public AjaxTodoController(TodoService todoService, UserService userService) {
		this.todoService = todoService;
		this.userService = userService;
	}

    @GetMapping("/todo")
    public ModelAndView index() {
        ModelAndView mv = new ModelAndView("ajax_todo");
        return mv;
    }

    @RequestMapping("/ajax/todo/all")
    @ResponseBody
    public String all(HttpServletRequest request) {
	    UserModel u = userService.currentUser(request);
	    List<TodoModel> todoList = todoService.all(u.getUserName());
	    String rs = JSON.toJSONString(todoList);
        return rs;
    }

	@RequestMapping("/ajax/todo/add")
	@ResponseBody
	public String add(HttpServletRequest request,
	                  @RequestBody HashMap<String, String> elements) {
		UserModel u = userService.currentUser(request);
		String content = elements.get("content");
		content = Utility.escapeScript(content);
		TodoModel todo = this.todoService.add(content, u.getUserName());
		Utility.log("ajax todo add todo %s", todo);
		String rs = JSON.toJSONString(todo);
		return rs;
	}

	@RequestMapping("/ajax/todo/delete")
	@ResponseBody
	public String delete(HttpServletRequest request,
	                     @RequestBody HashMap<String, String> elements) {
		// UserModel u = userService.currentUser(request);
		Integer todoId = Integer.valueOf(elements.get("id"));
		this.todoService.delete(todoId);
		return "SUCCESS";
	}

	@RequestMapping("/ajax/todo/complete")
	@ResponseBody
	public String complete(HttpServletRequest request,
	                       @RequestBody HashMap<String, String> elements) {
		// UserModel u = userService.currentUser(request);
		Integer todoId = Integer.valueOf(elements.get("id"));
		this.todoService.complete(todoId);
		return "SUCCESS";
	}

	@RequestMapping("/ajax/todo/update")
	@ResponseBody
	public String update(HttpServletRequest request,
	                     @RequestBody HashMap<String, String> elements) {
		// UserModel u = userService.currentUser(request);
		Integer todoId = Integer.valueOf(elements.get("id"));
		String content = elements.get("content");
		content = Utility.escapeScript(content);
		this.todoService.update(todoId, content);
		return "SUCCESS";
	}

}
