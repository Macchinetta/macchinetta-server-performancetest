
<!-- 【この下を編集する】 -->
<h1>ユーザ情報変更</h1>
<p class="guide">
  ユーザ情報を変更し、[確認]を押してください。変更情報確認画面へ進みます。 ユーザ情報管理画面へ戻る場合は、[前の画面へ戻る]を押してください。 入力項目は全て必須です。
</p>

<!-- エラーメッセージ出力 -->
<spring:hasBindErrors name="memberUpdateForm">
  <c:if test="${errors.globalErrorCount > 0}">
    <div class="info">
      <p class="error">
        <spring:nestedPath path="memberUpdateForm">
          <form:errors path="" cssClass="error"/>
        </spring:nestedPath>
      </p>
    </div>
  </c:if>
</spring:hasBindErrors>

<t:messagesPanel panelElement="div" panelClassName="info"
  panelTypeClassPrefix="" outerElement="p" innerElement="" />

<form:form name="memberUpdateForm" method="post"
  action="${pageContext.request.contextPath}/Member/update"
  modelAttribute="memberUpdateForm">

  <table>
    <tr>
      <th colspan="2"><div align="left">氏名を入力してください。</div></th>
    </tr>
    <tr>
      <td class="requireLine">
        <form:label path="memberForm.kanjiFamilyName">氏名（漢字）</form:label>
      </td>
      <td>
        <form:label path="memberForm.kanjiFamilyName">姓</form:label>&nbsp;<form:input path="memberForm.kanjiFamilyName" size="20" /> 
        <form:label path="memberForm.kanjiGivenName">名</form:label>&nbsp;<form:input path="memberForm.kanjiGivenName" size="20"/>
        <form:errors path="memberForm.kanjiFamilyName" cssClass="error" element="p"/>
        <form:errors path="memberForm.kanjiGivenName" cssClass="error" element="p"/>
      </td>
    </tr>
    <tr>
      <td class="requireLine">
        <form:label path="memberForm.kanaFamilyName">氏名（カナ）</form:label>
      </td>
      <td>
        <form:label path="memberForm.kanaFamilyName">セイ</form:label>&nbsp;<form:input  path="memberForm.kanaFamilyName" size="20"/> 
        <form:label path="memberForm.kanaGivenName">メイ</form:label>&nbsp;<form:input  path="memberForm.kanaGivenName" size="20"/>&nbsp;（全角カタカナ）
        <ul class="note">
          <li>国内線予約に使用します。</li>
        </ul>
        <form:errors path="memberForm.kanaFamilyName" cssClass="error" element="p"/>
        <form:errors path="memberForm.kanaGivenName" cssClass="error" element="p"/>
      </td>
    </tr>
    <tr>
      <th colspan="2"><div align="left">性別を指定してください。</div></th>
    </tr>
    <tr>
      <td class="requireLine">性別</td>
      <td>
        <form:radiobuttons path="memberForm.gender" items="${CL_GENDER}" />
        <form:errors path="memberForm.gender" cssClass="error" element="p"/>
      </td>
    </tr>
    <tr>
      <th colspan="2"><div align="left">生年月日を西暦で入力してください。</div></th>
    </tr>
    <tr>
      <td class="requireLine">
        <form:label path="memberForm.yearOfBirth">生年月日</form:label>
      </td>
      <td>
        <form:select path="memberForm.yearOfBirth">
          <form:option value="" label="年" />
          <form:options items="${CL_BIRTHYEAR}" />
        </form:select>&nbsp;&nbsp;&nbsp;
        <form:select path="memberForm.monthOfBirth">
          <form:option value="" label="月" />
          <form:options items="${CL_BIRTHMONTH}" />
        </form:select>&nbsp;&nbsp;&nbsp;
        <form:select path="memberForm.dayOfBirth">
          <form:option value="" label="日" />
          <form:options items="${CL_BIRTHDAY}" />
        </form:select>
        <form:errors path="memberForm.yearOfBirth" cssClass="error" element="p"/>
        <form:errors path="memberForm.monthOfBirth" cssClass="error" element="p"/>
        <form:errors path="memberForm.dayOfBirth" cssClass="error" element="p"/>
      </td>
    </tr>
    <tr>
      <th colspan="2"><div align="left">電話番号を入力してください。</div></th>
    </tr>
    <tr>
      <td class="requireLine">
        <form:label path="memberForm.tel1">電話番号</form:label>
      </td>
      <td>
        <form:input  path="memberForm.tel1" size="5" maxlength="5"/>-
        <form:input path="memberForm.tel2" size="5" maxlength="4" />-
        <form:input  path="memberForm.tel3" size="5" maxlength="4" />&nbsp;&nbsp;（半角数字）
        <form:errors path="memberForm.tel1" cssClass="error" element="p"/>
        <form:errors path="memberForm.tel2" cssClass="error" element="p"/>
        <form:errors path="memberForm.tel3" cssClass="error" element="p"/>
      </td>
    </tr>
    <tr>
      <th colspan="2"><div align="left">住所を入力してください。</div></th>
    </tr>
    <tr>
      <td class="requireLine">
        <form:label path="memberForm.zipCode1">郵便番号</form:label>
      </td>
      <td>
        <form:input  path="memberForm.zipCode1" size="3" maxlength="3" />&nbsp;<form:input path="memberForm.zipCode2" size="4" maxlength="4" />&nbsp;&nbsp;（半角数字）
        <form:errors path="memberForm.zipCode1" cssClass="error" element="p"/>
        <form:errors path="memberForm.zipCode2" cssClass="error" element="p"/>
      </td>
    </tr>
    <tr>
      <td class="requireLine">
        <form:label path="memberForm.address">住所</form:label>
      </td>
      <td>
        <form:input  path="memberForm.address" class="member_address" />&nbsp;&nbsp;（全角）
        <form:errors path="memberForm.address" cssClass="error" element="p"/>
      </td>
    </tr>
    <tr>
      <th colspan="2"><div align="left">eメールアドレスを入力してください。</div></th>
    </tr>
    <tr>
      <td class="requireLine">
        <form:label path="memberForm.mail">eメールアドレス</form:label>
      </td>
      <td>
        <form:input  path="memberForm.mail" class="member_mail" />&nbsp;&nbsp;（メールアドレス形式）
        <form:errors path="memberForm.mail" cssClass="error" element="p"/>
      </td>
    </tr>
    <tr>
      <td class="requireLine">
        <form:label path="memberForm.reEnterMail">再入力</form:label>
      </td>
      <td>
        <form:input  path="memberForm.reEnterMail" class="member_mail" />
        <ul class="input-auxiliary-message">
          <li>確認のため、入力されたメールアドレスをもう一度入力してください。</li>
        </ul>
        <form:errors path="memberForm.reEnterMail" cssClass="error" element="p"/>
      </td>
    </tr>
    <tr>
      <th colspan="2"><div align="left">チケット購入に使用するクレジットカード情報を入力してください。</div></th>
    </tr>
    <tr>
      <td class="requireLine">クレジットカード会社</td>
      <td>
        <form:radiobuttons path="memberForm.creditTypeCd" items="${CL_CREDITTYPE}"/>
        <form:errors path="memberForm.creditTypeCd" cssClass="error" element="p"/>
      </td>
    </tr>
    <tr>
      <td class="requireLine">
        <form:label path="memberForm.creditNo">クレジットカード番号</form:label>
      </td>
      <td>
        <form:input  path="memberForm.creditNo" size="18" maxlength="16" />&nbsp;&nbsp;(半角数字16桁)
        <form:errors path="memberForm.creditNo" cssClass="error" element="p"/>
      </td>
    </tr>
    <tr>
      <td class="requireLine">
        <form:label path="memberForm.creditMonth">クレジットカード有効期限</form:label>
      </td>
      <td>
        <form:select path="memberForm.creditMonth">
          <form:option value="" label="月" />
          <form:options items="${CL_CREDITMONTH}" />
        </form:select>
        /&nbsp;&nbsp;&nbsp;
        <form:select path="memberForm.creditYear">
          <form:option value="" label="年" />
          <form:options items="${CL_CREDITYEAR}" />
        </form:select>&nbsp;(mm/yy)
        <form:errors path="memberForm.creditMonth" cssClass="error" element="p"/>
        <form:errors path="memberForm.creditYear" cssClass="error" element="p"/>
      </td>
    </tr>
    <tr>
      <th colspan="2"><div align="left">ご希望のパスワードを入力してください。(変更しない場合は未記入のまま確認ボタンを押下してください。)</div></th>
    </tr>
    <tr>
      <td class="requireLine">
        <form:label path="nowPassword">現在のパスワード</form:label>
      </td>
      <td>
        <form:password path="nowPassword" maxlength="20" size="20" />&nbsp;&nbsp;(半角英数字8～20桁)
        <form:errors path="nowPassword" cssClass="error" element="p"/>
      </td>
    </tr>
    <tr>
      <td class="requireLine">
        <form:label path="password">パスワード</form:label>
      </td>
      <td>
        <form:password id="updatePassword" path="password" maxlength="20" size="20" />&nbsp;&nbsp;(半角英数字8～20桁)
        <form:errors path="password" cssClass="error" element="p"/>
      </td>
    </tr>
    <tr>
      <td class="requireLine">
        <form:label path="reEnterPassword">再入力</form:label>
      </td>
      <td>
        <form:password path="reEnterPassword" maxlength="20" size="20" />
        <ul class="input-auxiliary-message">
          <li>確認のため、入力されたパスワードをもう一度入力してください。</li>
        </ul>
        <form:errors path="reEnterPassword" cssClass="error" element="p"/>
      </td>
    </tr>
  </table>

  <div class="navi-forward">
    <form:button type="button" name="confirm" class="forward"
      onclick="atrs.submitAction('memberUpdateForm','/Member/update?confirm');">確認</form:button>
  </div>

  <div class="navi-backward">
    <form:button type="button" name="backwardTop" class="backward"
      onclick="atrs.moveTo('/Member/update?backToDetail')">前の画面へ戻る</form:button>
  </div>

</form:form>