
<div class="flow"> 
  <img src="${pageContext.request.contextPath}/resources/image/flow_2_select.jpg" alt="[選択]">
</div> 

<h1>選択フライト確認</h1> 

<p class="header_guide">
  選択されたフライトは下記の通りです。<br/>
  [会員予約]<security:authorize access="!hasRole('MEMBER')" >または[一般予約]</security:authorize>を押してください。お客様情報入力画面に進みます。
</p>

<h2>選択フライト情報</h2> 

<div id="flight">
  <form method="get" action="${pageContext.request.contextPath}/Ticket/search">
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
      <c:forEach var="reserveFlightBean" items="${selectFlightConfirmOutputDto.selectFlightDtoList}" varStatus="flightListRowStatus">
        <tr> 
          <td class="center">
            ${f:h(reserveFlightBean.lineType.name)}
          </td> 
          <td class="center">
            <fmt:formatDate value="${reserveFlightBean.departureDate}" pattern="M月d日(E)"/>
          </td>
          <td class="center">
            ${f:h(reserveFlightBean.flightName)}
          </td> 
          <td class="center">
            <fw:split str="${reserveFlightBean.departureTime}" split=":" position="2"/>
          </td> 
          <td class="center">
            <fw:split str="${reserveFlightBean.arrivalTime}" split=":" position="2"/>
          </td>
          <td class="center">
            ${f:h(CL_AIRPORT[reserveFlightBean.depAirportCd])}&nbsp;⇒&nbsp;${f:h(CL_AIRPORT[reserveFlightBean.arrAirportCd])}
          </td>
          <td class="center">
            ${f:h(CL_BOARDINGCLASS[reserveFlightBean.boardingClassCd.code])}
          </td> 
          <td class="center">
            ${f:h(CL_FARETYPE[reserveFlightBean.fareTypeCd.code])}
          </td> 
          <td class="center">
            &yen;<fmt:formatNumber value="${reserveFlightBean.fare}" pattern="###,###"/>
          </td>

          <c:if test="${selectFlightConfirmOutputDto.hasHomeward}">
            <spring:nestedPath path="ticketReserveSelectFlightForm.selectFlightFormList[${flightListRowStatus.index}]">
              <form:hidden path="departureDate" />
              <form:hidden path="boardingClassCd" />
              <form:hidden path="fareTypeCd" />
              <form:hidden path="flightName" />
            </spring:nestedPath>
          </c:if>

        </tr>
      </c:forEach>
    </table>

    <c:if test="${selectFlightConfirmOutputDto.hasHomeward}">
      <div class="info"> 
        <p>
          続けて[復路の空席照会]を押し、復路のフライトを選択してください。
          <button type="submit" class="backwardx1_5">復路の空席照会</button>
        </p> 
      </div>
    
      <input type="hidden" name="flightSearchCriteriaForm.month" value="${f:h(ticketReserveSelectFlightForm.outwardLineSearchCriteriaForm.month)}" />
      <input type="hidden" name="flightSearchCriteriaForm.day" value="${f:h(ticketReserveSelectFlightForm.outwardLineSearchCriteriaForm.day)}" />
      <input type="hidden" name="flightSearchCriteriaForm.depAirportCd" value="${f:h(ticketReserveSelectFlightForm.outwardLineSearchCriteriaForm.arrAirportCd)}" />
      <input type="hidden" name="flightSearchCriteriaForm.arrAirportCd" value="${f:h(ticketReserveSelectFlightForm.outwardLineSearchCriteriaForm.depAirportCd)}" />
      <input type="hidden" name="flightSearchCriteriaForm.boardingClassCd" value="${f:h(ticketReserveSelectFlightForm.outwardLineSearchCriteriaForm.boardingClassCd)}" />

      <spring:nestedPath path="ticketReserveSelectFlightForm.outwardLineSearchCriteriaForm">
        <form:hidden path="month" />
        <form:hidden path="day" />
        <form:hidden path="depAirportCd" />
        <form:hidden path="arrAirportCd" />
        <form:hidden path="boardingClassCd" />
        <form:hidden path="time" />
      </spring:nestedPath>
    </c:if>

  </form>
</div> 

<p>
  空席照会結果は照会時点の状況ですので、フライトを選択されていましてもその後の状況によりご予約いただけない場合がございます。<br/>
  [空席照会結果画面へ戻る]を押すと、選択されたフライトがクリアされますのでご注意ください。
</p>

