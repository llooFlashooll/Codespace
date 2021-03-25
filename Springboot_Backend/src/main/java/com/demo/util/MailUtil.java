package com.demo.util;



import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**/

public class MailUtil implements Runnable {
    private String email;// 收件人邮箱
    private String code;// 激活码
    private String subject;//邮件主题
    private String content;//邮件嫩容


    public MailUtil(String email, String code,String subject) {
        this.email = email;
        this.code = code;
        this.subject=subject;
        if(subject.equals("密码找回")){
            this.content="<html><head></head><body><h1>这是一封密码找回邮件</h1><h3>密码为" + code
                        + "</h3></body></html>";
        }else{
            this.content="<html><head></head><body><h1>这是一封激活邮件</h1><h3>验证码为" + code
                    + "</h3></body></html>";
        }
    }

    public void run() {
        // 1.创建连接对象javax.mail.Session
        // 2.创建邮件对象 javax.mail.Message
        // 3.发送一封激活邮件
        String from = "wyq12503784682020@163.com";// 发件人电子邮箱
      //  String host = "smtp.qq.com"; // 指定发送邮件的主机smtp.qq.com(QQ)|smtp.163.com(网易)
        String host="smtp.163.com";

        Properties properties = System.getProperties();// 获取系统属性

       // properties.setProperty("mail.smtp.host", host);// 设置邮件服务器
        //properties.setProperty("mail.smtp.auth", "true");// 打开认证
       // properties.setProperty("mail.smtp.port", "587");

        properties.put("mail.smtp.host", "smtp.163.com");
        //SSLSocketFactory类的端口
        properties.put("mail.smtp.socketFactory.port", "465");
        //SSLSocketFactory类
        properties.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.auth", "true");
        //网易提供的ssl加密端口,QQ邮箱也是该端口
        properties.put("mail.smtp.port", "465");

        try {
            //QQ邮箱需要下面这段代码，163邮箱不需要
//            MailSSLSocketFactory sf = new MailSSLSocketFactory();
//            sf.setTrustAllHosts(true);
//            properties.put("mail.smtp.ssl.enable", "true");
//            properties.put("mail.smtp.ssl.socketFactory", sf);


            // 1.获取默认session对象
            Session session = Session.getDefaultInstance(properties, new Authenticator() {
                public PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("wyq12503784682020@163.com", "TLNOFJSYQPYWISOA"); // 发件人邮箱账号、授权码
                }
            });

            // 2.创建邮件对象
            Message message = new MimeMessage(session);
            // 2.1设置发件人
            message.setFrom(new InternetAddress(from));
            // 2.2设置接收人
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            // 2.3设置邮件主题
            message.setSubject(this.subject);
            // 2.4设置邮件内容
            String content = this.content;
            message.setContent(content, "text/html;charset=UTF-8");
            // 3.发送邮件
            Transport.send(message);
            System.out.println("邮件成功发送!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("end");
    }
}