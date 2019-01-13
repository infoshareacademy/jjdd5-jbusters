<#if user.userRole == 1 >
    <#include "header-admin.ftlh">
<#elseif user.userRole == 2>
    <#include "include-user-header.ftlh">
</#if>
<div class="container">
    <div class="jumbotron jumbotron-opacity">
        <div class="row">
            <div class="col-8">
                <h1 class="display-2">📃🖊️</h1>
                <h2 class="lead">Zapisano nową transakcje</h2>
                <form method="get" action="/show-transaction">
                    <button type="submit" class="btn btn-primary">Zobacz listę transakcji</button>
                </form>
            </div>
        </div>
    </div>

</div>
</div>
</div>
<#include "include-user-footer.ftlh">


