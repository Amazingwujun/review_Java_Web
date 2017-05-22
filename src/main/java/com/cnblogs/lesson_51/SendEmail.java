package com.cnblogs.lesson_51;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.junit.Test;

import com.sun.mail.util.MailSSLSocketFactory;

public class SendEmail {
	private static String account;// 账户名
	private static String License_Key;// QQ邮箱第三方登录必须使用授权码
	private static String from;
	private static String host;
	private static String port;// ssl 端口
	private static String protocol;

	static {
		Properties props = new Properties();
		try {
			props.load(SendEmail.class.getClassLoader().getResourceAsStream("email.properties"));

			account = props.getProperty("e.account");
			License_Key = props.getProperty("e.License_Key");
			from = props.getProperty("e.from");
			host = props.getProperty("e.host");
			port = props.getProperty("e.port");
			protocol = props.getProperty("e.protocol");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// 用户名验证，实现abstract method getPasswordAuthentication()
	static class Myauthentication extends Authenticator {
		String u;
		String p;

		public Myauthentication(String u, String p) {
			this.u = u;
			this.p = p;
		}

		@Override
		protected PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(u, p);
		}
	}

	public void send() {
		Properties prop = new Properties();
		// 邮件协议
		prop.setProperty("mail.transport.protocol", protocol);
		// 服务器
		prop.setProperty("mail.smtp.host", host);
		// 端口
		prop.setProperty("mail.smtp.port", port);
		// 使用smtp身份验证
		prop.setProperty("mail.smtp.auth", "true");
		// 使用SSL
		// 开启安全协议
		MailSSLSocketFactory sf = null;
		try {
			sf = new MailSSLSocketFactory();
			sf.setTrustAllHosts(true);
		} catch (GeneralSecurityException e1) {
			e1.printStackTrace();
		}
		prop.put("mail.smtp.ssl.enable", "true");
		prop.put("mail.smtp.ssl.socketFactory", sf);

		Session session = Session.getDefaultInstance(prop, new Myauthentication(account, License_Key));
		session.setDebug(true);
		
		try {
			Message message = getEmail(session);
			Transport.send(message);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private Message getEmail(Session session) throws AddressException, MessagingException {
		MimeMessage msg = new MimeMessage(session);
		
		//邮件的来龙去脉
		msg.setFrom(new InternetAddress(from));
		msg.addRecipients(RecipientType.TO, new InternetAddress[] { new InternetAddress("amazingjun@sohu.com") });
		msg.setSentDate(new Date());
		
		//主题
		msg.setSubject("包含图片和附件的复杂邮件","utf8");
		
		//单个MIME类型建立
		//正文
		MimeBodyPart text = new MimeBodyPart();
		text.setContent("我的毕业照片：人生赢家<br/><img src='cid:aaa.jpg'>","text/html;charset=UTF-8");
		
		//图片
		MimeBodyPart img = new MimeBodyPart();
		//获取资源的URL
		URL imgPath = SendEmail.class.getClassLoader().getResource("IMG_4369.JPG");
		img.setDataHandler(new DataHandler(new FileDataSource(imgPath.getFile())));
		img.setContentID("aaa.jgp");
		
		/*MimeMultipart mp2 = new MimeMultipart();
		mp2.addBodyPart(text);
		mp2.addBodyPart(img);
		mp2.setSubType("related");*/
		
		//附件1
		MimeBodyPart attach1 = new MimeBodyPart();
		URL attach1Path = SendEmail.class.getClassLoader().getResource("config.zip");
		attach1.setDataHandler(new DataHandler(new FileDataSource(attach1Path.getFile())));
		attach1.setFileName("config.zip");
		
		//附件2
		MimeBodyPart attach2 = new MimeBodyPart();
		URL attach2Path = SendEmail.class.getClassLoader().getResource("db.properties");
		attach2.setDataHandler(new DataHandler(new FileDataSource(attach2Path.getFile())));
		attach2.setFileName("db.properties");
		
		MimeMultipart mp1 = new MimeMultipart();
		mp1.addBodyPart(text);
		mp1.addBodyPart(img);
		mp1.setSubType("related");
		
		MimeBodyPart content = new MimeBodyPart();
		content.setContent(mp1);
		
		MimeMultipart mp2 = new MimeMultipart();
		mp2.addBodyPart(attach1);
		mp2.addBodyPart(attach2);
		mp2.addBodyPart(content);
		mp2.setSubType("mixed");
		
		msg.setContent(mp2);
		
		return msg;
	}
	
	@Test
	public void datas(){
		assert 3>2;
		send();
	}

}
