<#include "header.ftlh">

     <div class="container  container-form">
        <form method="get" action="load-other-values">

            <div class="form-group">
                <label for="Select1">Miasto: </label>
                <input name="city" type="text" class="form-control" id="Select1" value="${city}" disabled>
            </div>

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
    </div>

<#include "footer.ftlh">