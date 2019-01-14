<#include "header-admin.ftlh">
    <div class="container">
        <br><br>
        <div class="jumbotron jumbotron-opacity">

            <div class="row my-border-bootom">
                <div class="col-8">
                    <h1 class="display">Twoje menu</h1>
                </div>
            </div>
            <br>
            <div class="row">

                <div class="col-12">

                    <div class="row">
                        <div class="col-sm-6">
                            <div class="card">
                                <div class="card-body">
                                    <h5 class="card-title">Panel administracyjny</h5>
                                    <h1>⚙️</h1>
                                    <p class="card-text"></p>
                                    <a href="/admin-panel" class="btn btn-primary">Przejdź dalej</a>
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-6">
                            <div class="card">
                                <div class="card-body">
                                    <h5 class="card-title">Wyceń mieszkanie</h5>
                                    <p class="card-text">Wypełnij formularz. Będziesz musiał podać nam parametry Twojego
                                        mieszkania.</p>
                                    <a href="/load-city" class="btn btn-primary">Przejdź dalej</a>
                                </div>
                            </div>
                        </div>
                        <br>
                        <div class="col-sm-6">
                            <div class="card">
                                <div class="card-body">
                                    <h5 class="card-title">Załaduj plik</h5>
                                    <p class="card-text">Załaduj Twój plik z wcześniej wycenionymi mieszkaniami</p>
                                    <a href="/upload-file" class="btn btn-primary">Przejdź dalej</a>
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-6">
                            <div class="card">
                                <div class="card-body">
                                    <h5 class="card-title">Twój plik CSV</h5>
                                    <p class="card-text">Mieszkania załadowane z Twojego pliku CSV</p>
                                    <a href="/my-flats" class="btn btn-primary">Przejdź dalej</a>
                                </div>
                            </div>
                        </div>
                        <br>
                        <div class="col-sm-6">
                            <div class="card">
                                <div class="card-body">
                                    <h5 class="card-title">Pobierz statystyki</h5>
                                    <p class="card-text">Generuje plik pdf ze statystykami</p>
                                    <a class="btn btn-primary" data-toggle="modal"
                                       data-target="#download">Pobierz</a>


                                    <!-- Modal -->
                                    <div class="modal fade" id="download" tabindex="-1" role="dialog"
                                         aria-labelledby="exampleModalLabel" aria-hidden="true">
                                        <div class="modal-dialog" role="document">
                                            <div class="modal-content">
                                                <div class="modal-header">
                                                    <h5 class="modal-title" id="exampleModalLabel">Pobierz PDF</h5>
                                                    <button type="button" class="close" data-dismiss="modal"
                                                            aria-label="Close">
                                                        <span aria-hidden="true">&times;</span>
                                                    </button>
                                                </div>
                                                <div class="modal-body">
                                                    Czy chcesz zapisać raport ze statystkami na swoim dysku?
                                                </div>
                                                <div class="modal-footer">
                                                    <a type="button" class="btn btn-secondary"
                                                       data-dismiss="modal">Nie
                                                    </a>
                                                    <a href="/generate-report" type="button"
                                                       class="btn btn-primary">Tak</a>
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                </div>
                            </div>
                        </div>
                        <br>
                        <div class="col-sm-6">
                            <div class="card">
                                <div class="card-body">
                                    <h5 class="card-title">Twoje mieszkania</h5>
                                    <p class="card-text">Sprawdź listę mieszkań, które zapisałes w naszym systemie</p>
                                    <a href="/show-transaction" class="btn btn-primary">Przejdź dalej</a>
                                </div>
                            </div>
                        </div>
                    </div>

                </div>

            </div>
        </div>


    </div>


</div>
</div>
<#include "include-user-footer.ftlh">


