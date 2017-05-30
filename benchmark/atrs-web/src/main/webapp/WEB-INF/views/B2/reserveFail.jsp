
<h1>予約失敗</h1>

<t:messagesPanel panelElement="div" panelClassName="info"
  panelTypeClassPrefix="" outerElement="p" innerElement=""
  disableHtmlEscape="true"/>

<form name="ticketReserveForm" method="get"
  action="${pageContext.request.contextPath }/Ticket/search">

  <div id="flightlist">
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
      <c:forEach var="reserveFlightBean" items="${selectFlightDtoList}">
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
          </tr>
        </c:forEach>
    </table>
  </div>

  <div class="navi-forward">
    <button type="submit" class="forwardx2_0">空席照会結果画面へ戻る</button>
  </div>  

  <input type="hidden" name="flightSearchCriteriaForm.month" value="${f:h(ticketReserveForm.outwardLineSearchCriteriaForm.month)}" />
  <input type="hidden" name="flightSearchCriteriaForm.day" value="${f:h(ticketReserveForm.outwardLineSearchCriteriaForm.day)}" />
  <input type="hidden" name="flightSearchCriteriaForm.depAirportCd" value="${f:h(ticketReserveForm.outwardLineSearchCriteriaForm.depAirportCd)}" />
  <input type="hidden" name="flightSearchCriteriaForm.arrAirportCd" value="${f:h(ticketReserveForm.outwardLineSearchCriteriaForm.arrAirportCd)}" />
  <input type="hidden" name="flightSearchCriteriaForm.boardingClassCd" value="${f:h(ticketReserveForm.outwardLineSearchCriteriaForm.boardingClassCd)}" />
  <input type="hidden" name="flightSearchCriteriaForm.time" value="${f:h(ticketReserveForm.outwardLineSearchCriteriaForm.time)}" />

</form>