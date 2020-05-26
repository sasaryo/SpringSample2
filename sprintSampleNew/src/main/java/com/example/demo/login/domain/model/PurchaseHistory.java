/**
 *
 */
package com.example.demo.login.domain.model;

import java.util.Date;

import lombok.Data;

/**
 * @author 佐々木亮
 *  購入履歴テーブル用のクラス
 */
@Data
public class PurchaseHistory {
	private String userId;			// ユーザID
	private Date purchaseDate;		// 購入日時
	private String purchaseItem;	// 購入品目
	private int purchasePrice;		// 購入金額
}
