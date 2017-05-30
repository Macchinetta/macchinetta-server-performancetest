<%-- 搭乗者情報リストの表示件数を設定する。 3件以下の場合、3行の搭乗者情報を表示し、4件以上の場合、6行の搭乗者情報を表示する。 --%>
<c:set var="showPassengerNum" value="3" />
<c:set var="passengerListSize" value="${ticketReserveForm.reservationForm.passengerFormList.size()}" />
<c:if test="${passengerListSize > 3 }" >
  <c:forEach var="passenger" items="${ticketReserveForm.reservationForm.passengerFormList}" begin="3" varStatus="status">
    <c:choose>
      <c:when test="${!empty passenger.familyName || !empty passenger.givenName || !empty passenger.age || !empty passenger.gender || !empty passenger.customerNo}">
        <c:set var="showPassengerNum" value="6" />
      </c:when>
      <c:otherwise>
        <spring:hasBindErrors name="ticketReserveForm">
          <c:set var="fldname" value="reservationForm.passengerFormList[${status.index}].*" />
          <c:if test="errors.hasFieldErrors(fldname)}">
            <c:set var="showPassengerNum" value="6" />
          </c:if>
        </spring:hasBindErrors>
      </c:otherwise>
    </c:choose>
  </c:forEach>
</c:if>

<c:if test="${showPassengerNum == 3 }" >
<script type="text/javascript">
<!--
var reserveForm = {};
reserveForm.addPassengerForm = function() {
  var table = document.getElementById("passengerFormListtable");
  var tr;
  for (var row = 3; row < 6; row++) {
    tr = table.insertRow(table.rows.length);
    var td = tr.insertCell(0);
    td.className ="center"; 
    td.innerHTML = '' + (row + 1);
    tr.insertCell(1).innerHTML = '姓 <input type="text" name="reservationForm.passengerFormList[' + row + '].familyName" size="15" value=""> 名 <input type="text" name="reservationForm.passengerFormList['+row+'].givenName" size="15" value="">';
    tr.insertCell(2).innerHTML = '<input type="text" name="reservationForm.passengerFormList[' + row + '].age" size="3" value=""> 歳';
    tr.insertCell(3).innerHTML = '<input type="radio" id="reservationForm.passengerFormList' + row + '.gender1" name="reservationForm.passengerFormList[' + row + '].gender" class="radio_btn" value="M" ><label for="reservationForm.passengerFormList' + row + '.gender1">男性</label>' 
                                 +
                                 '<input type="radio" id="reservationForm.passengerFormList' + row + '.gender2" name="reservationForm.passengerFormList['+row+'].gender" value="F" class="radio_btn" ><label for="reservationForm.passengerFormList' + row + '.gender2">女性</label>'; 
    tr.insertCell(4).innerHTML = '<input type="text" name="reservationForm.passengerFormList[' + row + '].customerNo" size="15" value="">(任意)';
  }
  
  var addPassengerBtn = document.getElementById("addPassengerBtn");
  addPassengerBtn.style.visibility = 'hidden';
};
//-->
</script>
</c:if>

<div class="flow"> 
  <img src="${pageContext.request.contextPath}/resources/image/flow_3_input.jpg" alt="[情報入力]"> 
</div>

<h1>お客様情報入力</h1> 
<p class="header_guide"> 
  お客様情報を入力し、[次へ]を押してください。お申し込み内容確認画面へ進みます。
</p> 

<!-- エラーメッセージ出力 -->
<spring:hasBindErrors name="ticketReserveForm">
  <c:if test="${errors.globalErrorCount > 0}">
    <div class="info">
      <p class="error">
        <spring:nestedPath path="ticketReserveForm">
          <form:errors path="" cssClass="error"/>
        </spring:nestedPath>
      </p>
    </div>
  </c:if>
</spring:hasBindErrors>

<t:messagesPanel panelElement="div" panelClassName="info"
  panelTypeClassPrefix="" outerElement="p" innerElement="" />

