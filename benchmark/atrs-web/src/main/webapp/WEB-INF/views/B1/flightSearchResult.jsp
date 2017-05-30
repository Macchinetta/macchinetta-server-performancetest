
<c:choose>
  <c:when test="${ticketSearchForm.selectFlightFormList == null }">
    <c:set var="selectFlightIndex" value="0" />
  </c:when>
  <c:otherwise>
    <c:set var="selectFlightIndex" value="${ticketSearchForm.selectFlightFormList.size()}" />
  </c:otherwise>
</c:choose>

<script type="text/javascript"><!--
  var flightSearchResult = {};
  flightSearchResult.send = function(flightName, fareTypeCd) {
      // 選択されたフライトの情報を送信用の隠し項目に設定
      document.getElementById("selectFlightFormList${selectFlightIndex}.flightName").value = flightName;
      document.getElementById("selectFlightFormList${selectFlightIndex}.fareTypeCd").value = fareTypeCd;
  };
  
  flightSearchResult.changeMonthAndDay = function(month, day) {
      document.getElementById("flightSearchCriteriaForm.month").value = month;
      document.getElementById("flightSearchCriteriaForm.day").value = day;
  };

  flightSearchResult.changeAirportCd = function() {
      var currentDepAirportCd = document.getElementById("flightSearchCriteriaForm.depAirportCd").value;
      document.getElementById("flightSearchCriteriaForm.depAirportCd").value = document.getElementById("flightSearchCriteriaForm.arrAirportCd").value;
      document.getElementById("flightSearchCriteriaForm.arrAirportCd").value = currentDepAirportCd;
  };

  flightSearchResult.changeBoardingClassCd = function(boardingClassCd) {
      document.getElementById("flightSearchCriteriaForm.boardingClassCd").value = boardingClassCd;
  };

  flightSearchResult.goPage = function(page) {
      document.searchForm.page.value = page;
      document.searchForm.submit();
  };
// --></script>

<div class="flow">
  <img src="${pageContext.request.contextPath}/resources/image/flow_2_select.jpg" alt="[選択]">
</div>
    
<h1>空席照会結果</h1>

<p class="header_guide">
   空席状況は以下の通りです。<br/>予約される場合は、〇、△、1～9のボタンを押してください。選択フライト確認画面へ進みます。
</p>

<!-- エラーメッセージ出力 -->
<t:messagesPanel panelElement="div" panelClassName="info"
  panelTypeClassPrefix="" outerElement="p" innerElement="" />

<h2>フライト情報</h2>

