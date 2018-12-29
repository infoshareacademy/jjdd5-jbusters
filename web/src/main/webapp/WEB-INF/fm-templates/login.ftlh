
<#include "login-header.ftlh">

<#if !session?? >
    <a>zaloguj</a>
</#if>

<h3>Welcome  ${session}</h3>

<#include "footer.ftlh">