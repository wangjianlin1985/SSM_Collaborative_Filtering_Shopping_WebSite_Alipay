package com.webshoprsmex.util;

import java.util.Date;
import java.util.Properties;
import java.util.Random;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * 邮箱验证码工具类
 */
public class MailUtil {

	//发件人的邮箱和授权码，填写自己的邮箱
	private final String myEmailAccount = "511873822@qq.com";//发送的邮箱
	//填写自己的授权码
	private final String myEmailPassword = "sekduciozciicajf";
	
	private String receiveMailAccount = null ;
	
	private String info = null;
	
	// 信息内容
	public void setReceiveMailAccount(String receiveMailAccount) {
		this.receiveMailAccount = receiveMailAccount;
	}
	
	public void setInfo(String info) {
		this.info = info;
	}
	
	// 发件人邮箱的 SMTP 服务器地址, 必须准确, 不同邮件服务器地址不同, 一般(只是一般, 绝非绝对)格式为: smtp.xxx.com
	// 网易163邮箱的 SMTP 服务器地址为: smtp.163.com    腾讯: smtp.qq.com
	private String myEmailSMTPServer = "smtp.qq.com";
 
	public  void Send() throws Exception {
		// 1. 创建参数配置, 用于连接邮件服务器的参数配置
		Properties props = new Properties();                    // 参数配置
		props.setProperty("mail.transport.protocol", "smtp");   // 使用的协议（JavaMail规范要求）
		props.setProperty("mail.smtp.host", myEmailSMTPServer);   // 发件人的邮箱的 SMTP 服务器地址
		props.setProperty("mail.smtp.auth", "true");            // 需要请求认证
		// PS: 某些邮箱服务器要求 SMTP 连接需要使用 SSL 安全认证 (为了提高安全性, 邮箱支持SSL连接, 也可以自己开启),
		//     如果无法连接邮件服务器, 仔细查看控制台打印的 log, 如果有有类似 “连接失败, 要求 SSL 安全连接” 等错误,
		//     打开下面 /* ... */ 之间的注释代码, 开启 SSL 安全连接。
 
		// SMTP 服务器的端口 (非 SSL 连接的端口一般默认为 25, 可以不添加, 如果开启了 SSL 连接,
		//                  需要改为对应邮箱的 SMTP 服务器的端口, 具体可查看对应邮箱服务的帮助,
		//                  QQ邮箱的SMTP(SLL)端口为465或587, 其他邮箱自行去查看)
		final String smtpPort = "465";
		props.setProperty("mail.smtp.port", smtpPort);
		props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.setProperty("mail.smtp.socketFactory.fallback", "false");
		props.setProperty("mail.smtp.socketFactory.port", smtpPort);
 
		// 2. 根据配置创建会话对象, 用于和邮件服务器交互
		Session session = Session.getDefaultInstance(props);
		session.setDebug(true);                                 // 设置为debug模式, 可以查看详细的发送 log
 
		// 3. 创建一封邮件
		MimeMessage message = createMessage(session, myEmailAccount, receiveMailAccount,info);
 
		// 4. 根据 Session 获取邮件传输对象
		Transport transport = session.getTransport();
 
		// 5. 使用 邮箱账号 和 密码 连接邮件服务器, 这里认证的邮箱必须与 message 中的发件人邮箱一致, 否则报错
		// 
		//    PS_01: 成败的判断关键在此一句, 如果连接服务器失败, 都会在控制台输出相应失败原因的 log,
		//           仔细查看失败原因, 有些邮箱服务器会返回错误码或查看错误类型的链接, 根据给出的错误
		//           类型到对应邮件服务器的帮助网站上查看具体失败原因。
		//
		//    PS_02: 连接失败的原因通常为以下几点, 仔细检查代码:
		//           (1) 邮箱没有开启 SMTP 服务;
		//           (2) 邮箱密码错误, 例如某些邮箱开启了独立密码;
		//           (3) 邮箱服务器要求必须要使用 SSL 安全连接;
		//           (4) 请求过于频繁或其他原因, 被邮件服务器拒绝服务;
		//           (5) 如果以上几点都确定无误, 到邮件服务器网站查找帮助。
		//
		//    PS_03: 仔细看log, 认真看log, 看懂log, 错误原因都在log已说明。
		transport.connect(myEmailAccount, myEmailPassword);
 
		// 6. 发送邮件, 发到所有的收件地址, message.getAllRecipients() 获取到的是在创建邮件对象时添加的所有收件人, 抄送人, 密送人
		transport.sendMessage(message, message.getAllRecipients());
 
		// 7. 关闭连接
		transport.close();
	}
 
	/**
	 * 创建一封只包含文本的简单邮件
	 *
	 * @param session 和服务器交互的会话
	 * @param sendMail 发件人邮箱
	 * @param receiveMail 收件人邮箱
	 * @return
	 * @throws Exception
	 */
	public  MimeMessage createMessage(Session session, String sendMail, String receiveMail,String info) throws Exception {
		// 1. 创建一封邮件
		MimeMessage message = new MimeMessage(session);
		// 2. From: 发件人（昵称有广告嫌疑，避免被邮件服务器误认为是滥发广告以至返回失败，请修改昵称）
		message.setFrom(new InternetAddress(sendMail, "邮箱验证码【个性化购物商城推荐系统】", "UTF-8"));
		// 3. To: 收件人（可以增加多个收件人、抄送、密送）
		message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiveMail, "xx用户", "UTF-8"));
		// 4. Subject: 邮件主题（标题有广告嫌疑，避免被邮件服务器误认为是滥发广告以至返回失败，请修改标题）
		message.setSubject("验证码", "UTF-8");
		// 5. Content: 邮件正文（可以使用html标签）（内容有广告嫌疑，避免被邮件服务器误认为是滥发广告以至返回失败，请修改发送内容）
		message.setContent("【验证码】:"+info, "text/html;charset=UTF-8");
		// 6. 设置发件时间
		message.setSentDate(new Date());
		// 7. 保存设置
		message.saveChanges();
 
		return message;
	}
	
	public static void main(String[] args) {
		MailUtil sendEmail = new MailUtil();
		//设置要发送的邮箱,输入要发送的邮箱的账号
		sendEmail.setReceiveMailAccount("2708953427@qq.com");
		//创建10位发验证码
		Random random = new Random();
		String str="";
		for(int i=0;i<10;i++) {
			int n=random.nextInt(10);
			str+=n;
		}
		sendEmail.setInfo(str);
		try {
			sendEmail.Send();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	
}
