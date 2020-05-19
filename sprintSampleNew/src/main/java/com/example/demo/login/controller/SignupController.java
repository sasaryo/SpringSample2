/**
 *
 */
package com.example.demo.login.controller;

import java.util.LinkedHashMap;
import java. util. Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.login.domain.model.GroupOrder;
import com.example.demo.login.domain.model.SignupForm;
import com.example.demo.login.domain.model.User;
import com.example.demo.login.domain.service.UserService;

/**
 * @author 佐々木亮
 *
 */
@Controller
public class SignupController {
	@Autowired
	private UserService userService;

	//ポイント 1 : ラジオボタンの実装
	private Map<String, String>radioMarriage;

	//ラジオボタンの初期化メソッド
	private Map<String, String>initRadioMarrige() {
		Map<String, String>radio = new LinkedHashMap<>();

		//既婚、未婚をMapに格納
		radio.put("既婚", "true");
		radio.put("未婚", "false");
		return radio;
	}

	// ポイント1 : @ModelAttribute
	//ユーザー登録画面のGET用コントローラー.
	@GetMapping("/signup")
	public String getSignUp(@ModelAttribute SignupForm form, BindingResult bindingResult, Model model) {
		//ラジオボタンの初期化メソッド呼び出し
		radioMarriage = initRadioMarrige();
		//ラジオボタン用のMapをModelに登録
		model.addAttribute("radioMarriage", radioMarriage);
		//signup.htmlに画面遷移
		return "login/signup";
	}

	//ユーザー登録画面のPOST用コントローラー.
	// ポイント2 : データバインドの結果の受け取り
	@PostMapping("/signup")
	public String postSignUp(@ModelAttribute @Validated(GroupOrder.class) SignupForm form, BindingResult bindingResult, Model model) {
		// ポイント3 : データバインド失敗の場合
		// 入力チェックに引っかかった場合、ユーザ登録画面に戻る
		if (bindingResult.hasErrors()) {
			// GETリクエスト用のメソッドを呼び出して、ユーザー登録画面に戻ります。
			return getSignUp(form, bindingResult, model);
		}

		// formの内容をコンソールに出して確認する
		System.out.println(form);

		// insert用変数
		User user = new User();

		user.setUserId(form.getUserId());		// ユーザーID
		user.setPassword(form.getPassword());	// パスワード
		user.setUserName(form.getUserName());	// ユーザー名
		user.setBirthday(form.getBirthday());	// 誕生日
		user.setAge(form.getAge());				// 年齢
		user.setMarriage(form.isMarriage());	// 結婚ステータス
		user.setRole("ROLE_GENERAL");			// ロール(一般)

		// ユーザー登録処理
		boolean result = userService.insert(user);

		// ユーザー登録結果の判定
		if (result == true) {
			System.out.println("insert成功");
		}
		else {
			System.out.println("insert失敗");
		}

		// login.htmlにリダイレクト
		return "redirect:/login";
	}

	@ExceptionHandler(DataAccessException.class)
	public String dataAccessExceptionHandler(DataAccessException e, Model model) {
		// 例外クラスのメッセージをModelに登録
		model.addAttribute("error", "内部サーバーエラー(DB) : ExceptionHandler");

		// 例外クラスのメッセージをModelに登録
		model.addAttribute("message", "SignupControllerでDataAccessExceptionが発生しました");

		// HTTPのエラーコード(500)をModelに登録
		model.addAttribute("status", HttpStatus.INTERNAL_SERVER_ERROR);

		return "error";
	}

	@ExceptionHandler(Exception.class)
	public String exceptionHandler(Exception e, Model model) {
		// 例外クラスのメッセージをModelに登録
		model.addAttribute("error", "内部サーバーエラー : ExceptionHandler");

		// 例外クラスのメッセージをModelに登録
		model.addAttribute("message", "SignupControllerでExceptionが発生しました");

		// HTTPのエラーコード(500)をModelに登録
		model.addAttribute("status", HttpStatus.INTERNAL_SERVER_ERROR);

		return "error";
	}
}
