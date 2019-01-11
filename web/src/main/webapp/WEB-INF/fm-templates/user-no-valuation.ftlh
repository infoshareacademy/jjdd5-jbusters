<#if sessionRole == 1 >
    <#include "header-admin.ftlh">
<#elseif sessionRole == 2>
    <#include "include-user-header.ftlh">
</#if>
    <div class="container">
        <div class="jumbotron jumbotron-opacity">
            <h1 class="display">Posiadamy za mało transakcji !!! 😓</h1>
            <p class="lead">Do prawidłowej wyceny potrzeba min 11 transakcji.</p>
            <hr class="my-4">
            <a class="btn btn-primary" href="/load-city" role="button">Spróbuj ponownie</a>
        </div>
    </div>
</div>
</div>
<#include "include-user-footer.ftlh">

