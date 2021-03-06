/**
 *
 */
package com.example.demo.login.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.login.domain.model.Prefectures;
import com.example.demo.login.domain.model.User;
import com.example.demo.login.domain.service.RestService;
import com.example.demo.login.domain.service.RestServiceMail;

/**
 * @author 佐々木亮
 *
 */
@RestController
public class UserRestController {

	@Autowired
	@Qualifier("RestServiceMybatisImpl")
	RestService service;

	@Autowired
	RestServiceMail serviceMail;

	// ユーザー全件取得
	@GetMapping("/rest/get")
	public List<User> getUserMany() {
		// ユーザー全件取得
		return service.selectMany();
	}

	// ユーザー1件取得
	@GetMapping("/rest/get/{id:.+}")
	public User getUserOne(@PathVariable("id") String userId) {
		// ユーザー1件取得
		return service.selectOne(userId);
	}

	@PostMapping("/rest/insert")
	public String postUserOne(@RequestBody User user) {
		// ユーザーを1件登録
		boolean result = service.insert(user);

		String str = "";

		if(result == true) {
			str = "{\"result\":\"ok\"}";
		} else {
			str = "{\"result\":\"error\"}";
		}

		// 結果用の文字列をリターン
		return str;
	}

	@PutMapping("/rest/update")
	public String putUserOne(@RequestBody User user) {
		// ユーザーを1件更新
		boolean result = service.update(user);

		String str = "";

		if (result == true) {
			str = "{\"result\":\"ok\"}";
		} else {
			str = "{\"result\":\"error\"}";
		}

		// 結果用の文字列をリターン
		return str;
	}

	@DeleteMapping("/rest/delete/{id:.+}")
	public String deleteUserOne(@PathVariable("id") String userId) {
		// ユーザーを1件削除
		boolean result = service.delete(userId);

		String str = "";

		if (result == true) {
			str = "{\"result\":\"ok\"}";
		} else {
			str = "{\"result\":\"error\"}";
		}

		// 結果用の文字列リターン
		return str;
	}

	@GetMapping("/rest/getPrefectures")
	public List<Prefectures> selectGetPrefectures() {
		// 都道府県全件取得を実施
		return service.selectPrefectures();
	}

	@PostMapping("/rest/sendMail/{id:.+}")
	public String sendMailOne(@PathVariable("id") String userId) {
		// 1宛先にメール送信実施
		boolean result = serviceMail.sendMail(userId);

		String str;

		if (result == true) {
			str = "{\"result\":\"ok\"}";
		} else {
			str = "{\"result\":\"error\"}";
		}

		// 結果用の文字列をリターン
		return str;
	}
}
