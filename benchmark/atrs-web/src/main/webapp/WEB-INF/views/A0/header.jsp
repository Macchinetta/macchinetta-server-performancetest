<div id="menu">
  <ul class="main-menu">
    <li class="off" onmouseover="this.className='on'" onmouseout="this.className='off'"><a
      href="#">予約&nbsp;/&nbsp;購入&nbsp;/&nbsp;変更</a>
      <ol class="sub-menu">
        <li class="off2" onmouseover="this.className='on2'" onmouseout="this.className='off2'"><a
          href="${pageContext.request.contextPath}/Ticket/search?form">空席照会・予約</a></li>
      </ol></li>
    <li class="off" onmouseover="this.className='on'" onmouseout="this.className='off'"><security:authorize
        access="!hasRole('MEMBER')">
        <a href="${pageContext.request.contextPath}/Member/register?form">ユーザ情報登録</a>
      </security:authorize> <security:authorize access="hasRole('MEMBER')">
        <a href="${pageContext.request.contextPath}/Member/update?showDetail">ユーザ情報管理</a>
      </security:authorize></li>
  </ul>
</div>