<form:form name="ticketReserveForm" method="post"
  action="${pageContext.request.contextPath}/Ticket/reserve"
  modelAttribute="ticketReserveForm">

  <h2>選択フライト情報</h2> 

  <div id="flight"> 
    <table> 
      <tr> 
        <th class="top_label">&nbsp;</th> 
        <th class="top_label">搭乗日</th> 
        <th class="top_label">便名</th> 
        <th class="top_label">出発時刻</th> 
        <th class="top_label">到着時刻</th> 
        <th class="top_label">区間</th> 
        <th class="top_label">搭乗クラス</th> 
        <th class="top_label">運賃種別</th> 
        <th class="top_label">運賃</th> 
      </tr>
      <c:forEach var="reserveFlightBean" items="${selectFlightDtoList}" varStatus="flightListRowStatus">
        <tr>
          <td class="info-center">
            ${f:h(reserveFlightBean.lineType.name)}
          </td> 
          <td class="info-center">
            <fmt:formatDate value="${reserveFlightBean.departureDate}" pattern="M月d日(E)"/>
          </td>
          <td class="info-center">
            ${f:h(reserveFlightBean.flightName)}
          </td> 
          <td class="info-center">
            <fw:split str="${reserveFlightBean.departureTime}" split=":" position="2"/>
          </td> 
          <td class="info-center">
            <fw:split str="${reserveFlightBean.arrivalTime}" split=":" position="2"/>
          </td>
          <td class="info-center">
            ${f:h(CL_AIRPORT[reserveFlightBean.depAirportCd])}&nbsp;⇒&nbsp;${f:h(CL_AIRPORT[reserveFlightBean.arrAirportCd])}
          </td> 
          <td class="info-center">
            ${f:h(CL_BOARDINGCLASS[reserveFlightBean.boardingClassCd.code])}
          </td> 
          <td class="info-center">
            ${f:h(CL_FARETYPE[reserveFlightBean.fareTypeCd.code])}
          </td>
          <td class="info-center">
            &yen;<fmt:formatNumber value="${reserveFlightBean.fare}" pattern="###,###"/>
          </td>
          <spring:nestedPath path="selectFlightFormList[${flightListRowStatus.index}]">
            <form:hidden path="departureDate" />
            <form:hidden path="boardingClassCd" />
            <form:hidden path="fareTypeCd" />
            <form:hidden path="flightName" />
          </spring:nestedPath>
        </tr>
      </c:forEach>
    </table>
  </div> 

  <h2>お客様情報</h2> 
  <p class="guide"> 
    ご搭乗されるお客様の情報を人数分ご入力ください。
  </p> 

  <div id="passengerFormList">
    <table id="passengerFormListtable"> 
      <tr> 
        <th class="top_label"><!-- 空欄 --></th> 
        <th class="top_label">お名前（全角カナ）</th> 
        <th class="top_label">年齢</th> 
        <th class="top_label">性別</th> 
        <th class="top_label">お客様番号(10桁)</th> 
      </tr>
      <c:forEach var="passenger" items="${ticketReserveForm.reservationForm.passengerFormList}" begin="0" end="${showPassengerNum-1}" varStatus="passengerListRowStatus">
        <spring:nestedPath path="reservationForm.passengerFormList[${passengerListRowStatus.index}]">
          <tr>
            <td class="center">
              ${f:h(passengerListRowStatus.count)}
            </td> 
            <td>
              <p>
                姓&nbsp;<form:input path="familyName" size="15"/>
                名&nbsp;<form:input path="givenName" size="15"/>
              </p>
              <form:errors path="familyName" cssClass="error" element="p"/>
              <form:errors path="givenName" cssClass="error" element="p"/>
            </td> 
            <td>
              <p>
                <form:input path="age" size="3"/>&nbsp;歳
              </p>
              <form:errors path="age" cssClass="error" element="p"/>
            </td> 
            <td>
              <p>
                <form:radiobuttons path="gender" items="${CL_GENDER}" cssClass="radio_btn"/>
              </p>
              <form:errors path="gender" cssClass="error" element="p"/>
            </td> 
            <td>
              <p>
                <form:input path="customerNo" size="15" />(任意)
              </p>
              <form:errors path="customerNo" cssClass="error" element="p"/>
            </td>
          </tr>
        </spring:nestedPath>
      </c:forEach>
      <c:forEach begin="${passengerListSize}" end="${showPassengerNum - 1}" varStatus="passengerListRowStatus">
        <spring:nestedPath path="reservationForm.passengerFormList[${passengerListRowStatus.index}]">
          <tr>
            <td class="center">
              ${passengerListRowStatus.index + 1}
            </td> 
            <td>
              姓&nbsp;<form:input path="familyName" size="15" />
              名&nbsp;<form:input path="givenName" size="15" />
            </td>
            <td>
              <form:input path="age" size="3" />&nbsp;歳
            </td>
            <td>
              <form:radiobuttons path="gender" items="${CL_GENDER}" cssClass="radio_btn"/>
            </td>
            <td>
              <form:input path="customerNo" size="15" />(任意)</td>
          </tr>
        </spring:nestedPath>
      </c:forEach>
    </table> 

    <c:if test="${showPassengerNum == 3}">
      <div> 
        <form:button type="button" id="addPassengerBtn" class="backward" onclick="reserveForm.addPassengerForm();">搭乗者追加</form:button> 
      </div> 
    </c:if>

  </div>

  <h2>予約代表者情報</h2> 
  <p class="guide">
    この予約の代表者となる方の情報をご入力ください。
  </p> 

  <div id="representativeForm">
    <spring:nestedPath path="reservationForm">
      <table id="representative"> 
        <tr> 
          <th class="top_label" id="name_label">お名前（全角カナ）</th> 
          <th class="top_label" id="age_label">年齢</th> 
          <th class="top_label" id="sex_label">性別</th> 
          <th class="top_label" id="costomer_no_label">お客様番号(10桁)</th> 
        </tr> 
        <tr> 
          <security:authorize access="!hasRole('MEMBER')">
            <td>
              <p>
                姓&nbsp;<form:input path="repFamilyName" size="15" />
                名&nbsp;<form:input path="repGivenName" size="15" />
              </p>
              <form:errors path="repFamilyName" cssClass="error" element="p"/>
              <form:errors path="repGivenName" cssClass="error" element="p"/>
            </td> 
            <td>
              <p>
                <form:input path="repAge" size="3" />&nbsp;歳
              </p>
              <form:errors path="repAge" cssClass="error" element="p"/>
            </td> 
            <td>
              <p>
                <form:radiobuttons path="repGender" items="${CL_GENDER}" cssClass="radio_btn"/>
              </p>
              <form:errors path="repGender" cssClass="error" element="p"/>
            </td> 
            <td>
              <p>
                <form:input path="repCustomerNo" size="15" />(任意)
              </p>
              <form:errors path="repCustomerNo" cssClass="error" element="p"/>
            </td> 
          </security:authorize>
          <security:authorize access="hasRole('MEMBER')" >
            <td>
              ${f:h(ticketReserveForm.reservationForm.repFamilyName)}&nbsp;${f:h(ticketReserveForm.reservationForm.repGivenName)}
            </td> 
            <td>
              ${f:h(ticketReserveForm.reservationForm.repAge)}&nbsp;歳
            </td> 
            <td>
              ${f:h(CL_GENDER[ticketReserveForm.reservationForm.repGender.code]) }
            </td> 
            <td>
              ${f:h(ticketReserveForm.reservationForm.repCustomerNo)}
            </td> 
            <form:hidden path="repFamilyName" />
            <form:hidden path="repGivenName" />
            <form:hidden path="repAge" />
            <form:hidden path="repGender" />
            <form:hidden path="repCustomerNo" />
          </security:authorize>
        </tr>
        <tr>
          <th class="top_label">電話番号</th> 
          <th colspan="3" class="top_label">メールアドレス</th> 
        </tr> 
        <tr>
          <security:authorize access="!hasRole('MEMBER')">
            <td>
              <form:input path="repTel1" size="5" />-<form:input path="repTel2" size="4" />-<form:input path="repTel3" size="4" />
              <form:errors path="repTel1" cssClass="error" element="p"/>
              <form:errors path="repTel2" cssClass="error" element="p"/>
              <form:errors path="repTel3" cssClass="error" element="p"/>
            </td> 
            <td colspan="3">
              <form:input path="repMail" id="rep_mail"/>
              <form:errors path="repMail" cssClass="error" element="p"/>
            </td> 
          </security:authorize>
          <security:authorize access="hasRole('MEMBER')" >
            <td>
              ${f:h(ticketReserveForm.reservationForm.repTel1)}-${f:h(ticketReserveForm.reservationForm.repTel2)}-${f:h(ticketReserveForm.reservationForm.repTel3)}
            </td> 
            <td colspan="3">
              ${f:h(ticketReserveForm.reservationForm.repMail)}
            </td> 
            <form:hidden path="repTel1" />
            <form:hidden path="repTel2" />
            <form:hidden path="repTel3" />
            <form:hidden path="repMail" />
          </security:authorize>
        </tr>
      </table> 
    </spring:nestedPath>
  </div> 
  
  <div class="navi-forward"> 
    <form:button type="submit" name="confirm" class="forward">次へ</form:button>
  </div>

  <div class="navi-backward">
    <form:button type="button" class="backward" onclick="atrs.moveTo('/');">TOPへ戻る</form:button>
  </div>

  <spring:nestedPath path="outwardLineSearchCriteriaForm">
    <form:hidden path="month" />
    <form:hidden path="day" />
    <form:hidden path="depAirportCd" />
    <form:hidden path="arrAirportCd" />
    <form:hidden path="boardingClassCd" />
    <form:hidden path="time" />
  </spring:nestedPath>

</form:form>
