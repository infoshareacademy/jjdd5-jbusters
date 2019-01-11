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
                    <h1 class="display-4">Wyceń mieszkanie</h1>
                    <p class="lead">Wypełnij formularz. Musisz podać parametry Twojego mieszkania..</p>
                    <form method="post" action="load-user-menu">
                        <button type="submit" class="btn btn-primary">Menu</button>
                    </form>
                </div>
                <#include "include-user-info.ftlh">
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
            </div>
        </div>
    </div>
</div>
</div>
<#include "include-user-footer.ftlh">


