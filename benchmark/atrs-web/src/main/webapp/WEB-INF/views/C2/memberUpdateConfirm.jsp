
<h1>変更情報確認</h1>

<p class="guide">
  以下の登録内容でよろしければ、[変更]を押してください。<br>
  ユーザ情報登録画面へ戻る場合は、[前の画面へ戻る]を押してください。
</p>

<form:form  name="memberUpdateForm" method="post"
  action="${pageContext.request.contextPath}/Member/update"
  modelAttribute="memberUpdateForm">

  <table>
    <tr>
      <td>氏名（漢字）</td>
      <td>
        ${f:h(memberUpdateForm.memberForm.kanjiFamilyName)}
        &nbsp;
        ${f:h(memberUpdateForm.memberForm.kanjiGivenName)}
      </td>
    </tr>
    <tr>
      <td>氏名（カナ）</td>
      <td>
        ${f:h(memberUpdateForm.memberForm.kanaFamilyName)}
        &nbsp;
        ${f:h(memberUpdateForm.memberForm.kanaGivenName)}
      </td>
    </tr>
    <tr>
      <td>性別</td>
      <td>
        ${f:h(CL_GENDER[memberUpdateForm.memberForm.gender.code])}
      </td>
    </tr>
    <tr>
      <td>生年月日</td>
      <td>
        <fmt:formatNumber value="${memberUpdateForm.memberForm.yearOfBirth}" pattern="0000"/>年<fmt:formatNumber value="${memberUpdateForm.memberForm.monthOfBirth}" pattern="00"/>月<fmt:formatNumber value="${memberUpdateForm.memberForm.dayOfBirth}" pattern="00"/>日
      </td>
    </tr>
    <tr>
      <td>電話番号</td>
      <td>
        ${f:h(memberUpdateForm.memberForm.tel1)}-${f:h(memberUpdateForm.memberForm.tel2)}-${f:h(memberUpdateForm.memberForm.tel3)}
      </td>
    </tr>
    <tr>
      <td>住所</td>
      <td>
        ${f:h(memberUpdateForm.memberForm.zipCode1)}
        -
        ${f:h(memberUpdateForm.memberForm.zipCode2)}
        <br>
        ${f:h(memberUpdateForm.memberForm.address)}
      </td>
    </tr>
    <tr>
      <td>eメールアドレス</td>
      <td colspan="3" id="mail">
        ${f:h(memberUpdateForm.memberForm.mail)}
      </td>
    </tr>
    <tr>
      <td>クレジットカード会社</td>
      <td>
        ${f:h(CL_CREDITTYPE[memberUpdateForm.memberForm.creditTypeCd])}
      </td>
    </tr>
    <tr>
      <td>クレジットカード番号</td>
      <td>
        ${f:h(memberUpdateForm.memberForm.creditNo)}
      </td>
    </tr>
    <tr>
      <td>クレジットカード有効期限(mm/yy)</td>
      <td>
        ${f:h(memberUpdateForm.memberForm.creditMonth)}/${f:h(memberUpdateForm.memberForm.creditYear)}
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
      onclick="atrs.submitAction('memberUpdateForm','/Member/update');">変更</form:button>
  </div>

  <div class="navi-backward">
    <form:button type="button" name="redo" class="backward"
      onclick="atrs.submitAction('memberUpdateForm','/Member/update?redo');">前の画面へ戻る</form:button>
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
  <form:hidden path="nowPassword"/>
  <form:hidden path="password"/>
  <form:hidden path="reEnterPassword"/>

</form:form>