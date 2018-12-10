<#include "header.ftlh">

<div class="container">
        <div class="jumbotron jumbotron-opacity">
            <h1 class="display-4">Wartość twojego mieszkania za m<sup>2</sup> to:</h1>
            <h1 id="price" class="display-4">${cena} zł</h1>
            <hr class="my-4">

    <form method="POST" action="/valuation">
        <input class="btn btn-primary" type="submit" value="Zapisz mieszkanie" name="upload" id="upload"/>
    </form>

        </div>
    </div>

<#include "footer.ftlh">