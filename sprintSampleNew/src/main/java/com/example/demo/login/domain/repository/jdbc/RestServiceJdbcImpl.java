/**
 *
 */
package com.example.demo.login.domain.repository.jdbc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.login.domain.model.User;
import com.example.demo.login.domain.repository.UserDao;
import com.example.demo.login.domain.service.RestService;

/**
 * @author 佐々木亮
 *
 */
@Transactional
@Service
public class RestServiceJdbcImpl implements RestService {

	@Autowired
	@Qualifier("UserDaoJdbcImpl")
	UserDao dao;

	// 1件登録用メソッド
	@Override
	public boolean insert(User user) {
		return false;
	}

	// 1件検索用メソッド
	@Override
	public User selectOne(String userId) {
		return null;
	}

	// 全件検索用メソッド
	@Override
	public List<User> selectMany() {
		return null;
	}

	// 1件更新用メソッド
	@Override
	public boolean update(User user) {
		return false;
	}

	// 1件削除用メソッド
	@Override
	public boolean delete(String userId) {
		return false;
	}

}
