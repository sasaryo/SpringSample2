/**
 *
 */
package com.example.demo.login.domain.model;

import java.util.Date;

import javax.validation.constraints.AssertFalse;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

/**
 * @author 佐々木亮
 *
 */
@Data
public class SignupForm {
	@NotBlank(groups = ValidGroup1.class)
	@Email(groups = ValidGroup2.class)
	private String userId;		// ユーザー ID
	@NotBlank(groups = ValidGroup1.class)
	@Length(min=4, max=100, groups = ValidGroup2.class)
	@Pattern(regexp = "^[a-zA-Z0-9]+$", groups = ValidGroup3.class)
	private String password;	// パスワード
	@NotBlank(groups = ValidGroup1.class)
	private String userName;	// ユーザー 名
	// ポイント：@ DateTimeFormat
	@NotNull(groups = ValidGroup1.class)
	@DateTimeFormat( pattern = "yyyy/MM/dd")
	private Date birthday;		// 誕生日
	@Min(value=20, groups = ValidGroup2.class)
	@Max(value=100, groups = ValidGroup2.class)
	private int age;			// 年齢
	@AssertFalse(groups = ValidGroup2.class)
	private boolean marriage;	// 結婚 ステータス
	@NotBlank(groups = ValidGroup1.class)
	private String prefectureName; // 都道府県名
}
