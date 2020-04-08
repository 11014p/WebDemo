package com.demo.listner;

import com.demo.listner.event.RegesitSuccessEvent;
import com.demo.model.Account;
import com.demo.model.EmailTemplate;
import com.demo.utils.EncryptUtil;
import com.demo.utils.SendMailUtil;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class RegesitEmailSenderListner implements ApplicationListener<RegesitSuccessEvent> {
    private static final Logger logger = LoggerFactory.getLogger(RegesitEmailSenderListner.class);
    @SneakyThrows
    @Override
    public void onApplicationEvent(RegesitSuccessEvent regesitSuccessEvent) {
        Account account= (Account) regesitSuccessEvent.getSource();
        EmailTemplate template = new EmailTemplate();
        template.setToAddress(account.getEmail());
        template.setSubject("账号激活");
        String url = "http://localhost:9621/#/active?email=" + account.getEmail()
                + "&myToken=" + EncryptUtil.getToken(new String[]{account.getName(), account.getEmail(), account.getPassword(), account.getUpdateTime().toString()});
        template.setContent(EmailHtmlTemplate.getRegesitEmailContent(url));
        SendMailUtil.sendEmail(template);
        logger.info("account active email send success.email:{}",account.getEmail());
    }
}
