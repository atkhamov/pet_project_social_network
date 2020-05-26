package com.caveofprogramming.socialnetwork.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;

@Service
public class EmailService {

    private TemplateEngine templateEngine;

    @Autowired
    private JavaMailSender mailSender;

    @Value("${site.url}")
    private String url;


    private void send(MimeMessagePreparator preparator){
        mailSender.send(preparator);
    }

    @Autowired
    public EmailService(TemplateEngine templateEngine){

        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();

        templateResolver.setPrefix("mail/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode("HTML5");
        templateResolver.setCacheable(false);
        templateEngine.setTemplateResolver(templateResolver);

        this.templateEngine = templateEngine;
    }

    //The following @Async annotation helps us to run the method sendVerificationEmail() asynchronously
    @Async
    public void sendVerificationEmail(String emailAddress, String token) {

        Context context = new Context();
        context.setVariable("token", token);
        context.setVariable("url", url);

        String emailContents = templateEngine.process("verifyemail", context);

        MimeMessagePreparator preparator = new MimeMessagePreparator() {
            @Override
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper message = new MimeMessageHelper(mimeMessage);

                message.setTo(emailAddress);
                message.setFrom(new InternetAddress("8abac927a6-4315f8@inbox.mailtrap.io"));
                message.setSubject("Please, verify your email address");
                message.setSentDate(new Date());

                message.setText(emailContents, true);
            }
        };
        send(preparator);
    }
}
