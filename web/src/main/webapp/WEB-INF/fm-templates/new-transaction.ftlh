<#include "header.ftlh">
    <div class="container  container-form">
        <p class="lead">Wczytaj plik z mieszkaniami: </p>
        <form method="POST" action="/users-transactions" enctype="multipart/form-data">
            <input class="btn btn-primary" type="file" name="file" id="file"/> <br/>
            </br>
            <input class="btn btn-primary" type="submit" value="Załaduj" name="upload" id="upload"/>
        </form>
    </div>

    </div>
<#include "footer.ftlh">