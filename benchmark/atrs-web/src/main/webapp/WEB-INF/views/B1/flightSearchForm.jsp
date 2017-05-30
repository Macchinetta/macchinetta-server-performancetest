<script type="text/javascript"><!--
  var flightSearchForm = {};

  /**
   * フライトの検索条件を指定するフォームをデフォルト値にリセットする。
   */
  flightSearchForm.resetFlightSearchCriteriaForm = function() {
      // 初期値をフォームに設定
      document.getElementById("flightSearchCriteriaForm.month").value = "${f:js(flightSearchFormOutputDto.defaultMonth)}";
      document.getElementById("flightSearchCriteriaForm.day").value = "${f:js(flightSearchFormOutputDto.defaultDay)}";
      document.getElementById("flightSearchCriteriaForm.time").value = "${f:js(flightSearchFormOutputDto.defaultTime)}";
      document.getElementById("flightSearchCriteriaForm.depAirportCd").value = "${f:js(flightSearchFormOutputDto.defaultDepAirportCd)}";
      document.getElementById("flightSearchCriteriaForm.arrAirportCd").value = "${f:js(flightSearchFormOutputDto.defaultArrAirportCd)}";
      var boardingClassCdObj = document.getElementsByName("flightSearchCriteriaForm.boardingClassCd");
      for(var i = 0; i < boardingClassCdObj.length; i++) {
          if (boardingClassCdObj[i].value == "${f:js(flightSearchFormOutputDto.defaultBoardingClassCd)}") {
              boardingClassCdObj[i].checked = true;
              break;
          }
      }
  };
// --></script>

<div class="flow">
  <img src="${pageContext.request.contextPath}/resources/image/flow_1_search.jpg" alt="[検索]" />
</div>

<h1>空席照会条件入力</h1>

<p class="header_guide">搭乗日、区間、搭乗クラスを選択し、[照会]を押してください。空席照会画面へ進みます。</p>

<div class="info">
  <p>
    <fmt:formatDate value="${flightSearchFormOutputDto.beginningPeriod}" pattern="M月d日"/> - <fmt:formatDate value="${flightSearchFormOutputDto.endingPeriod}" pattern="M月d日"/>(搭乗分)の空席照会ができます。<br/>
    フライトは片道便か往復便が予約でき、6席まで予約できます。
  </p>
</div>

<%-- エラーメッセージ出力 --%>
<spring:hasBindErrors name="ticketSearchForm">
  <div class="info">
    <p class="error">
      <spring:nestedPath path="ticketSearchForm">
        <form:errors path="*" />
      </spring:nestedPath>
    </p>
  </div>
</spring:hasBindErrors>

<t:messagesPanel panelElement="div" panelClassName="info"
  panelTypeClassPrefix="" outerElement="p" innerElement="" />

<h2>フライト情報</h2>

<form name="flightSearchCriteriaForm" method="get"
  action="${pageContext.request.contextPath}/Ticket/search">
<spring:nestedPath path="ticketSearchForm.flightSearchCriteriaForm">

  <table>
    <tr>
      <th class="top_label">搭乗日</th>
      <th class="top_label">出発時刻</th>
      <th class="top_label">区間</th>
      <th class="top_label">搭乗クラス</th>
    </tr>
    <tr>
      <td>
        <form:select path="month" class="month" items="${CL_DEPMONTH}" />
        <form:select path="day" class="day" items="${CL_DEPDAY}" />
      </td>
      <td>
        <form:select path="time" class="time" items="${CL_DEPTIME}"/>
      </td>
      <td>
        <form:select path="depAirportCd" class="depAirportCd" items="${CL_AIRPORT}"/>
        &nbsp;⇒&nbsp;
        <form:select path="arrAirportCd" class="arrAirportCd" items="${CL_AIRPORT}"/>
      </td>
      <td>
        <form:radiobuttons path="boardingClassCd" items="${CL_BOARDINGCLASS}" cssClass="radio_btn"/>
      </td>
    </tr>
  </table>

  <div>
    <div class="navi-left">
      <form:button type="button" class="backward" onclick="flightSearchForm.resetFlightSearchCriteriaForm();">リセット</form:button>
    </div>
    <div class="navi-right">
      <form:button type="submit" class="forward">照会</form:button>
    </div>
  </div>

  <br />
  <br />

  <div class="navi-backward">
    <form:button type="button" name="backwardTop" class="backward" 
      onclick="atrs.moveTo('/');">TOPへ戻る</form:button>
  </div>

</spring:nestedPath>
</form>
