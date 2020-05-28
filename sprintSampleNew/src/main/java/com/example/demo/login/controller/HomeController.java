/**
 *
 */
package com.example.demo.login.controller;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.login.domain.model.GroupOrder;
import com.example.demo.login.domain.model.Prefectures;
import com.example.demo.login.domain.model.PurchaseHistory;
import com.example.demo.login.domain.model.PurchaseHistoryForm;
import com.example.demo.login.domain.model.SignupForm;
import com.example.demo.login.domain.model.User;
import com.example.demo.login.domain.service.RestServiceMail;
import com.example.demo.login.domain.service.UserService;

/**
 * @author 佐々木亮
 *
 */
@Controller
public class HomeController {
    @Autowired
    UserService userService;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    RestServiceMail restServiceMail;

    // 結婚ステータスのラジオボタン用変数
    private Map<String, String>radioMarriage;

    // ラジオボタンの初期化メソッド
    private Map<String, String>initRadioMarriage() {
    	Map<String, String>radio = new LinkedHashMap<>();

    	// 既婚、未婚をMapに格納
    	radio.put("既婚", "true");
    	radio.put("未婚", "false");

    	return radio;
    }

	// プルダウンリストの実装
	private Map<String, String>pulldownPrefecture;

	// プルダウンリストの初期化メソッド
	private Map<String, String>intPulldownPrefecture() {
		Map<String, String>pulldown = new LinkedHashMap<>();

		List<Prefectures> prefectures = userService.selectPrefectures();

		// DBから取得した都道府県情報をMapに格納
		for (int i = 0; i < prefectures.size(); i++) {
			pulldown.put(prefectures.get(i).getPrefectureId(), prefectures.get(i).getPrefectureName());
		}
		return pulldown;
	}

    //ユーザー一覧画面のGET用メソッド.
    @GetMapping("/home")
    public String getHome(Model model) {

        //コンテンツ部分にユーザー詳細を表示するための文字列を登録
        model.addAttribute("contents", "login/home :: home_contents");

        return "login/homeLayout";
    }

    // ユーザー一覧画面のGET用メソッド
    @GetMapping("/userList")
    public String getUserList(Model model) {
    	// コンテンツ部分にユーザー一覧を表示させるもの文字列を登録
    	model.addAttribute("contents", "login/userList::userList_contents");

    	// ユーザ一覧の生成
    	List<User> userList = userService.selectMany();

    	// Modelにユーザーリストを登録
    	model.addAttribute("userList", userList);

    	// データ件数を取得
    	int count = userService.count();
    	model.addAttribute("userListCount", count);

    	return "login/homeLayout";
    }

    // ユーザー一覧画面のPostメソッド
    @GetMapping("/userList/{id:.+}")
    public String postUserListMailSend(Model model, @PathVariable("id")String userId) {
    	// ユーザーID確認(デバッグ)
    	System.out.println("userId = " + userId);

    	// メール送信実施
    	boolean result = restServiceMail.sendMail(userId);

    	if(result == true) {
    		model.addAttribute("result", "メール送信成功");
    	}
    	else {
    		model.addAttribute("result", "メール送信失敗");
    	}

    	// ユーザー一覧画面を表示
    	return getUserList(model);
    }

    // ユーザー詳細画面のGET用メソッド
    @GetMapping("/userDetail/{id:.+}")
    public String getUserDetail(@ModelAttribute SignupForm form, Model model, @PathVariable("id")String userId) {
    	// ユーザーID確認（デバッグ）
    	System.out.println("userId = " + userId);

    	// コンテンツ部分にユーザー詳細を表示するための文字列を登録
    	model.addAttribute("contents", "login/userDetail::userDetail_contents");

    	//ラジオボタン、プルダウンの初期化メソッド呼び出し
    	radioMarriage = initRadioMarriage();
    	pulldownPrefecture = intPulldownPrefecture();

    	// ラジオボタン、プルダウン用のMapをModelに登録
    	model.addAttribute("radioMarriage", radioMarriage);
    	model.addAttribute("pulldownPrefecture", pulldownPrefecture);

    	// ユーザーIDのチェック
    	if (userId != null && userId.length() > 0) {
    		// ユーザー情報を取得
    		User user = userService.selectOne(userId);

    		// Userクラスをフォームクラスに変換
    		form.setUserId(user.getUserId());					// ユーザーID
    		form.setUserName(user.getUserName());				// ユーザー名
    		form.setBirthday(user.getBirthday());				// 誕生日
    		form.setAge(user.getAge());							// 年齢
    		form.setMarriage(user.isMarriage());				// 結婚ステータス
    		form.setPrefectureName(user.getPrefectureName());	// 都道府県

    		// Modelに登録
    		model.addAttribute("signupForm", form);
    	}

    	return "login/homeLayout";
    }