<div id="vacantsearch">
  <form name="searchForm" method="get"
    action="${pageContext.request.contextPath}/Ticket/search">
  
    <spring:nestedPath path="ticketSearchForm">
    <table>
      <tr>
        <th class="top_label">搭乗日</th>
        <th class="top_label">区間</th>
        <th class="top_label">搭乗クラス</th>
      </tr>
      <tr>
        <td class="dep_date_td">
        <div>
          <c:if test="${flightSearchResultOutputDto.isDepDateAfterToday}">
            <form:button type="submit" class="util-btn" 
              onclick="flightSearchResult.changeMonthAndDay(${f:hjs(flightSearchResultOutputDto.monthOfPreviousDate)},
                                                            ${f:hjs(flightSearchResultOutputDto.dayOfPreviousDate)});">←前日</form:button>
          </c:if>
        </div>
        <div>
          <fmt:formatDate value="${flightSearchResultOutputDto.depDate}" pattern="M月d日(E)"/>
        </div>
        <div>
          <c:if test="${flightSearchResultOutputDto.isDepDateBeforeLimitDate}">
            <form:button type="submit" class="util-btn"
              onclick="flightSearchResult.changeMonthAndDay(${f:hjs(flightSearchResultOutputDto.monthOfNextDate)},
                                                            ${f:hjs(flightSearchResultOutputDto.dayOfNextDate)});">翌日→</form:button>
          </c:if>
        </div>
        </td>
        <td class="route_td">
          ${f:h(CL_AIRPORT[ticketSearchForm.flightSearchCriteriaForm.depAirportCd])}&nbsp;⇒&nbsp;${f:h(CL_AIRPORT[ticketSearchForm.flightSearchCriteriaForm.arrAirportCd])}
          <form:button type="submit" class="util-btn" onclick="flightSearchResult.changeAirportCd();">⇔逆区間</form:button>
        </td>
        <td class="boarding_class_td">
          ${f:h(CL_BOARDINGCLASS[ticketSearchForm.flightSearchCriteriaForm.boardingClassCd.code])}
          <form:button type="submit" class="util-btn" 
            onclick="flightSearchResult.changeBoardingClassCd('${f:hjs(flightSearchResultOutputDto.otherBoardingClassCd.code)}');" 
            >⇔${f:hjs(CL_BOARDINGCLASS[flightSearchResultOutputDto.otherBoardingClassCd.code])}</form:button>
        </td>
      </tr>
    </table>
    </spring:nestedPath>

    <br/>

    <div class="pagination">
      <c:set var="flightVacantInfoPage" value="${flightSearchResultOutputDto.ticketSearchResultDto.flightVacantInfoPage}" />
      <c:set var="startPosition" value="${(flightVacantInfoPage.number * flightVacantInfoPage.size) + 1}" />
      <c:set var="endPosition" value="${startPosition + flightVacantInfoPage.numberOfElements - 1}" />
      <p>
        ${startPosition}-${endPosition}&nbsp;&nbsp;&nbsp;便を表示（${flightVacantInfoPage.totalElements}便中）
      </p>
      
      <t:pagination
        page="${flightVacantInfoPage}" 
        firstLinkText='<img src="${pageContext.request.contextPath}/resources/image/page_first_on.jpg" alt="先頭へ">'
        previousLinkText='<img src="${pageContext.request.contextPath}/resources/image/page_prev_on.jpg" alt="前へ">'
        nextLinkText='<img src="${pageContext.request.contextPath}/resources/image/page_next_on.jpg" alt="次へ">'
        lastLinkText='<img src="${pageContext.request.contextPath}/resources/image/page_last_on.jpg" alt="最終へ">'
        maxDisplayCount="0"
        disabledHref="javascript:void(0);"
        pathTmpl="javascript:flightSearchResult.goPage({page});" 
        queryTmpl=""/>
    </div>

    <%-- 検索条件の引き継ぎ用の隠し項目 --%>
    <spring:nestedPath path="ticketSearchForm.flightSearchCriteriaForm" >
      <form:hidden path="month" />
      <form:hidden path="day" />
      <form:hidden path="depAirportCd" />
      <form:hidden path="arrAirportCd" />
      <form:hidden path="boardingClassCd" />
      <form:hidden path="time" />
    </spring:nestedPath>

    <spring:nestedPath path="ticketSearchForm.outwardLineSearchCriteriaForm">
      <form:hidden path="month" />
      <form:hidden path="day" />
      <form:hidden path="depAirportCd" />
      <form:hidden path="arrAirportCd" />
      <form:hidden path="boardingClassCd" />
      <form:hidden path="time" />
    </spring:nestedPath>

    <%-- 選択済みフライトの引き継ぎ用の隠し項目 --%>
    <c:forEach items="${ticketSearchForm.selectFlightFormList}" varStatus="rowStatus">
      <spring:nestedPath path="ticketSearchForm.selectFlightFormList[${rowStatus.index}]">
        <form:hidden path="departureDate" />
        <form:hidden path="boardingClassCd" />
        <form:hidden path="fareTypeCd" />
        <form:hidden path="flightName" />
      </spring:nestedPath>
    </c:forEach>

    <input type="hidden" id="page" name="page" />

  </form>
</div>

