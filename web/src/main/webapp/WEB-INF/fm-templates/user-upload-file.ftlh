<#include "include-user-header.ftlh">

    <div class="container">
        <br><br>

        <div class="jumbotron jumbotron-opacity">

            <div class="row">
                <div class="col-8">
                    <h1 class="display-4">Wyceń mieszkanie</h1>
                    <p class="lead">Wypełnij formularz. Musisz podać parametry Twojego mieszkania..</p>
                    <form method="post" action="load-user-menu">
                        <button type="submit" class="btn btn-primary">Menu</button>
                    </form>
                </div>

<#include "include-user-info.ftlh">

            </div>

            <hr class="my-4">
            <br>
            <form method="POST" action="/users-transactions" enctype="multipart/form-data">
                <input class="btn btn-primary" type="file" name="file" id="file"/> <br/>
                </br>

                <!-- Large modal -->
                <button type="button" class="btn btn-primary" data-toggle="modal" data-target=".bd-example-modal-lg">Załaduj</button>

                <div class="modal fade bd-example-modal-lg" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
                    <div class="modal-dialog modal-lg">
                        <div class="modal-content">
                            <div class="jumbotron">
                                <h1 class="display-4">Uwaga!!!</h1>
                                <p class="lead">Wczytanie nowego pliku usunie wszystkie Twoje niezapisane transakcje z bieżącej sesji.</p>
                                <hr class="my-4">
                                <button type="button" class="btn btn-secondary" data-dismiss="modal">Wróć</button>
                                <input class="btn btn-primary" type="submit" value="Załaduj" name="upload" id="upload"/>
                            </div>

                        </div>
                    </div>
                </div>

            </form>

        </div>

    </div>

</div>
</div>
<#include "include-user-footer.ftlh">


