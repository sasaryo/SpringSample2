/**
 *
 */
package com.example.demo.login.domain.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

/**
 * @author 佐々木亮
 *
 */
@Data
public class SignupForm {
	private String userId;		// ユーザー ID
	private String password;	// パスワード
	private String userName;	// ユーザー 名
	// ポイント：@ DateTimeFormat
	@DateTimeFormat( pattern = "yyyy/MM/dd")
	private Date birthday;		// 誕生日
	private int age;			// 年齢
	private boolean marriage;	// 結婚 ステータス
}
