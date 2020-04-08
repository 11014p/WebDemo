package com.demo.listner;

import com.demo.listner.event.PwdFindEvent;
import com.demo.model.AccountFindpwdRecord;
import com.demo.model.EmailTemplate;
import com.demo.utils.SendMailUtil;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class PwdFindEmailSenderListner implements ApplicationListener<PwdFindEvent> {
    private static final Logger logger = LoggerFactory.getLogger(PwdFindEmailSenderListner.class);
    @SneakyThrows
    @Override
    public void onApplicationEvent(PwdFindEvent pwdFindEvent) {
        AccountFindpwdRecord record= (AccountFindpwdRecord) pwdFindEvent.getSource();
        EmailTemplate template = new EmailTemplate();
        template.setToAddress(record.getAccountEmail());
        template.setSubject("密码重置");
        String url = "http://localhost/#/password/reset?myToken=" + record.getToken();
        template.setContent(EmailHtmlTemplate.getRegesitEmailContent(url));
        SendMailUtil.sendEmail(template);
    }
}
