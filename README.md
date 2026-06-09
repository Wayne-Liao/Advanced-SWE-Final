### 專案上傳與提交指令

```bash
# 1. 進入專案資料夾
cd C:\Users\user\eclipse-workspace\Advanced-SWE-Final

# 2. 設定你的 Git 身份 (若已設定過可跳過)
git config --global user.name "Wayne-Liao"
git config --global user.email "your-email@example.com"

# 3. 加入修改的檔案 (使用 . 代表所有變更，也可改成路徑)
git add .

# 4. 提交變更
git commit -m "feat: 請替換為你的提交訊息"

# 5. push 到 GitHub
git push -u origin main
