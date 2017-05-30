<security:authentication property="principal.member" var="member" scope="page"/>

<div id="header_input_area">
  <p>
    ようこそ&nbsp;&nbsp;${f:h(member.kanjiFamilyName)}&nbsp;${f:h(member.kanjiGivenName)}&nbsp;様
  </p>
</div>

<div id="header_button_area">
  <button name="logout" class="button" onclick="atrs.moveTo('/Auth/logout?confirm')">ログアウト</button>
</div>
