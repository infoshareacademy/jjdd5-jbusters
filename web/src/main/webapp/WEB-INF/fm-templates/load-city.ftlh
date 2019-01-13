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

    <div class="card float-right" style="width: 18rem;">
        <div class="card-body">
            <h5 class="card-title">Brak twojego miasta lub dzielnicy?</h5>
            <p class="card-text">Jeśli twojego miasta nie ma na liście, daj nam o tym znać! Postaramy się wkrótce
                uzupełnić naszą bazę!</p>
            <a href=/send-info class="btn btn-primary">Wyślij wiadomość</a>
        </div>
    </div>
</div>

<#include "footer.ftlh">