<div class="navi-forward">
  <form name="selectFlightForm" method="get">
    <security:authorize access="hasRole('MEMBER')" >
      <button type="submit" name="form" class="forward"
        onclick="atrs.setFormActionAndHttpMethod('selectFlightForm','/Ticket/reserve');">会員予約</button>
    </security:authorize>
    <security:authorize access="!hasRole('MEMBER')">
      <c:set var="backToUrl" value="/Ticket/reserve?" />
      <c:forEach var="requestParam" items="${param }">
        <c:set var="backToUrl" value="${backToUrl}&${requestParam.key}=" />
        <c:if test="${requestParam.key != 'select' }">
          <c:set var="backToUrl" value="${backToUrl}${requestParam.value}" />
        </c:if>
      </c:forEach>
      <input type="hidden" id="backToUrl" name="backToUrl" value="${f:h(backToUrl)}" />
        
      <button type="submit" class="forward" name="member"
        onclick="atrs.setFormActionAndHttpMethod('selectFlightForm','/Ticket/reserve/member');">会員予約</button>
        
      <button type="submit" name="form" class="forward"
        onclick="atrs.setFormActionAndHttpMethod('selectFlightForm','/Ticket/reserve');">一般予約</button>
    </security:authorize>

    <input type="hidden" name="flightSearchCriteriaForm.month" value="${f:h(ticketReserveSelectFlightForm.outwardLineSearchCriteriaForm.month)}" />
    <input type="hidden" name="flightSearchCriteriaForm.day" value="${f:h(ticketReserveSelectFlightForm.outwardLineSearchCriteriaForm.day)}" />
    <input type="hidden" name="flightSearchCriteriaForm.depAirportCd" value="${f:h(ticketReserveSelectFlightForm.outwardLineSearchCriteriaForm.arrAirportCd)}" />
    <input type="hidden" name="flightSearchCriteriaForm.arrAirportCd" value="${f:h(ticketReserveSelectFlightForm.outwardLineSearchCriteriaForm.depAirportCd)}" />
    <input type="hidden" name="flightSearchCriteriaForm.boardingClassCd" value="${f:h(ticketReserveSelectFlightForm.outwardLineSearchCriteriaForm.boardingClassCd)}" />

    <spring:nestedPath path="ticketReserveSelectFlightForm.outwardLineSearchCriteriaForm">
      <form:hidden path="month" />
      <form:hidden path="day" />
      <form:hidden path="depAirportCd" />
      <form:hidden path="arrAirportCd" />
      <form:hidden path="boardingClassCd" />
      <form:hidden path="time" />
    </spring:nestedPath>

    <c:forEach var="reserveFlightBean" items="${selectFlightConfirmOutputDto.selectFlightDtoList}" varStatus="flightListRowStatus">
      <spring:nestedPath path="ticketReserveSelectFlightForm.selectFlightFormList[${flightListRowStatus.index}]">
        <form:hidden path="departureDate" />
        <form:hidden path="boardingClassCd" />
        <form:hidden path="fareTypeCd" />
        <form:hidden path="flightName" />
      </spring:nestedPath>
    </c:forEach>
  </form>
</div>

<div class="navi">
  <form method="get"
    action="${pageContext.request.contextPath}/Ticket/search">
    <button type="submit" class="backwardx2_0" >空席照会結果画面へ戻る</button>
    <button type="button" class="backward" onclick="atrs.moveTo('/');">TOPへ戻る</button>
    <input type="hidden" name="flightSearchCriteriaForm.month" value="${f:h(ticketReserveSelectFlightForm.outwardLineSearchCriteriaForm.month)}" />
    <input type="hidden" name="flightSearchCriteriaForm.day" value="${f:h(ticketReserveSelectFlightForm.outwardLineSearchCriteriaForm.day)}" />
    <input type="hidden" name="flightSearchCriteriaForm.depAirportCd" value="${f:h(ticketReserveSelectFlightForm.outwardLineSearchCriteriaForm.depAirportCd)}" />
    <input type="hidden" name="flightSearchCriteriaForm.arrAirportCd" value="${f:h(ticketReserveSelectFlightForm.outwardLineSearchCriteriaForm.arrAirportCd)}" />
    <input type="hidden" name="flightSearchCriteriaForm.boardingClassCd" value="${f:h(ticketReserveSelectFlightForm.outwardLineSearchCriteriaForm.boardingClassCd)}" />
    <input type="hidden" name="flightSearchCriteriaForm.time" value="${f:h(ticketReserveSelectFlightForm.outwardLineSearchCriteriaForm.time)}" />
  </form>
</div>
