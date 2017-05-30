<div class="info">
  <p>
    [予約を中止する]が押されました。<br />
    [中止を確定する]を押すと手続きを終了し、TOPページに移動します。<br />
    [戻る]を押すと申し込み内容確認画面に戻ります。
  </p>
</div>
  
<form:form name="ticketReserveForm" method="POST"
  action="${pageContext.request.contextPath }/Ticket/reserve"
  modelAttribute="ticketReserveForm">

  <div class="navi-forward">
    <form:button type="button" name="abort" class="forwardx1_5"
      onclick="atrs.moveTo('/');">中止を確定する</form:button>
  </div>

  <div class="navi-backward">
    <form:button type="submit" name="confirm" class="backward">戻る</form:button>
  </div>
  
  <spring:nestedPath path="outwardLineSearchCriteriaForm">
    <form:hidden path="month" />
    <form:hidden path="day" />
    <form:hidden path="depAirportCd" />
    <form:hidden path="arrAirportCd" />
    <form:hidden path="boardingClassCd" />
    <form:hidden path="time" />
  </spring:nestedPath>
  
  <c:forEach items="${ticketReserveForm.selectFlightFormList}" varStatus="flightListStatus">
    <spring:nestedPath path="selectFlightFormList[${flightListStatus.index}]">
      <form:hidden path="departureDate" />
      <form:hidden path="boardingClassCd" />
      <form:hidden path="fareTypeCd" />
      <form:hidden path="flightName" />
    </spring:nestedPath>
  </c:forEach>
  
  <spring:nestedPath path="reservationForm">
    <form:hidden path="repFamilyName" />
    <form:hidden path="repGivenName" />
    <form:hidden path="repAge" />
    <form:hidden path="repGender" />
    <form:hidden path="repCustomerNo" />
    <form:hidden path="repTel1" />
    <form:hidden path="repTel2" />
    <form:hidden path="repTel3" />
    <form:hidden path="repMail" />
  </spring:nestedPath>
  
  <c:forEach items="${ticketReserveForm.reservationForm.passengerFormList}" varStatus="passengerListStatus">
    <spring:nestedPath path="reservationForm.passengerFormList[${passengerListStatus.index}]">
    <form:hidden path="familyName" />
    <form:hidden path="givenName" />
    <form:hidden path="age" />
    <form:hidden path="gender" />
    <form:hidden path="customerNo" />
    </spring:nestedPath>
  </c:forEach>

</form:form>