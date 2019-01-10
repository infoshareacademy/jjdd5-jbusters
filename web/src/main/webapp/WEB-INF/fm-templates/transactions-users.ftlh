<#include "header.ftlh">

<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.19/css/jquery.dataTables.min.css">
<script type="text/javascript" charset="utf8" src="https://code.jquery.com/jquery-3.3.1.js"></script>
<script type="text/javascript" charset="utf8"
        src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.min.js"></script>

<#if error?? >

    <div class="container">
        <div class="jumbotron jumbotron-opacity">
            <h1 class="display-10"><p>${error} </p></h1>
            <hr class="my-4">
            <a class="btn btn-secondary btn-lg btn-dark" href="uploadFile.html" role="button">Załaduj plik</a>
            <a class="btn btn-secondary btn-lg btn-dark" href="/load-city" role="button">Wyceń mieszkanie</a>
        </div>
    </div>

<#elseif flats?has_content>
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
        <th>Nazwa</th>
        <th>Miasto</th>
        <th>Dzielnica</th>
        <th>Ulica</th>
        <th>Rodzaj rynku</th>
        <th>Wielkość</th>
        <th>Piętro</th>
        <th>Miejsce postojowe</th>
        <th>Poziom wykończenia</th>
        <th>Rok budowy</th>
        </thead>

        <tbody>

        <#list flats as flat>

            <#if flat.important>
                <tr style="background: lawngreen">
            <#else>
                <tr>
            </#if>

            <td>
                <a class="btn btn-primary btn-lg btn-dark active" role="button"
                   aria-pressed="true"
                   href="/valuation?city=${flat.city}&district=${flat.district}&market-type=${flat.typeOfMarket}&flat-area=${flat.flatArea}&level=${flat.level}&parking-spot=${flat.parkingSpot}&standard-level=${flat.standardLevel}&construction=${flat.constructionYearCategory}">
                    Wyceń ponownie</a>
            </td>

            <td>${flat.transactionName}</td>
            <td>${flat.city}</td>
            <td>${flat.district}</td>
            <td>${flat.street}</td>
            <td>${flat.typeOfMarket}</td>
            <td>${flat.flatArea}m<sup>2</sup></td>
            <td>${flat.level}</td>
            <td>${flat.parkingSpot}</td>
            <td>${flat.standardLevel}</td>
            <#if flat.constructionYearCategory = 1>
                <td>przed 1970r</td>
            <#elseif  flat.constructionYearCategory = 2>
                <td>1970 - 1990r</td>
            <#elseif  flat.constructionYearCategory = 3>
                <td>po roku 1990</td>
            </#if>
            </tr>

        </#list>
        </tbody>
    </table>

    <div class="jumbotron jumbotron-opacity" style="margin: 10px, 30%, 0, 30%">
        <div style="text-align: center">
            <form method="GET" action="/saveAs">
                <input class="btn btn-primary btn-lg btn-dark active" type="submit" value="Pobierz listę mieszkań">
            </form>
            <hr class="my-4">
            <form method="POST" action="/my-flats">

                <!-- Large modal -->
                <button type="button" class="btn btn-primary" data-toggle="modal" data-target=".bd-example-modal-lg">
                    Wyczyść
                    listę
                </button>

                <div class="modal fade bd-example-modal-lg" tabindex="-1" role="dialog"
                     aria-labelledby="myLargeModalLabel"
                     aria-hidden="true">
                    <div class="modal-dialog modal-lg">
                        <div class="modal-content">
                            <div class="jumbotron">
                                <h1 class="display-4">Uważaj!!!😕</h1>
                                <p class="lead">Operacja "Wyszczyść listę" usuwa z bieżącej sesji transakcje, które
                                    załadowałeś."</p>
                                <hr class="my-4">
                                <p>Jeżeli chcesz kontynuować kliknij <b>"Wyczyść listę"</b> w przeciwnym razie naciśnij
                                    <b>"Esc"</b>
                                    na swojej klawiaturze.</p>
                                <input class="btn btn-primary btn-lg btn-dark active" type="submit"
                                       value="Wyczyść listę">
                            </div>
                        </div>
                    </div>
                </div>

            </form>
        </div>
    </div>

<#else>
    <div class="container">
        <div class="jumbotron jumbotron-opacity">
            <h1 class="display-10">Twój plik zawiera błędy i nie jesteśmy w stanie go odczytać. Przykro nam.</h1>
            <hr class="my-4">
        </div>
    </div>
</#if>
<#include "footer.ftlh">