<h1>ログアウト確認画面</h1>

<div class="logout_info" >
  <p>
    ログアウトします。よろしいですか？
  </p>
</div>

<div class="navi-forward">
  <form:form method="post" action="${pageContext.request.contextPath}/Auth/dologout">
    <button type="submit" name="yes"class="logout_forward">はい</button>
    <button type="button" name="no" class="logout_backward" onclick="atrs.moveTo('/')">いいえ</button>
  </form:form>
</div>
