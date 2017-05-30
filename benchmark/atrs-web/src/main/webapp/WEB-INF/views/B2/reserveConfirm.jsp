
<div class="flow"> 
  <img src="${pageContext.request.contextPath}/resources/image/flow_4_confirm.jpg" alt="[確認]" /> 
</div> 
<h1>お申込み内容確認</h1> 

<p class="header_guide"> 
お申込み内容は以下の通りです。この内容でよろしければ、[予約を確定する]を押してください。<br/>
[予約を確定する]を押すと予約手続きを行います。
</p> 

<form:form name="ticketReserveForm" method="POST"
  action="${pageContext.request.contextPath }/Ticket/reserve"
  modelAttribute="ticketReserveForm"> 

  <h2>合計金額</h2> 
  <table class="vertical" id="total_fare"> 
    <tr> 
      <th class="label">合計金額</th>
      <td class="info-center">
        <fmt:formatNumber value="${reserveConfirmOutputDto.totalFare}" pattern="###,###"/>円
      </td>
    </tr> 
  </table> 

  <h2>予約代表者情報</h2> 
  <table id="customer"> 
    <tr> 
      <th id="rep_name_label" class="top_label">代表者氏名</th> 
      <th id="rep_age_label" class="top_label">年齢</th> 
      <th id="rep_sex_label" class="top_label">性別</th> 
      <th id="rep_costomer_no_label" class="top_label">お客様番号</th> 
    </tr>
    <tr>
      <td class="info-left">
        ${f:h(ticketReserveForm.reservationForm.repFamilyName)}&nbsp;${f:h(ticketReserveForm.reservationForm.repGivenName)}
      </td>
      <td class="info-center">
        ${f:h(ticketReserveForm.reservationForm.repAge)}歳
      </td> 
      <td class="info-center">
        ${f:h(CL_GENDER[ticketReserveForm.reservationForm.repGender.code])}
      </td> 
      <td class="info-center">
        ${f:h(ticketReserveForm.reservationForm.repCustomerNo)}
      </td> 
    </tr> 
    <tr> 
      <th class="top_label">電話番号</th> 
      <th colspan="3" class="top_label">メールアドレス</th> 
    </tr> 
    <tr> 
      <td>
        ${f:h(ticketReserveForm.reservationForm.repTel1)}-${f:h(ticketReserveForm.reservationForm.repTel2)}-${f:h(ticketReserveForm.reservationForm.repTel3)}
      </td> 
      <td colspan="3" id="mail">
        ${f:h(ticketReserveForm.reservationForm.repMail)}
      </td> 
    </tr> 
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
  </table> 

  <h2>選択フライト情報</h2>
   <table id="reserveflightlist"> 
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
    <c:forEach var="reserveFlightBean" items="${reserveConfirmOutputDto.selectFlightDtoList}" varStatus="flightListStatus">
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
        <spring:nestedPath path="selectFlightFormList[${flightListStatus.index}]">
          <form:hidden path="departureDate" />
          <form:hidden path="boardingClassCd" />
          <form:hidden path="fareTypeCd" />
          <form:hidden path="flightName" />
        </spring:nestedPath>
      </tr>
    </c:forEach>
  </table>

  <h2>搭乗者情報</h2> 
  <table> 
    <tr> 
      <th id="no_label" class="top_label">&nbsp;</th> 
      <th id="name_label" class="top_label">お客様氏名</th> 
      <th id="age_label" class="top_label">年齢</th> 
      <th id="sex_label" class="top_label">性別</th> 
      <th id="costomer_no_label" class="top_label">お客様番号</th> 
    </tr>
  
    <c:forEach var="passengerForm" items="${ticketReserveForm.reservationForm.passengerFormList}" varStatus="passengerListStatus">
      <tr>
        <td class="info-center">
          ${f:h(passengerListStatus.count)}
        </td>
        <td class="info-left">
          ${f:h(passengerForm.familyName)}&nbsp;${f:h(passengerForm.givenName)}&nbsp;様
        </td> 
        <td class="info-center">
          ${f:h(passengerForm.age)}&nbsp;歳
        </td> 
        <td class="info-center">
          ${f:h(CL_GENDER[passengerForm.gender.code])}
        </td> 
        <td class="info-center">
          ${f:h(passengerForm.customerNo)}
        </td>
        <spring:nestedPath path="reservationForm.passengerFormList[${passengerListStatus.index}]">
          <form:hidden path="familyName" />
          <form:hidden path="givenName" />
          <form:hidden path="age" />
          <form:hidden path="gender" />
          <form:hidden path="customerNo" />
        </spring:nestedPath>
      </tr>
    </c:forEach>
  </table>

  <div class="navi-forward">
    <form:button type="submit" class="forwardx1_5">予約を確定する</form:button>
  </div>

  <div class="navi-backward">
    <form:button type="submit" name="redo" class="backwardx2_0">お客様情報入力画面へ戻る</form:button> 
    <form:button type="submit" name="cancel" class="backward">予約を中止する</form:button>
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
