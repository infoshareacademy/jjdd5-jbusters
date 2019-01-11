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
                <#if size =  0>
                <h1 class="display-4">Brak zapisanych mieszkań</h1>
                <#else >
                <h1 class="display-4">Lista mieszkań</h1>
                <p class="lead">Obecnie posiadasz: <b>${size}</b></p>
            </div>
            </#if>
        </div>

        <#if size gt 0>
            <hr class="my-4">

            <link rel="stylesheet" type="text/css"
                  href="https://cdn.datatables.net/1.10.19/css/jquery.dataTables.min.css">
            <script type="text/javascript" charset="utf8" src="https://code.jquery.com/jquery-3.3.1.js"></script>
            <script type="text/javascript" charset="utf8"
                    src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.min.js"></script>

            <script>
                $(document).ready(function () {
                    $('#flats').DataTable({
                            "dom": '<"top"iflp<"clear">>rt<"bottom"iflp<"clear">>',
                            "language": {
                                "url": "js/dataTable.json"
                            }
                        }
                    );
                });
            </script>

            <table id="flats" class="display nowrap" style="width: 100%">
                <thead>
                <th></th>
                <th>Opis</th>
                <th>Miasto</th>
                <th>Dzielnica</th>
                <th>Ulica</th>
                <th>Sprzedane</th>
                <th></th>
                <th></th>
                </thead>
                <tbody>
                <#list trans as flat>

                    <#if flat.newTransactionImportant == "tak">
                        <tr style="background: lawngreen">
                    <#else>
                        <tr>
                    </#if>
                    <td>
                        <a class="btn btn-primary btn-lg btn-dark active" role="button"
                           aria-pressed="true" href="/valuation?city=${flat.newTransactionCity}&district=${flat.newTransactionDistrict}&market-type=${flat.newTransactionTypeOfMarket}&flat-area=${flat.newTransactionFlatArea}&level=${flat.newTransactionLevel}&parking-spot=${flat.newTransactionParkingSpot}&standard-level=${flat.newTransactionStandardLevel}&construction=${flat.newTransactionConstructionYearCategory}">
                            Wyceń ponownie</a>
                    </td>
                    <td>${flat.newTransactionDescription}</td>
                    <td>${flat.newTransactionCity}</td>
                    <td>${flat.newTransactionDistrict}</td>
                    <td>${flat.newTransactionStreet}</td>
                    <td>${flat.newTransactionSale}</td>
                    <td>
                        <a href="/edit-transaction?id=${flat.newTransactionId}" class="btn btn-dark">Edytuj</a>
                    </td>
                    <td>
                        <button type="button" class="btn btn-danger" data-toggle="modal" data-target="#exampleModal">
                            Usuń
                        </button>

                        <!-- Modal -->
                        <div class="modal fade" id="exampleModal" tabindex="-1" role="dialog"
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
                                        <p>Czy na pewno chcesz usunąć to mieszkanie?</p>
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-primary" data-dismiss="modal">Zamknij
                                        </button>
                                        <a href="/delete-new-transaction?id=${flat.newTransactionId}" type="button"
                                           class="btn btn-danger">Usuń</a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </td>
                    </tr>
                </#list>
                </tbody>
            </table>
        </#if>
    </div>

</div>

</div>
</div>
<#include "include-user-footer.ftlh">


