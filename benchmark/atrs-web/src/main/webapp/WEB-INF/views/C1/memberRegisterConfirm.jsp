
<!-- 【この下を編集する】 -->
<h1>登録情報確認</h1>

<p class="guide">
  以下の登録内容でよろしければ、[登録]を押してください。<br> ユーザ情報登録画面へ戻る場合は、[前の画面へ戻る]を押してください。
</p>

<!-- エラーメッセージ出力 -->
<t:messagesPanel panelElement="div" panelClassName="info"
  panelTypeClassPrefix="" outerElement="p" innerElement="" />

<form:form name="memberRegisterForm" method="post"
  action="${pageContext.request.contextPath}/Member/register"
  modelAttribute="memberRegisterForm">

  <table>
    <tr>
      <td>氏名（漢字）</td>
      <td>
        ${f:h(memberRegisterForm.memberForm.kanjiFamilyName)}
        &nbsp;
        ${f:h(memberRegisterForm.memberForm.kanjiGivenName)}
      </td>
    </tr>
    <tr>
      <td>氏名（カナ）</td>
      <td>
        ${f:h(memberRegisterForm.memberForm.kanaFamilyName)}
        &nbsp;
        ${f:h(memberRegisterForm.memberForm.kanaGivenName)}
      </td>
    </tr>
    <tr>
      <td>性別</td>
      <td>
        ${f:h(CL_GENDER[memberRegisterForm.memberForm.gender.code])}
      </td>
    </tr>
    <tr>
      <td>生年月日</td>
      <td>
       <fmt:formatNumber value="${memberRegisterForm.memberForm.yearOfBirth}" pattern="0000"/>年<fmt:formatNumber value="${memberRegisterForm.memberForm.monthOfBirth}" pattern="00"/>月<fmt:formatNumber value="${memberRegisterForm.memberForm.dayOfBirth}" pattern="00"/>日
      </td>
    </tr>
    <tr>
      <td>電話番号</td>
      <td>
        ${f:h(memberRegisterForm.memberForm.tel1)}-
        ${f:h(memberRegisterForm.memberForm.tel2)}-
        ${f:h(memberRegisterForm.memberForm.tel3)}
      </td>
    </tr>
    <tr>
      <td>住所</td>
      <td>
        ${f:h(memberRegisterForm.memberForm.zipCode1)}-
        ${f:h(memberRegisterForm.memberForm.zipCode2)}
        <br>
        ${f:h(memberRegisterForm.memberForm.address)}
      </td>
    </tr>
    <tr>
      <td>eメールアドレス</td>
      <td colspan="3" id="mail">
        ${f:h(memberRegisterForm.memberForm.mail)}
      </td>
    </tr>
    <tr>
      <td>クレジットカード会社</td>
      <td>
        ${f:h(CL_CREDITTYPE[memberRegisterForm.memberForm.creditTypeCd])}
      </td>
    </tr>
    <tr>
      <td>クレジットカード番号</td>
      <td>
        ${f:h(memberRegisterForm.memberForm.creditNo)}
      </td>
    </tr>
    <tr>
      <td>クレジットカード有効期限(mm/yy)</td>
      <td>
        ${f:h(memberRegisterForm.memberForm.creditMonth)}/${f:h(memberRegisterForm.memberForm.creditYear)}
      </td>
    </tr>
    <tr>
      <td>パスワード</td>
      <td>
        ************
      </td>
    </tr>
  </table>

  <div class="navi-forward">
    <form:button type="button" class="forward"
      onclick="atrs.checkSubmitAction('memberRegisterForm','/Member/register');">登録</form:button>
  </div>

  <div class="navi-backward">
    <form:button type="button" name="backward" class="backward"
      onclick="atrs.submitAction('memberRegisterForm','/Member/register?redo');">前の画面へ戻る</form:button>
  </div>

  <spring:nestedPath path="memberForm">
    <form:hidden path="kanjiFamilyName"/>
    <form:hidden path="kanjiGivenName"/>
    <form:hidden path="kanaFamilyName"/>
    <form:hidden path="kanaGivenName"/>
    <form:hidden path="gender"/>
    <form:hidden path="yearOfBirth"/>
    <form:hidden path="monthOfBirth"/>
    <form:hidden path="dayOfBirth"/>
    <form:hidden path="tel1"/>
    <form:hidden path="tel2"/>
    <form:hidden path="tel3"/>
    <form:hidden path="zipCode1"/>
    <form:hidden path="zipCode2"/>
    <form:hidden path="address"/>
    <form:hidden path="mail"/>
    <form:hidden path="reEnterMail"/>
    <form:hidden path="creditTypeCd"/>
    <form:hidden path="creditNo"/>
    <form:hidden path="creditMonth"/>
    <form:hidden path="creditYear"/>
  </spring:nestedPath>
  <form:hidden path="password"/>
  <form:hidden path="reEnterPassword"/>


</form:form>