    // ユーザー更新用処理
    @PostMapping(value = "/userDetail", params = "update")
    public String postUserDetailUpdate(@ModelAttribute SignupForm form, Model model) {

    	System.out.println("更新ボタンの処理");

    	// パスワード暗号化
		String password = passwordEncoder.encode(form.getPassword());

    	// Userインスタンスの生成
    	User user = new User();

    	// フォームクラスをUserクラスに変換
    	user.setUserId(form.getUserId());
    	user.setPassword(password);
    	user.setUserName(form.getUserName());
    	user.setBirthday(form.getBirthday());
    	user.setAge(form.getAge());
    	user.setMarriage(form.isMarriage());
    	user.setPrefectureName(form.getPrefectureName());

    	try {
    		// 更新実行
    		boolean result = userService.updateOne(user);

    		if (result == true) {
    			model.addAttribute("result", "更新成功");
    		}
    		else {
    			model.addAttribute("result", "更新失敗");
    		}

    	} catch(DataAccessException e) {
    		model.addAttribute("result", "更新失敗(トランザクションテスト)");
    	}
    	// ユーザー一覧画面を表示
    	return getUserList(model);
    }

    @PostMapping(value = "/userDetail", params = "delete")
    public String postUserDetailDelete(@ModelAttribute SignupForm form, Model model) {
    	System.out.println("削除ボタンの処理");

    	// 削除実行
    	boolean result = userService.deleteOne(form.getUserId());

    	if(result == true) {
    		model.addAttribute("result", "削除成功");
    	}
    	else {
    		model.addAttribute("result", "削除失敗");
    	}

    	// ユーザー一覧画面を表示
    	return getUserList(model);
    }

    //ログアウト用メソッド.
    @PostMapping("/logout")
    public String postLogout() {

        //ログイン画面にリダイレクト
        return "redirect:/login";
    }

    // ユーザー一覧のCSV出力用メソッド
    @GetMapping("/userList/csv")
    public ResponseEntity<byte[]> getUserListCsv(Model model) {


    	byte[] bytes = null;

    	try {
        	// ユーザーを全件取得して、CSVをサーバーに保存する
        	userService.userCsvOut();

    		// サーバーに保存されているsample.csvファイルをbyteで取得する
    		bytes = userService.getFile("sample.csv");
    	} catch (IOException e) {
    		e.printStackTrace();
    	}

    	// HTTPヘッダーの設定
    	HttpHeaders header = new HttpHeaders();
    	header.add("Content-type", "text/csv; charset=UTF-8");
    	header.setContentDispositionFormData("filename", "sample.csv");

    	// sample.csvを戻す
    	return new ResponseEntity<>(bytes, header, HttpStatus.OK);
    }

    // アドミン権限専用画面のGET用メソッド
    @GetMapping("/admin")
    public String getAdmin(Model model) {
    	// コンテンツ部分にユーザー詳細を表示するための文字列を登録
    	model.addAttribute("contents", "login/admin::admin_contents");

    	// レイアウト用テンプレート
    	return "login/homeLayout";
    }

    // 購入履歴画面のGET用メソッド
    @GetMapping("/userPurchaseHistory/{id:.+}")
    public String getUserPurchaseHistory(Model model, @PathVariable("id")String userId) {
    	// ユーザーID確認（デバッグ）
    	System.out.println("userId = " + userId);

    	// コンテンツ部分に購入履歴を表示するための文字列を登録
    	model.addAttribute("contents", "login/userPurchaseHistory::userPurchaseHistory_contents");

    	// ModelにユーザーIDを登録
    	model.addAttribute("userId", userId);

    	// ユーザーIDのチェック
    	if (userId != null && userId.length() > 0) {
    		// ユーザー情報を取得
    		List<PurchaseHistory> purchaseHistoryList = userService.selectPurchaseHistory(userId);

    		// Modelに購入履歴を登録
    		model.addAttribute("purchaseHistoryList", purchaseHistoryList);

    		// 購入金額合計を算出
    		int sumPrice = userService.selectSumPurchaseHistoryPrice(userId);

    		// Modelに購入金額登録
    		model.addAttribute("sumPrice", sumPrice);
    	}
    	return "login/homeLayout";
    }

    // 購入履歴更新画面のGET用メソッド
    @GetMapping("/userPurchaseHistoryUpdate/{userId:.+}/{id}")
    public String getUserPurchaseHistoryUpdate(@ModelAttribute PurchaseHistoryForm purchaseHistoryForm,
    		BindingResult bindingResult, Model model,
    		@PathVariable("userId")String userId, @PathVariable("id")int id) {
    	// ユーザーIDとidを確認（デバッグ）
    	System.out.println("userId = " + userId +", id = " + id);

    	// コンテンツ部分に購入履歴を表示するための文字列を登録
    	model.addAttribute("contents", "login/userPurchaseUpdate::userPurchaseUpdate_contents");

    	// ModelにユーザーID、idを登録
    	model.addAttribute("userId", userId);
    	model.addAttribute("id", id);

    	// 更新対象の行を取得
    	PurchaseHistory purchaseHistory = userService.selectOnePurchaseHistory(id);

    	purchaseHistoryForm.setId(purchaseHistory.getId());							// id
    	purchaseHistoryForm.setPurchaseDate(purchaseHistory.getPurchaseDate());		// 購入日
    	purchaseHistoryForm.setPurchaseItem(purchaseHistory.getPurchaseItem());		// 購入品目
    	purchaseHistoryForm.setPurchasePrice(purchaseHistory.getPurchasePrice());	// 購入金額

    	model.addAttribute("purchaseHistoryForm", purchaseHistoryForm);

    	return "login/homeLayout";
    }

