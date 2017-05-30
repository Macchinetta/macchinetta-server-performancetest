<h1>空席照会・ご予約</h1>

<form name="flightSearchCriteriaForm" method="get"
  action="${pageContext.request.contextPath}/Ticket/search">
<spring:nestedPath path="ticketSearchForm.flightSearchCriteriaForm">

  <table>
    <tr>
      <th colspan="2">ご希望の内容を入力し、[照会]を押して下さい。</th>
    </tr>
    <tr>
      <th class="label"><p>搭乗日</p></th>
      <td>
        <p>
          <form:select name="month" path="month" class="month" items="${CL_DEPMONTH}" />&nbsp;<form:select name="day" path="day" class="day" items="${CL_DEPDAY}" />
        </p>
        <p>
          <form:select name="time" path="time" class="time" items="${CL_DEPTIME}"/>
        </p>
      </td>
    </tr>
    <tr>
      <th class="label"><p>区間</p></th>
      <td>
        <p>
          <form:select name="depAirportCd" path="depAirportCd" class="depAirportCd" items="${CL_AIRPORT}"/>&nbsp;⇒&nbsp;<form:select name="arrAirportCd" path="arrAirportCd" class="arrAirportCd" items="${CL_AIRPORT}"/>
        </p>
      </td>
    </tr>
    <tr>
      <th class="label"><p>搭乗クラス</p></th>
      <td>
        <form:radiobuttons name="boardingClassCd" path="boardingClassCd" class="boardingClassCd" items="${CL_BOARDINGCLASS}" cssClass="radio_btn"/>
      </td>
    </tr>
  </table>

  <div id="button_area">
    <form:button type="submit" class="forward">照会</form:button>
    <form:button type="reset" class="backward">リセット</form:button>
  </div>

</spring:nestedPath>
</form>
