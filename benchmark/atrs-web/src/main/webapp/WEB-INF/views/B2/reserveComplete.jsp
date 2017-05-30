
<div class="flow">
  <img src="${pageContext.request.contextPath}/resources/image/flow_5_complete.jpg" alt="[完了]"> 
</div>

<h1>予約完了</h1>

<div class="info"> 
  <p>
     ご予約ありがとうございました。<br>
      期限までにご購入いただけない場合、すべてのフライトが自動的にキャンセルされます。
  </p>
</div>

<h2>チケット</h2>
<table class="vertical" id="total_fare">
  <tr>
    <th class="label">予約番号</th>
    <td>
      ${f:h(reserveCompleteOutputDto.reserveInfo.reserveNo)}
    </td>
  </tr>
  <tr>
    <th class="label">合計金額</th>
    <td>
      <fmt:formatNumber value="${reserveCompleteOutputDto.totalFare}" pattern="###,###"/>円
    </td>
  </tr>
  <tr>
    <th class="label">お支払期限</th>
    <td>
      <fmt:formatDate value="${reserveCompleteOutputDto.reserveInfo.paymentDate}" pattern="M月d日(E)"/>
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
    <td>
      ${f:h(reserveCompleteOutputDto.reservationForm.repFamilyName)}&nbsp;${f:h(reserveCompleteOutputDto.reservationForm.repGivenName)}
    </td>
    <td class="info-center">
      ${f:h(reserveCompleteOutputDto.reservationForm.repAge)}歳
    </td>
    <td class="info-center">
      ${f:h(CL_GENDER[reserveCompleteOutputDto.reservationForm.repGender.code])}
    </td>
    <td class="info-center">
      ${f:h(reserveCompleteOutputDto.reservationForm.repCustomerNo)}
    </td>
  </tr>
  <tr>
    <th class="top_label">電話番号</th>
    <th colspan="3" class="top_label">メールアドレス</th>
  </tr>
  <tr>
    <td>
      ${f:h(reserveCompleteOutputDto.reservationForm.repTel1)}-${f:h(reserveCompleteOutputDto.reservationForm.repTel2)}-${f:h(reserveCompleteOutputDto.reservationForm.repTel3)}
    </td>
    <td colspan="3" id="mail">
      ${f:h(reserveCompleteOutputDto.reservationForm.repMail)}
    </td>
  </tr>
</table>

<h2>予約フライト情報</h2> 

<div id="flight">
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
    <c:forEach var="reserveFlightBean" items="${reserveCompleteOutputDto.selectFlightDtoList}">
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
          ${f:h(CL_AIRPORT[reserveFlightBean.depAirportCd])}&nbsp;→&nbsp;${f:h(CL_AIRPORT[reserveFlightBean.arrAirportCd])}
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
      </tr>
    </c:forEach>
  </table>
</div>

<h2>搭乗者情報</h2>
<table>
  <tr>
    <th id="no_label" class="top_label">&nbsp;</th>
    <th id="name_label" class="top_label">お客様氏名</th>
    <th id="age_label" class="top_label">年齢</th>
    <th id="sex_label" class="top_label">性別</th>
    <th id="costomer_no_label" class="top_label">お客様番号</th>
  </tr>
  
  <c:forEach var="Passenger" items="${reserveCompleteOutputDto.reservationForm.passengerFormList}" varStatus="passengerListStatus">
    <tr>
      <td class="info-center">
        ${f:h(passengerListStatus.count)}
      </td>
      <td>
        ${f:h(Passenger.familyName)}&nbsp;${f:h(Passenger.givenName)}&nbsp;様
      </td>
      <td class="info-center">
        ${f:h(Passenger.age)}&nbsp;歳
      </td>
      <td class="info-center">
        ${f:h(CL_GENDER[Passenger.gender.code])}
      </td>
      <td class="info-center">
        ${f:h(Passenger.customerNo)}
      </td>
    </tr>
  </c:forEach>
</table>

<div class="navi-forward"> 
  <button type="button" name="forwardTop" class="forward" onclick="atrs.moveTo('/');">TOPへ戻る</button>
</div>
