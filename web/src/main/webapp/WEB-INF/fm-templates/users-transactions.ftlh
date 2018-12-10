<#include "header.ftlh">

    <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.19/css/jquery.dataTables.min.css">
    <script type="text/javascript" charset="utf8" src="https://code.jquery.com/jquery-3.3.1.js"></script>
    <script type="text/javascript" charset="utf8"
            src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.min.js"></script>


<#if flats?has_content>
<script>
    $(document).ready(function () {
        $('#flats').DataTable({
            "dom": '<"top"iflp<"clear">>rt<"bottom"iflp<"clear">>'
        });
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
        <tr>
        <td>
        <a class="btn btn-primary btn-lg active" role="button" aria-pressed="true" href="/valuation?city=${flat.city}&district=${flat.district}&market-type=${flat.typeOfMarket}&flat-area=${flat.flatArea}&level=${flat.level}&parking-spot=${flat.parkingSpot}&standard-level=${flat.standardLevel}&construction=${flat.constructionYearCategory}">
            Wyceń</a>
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
<#else>
    <div class="container">
        <div class="jumbotron jumbotron-opacity">
            <h1 class="display-10">Twój plik zawiera błędy i nie jesteśmy w stanie go odczytać. Przykro nam.</h1>
            <hr class="my-4">
        </div>
    </div>
</#if>
<#include "footer.ftlh">