/**
 *
 */
package com.example.demo.login.domain.service;

import java.util.List;

import com.example.demo.login.domain.model.Prefectures;
import com.example.demo.login.domain.model.User;

/**
 * @author 佐々木亮
 *
 */
public interface RestService {
	// 1件登録用メソッド
	public boolean insert(User user);

	// 1件検索用メソッド
	public User selectOne(String userId);

	// 全件検索用メソッド
	public List<User> selectMany();

	// 1件更新用メソッド
	public boolean update(User user);

	// 1件削除用メソッド
	public boolean delete(String userId);

	// 都道府県情報全件検索
	public List<Prefectures> selectPrefectures();
}
