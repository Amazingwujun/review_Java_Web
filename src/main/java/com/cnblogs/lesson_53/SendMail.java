package com.cnblogs.lesson_53;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.cnblogs.lesson_51.SendEmail;
import com.sun.mail.util.MailSSLSocketFactory;

public class SendMail implements Runnable {
	private User user;// 用户

	private static String account;// 账户名
	private static String License_Key;// QQ邮箱第三方登录必须使用授权码
	private static String from;
	private static String host;
	private static String port;// ssl 端口
	private static String protocol;

	public SendMail(User user) {
		this.user = user;
	}
	
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

	static class MyAuthticator extends Authenticator {
		private String u;
		private String p;

		public MyAuthticator(String u, String p) {
			this.u = u;
			this.p = p;
		}

		@Override
		protected PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(u, p);
		}

	}


	@Override
	public void run() {
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

		Session session = Session.getDefaultInstance(prop, new MyAuthticator(account, License_Key));

		try {
			Message message = getMimeMessage(session, user);
			Transport.send(message);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private Message getMimeMessage(Session session, User user2) throws MessagingException {
		MimeMessage msg = new MimeMessage(session);

		msg.setSubject("用户注册邮件", "utf8");
		msg.addFrom(new InternetAddress[] { new InternetAddress(from) });
		msg.addRecipients(RecipientType.TO, new InternetAddress[] { new InternetAddress(user2.getEmail()) });

		MimeBodyPart content = new MimeBodyPart();
		String info = "恭喜您注册成功，您的用户名：" + user.getUsername() + ",您的密码：" + user.getPassword() + "，请妥善保管，如有问题请联系网站客服!!";
		content.setContent(info, "text/html;charset=utf8");

		MimeMultipart mp = new MimeMultipart();
		mp.addBodyPart(content);

		msg.setContent(mp);

		return msg;
	}

}
