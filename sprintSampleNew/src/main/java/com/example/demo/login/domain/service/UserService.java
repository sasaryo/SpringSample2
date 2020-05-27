/**
 *
 */
package com.example.demo.login.domain.service;

import java.io.IOException;
import java.util.List;

import com.example.demo.login.domain.model.Prefectures;
import com.example.demo.login.domain.model.PurchaseHistory;
import com.example.demo.login.domain.model.User;

/**
 * @author 佐々木亮
 *
 */
public interface UserService {
	// 1件登録用メソッド
	public boolean insert(User user);

	// 1件検索用メソッド
	public User selectOne(String userId);

	// 全件検索用メソッド
	public List<User> selectMany();

	// カウント用メソッド
	public int count();

	// 1件更新用メソッド
	public boolean updateOne(User user);

	// 1件削除用メソッド
	public boolean deleteOne(String userId);

	// csv生成処理
	public void userCsvOut() throws IOException;

	// サーバーに保存されているファイルを取得して、byte配列に変換する
	public byte[] getFile(String fileName) throws IOException;

	// 都道府県情報全件検索
	public List<Prefectures> selectPrefectures();

	// 購入履歴検索用メソッド
	public List<PurchaseHistory> selectPurchaseHistory(String userId);

	// 購入履歴登録用メソッド
	public boolean insertPurchaseHistory(PurchaseHistory purchaseHistory);

	// 購入履歴金額合計
	public int selectSumPurchaseHistoryPrice(String userId);

	// 購入履歴更新用メソッド
	public boolean updatePurchaseHistory(PurchaseHistory purchaseHistory);

	// 購入履歴削除用メソッド
	public boolean deletePurchaseHistory(int id);
}
