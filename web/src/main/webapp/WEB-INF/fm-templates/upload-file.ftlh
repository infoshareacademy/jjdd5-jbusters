<#include "header.ftlh">

    <div class="container">
        <div class="jumbotron jumbotron-opacity">
            <h1 class="display-10">Wybierz plik, z którego chcesz załadować swoją listę mieszkań do wyceny.</h1>
            <hr class="my-4">

            <form method="POST" action="/upload-file" enctype="multipart/form-data">
                <input class="btn btn-primary" type="file" name="file" id="file"/> <br/>
                </br>

                <!-- Large modal -->
                <button type="button" class="btn btn-primary" data-toggle="modal" data-target=".bd-example-modal-lg">załaduj</button>

                <div class="modal fade bd-example-modal-lg" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
                    <div class="modal-dialog modal-lg">
                        <div class="modal-content">
                            <div class="jumbotron">
                                <h1 class="display-4">Uwaga!!!️</h1>
                                <p class="lead">Wczytanie nowego pliku usunie wszystkie Twoje niezapisane transakcje z bieżącej sesji.</p>
                                <hr class="my-4">
                                <p>Jeżeli chcesz kontynuować kliknij <b>"załaduj"</b> w przeciwnym razie naciśnij <b>"Esc"</b> na swojej klawiaturze.</p>
                                <input class="btn btn-primary" type="submit" value="Załaduj" name="upload" id="upload"/>
                            </div>

                        </div>
                    </div>
                </div>

            </form>
        </div>
    </div>

</div>

<#include "footer.ftlh">