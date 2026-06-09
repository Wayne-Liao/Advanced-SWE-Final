package com.example;

import org.junit.Before; // 新增這行
import org.junit.Test;
import static org.junit.Assert.*;

public class OrderSystemCalculatorTest {

    // 重構：將計算機宣告為類別層級的屬性
    private OrderSystemCalculator calc;

    // 重構：使用 @Before，讓 JUnit 在「每一個」 @Test 執行前，都會自動跑一次這段初始化
    @Before
    public void setUp() {
        calc = new OrderSystemCalculator();
    }

    // ==========================================
    // 功能一：基本單點計價測試 (無套餐、無會員)
    // ==========================================
    @Test
    public void testBasicSingleItems() {
        // 不用再自己 new 物件了，直接使用 calc
        // 只有主餐 (2份 * 100 = 200)
        assertEquals(200, calc.calculateTotal(2, 0, ""));
        
        // 只有飲料 (3杯 * 50 = 150)
        assertEquals(150, calc.calculateTotal(0, 3, "None"));
    }

    // ==========================================
    // 功能二：套餐組合邏輯測試 (1主餐+1飲料=120)
    // ==========================================
    @Test
    public void testComboLogic() {
        // 剛好湊成1組套餐
        assertEquals(120, calc.calculateTotal(1, 1, null));
        
        // 2份主餐 + 1杯飲料 = 1組套餐(120) + 1份單點主餐(100) = 220
        assertEquals(220, calc.calculateTotal(2, 1, ""));
        
        // 1份主餐 + 3杯飲料 = 1組套餐(120) + 2杯單點飲料(100) = 220
        assertEquals(220, calc.calculateTotal(1, 3, ""));
    }

    // ==========================================
    // 功能三：會員折扣測試 (輸入 Member 打 9 折)
    // ==========================================
    @Test
    public void testMemberDiscount() {
        // 1組套餐(120) * 會員9折 = 108
        assertEquals(108, calc.calculateTotal(1, 1, "Member"));
        
        // 只有主餐(200) * 會員9折 = 180
        assertEquals(180, calc.calculateTotal(2, 0, "Member"));
        
        // 測試大小寫或錯誤會員碼，不該打折 (預期維持原價 120)
        assertEquals(120, calc.calculateTotal(1, 1, "member")); 
        assertEquals(120, calc.calculateTotal(1, 1, "VIP"));    
    }

    // ==========================================
    // 功能四：異常處理與邊界防呆測試
    // ==========================================
    
    // 測試防呆：數量不能同時 <= 0
    @Test(expected = IllegalAmountException.class)
    public void testException_ZeroAmount() {
        calc.calculateTotal(0, 0, "Member"); // 預期拋出例外
    }

    // 測試防呆：單一品項不能超過 50
    @Test(expected = IllegalAmountException.class)
    public void testException_ExceedLimit() {
        calc.calculateTotal(51, 10, null); // 預期拋出例外
    }

    // 測試極限值：剛好 50 份 (合法邊界，不該拋出例外)
    @Test
    public void testBoundary_ExactlyFifty() {
        // 50組套餐 (50 * 120 = 6000)
        assertEquals(6000, calc.calculateTotal(50, 50, "NORMAL"));
    }
}