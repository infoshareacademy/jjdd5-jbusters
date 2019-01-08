<#include "include-user-header.ftlh">
    <div class="container">
        <br><br>

        <div class="jumbotron jumbotron-opacity">

            <div class="row">
                <div class="col-8">
                    <h1 class="display-4">Lista mieszkań</h1>
                    <p class="lead">Obecnie posiadasz: <b>${size}</b></p>
                    <form method="post" action="load-user-menu">
                        <button type="submit" class="btn btn-primary">Menu</button>
                    </form>
                </div>

<#include "include-user-info.ftlh">
            </div>

            <hr class="my-4">

            <table class="table table-striped">
                <thead>
                <tr>
                    <th scope="col">Opis</th>
                    <th scope="col">Miasto</th>
                    <th scope="col">Dzielnica</th>
                    <th scope="col">Ulica</th>
                    <th scope="col">Sprzedane</th>
                </tr>
                </thead>
                <tbody>
                <#list trans as flat>

                    <#if flat.newTransactionImportant == "tak">
                        <tr style="background: lawngreen">
                    <#else>
                        <tr>
                    </#if>
                    <td>${flat.newTransactionDescription}</td>
                    <td>${flat.newTransactionCity}</td>
                    <td>${flat.newTransactionDistrict}</td>
                    <td>${flat.newTransactionStreet}</td>
                    <td>${flat.newTransactionSale}</td>
                    <td>
                        <a href="#" class="btn btn-dark">Edytuj</a>
                    </td>
                    <td>
                        <a href="/delete-new-transaction?id=${flat.newTransactionId}" class="btn btn-danger">Usuń</a>
                    </td>
                    </tr>
                </#list>
                </tbody>
            </table>
        </div>

    </div>

</div>
</div>
<#include "include-user-footer.ftlh">


