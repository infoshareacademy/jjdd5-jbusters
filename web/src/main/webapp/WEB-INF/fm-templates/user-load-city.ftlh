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
            </div>

        </div>

    </div>

</div>
</div>
<#include "include-user-footer.ftlh">


