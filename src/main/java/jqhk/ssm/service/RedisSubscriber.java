package jqhk.ssm.service;

import com.alibaba.fastjson.JSON;
import jqhk.ssm.Utility;
import jqhk.ssm.model.EmailModel;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


@Component
class AsyncTask {
    private MailSender sender;
    private MailProperties mailProperties;

    public AsyncTask(MailSender sender, MailProperties mailProperties) {
        this.sender = sender;
        this.mailProperties = mailProperties;
    }

    @Async
    public void sendMail(EmailModel email) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(mailProperties.getUsername());
        String title = email.getTitle();
        String address = email.getAddress();
        String content = email.getContent();
        mailMessage.setSubject(title);
        mailMessage.setTo(address);
        mailMessage.setText(content);
        // sender.send(mailMessage);
        Utility.log("异步发送邮件函数");
    }
}

@Service
public class RedisSubscriber extends MessageListenerAdapter {
    private RedisTemplate<String, String> redisTemplate;

    public RedisSubscriber(RedisTemplate<String, String> template) {
        this.redisTemplate = template;
    }

    // 接受到订阅的消息
    @Override
    public void onMessage(Message message, byte[] bytes) {
        // EmailModel email = (EmailModel) JSON.parse(message.toString());
        EmailModel email = JSON.parseObject(message.toString(), EmailModel.class);
        Utility.log("messageQueue email %s", email);
    }
}
