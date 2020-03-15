package com.demo.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class EmailTemplate implements Serializable {
    //发件人
    private String fromAddress;
    // 收件人(多个地址用逗号分隔)
    private String toAddress;
    // 抄送人(多个地址用逗号分隔)
    private String ccAddress;
    // 密送人(多个地址用逗号分隔)
    private String bccAddress;
    // 附件信息(暂不支持)
    //    private List<EmailAttachment> attachments;
    // 邮件主题
    private String subject;
    // 邮件的文本内容
    private String content;
}
