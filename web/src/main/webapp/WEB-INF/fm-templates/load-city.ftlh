<#include "header.ftlh">

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

    <div class="row ">
        <div class="col col-lg-8 float-right">
            <div class="card bg-light mb-3 float-right">
                <div class="card-header"><b>Brak twojego miasta lub dzielnicy?</b></div>
                <div class="card-body">
                    <p class="card-text">Jeśli twojego miasta nie ma na liście, daj nam o tym znać! Postaramy się wkrótce
                        uzupełnić naszą bazę!</p>
                    <a href=/send-info class="btn btn-primary float-right">Wyślij wiadomość</a>
                </div>
            </div>
        </div>
    </div>
</div>


<#include "footer.ftlh">