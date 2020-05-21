/**
 *
 */
package com.example.demo.login.domain.repository.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.login.domain.model.Prefectures;
import com.example.demo.login.domain.model.User;

/**
 * @author 佐々木亮
 *
 */
@Mapper
public interface UserMapper2 {

	// 登録用メソッド
	public boolean insert(User user);

	// 1件検索用メソッド
	public User selectOne(String userId);

	// 全件検索用メソッド
	public List<User> selectMany();

	// 1件更新用メソッド
	public boolean updateOne(User user);

	// 1件削除用メソッド
	public boolean deleteOne(String userId);

	// 都道府県情報全件取得
	public List<Prefectures> selectPrefectures();
}
