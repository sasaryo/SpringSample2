/**
 *
 */
package com.example.demo.login.domain.service.mybatis;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.login.domain.model.Prefectures;
import com.example.demo.login.domain.model.PurchaseHistory;
import com.example.demo.login.domain.model.User;
import com.example.demo.login.domain.repository.mybatis.UserMapper2;
import com.example.demo.login.domain.service.UserService;

/**
 * @author 佐々木亮
 *
 */
@Transactional
@Service("UserServiceMybatisImpl")
public class UserServiceMybatisImpl implements UserService {
    @Autowired
    UserMapper2 userMapper;

    @Override
    public boolean insert(User user) {
        //insert実行
        return userMapper.insert(user);
    }

    @Override
    public User selectOne(String userId) {
        //select実行
        return userMapper.selectOne(userId);
    }

    @Override
    public List<User> selectMany() {
        //select実行
        return userMapper.selectMany();
    }

    @Override
    public int count() {
    	return userMapper.selectCount();
    }

    @Override
    public boolean updateOne(User user) {
        //update実行
        return userMapper.updateOne(user);
    }

    @Override
    public boolean deleteOne(String userId) {
        //delete実行
        return userMapper.deleteOne(userId);
    }

    @Override
    public void userCsvOut() throws IOException {
    	// 全件検索
    	List<User> userList = userMapper.selectMany();

        try {

            //ファイル書き込みの準備
            File file = new File("sample.csv");
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);


            //取得件数分loop
            for (int i = 0; i < userList.size(); i++) {
            	String str = userList.get(i).getUserId() + ","
            			+ userList.get(i).getPassword() + ","
            			+ userList.get(i).getUserName() + ","
            			+ userList.get(i).getBirthday().toString() + ","
            			+ userList.get(i).getAge() + ","
            			+ userList.get(i).isMarriage() + ","
            			+ userList.get(i).getPrefectureName() + ","
            			+ userList.get(i).getRole();

            	// ファイルに書き込み＆開業
            	bw.write(str);
            	bw.newLine();
            }

            //強制的に書き込み＆ファイルクローズ
            bw.flush();
            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException(e);
        }
    }

    @Override
    // サーバーに保存されているファイルを取得して、byte配列に変換する
    public byte[] getFile(String fileName) throws IOException {
    	// ファイルシステム(デフォルト)の取得
    	FileSystem fs = FileSystems.getDefault();

    	// ファイル取得
    	Path p = fs.getPath(fileName);

    	// ファイルをbyte配列に変換
    	byte[] bytes = Files.readAllBytes(p);

    	return bytes;
    }

    @Override
    public List<Prefectures> selectPrefectures() {
    	// 都道府県情報全件取得実行
    	return userMapper.selectPrefectures();
    }

    @Override
	// 購入履歴検索用メソッド
	public List<PurchaseHistory> selectPurchaseHistory(String userId) {
    	return userMapper.selectPurchaseHistory(userId);
    }

    @Override
	// 購入履歴登録用メソッド
	public boolean insertPurchaseHistory(PurchaseHistory purchaseHistory) {
    	return userMapper.insertPurchaseHistory(purchaseHistory);
    }

    @Override
	// 購入履歴金額合計
	public int selectSumPurchaseHistoryPrice(String userId) {
    	return userMapper.selectSumPurchaseHistoryPrice(userId);
    }

    @Override
	// 購入履歴1件検索用メソッド
	public PurchaseHistory selectOnePurchaseHistory(int id) {
    	return userMapper.selectOnePurchaseHistory(id);
    }

    @Override
    // 購入履歴更新用メソッド
    public boolean updatePurchaseHistory(PurchaseHistory purchaseHistory) {
    	return userMapper.updatePurchaseHistory(purchaseHistory);
    }

    @Override
    // 購入履歴削除用メソッド
    public boolean deletePurchaseHistory(int id) {
    	return userMapper.deletePurchaseHistory(id);
    }
}
