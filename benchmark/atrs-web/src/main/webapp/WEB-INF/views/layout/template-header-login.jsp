<%-- ロールによってログイン状態を判断し、ヘッダ表示を制御 --%>
<security:authorize access="!hasRole('MEMBER')">
  <tiles:insertAttribute name="loginForm" />
</security:authorize>
<security:authorize access="hasRole('MEMBER')" >
  <tiles:insertAttribute name="memberInfo" />
</security:authorize>
