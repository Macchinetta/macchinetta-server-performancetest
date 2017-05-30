<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>

<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/style.css" />
  <title>Airline Ticket Reservation System</title>
</head>

<body>
  <div id="container">
    <div id="main">

      <%-- ここからヘッダ --%>
      <%@ include file="error-header.jsp" %>
      <%-- ヘッダここまで --%>

      <%-- ここからメイン --%>
      <div id="content">

        <!-- 【この上までを編集する】 -->
        <div class="info">
          <p class="error">
            <c:if test="${!empty exceptionCode}">
              [${f:h(exceptionCode)}]
            </c:if>
            <spring:message code="e.ar.fw.9999" />
          </p>
        </div>

        <div class="navi-backward">
          <button type="button" name="backwardTop" class="backward" onclick="atrs.moveTo('/')">TOPへ戻る</button>
        </div>
        <!-- 【この上までを編集する】 -->

      </div>
      <%-- メインここまで --%>

      <%-- フッタここから --%>
      <div id="footer">
        <%@ include file="../../A0/footer.jsp" %>
      </div>
      <%-- フッタここまで --%>

    </div>
  </div>
</body>

<script type="text/javascript">
  if (!atrs) var atrs = {};
  atrs.baseUrl = "${f:js(pageContext.request.contextPath)}";
</script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/common.js" >
</script>

</html>