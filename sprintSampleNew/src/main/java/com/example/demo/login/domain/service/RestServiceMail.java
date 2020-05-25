/**
 *
 */
package com.example.demo.login.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

/**
 * @author 佐々木亮
 *
 */
@Service
public class RestServiceMail {
	@Autowired
	private MailSender mailSender;

	// メール送信用メソッド
	public boolean sendMail(String userId) {
		SimpleMailMessage msg = new SimpleMailMessage();

		msg.setFrom("Spring.boot.test@mail.com");
		msg.setTo("<" + userId + ">");
		msg.setSubject("テストメール"); //タイトルの設定
		msg.setText("テストメール(本文)"); //本文の設定

		try {
			mailSender.send(msg);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
		return true;
	}
}
