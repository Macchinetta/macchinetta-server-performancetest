<div id="header">
  <div id="header-logo">
    <a href="#" onclick="atrs.moveTo('/')">
      <img alt="" src="${pageContext.request.contextPath}/resources/image/logo.jpg">
    </a>
  </div>
  <div id="header-content">
    <div id="header-login">
    </div>
    <div id="header-menu">
      <div id="menu">
        <ul class="main-menu">
          <li class="off" onmouseover="this.className='on'" onmouseout="this.className='off'">
            <a href="#">予約&nbsp;/&nbsp;購入&nbsp;/&nbsp;変更</a>
            <ol class="sub-menu">
              <li class="off2" onmouseover="this.className='on2'" onmouseout="this.className='off2'">
                <a href="${pageContext.request.contextPath}/Ticket/search?form">空席照会・予約</a>
              </li>
            </ol>
          </li>
        </ul>
      </div>
    </div>
  </div>
</div>
