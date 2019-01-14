<#include "header-admin.ftlh">
<div class="container">
    <br><br>

    <div class="jumbotron jumbotron-opacity">

        <div class="row">
            <div class="col-8">
                <h1 class="display">Sugestie użytkowników</h1>
                <p class="lead">Liczba sugestii w bazie: <b>${size}</b></p>
            </div>
        </div>

        <table class="table table-striped">
            <thead>
            <tr>
                <th scope="col">Id</th>
                <th scope="col">Miasto</th>
                <th scope="col">Dzielnica</th>
            </tr>
            </thead>
            <tbody>
            <#list suggestions as s>

                <tr>
                    <td><h5>${s.suggestionsId}</h5></td>
                    <td><h5>${s.suggestionsCity}</h5></td>
                    <td><h5>${s.suggestionsDistrict}</h5></td>
                    <td>
                        <button type="button" class="btn btn-danger" data-toggle="modal"
                                data-target="#exampleModal-${s.suggestionsId}">Usuń</button>

                        <!-- Modal -->
                        <div class="modal fade" id="exampleModal-${s.suggestionsId}" tabindex="-1" role="dialog"
                             aria-labelledby="exampleModalLabel" aria-hidden="true">
                            <div class="modal-dialog" role="document">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title" id="exampleModalLabel">Uwaga!</h5>
                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                            <span aria-hidden="true">&times;</span>
                                        </button>
                                    </div>
                                    <div class="modal-body">
                                        <p>Czy na pewno chcesz usunąć sugestie?</p>
                                        <p>Zmiany będą nieodwracalne!</p>
                                    </div>
                                    <div class="modal-footer">
                                        <form method="post" action="load-suggestions?id=${s.suggestionsId}">
                                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Zamknij
                                            </button>
                                            <button type="submit" class="btn btn-danger">Usuń</button>
                                        </form>
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


