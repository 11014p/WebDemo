package com.demo.utils;

import com.demo.model.EmailTemplate;
import com.google.common.base.Joiner;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class SendMail extends Thread {

    //用于给用户发送邮件的邮箱
    private String emailFrom = "345303441@qq.com";
    //邮箱授权码(修改密码会导致授权码过期)
    private String authKey = "doljblzhsullcagc";
    //发送邮件的服务器地址
    private String smtpHost = "smtp.qq.com";

    private EmailTemplate emailTemplate;

    public SendMail(EmailTemplate emailTemplate) {
        this.emailTemplate = emailTemplate;
    }

    //重写run方法的实现，在run方法中发送邮件给指定的用户
    @Override
    public void run() {
        try {
            Properties prop = new Properties();
            prop.setProperty("mail.smtp.host", smtpHost);
            prop.setProperty("mail.transport.protocol", "smtp");
            prop.setProperty("mail.smtp.auth", "true");
//
//            // 关于QQ邮箱，还要设置SSL加密，加上以下代码即可
//            MailSSLSocketFactory sf = new MailSSLSocketFactory();
//            sf.setTrustAllHosts(true);
//            prop.put("mail.smtp.ssl.enable", "true");
//            prop.put("mail.smtp.ssl.socketFactory", sf);

            //1、创建定义整个应用程序所需的环境信息的 Session 对象
            Session session = Session.getDefaultInstance(prop, new Authenticator() {
                public PasswordAuthentication getPasswordAuthentication() {
                    //发件人邮件用户名、授权码
                    return new PasswordAuthentication(emailFrom, authKey);
                }
            });

            //开启Session的debug模式，这样就可以查看到程序发送Email的运行状态
//            session.setDebug(true);

            //2、通过session得到transport对象
            Transport ts = session.getTransport();

            //3、使用邮箱的用户名和授权码连上邮件服务器
            ts.connect(smtpHost, emailFrom, authKey);

            //4、创建邮件
            MimeMessage message = new MimeMessage(session);
            //发件人
            message.setFrom(new InternetAddress(emailFrom));
            //收件人
            if (emailTemplate.getToAddress() != null) {
                message.setRecipients(Message.RecipientType.TO,
                        InternetAddress.parse(emailTemplate.getToAddress()));
            }
            //抄送人
            if (emailTemplate.getCcAddress() != null) {
                message.setRecipients(Message.RecipientType.CC,
                        InternetAddress.parse(emailTemplate.getCcAddress()));
            }
            //密送人
            if (emailTemplate.getBccAddress() != null) {
                message.setRecipients(Message.RecipientType.BCC,
                        InternetAddress.parse(emailTemplate.getBccAddress()));
            }
            //邮件的标题
            message.setSubject(emailTemplate.getSubject(),"UTF-8");

            message.setContent(emailTemplate.getContent(), "text/html;charset=UTF-8");
            message.saveChanges();

            //发送邮件
            ts.sendMessage(message, message.getAllRecipients());
            ts.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
