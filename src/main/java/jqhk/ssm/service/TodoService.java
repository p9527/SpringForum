package jqhk.ssm.service;


import jqhk.ssm.mapper.TodoMapper;
import jqhk.ssm.model.TodoModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoService {
    private TodoMapper mapper;

    public TodoService (TodoMapper todoMapper) {
        this.mapper = todoMapper;
    }

    public List<TodoModel> all(String username) {
        return this.mapper.selectAllTodo(username);
    }

    public TodoModel add(String content, String username) {
        TodoModel t = new TodoModel();
        t.setContent(content);
        t.setUsername(username);
        this.mapper.insertTodo(t);
        return t;
    }

    public void delete(int id) {
        this.mapper.deleteTodo(id);
    }

    public void complete(int id) {
        this.mapper.completeTodo(id);
    }

    public void update(int id, String content) {
        TodoModel t = this.mapper.selectTodo(id);
        t.setContent(content);
        this.mapper.updateTodo(t);
    }
}

