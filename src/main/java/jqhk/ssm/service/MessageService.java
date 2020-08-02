package jqhk.ssm.service;


import jqhk.ssm.mapper.MessageMapper;
import jqhk.ssm.model.MessageModel;
import jqhk.ssm.model.MessageType;
import jqhk.ssm.model.TodoModel;
import org.apache.catalina.users.MemoryRole;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {
    private MessageMapper mapper;

    public MessageService(MessageMapper messageMapper) {
        this.mapper = messageMapper;
    }

    public List<MessageModel> readAll(Integer readId) {
        return this.mapper.selectReadMessage(readId);
    }

    public List<MessageModel> notReadAll(Integer readId) {
        return this.mapper.selectNotReadMessage(readId);
    }

    public MessageModel add(Integer senderId, Integer readerId, Integer topicId) {
        MessageModel t = new MessageModel();
        t.setReaderId(readerId);
        t.setSenderId(senderId);
        t.setTopicId(topicId);
        Long time = System.currentTimeMillis() / 1000L;
        t.setCreatedTime(time);
        t.setType(MessageType.reply);
        this.mapper.insertMessage(t);
        return t;
    }

    public void readAll(List<MessageModel> messages) {
        for (MessageModel m : messages) {
            this.read(m);
        }
    }

    public void read(MessageModel message) {
        this.mapper.readMessage(message);
    }

}

