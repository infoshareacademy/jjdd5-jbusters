<#if user.userRole == 1 >
    <#include "header-admin.ftlh">
<#elseif user.userRole == 2>
    <#include "include-user-header.ftlh">
</#if>
<div class="container">
    <div class="jumbotron jumbotron-opacity">
        <div class="row">
            <div class="col-8">
                <h1 class="display">Wyceń mieszkanie</h1>
            </div>
        </div>
        <hr class="my-4">
        <div class="container  container-form">
            <form method="get" action="load-other-values">

                <div class="form-group">
                    <label for="Select1">Miasto: </label>
                    <input name="city" type="text" class="form-control" id="Select1" value="${city}" disabled>
                </div

                <div class="form-group">
                    <label for="Select2">Wybierz dzielnice</label>
                    <select name="district" class="form-control" id="Select2" required>
                        <option></option>
                        <#list district as d>
                            <option>${d}</option>
                        </#list>
                    </select>
                </div>
                <button type="submit" class="btn btn-primary">Dalej</button>

                <div class="form-group hidden">
                    <label for="Select1">Miasto: </label>
                    <input name="city" type="text" class="form-control" id="Select1" value="${city}">
                </div>
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


