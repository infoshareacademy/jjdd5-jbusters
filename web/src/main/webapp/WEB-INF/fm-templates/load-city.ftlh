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
        <div class="col col-lg-8">
            <a href=/send-info>Brak Twojego miasta lub dzielnicy?</a>
        </div>
    </div>
</div>

<#include "footer.ftlh">