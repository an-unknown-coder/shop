package com.qf.controller;

import com.qf.dto.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.internet.MimeMessage;

@RestController
@RequestMapping("email")
public class EmailController {

    @Autowired
    private JavaMailSender sender;

    @Autowired
    private TemplateEngine templateEngine;

    @Value("${activateUrl}")
    private String activateUrl;

    /**
     * 邮件发送模板代码
     */
    @GetMapping("send/{addr}/{uuid}")
    public ResultBean sendEmail(@PathVariable String addr, @PathVariable String uuid) {
        MimeMessage message = sender.createMimeMessage();
        try {
            MimeMessageHelper mailMessage = new MimeMessageHelper(message, true);
            mailMessage.setSubject("请激活您的账号");
            Context context = new Context();
            context.setVariable("username", addr.substring(0, addr.lastIndexOf('@')));
            context.setVariable("url", activateUrl + uuid);
            String info = templateEngine.process("emailTemplate", context);
            mailMessage.setText(info, true);
            mailMessage.setFrom("1525426775@qq.com");
            mailMessage.setTo(addr);
            sender.send(message);
        } catch (Exception e) {
            return ResultBean.error("Fail to activate your account!");
        }
        return ResultBean.success("Successfully activate your account!");
    }
}
