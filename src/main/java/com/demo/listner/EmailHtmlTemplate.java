package com.demo.listner;

public class EmailHtmlTemplate {
    public static String getRegesitEmailContent(String url) {
        StringBuffer buffer = new StringBuffer(getStyleCss());
        buffer.append("<body>")
                .append("<div class='outdisplay'>")
                .append("<div style='margin-bottom:50px;margin-top: 40px;'>" +
                        "     <strong class='title'>You are on your way!</strong>" +
                        "     <strong class='title'> 差一点就完成了！</strong>" +
                        "     <strong class='title'>あと少しです！</strong>" +
                        "</div>")
                .append("<div style='margin-bottom: 30px;'>" +
                        "     <p class='content'>Please click the link below to authenticate your email.</p>" +
                        "     <p class='content'>请点击以下链接认证邮件。</p>" +
                        "     <p class='content'>以下のリンクをクリックしてメールアドレスを認証します。</p>" +
                        "</div>")
                .append("<div style='margin-bottom: 20px;'>" +
                        "     <a href=" + url + "><button class='confirmbutton'>Confirm/确认/認証</button></a>" +
                        "</div>")
                .append("<div class='linebottom'>" +
                        "     <div style='float: right;'>" +
                        "          <span style='color: rgb(143, 143, 143);'>2020</span>" +
                        "          <a href='http://www.baidu.com'><span style='color: blue;'>ugogo</span></a>" +
                        "     </div>" +
                        "</div>")
                .append("</div>")
                .append("</body>");
        return buffer.toString();
    }

    private static String getStyleCss() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("<style>")
                .append(".outdisplay{" +
                        "margin: 0 auto;" +
                        "width: 100%;" +
                        "max-width:600px;" +
                        "color:#366eed}")
                .append(".title{" +
                        "margin: 1px auto;" +
                        "font-size: 36px;" +
                        "display: block;}")
                .append(".linebottom{" +
                        "padding:20px 0px 20px 0px;" +
                        "line-height:20px;" +
                        "border-top: 2px solid rgba(189, 188, 188, 0.3);}")
                .append(".content{" +
                        "font-size: 15px;" +
                        "margin: 1px auto;}")
                .append(".confirmbutton{" +
                        "cursor: pointer;" +
                        "background-color:#366EED;" +
                        "border:1px solid #333333;" +
                        "border-color:#366EED;" +
                        "border-radius:4px;" +
                        "border-width:0px;" +
                        "color:#ffffff;" +
                        "display:inline-block;" +
                        "font-family:arial,helvetica,sans-serif;" +
                        "font-size:20px;" +
                        "font-weight:normal;" +
                        "letter-spacing:0px;" +
                        "line-height:40px;" +
                        "padding:10px 20px 10px 20px;" +
                        "text-align:center;" +
                        "text-decoration:none}")
                .append("</style>");
        return buffer.toString();
    }
}
