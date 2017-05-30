<c:choose>
  <c:when test="${param.backToUrl != null && param.backToUrl != ''}">
    <c:set var="loginLabel" value="次へ" />
    <c:set var="backLabel" value="戻る" />
    <c:set var="backUrl" value="${param.backToUrl}" />
  </c:when>
  <c:otherwise>
    <c:set var="loginLabel" value="ログイン" />
    <c:set var="backLabel" value="TOPへ戻る" />
    <c:set var="backUrl" value="/" />
  </c:otherwise>
</c:choose>

<h1>ログイン<c:if test="${requestScope.SPRING_SECURITY_LAST_EXCEPTION != null}">失敗</c:if></h1>

<br />

<t:messagesPanel panelElement="div" panelClassName="info"
  panelTypeClassPrefix="" outerElement="p" innerElement="" messagesType="error"
  messagesAttributeName="SPRING_SECURITY_LAST_EXCEPTION"/>

<br />

<form:form name="loginForm" method="post" action="${pageContext.request.contextPath}/Auth/dologin">
  <div id="login">
    <div class="navi-left">
      <label for="customerNo">お客様番号</label>&nbsp;<input type="text" id="customerNo" name="customerNo" size="15" value="">
      <label for="password">パスワード</label>&nbsp;<input type="password" id="password" name="password" size="15" value="">
    </div>
    <div class="navi-right">
      <button type="submit" name="loginBtn" class="forward">${loginLabel}</button>
      <c:if test="${param.backToUrl != null && param.backToUrl != ''}">
        <input type="hidden" name="backToUrl" value="${f:h(param.backToUrl)}">
      </c:if>
    </div>
  </div>
  <div class="navi-backward">
    <button type="button" name="closeBtn" class="backward" 
      onclick="atrs.moveTo('${f:hjs(backUrl)}')">${backLabel}</button>
  </div>
</form:form>
