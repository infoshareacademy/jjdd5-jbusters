<#include "header.ftlh">
<script>
    $(document).ready( function () {
    $('#flats').DataTable();
    } );
</script>

    Total flats: ${flats?size}

<table id="flats" class="display" style="width:100%">
    <thead>
    <th>Nazwa</th>
    <th>Miasto</th>
    <th>Dzielnica</th>
    <th>Ulica</th>
    <th>Rodzaj rynku</th>
    <th>Wielkość w m2</th>
    <th>Piętro</th>
    <th>Miejsce postojowe</th>
    <th>Poziom wykończenia</th>
    <th>Kategoria roku budowy</th>
    </thead>

    <tbody>
    <#list flats as flat>
        <#if flat?index % 2 != 0>
            <#assign rowColor="#ffffff">
        <#else>
            <#assign rowColor="#eeeeee">
        </#if>

        <tr bgcolor="${rowColor}">
        <td>${flat.transactionName}</td>
        <td>${flat.city}</td>
        <td>${flat.district}</td>
        <td>${flat.street}</td>
        <td>${flat.typeOfMarket}</td>
        <td>${flat.flatArea}</td>
        <td>${flat.level}</td>
        <td>${flat.parkingSpot}</td>
        <td>${flat.standardLevel}</td>
        <td>${flat.constructionYearCategory}</td>
        </tr>
    </#list>
    </tbody>
</table>

<#include "footer.ftlh">