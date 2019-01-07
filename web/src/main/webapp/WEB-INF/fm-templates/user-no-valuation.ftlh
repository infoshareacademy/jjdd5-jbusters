<#include "include-user-header.ftlh">
    <div class="container">
        <br><br>

        <div class="jumbotron jumbotron-opacity">

            <div class="row">
                <div class="col-8">

                </div>
                <#include "include-user-info.ftlh">

            </div>

            <hr class="my-4">
            <h1 class="display-4">Posiadamy za mało transakcji !!! 😓</h1>
            <p class="lead">Do prawidłowej wyceny potrzeba min 11 transakcji.</p>
            <form method="post" action="load-user-menu">
                <button type="submit" class="btn btn-primary">Menu</button>
            </form>
            <hr class="my-4">

            <a class="btn btn-primary" href="/load-city" role="button">Spróbuj ponownie</a>
        </div>
    </div>
</div>
</div>
<#include "include-user-footer.ftlh">