    // 購入履歴更新画面のPost用メソッド
    @PostMapping("/userPurchaseHistoryUpdate/{userId:.+}/{id}")
    public String postUserPurchaseHistoryUpdate(@ModelAttribute @Validated(GroupOrder.class) PurchaseHistoryForm purchaseHistoryForm,
    		BindingResult bindingResult, Model model, @PathVariable("userId")String userId, @PathVariable("id")int id) {
    	// ユーザーIDを確認（デバッグ）
    	System.out.println("userId = " + userId +", id = " + id);

		// 入力チェックに引っかかった場合、購入履歴更新画面に戻る
		if (bindingResult.hasErrors()) {
			// GETリクエスト用のメソッドを呼び出して、ユーザー登録画面に戻ります。
			return getUserPurchaseHistoryUpdate(purchaseHistoryForm, bindingResult, model,
					userId, id);
		}

		PurchaseHistory purchaseHistory = new PurchaseHistory();

		purchaseHistory.setId(purchaseHistoryForm.getId());							// id
		purchaseHistory.setPurchaseDate(purchaseHistoryForm.getPurchaseDate());		// 購入日
		purchaseHistory.setPurchaseItem(purchaseHistoryForm.getPurchaseItem());		// 購入品目
		purchaseHistory.setPurchasePrice(purchaseHistoryForm.getPurchasePrice());	// 購入金額


		// 対象の行を更新する。
		boolean result = userService.updatePurchaseHistory(purchaseHistory);

    	if(result == true) {
    		model.addAttribute("result", "更新成功");
    	}
    	else {
    		model.addAttribute("result", "更新失敗");
    	}

    	return getUserPurchaseHistory(model, userId);
    }

    // 購入履歴画面のGET用メソッド(削除実施)
    @GetMapping("/userPurchaseHistoryDelete/{userId:.+}/{id}")
    public String getUserPurchaseHistoryDelete(Model model, @PathVariable("userId")String userId,
    		@PathVariable("id")int id) {
    	// ユーザーIDとidを確認（デバッグ）
    	System.out.println("userId = " + userId +", id = " + id);

    	// 対象の行を削除
    	boolean result = userService.deletePurchaseHistory(id);

    	if(result == true) {
    		model.addAttribute("result", "削除成功");
    	}
    	else {
    		model.addAttribute("result", "削除失敗");
    	}

    	return getUserPurchaseHistory(model, userId);
    }

    // 購入履歴入力画面のGETメソッド
    @GetMapping("/userPurchaseInput/{id:.+}")
    public String getUserPurchaseInput(@ModelAttribute PurchaseHistoryForm purchaseHistoryForm, BindingResult bindingResult, Model model, @PathVariable("id")String userId) {
    	// ユーザーID確認（デバッグ）
    	System.out.println("userId = " + userId);

    	// コンテンツ部分に購入履歴を表示するための文字列を登録
    	model.addAttribute("contents", "login/userPurchaseInput::userPurchaseInput_contents");

    	// ModelにユーザーIDを登録
    	model.addAttribute("userId", userId);

    	return "login/homeLayout";
    }

    // 購入履歴入力画面のPostメソッド
    @PostMapping("/userPurchaseInput/{id:.+}")
    public String postUserPurchaseInput(@ModelAttribute @Validated(GroupOrder.class) PurchaseHistoryForm purchaseHistoryForm, BindingResult bindingResult, Model model, @PathVariable("id")String userId) {
    	// ユーザーID確認（デバッグ）
    	System.out.println("userId = " + userId);

		// 入力チェックに引っかかった場合、ユーザ登録画面に戻る
		if (bindingResult.hasErrors()) {
			// GETリクエスト用のメソッドを呼び出して、ユーザー登録画面に戻ります。
			return getUserPurchaseInput(purchaseHistoryForm, bindingResult, model, userId);
		}

		// purchaseHistoryFormの内容をコンソールに出して確認する
		System.out.println(purchaseHistoryForm);

		// insert用変数
		PurchaseHistory purchaseHistory = new PurchaseHistory();

		purchaseHistory.setUserId(userId);											// ユーザーID
		purchaseHistory.setPurchaseDate(purchaseHistoryForm.getPurchaseDate());		// 購入日
		purchaseHistory.setPurchaseItem(purchaseHistoryForm.getPurchaseItem());		// 購入品目
		purchaseHistory.setPurchasePrice(purchaseHistoryForm.getPurchasePrice());	// 購入金額

		// 購入履歴登録処理
		boolean result = userService.insertPurchaseHistory(purchaseHistory);

		// 購入履歴登録の判定
		if (result == true) {
			System.out.println("insert成功");
		}
		else {
			System.out.println("insert失敗");
		}

    	// ユーザー一覧画面を表示
    	return getUserPurchaseHistory(model, userId);
    }
}