<div id="vacantflightlist">
  <form name="selectForm" method="get"
    action="${pageContext.request.contextPath}/Ticket/search">

    <spring:nestedPath path="ticketSearchForm">
    <table>
      <%-- 見出し項目 --%>
      <tr>
        <th class="flight_name_th">便名</th>
        <th class="time_th">出発</th>
        <th class="time_th">到着</th>
        <th class="craft_type_th">機種</th>
          
        <c:set var="fareTypeCount" value="${flightSearchResultOutputDto.ticketSearchResultDto.fareTypeList.size()}" />
        <c:forEach var="faretype" items="${flightSearchResultOutputDto.ticketSearchResultDto.fareTypeList}">
          <th class="fare_th">
            <c:choose>
              <c:when test="${'LD' == faretype}">
                <p class="faretype-top">レディース</p>
                <p class="faretype-bottom">割</p>
              </c:when>
              <c:when test="${'SOW' == faretype}">
                <p class="faretype-top">特別</p>
                <p class="faretype-bottom">片道運賃</p>
              </c:when>
              <c:when test="${'SRT' == faretype}">
                <p class="faretype-top">特別</p>
                <p class="faretype-bottom">往復運賃</p>
              </c:when>
              <c:when test="${'SRD' == faretype}">
                <p class="faretype-top">特別</p>
                <p class="faretype-bottom">予約割</p>
              </c:when>
              <c:otherwise>
                <p class="faretype">
                  ${f:h(CL_FARETYPE[faretype.code])}
                </p>
              </c:otherwise>
            </c:choose>
          </th>
        </c:forEach>
        <c:forEach begin="${fareTypeCount}" end="7">
          <th class="null"></th>
        </c:forEach>
      </tr>
        
      <%-- 実データ表示 --%>
      <c:forEach var="flightVacantInfo" items="${flightVacantInfoPage.content}" varStatus="rowStatus">
        <c:set var="flightMaster" value="${flightVacantInfo.flightMaster}" />
        <tr>
          <th class="label">
            ${f:h(flightMaster.flightName)}
          </th>
          <th class="label">
            <fw:split str="${flightMaster.departureTime}" split=":" position="2"/>
          </th>
          <th class="label">
            <fw:split str="${flightMaster.arrivalTime}" split=":" position="2"/>
          </th>
          <th class="label">
            ${f:h(flightMaster.plane.craftType)}
          </th>
             
          <%-- 運賃種別情報 --%>
          <c:forEach var="fareTypeCd" items="${flightSearchResultOutputDto.ticketSearchResultDto.fareTypeList}" varStatus="queStatus">
            <c:set var="fareTypeVacantInfo" value="${flightVacantInfo.fareTypeVacantInfoMap[fareTypeCd]}" />
            <td>
              <c:set var="vacantNum" value="${f:h(fareTypeVacantInfo.vacantNum)}"/>
              <c:choose>
                <c:when test="${vacantNum >= 20 }">
                  <form:button type="submit" name="select" class="vacant-seat-over20"
                    onclick="flightSearchResult.send('${f:hjs(flightMaster.flightName)}','${fareTypeCd}');">〇</form:button>
                </c:when>
                <c:when test="${vacantNum >= 10 && vacantNum < 20}">
                  <form:button type="submit" name="select" class="vacant-seat-over10"
                    onclick="flightSearchResult.send('${f:hjs(flightMaster.flightName)}','${fareTypeCd}');">△</form:button>
                </c:when>
                <c:when test="${vacantNum > 0 && vacantNum < 10 }">
                  <form:button type="submit" name="select" class="vacant-seat-over0"
                    onclick="flightSearchResult.send('${f:hjs(flightMaster.flightName)}','${fareTypeCd}');">${vacantNum}</form:button>
                </c:when>
                <c:otherwise>
                  <form:button type="button" name="select" class="vacant-seat-0 disabled" disabled="true">×</form:button>
                </c:otherwise>
              </c:choose>
              &yen;<fmt:formatNumber value="${fareTypeVacantInfo.fare}" pattern="###,###"/>
            </td>
          </c:forEach>
          <c:forEach begin="${fareTypeCount}" end="7">
            <th class="null"></th>
          </c:forEach>
        </tr>
      </c:forEach>
      
    </table>

    <div id="example">
      <form:button type="button" class="vacant-seat-over20 legend-btn">〇</form:button><span>空席あり(20席以上)</span>
      <form:button type="button" class="vacant-seat-over10 legend-btn">△</form:button><span>空席あり(10～19席)</span>
      <form:button type="button" class="vacant-seat-over0 legend-btn">9</form:button><span>空席あり(1～9席)</span>
      <form:button type="button" class="vacant-seat-0 legend-btn" disabled="true">×</form:button><span>満席</span>
    </div>

    </spring:nestedPath>
    <br/>

    <%-- 検索条件の引き継ぎ用の隠し項目 --%>
    <spring:nestedPath path="ticketSearchForm.flightSearchCriteriaForm">
      <form:hidden path="month" />
      <form:hidden path="day" />
      <form:hidden path="depAirportCd" />
      <form:hidden path="arrAirportCd" />
      <form:hidden path="boardingClassCd" />
      <form:hidden path="time" />
    </spring:nestedPath>

    <spring:nestedPath path="ticketSearchForm.outwardLineSearchCriteriaForm">
      <form:hidden path="month" />
      <form:hidden path="day" />
      <form:hidden path="depAirportCd" />
      <form:hidden path="arrAirportCd" />
      <form:hidden path="boardingClassCd" />
      <form:hidden path="time" />
    </spring:nestedPath>
 
    <%-- 現在のページ位置(フライト選択時にエラーが発生した際に行う再検索で使用する) --%>
    <input type="hidden" name="page" value="${f:h(flightVacantInfoPage.number)}"/>
 
    <%-- 選択済みフライトの引き継ぎ用の隠し項目 --%>
    <c:forEach items="${ticketSearchForm.selectFlightFormList}" varStatus="rowStatus">
      <spring:nestedPath path="ticketSearchForm.selectFlightFormList[${rowStatus.index}]">
        <form:hidden path="departureDate" />
        <form:hidden path="boardingClassCd" />
        <form:hidden path="fareTypeCd" />
        <form:hidden path="flightName" />
      </spring:nestedPath>
    </c:forEach>

    <%-- 選択したフライトの送信用の隠し項目(JavaScriptによって動的に値が設定され送信される) --%>
    <fmt:formatDate value="${flightSearchResultOutputDto.depDate}" pattern="yyyy-MM-dd" var="formattedDepDateString" />
    <spring:nestedPath path="ticketSearchForm.selectFlightFormList[${selectFlightIndex}]">
      <form:hidden path="departureDate" value="${formattedDepDateString}" />
      <form:hidden path="boardingClassCd" value="${ticketSearchForm.flightSearchCriteriaForm.boardingClassCd}" />
      <form:hidden path="fareTypeCd" />
      <form:hidden path="flightName" />
    </spring:nestedPath>

  </form>

