<#include "header.ftlh">

<#if error?? >
    <div class="container">
        <div class="jumbotron jumbotron-opacity">
            <div class="alert alert-danger">
                <strong>${error}</strong>
            </div>
        </div>
    </div>
</#if>

<div class="container">
    <div class="jumbotron jumbotron-opacity">
            <h1 class="display">Wybierz plik csv</h1>
            <hr class="my-4">

        <form method="POST" action="/upload-file" enctype="multipart/form-data">
            <input class="btn btn-primary" type="file" name="file" id="file"/> <br/>
            <br/>

            <!-- Large modal -->
            <button type="button" class="btn btn-primary" data-toggle="modal" data-target=".bd-example-modal-lg">
                Załaduj
            </button>

            <div class="modal fade bd-example-modal-lg" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel"
                 aria-hidden="true">
                <div class="modal-dialog modal-lg">
                    <div class="modal-content">
                        <div class="jumbotron">
                            <h1 class="display-4">Uwaga!!!</h1>
                            <p class="lead">Wczytanie nowego pliku usunie wszystkie Twoje niezapisane transakcje z
                                bieżącej sesji.</p>
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

<#include "footer-jquery.ftlh">