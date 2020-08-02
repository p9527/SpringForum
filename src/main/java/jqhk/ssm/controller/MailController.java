package jqhk.ssm.controller;

import com.alibaba.fastjson.JSON;
import jqhk.ssm.Utility;
import jqhk.ssm.model.EmailModel;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/mail")
public class MailController {
    private ChannelTopic mailTopic;
    private final RedisTemplate<String, String> templateNormal;

    public MailController(ChannelTopic mailTopic, RedisTemplate<String, String> templateNormal) {
        this.mailTopic = mailTopic;
        this.templateNormal = templateNormal;
    }

    @GetMapping("/index")
    public ModelAndView index() {
        return new ModelAndView("mail/index");
    }

    @ResponseBody
    @PostMapping("/send/async")
    public String sendAsync(String address, String title, String content) {
        EmailModel email = new EmailModel(address, title, content);
        String jsonEmail = JSON.toJSONString(email);
        templateNormal.convertAndSend(mailTopic.getTopic(), jsonEmail);
        return "发送成功";
    }
}
