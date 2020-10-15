package com.dzk.web.service.mail;

import com.dzk.common.utils.PropertiesLoader;
import com.dzk.web.entity.Mail;
import com.dzk.web.entity.Register;
import com.sun.mail.util.MailSSLSocketFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.security.GeneralSecurityException;
import java.util.Properties;

/**
 * @author dzk
 * @date 2020/7/8 17:27
 * @description  用于发送验证信息到 用户邮箱  如注册时
 */
@Service
@Scope(value = WebApplicationContext.SCOPE_REQUEST,proxyMode = ScopedProxyMode.TARGET_CLASS)//为每一个请求创建一个bean
/*proxyMode是必要的，因为在Application Context实例化的时候，并没有实际的请求，Spring将会创建一个代理来进行注入，并在request需要的时候进行实例化。*/
public class SendMail  extends Thread{
    //加载属性文件
    private PropertiesLoader loader=new PropertiesLoader("applicationProperties.properties");
    private String from =loader.getProperty("mail.from");//发件人信息
    private String recipient=loader.getProperty("mail.recipient");//发件人邮箱
    private String password=loader.getProperty("mail.code");//邮箱密码 --16位授权码
    private String host=loader.getProperty("mail.host");//邮件发送的服务器
    private Mail mail;//邮箱bean
    public void setRegister(Mail mail) {
        this.mail = mail;
    }

    @Override
    public void run() {

        try {
            Properties properties = new Properties();
            properties.setProperty("mail.host", "smtp.qq.com");
            properties.setProperty("mail.transport.protocol", "smtp");
            properties.setProperty("mail.smtp.auth", "true");
            MailSSLSocketFactory sf = null;//QQ存在一个特性设置SSL加密
            try {
                sf=new MailSSLSocketFactory();
            } catch (GeneralSecurityException e) {
                e.printStackTrace();
            }
            sf.setTrustAllHosts(true);
            properties.put("mail.smtp.ssl.enable", "true");
            properties.put("mail.smtp.ssl.socketFactory", sf);

            //创建一个session对象
            Session session = Session.getDefaultInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(recipient,password);
                }
            });
            System.out.println(mail+"----------------------------------");
            //开启debug模式
            session.setDebug(true);

            //获取连接对象
            Transport transport = null;
            try {
                transport = session.getTransport();
            } catch (NoSuchProviderException e) {
                e.printStackTrace();
            }
            //连接服务器
            transport.connect(host,from,password);

            //创建一个邮件发送对象
            MimeMessage mimeMessage = new MimeMessage(session);

            //邮件发送人
            mimeMessage.setFrom(new InternetAddress(recipient));

            //邮件接收人
            mimeMessage.setRecipient(Message.RecipientType.TO,new InternetAddress(mail.getMail()));

            //邮件标题
            mimeMessage.setSubject(mail.getTitle());

            Multipart multipart=new MimeMultipart();// MiniMultipart类是一个容器类，包含MimeBodyPart类型的对象
            // 创建一个包含HTML内容的MimeBodyPart
            BodyPart html=new MimeBodyPart();
            String message=mail.getSendMessage();
            html.setContent(message,"text/html; charset=utf-8");
            multipart.addBodyPart(html);
            mimeMessage.setContent(multipart);// 将MiniMultipart对象设置为邮件内容
            /*
            //邮件内容  此处是纯文本内容
            mimeMessage.setContent("您好!欢迎您在"+loader.getProperty("productName")+"上注册账户!\n"+"您的用户名为:"+register.getUsername()+"\n"+"您的账户激活验证码为:"+register.getActivationCode(),"text/html;charset=UTF-8");
            */

            //发送邮件
            transport.sendMessage(mimeMessage,mimeMessage.getAllRecipients());

            transport.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
