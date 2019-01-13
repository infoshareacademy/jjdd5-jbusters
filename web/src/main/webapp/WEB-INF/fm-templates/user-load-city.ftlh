<#if user.userRole == 1 >
    <#include "header-admin.ftlh">
<#elseif user.userRole == 2>
    <#include "include-user-header.ftlh">
</#if>

<div class="container">
    <br><br>

    <div class="jumbotron jumbotron-opacity">

        <div class="row">
            <div class="col-8">
                <h1 class="display">Wyceń mieszkanie</h1>
            </div>

        </div>

        <hr class="my-4">
        <div class="container  container-form">

            <form method="get" action="/load-district">
                <div class="form-group">
                    <label for="Select1">Wybierz miasto</label>
                    <select name="city" class="form-control" id="Select1" required>
                        <option></option>
                        <#list cities as c>
                            <option>${c}</option>
                        </#list>
                    </select>
                </div>
                <button type="submit" class="btn btn-primary">Dalej</button>
            </form>
            <div class="card float-right" style="width: 18rem;">
                <div class="card-body">
                    <h5 class="card-title">Brak twojego miasta lub dzielnicy?</h5>
                    <p class="card-text">Jeśli twojego miasta nie ma na liście, daj nam o tym znać! Postaramy się
                        wkrótce
                        uzupełnić naszą bazę!</p>
                    <a href=/send-info class="btn btn-primary">Wyślij wiadomość</a>
                </div>
            </div>
        </div>

    </div>

</div>

</div>
</div>
<#include "include-user-footer.ftlh">


