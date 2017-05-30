<form:form name="headerLoginForm" method="post"
  action="${pageContext.request.contextPath}/Auth/dologin">

  <div id="header_input_area">
    <label for="headerCustomerNo">お客様番号</label>&nbsp;<input type="text" id="headerCustomerNo" name="customerNo" value="">
    <label for="headerPassword">パスワード</label>&nbsp;<input type="password" id="headerPassword" name="password" value="">
  </div>  

  <div id="header_button_area">
    <button type="submit" class="button">ログイン</button>
  </div>

</form:form>