</div>

<div class="navi-backward">
  <form name="backForm" method="get"
    action="${pageContext.request.contextPath}/Ticket/search">

    <button type="submit" name="redo" class="backwardx2_0" >空席照会画面へ戻る</button>
    <button type="button" class="backward" onclick="atrs.moveTo('/');">TOPへ戻る</button>

    <input type="hidden" name="flightSearchCriteriaForm.month" value="${f:h(ticketSearchForm.outwardLineSearchCriteriaForm.month)}" />
    <input type="hidden" name="flightSearchCriteriaForm.day" value="${f:h(ticketSearchForm.outwardLineSearchCriteriaForm.day)}" />
    <input type="hidden" name="flightSearchCriteriaForm.depAirportCd" value="${f:h(ticketSearchForm.outwardLineSearchCriteriaForm.depAirportCd)}" />
    <input type="hidden" name="flightSearchCriteriaForm.arrAirportCd" value="${f:h(ticketSearchForm.outwardLineSearchCriteriaForm.arrAirportCd)}" />
    <input type="hidden" name="flightSearchCriteriaForm.boardingClassCd" value="${f:h(ticketSearchForm.outwardLineSearchCriteriaForm.boardingClassCd)}" />
    <input type="hidden" name="flightSearchCriteriaForm.time" value="${f:h(ticketSearchForm.outwardLineSearchCriteriaForm.time)}" />
  </form>
</div>
