<#if sessionRole == 1 >
    <#include "header-admin.ftlh">
<#elseif sessionRole == 2>
    <#include "include-user-header.ftlh">
</#if>
<div class="container">
    <br><br>
    <div class="jumbotron jumbotron-opacity">
        <div class="row">
            <div class="col-8">
                <h1 class="display-4">📃🖊️</h1>
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


