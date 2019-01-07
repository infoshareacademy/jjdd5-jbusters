<#include "include-user-header.ftlh">
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


