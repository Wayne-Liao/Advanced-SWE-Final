package com.example;

// 自訂異常類別，用於處理點餐系統的業務邏輯錯誤
@SuppressWarnings("serial") // 重構項目 4：處理 Unresolved warnings
class IllegalAmountException extends RuntimeException {
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
    
    // 重構項目 2：消除 Literal constants (Magic Number & String)
    private static final String VALID_MEMBER_CODE = "Member";
    private static final double MEMBER_DISCOUNT_RATE = 0.9;

    /**
     * 核心計算方法
     * @param mainDishCount 主餐份數 (重構項目 1：改善變數命名)
     * @param drinkCount    飲料杯數 (重構項目 1：改善變數命名)
     * @param membercode    會員代碼
     * @return 結帳總金額
     */
    public int calculateTotal(int mainDishCount, int drinkCount, String membercode) {
        
        // 重構項目 3：將防呆邏輯抽取為獨立方法 (Extract Method)
        validateOrderAmounts(mainDishCount, drinkCount);

        // 第一步：判定是否觸發「套餐優惠」，計算出初步總價
        int combos = Math.min(mainDishCount, drinkCount); 
        int remainingMain = mainDishCount - combos;      
        int remainingDrink = drinkCount - combos;     

        int subtotal = (combos * PRICE_COMBO) 
                     + (remainingMain * PRICE_MAIN_DISH) 
                     + (remainingDrink * PRICE_DRINK);

        // 第二步：判定 MemberCode 是否有效，若是則打折
        // 使用常數取代字串，並避免 NullPointerException
        if (VALID_MEMBER_CODE.equals(membercode)) {
            subtotal = (int) (subtotal * MEMBER_DISCOUNT_RATE);
        }

        return subtotal;
    }

    /**
     * 獨立的驗證邏輯：確保訂單數量合法
     */
    private void validateOrderAmounts(int mainDishCount, int drinkCount) {
        if (mainDishCount == 0 && drinkCount == 0) {
            throw new IllegalAmountException("訂購數量必須大於0");
        }
        if (mainDishCount < 0 || drinkCount < 0) {
            throw new IllegalAmountException("訂購數量必須大於0");
        }
        if (mainDishCount > MAX_ORDER_LIMIT || drinkCount > MAX_ORDER_LIMIT) {
            throw new IllegalAmountException("訂購數量超過上限，請聯絡客服訂餐");
        }
    }
}