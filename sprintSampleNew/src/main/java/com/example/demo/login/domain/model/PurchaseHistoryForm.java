/**
 *
 */
package com.example.demo.login.domain.model;

import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

/**
 * @author 佐々木亮
 *
 */
@Data
public class PurchaseHistoryForm {
	private int id;				// ID
	@NotNull(groups = ValidGroup1.class)
	@DateTimeFormat( pattern = "yyyy/MM/dd")
	private Date purchaseDate;		// 購入日時
	@NotBlank(groups = ValidGroup1.class)
	private String purchaseItem;	// 購入品目
	@Min(value=1, groups = ValidGroup2.class)
	@Max(value=Integer.MAX_VALUE, groups = ValidGroup2.class)
	private int purchasePrice;		// 購入金額
}
