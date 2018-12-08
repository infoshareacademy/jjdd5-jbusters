<#include "header.ftlh">

    <div class="container  container-form">

    <form method="get" action="/load-district">
    <div class="form-group">
    <label for="Select1">Wybierz miasto</label>
    <select name="city" class="form-control" id="Select1">
    <option></option>
<#list cities as c>
    <option>${c}</option>
</#list>
    </select>
    </div>
    <button type="submit" class="btn btn-primary">Dalej</button>
    </form>
    </div>

<#include "footer.ftlh">