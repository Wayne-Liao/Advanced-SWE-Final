package com.example;

// 自訂異常類別，用於處理點餐系統的業務邏輯錯誤
class IllegalAmountException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public IllegalAmountException(String message) {
        super(message);
    }
}

public class OrderSystemCalculator {

    // 類別常數定義 (Constants)
    private static final int PRICE_MAIN_DISH = 100;
    private static final int PRICE_DRINK = 50;
    private static final int PRICE_COMBO = 120;
    private static final int MAX_ORDER_LIMIT = 50;

    /**
     * 核心計算方法
     * @param quantity1  主餐份數
     * @param quantity2  飲料杯數
     * @param membercode 會員代碼
     * @return 結帳總金額
     */
    public int calculateTotal(int quantity1, int quantity2, String membercode) {
        
        // 1. 異常處理：針對情境 3 (同時 <= 0)
        if (quantity1 <= 0 && quantity2 <= 0) {
            throw new IllegalAmountException("訂購數量必須大於0");
        }
        
        // 2. 異常處理：針對情境 4 (任一品項超過 50)
        if (quantity1 > MAX_ORDER_LIMIT || quantity2 > MAX_ORDER_LIMIT) {
            throw new IllegalAmountException("訂購數量超過上限，請聯絡客服訂餐");
        }

        // 3. 第一步：判定是否觸發「套餐優惠」，計算出初步總價 (情境 1 & 情境 2)
        int combos = Math.min(quantity1, quantity2); // 組合套餐的數量
        int remainingMain = quantity1 - combos;      // 剩下隻能單點的主餐
        int remainingDrink = quantity2 - combos;     // 剩下隻能單點的飲料

        int subtotal = (combos * PRICE_COMBO) 
                     + (remainingMain * PRICE_MAIN_DISH) 
                     + (remainingDrink * PRICE_DRINK);

        // 4. 第二步：判定 MemberCode 是否為 "Member"，若是則打 9 折 (情境 5)
        if (membercode != null && membercode.equals("Member")) {
            // 依據商務規則優先級，在初步總價計算完後再打 9 折
            subtotal = (int) (subtotal * 0.9);
        }

        return subtotal;
    }
}