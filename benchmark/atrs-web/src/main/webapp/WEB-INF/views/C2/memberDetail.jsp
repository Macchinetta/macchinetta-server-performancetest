
<h1>ユーザ情報管理</h1>

<p class="guide">
  現在のご登録内容は以下のとおりです。
  ご登録内容を変更する場合は、[ユーザ情報変更]を押してください。ユーザ情報変更画面へ進みます。
  TOP画面へ戻る場合は、[TOP画面へ戻る]を押してください。 
</p>

<table>
  <tr>
    <td>氏名（漢字）</td>
    <td>
      ${f:h(member.kanjiFamilyName)}
      &nbsp;
      ${f:h(member.kanjiGivenName)}
    </td>
  </tr>
  <tr>
    <td>氏名（カナ）</td>
    <td>
      ${f:h(member.kanaFamilyName)}
      &nbsp;
      ${f:h(member.kanaGivenName)}
    </td>
  </tr>
  <tr>
    <td>性別</td>
    <td>
      ${f:h(CL_GENDER[member.gender.code])}
    </td>
  </tr>
  <tr>
    <td>生年月日</td>
    <td>
      <fmt:formatDate value="${member.birthday}" pattern="Y年MM月dd日"/> 
    </td>
  </tr>
  <tr>
    <td>電話番号</td>
    <td>
      ${f:h(member.tel)}
    </td>
  </tr>
  <tr>
    <td>住所</td>
    <td>
      <fw:split str="${member.zipCode}" position="3" split=" - "/>
      <br>
      ${f:h(member.address)}
    </td>
  </tr>
  <tr>
    <td>eメールアドレス</td>
    <td colspan="3" id="mail">
      ${f:h(member.mail)}
    </td>
  </tr>
  <tr>
    <td>クレジットカード会社</td>
    <td>
      ${f:h(CL_CREDITTYPE[member.creditType.creditTypeCd])}
  </td>
  </tr>
  <tr>
    <td>クレジットカード番号</td>
    <td>
      ${f:h(member.creditNo)}
    </td>
  </tr>
  <tr>
    <td>クレジットカード有効期限(mm/yy)</td>
    <td>
     ${f:h(member.creditTerm)}
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
  <button name="inputForm" onclick="atrs.moveTo('/Member/update?form');" class="forwardx1_5">ユーザ情報変更</button>
</div>

<div class="navi-backward">
  <button name="backwardTop" class="backward" onclick="atrs.moveTo('/');">TOPへ戻る</button>
</div>
