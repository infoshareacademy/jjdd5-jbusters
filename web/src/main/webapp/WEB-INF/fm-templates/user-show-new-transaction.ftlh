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
                        <a href="/edit-transaction?id=${flat.newTransactionId}" class="btn btn-dark">Edytuj</a>
                    </td>
                    <td>
                        <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#exampleModal">
                            Usuń
                        </button>

                        <!-- Modal -->
                        <div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
                            <div class="modal-dialog" role="document">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title" id="exampleModalLabel">Uwaga!</h5>
                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                            <span aria-hidden="true">&times;</span>
                                        </button>
                                    </div>
                                    <div class="modal-body">
                                        <p>Czy na pewno chcesz usunąć to mieszkanie?</p>
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Zamknij</button>
                                        <a href="/delete-new-transaction?id=${flat.newTransactionId}" type="button" class="btn btn-primary">Usuń</a>
                                    </div>
                                </div>
                            </div>
                        </div>
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


