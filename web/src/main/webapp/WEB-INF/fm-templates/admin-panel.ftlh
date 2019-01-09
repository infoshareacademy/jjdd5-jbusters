<#include "include-user-header.ftlh">

    <div class="container">
        <br><br>

        <div class="jumbotron jumbotron-opacity">
            <div class="row my-border-bootom">
                <div class="col-8">
                    <h1 class="display-4">Panel administracyjny ⚙</h1>
                    <p class="lead">Skorzystaj z poniższych funkcjonalności...</p>
                    <form method="post" action="load-user-menu">
                        <button type="submit" class="btn btn-primary">Menu</button>
                    </form>
                </div>

<#include "include-user-info.ftlh">
            </div>
            <br>
            <br>
            <div class="row">

                <div class="col-12">

                    <div class="row">

                        <div class="col-sm-6">
                            <div class="card">
                                <div class="card-body">
                                    <h5 class="card-title">Raporty e-mail</h5>
                                    <p class="card-text">Wysłanie cyklicznej korespondencji do subskrybentów wraz z załączonym raportem.</p>
                                    <a href="/generate-email-report" class="btn btn-primary" style="float: left">Wyślij teraz</a>
                                    <p class="text-muted btn"><em>${sentStatus}</em></p>
                                </div>
                            </div>
                        </div>

                        <div class="col-sm-6">
                            <div class="card">
                                <div class="card-body">
                                    <h5 class="card-title">Opcja...</h5>
                                    <p class="card-text">Tekst...</p>
                                    <a href="/load-city" class="btn btn-primary">Przejdź dalej</a>
                                </div>
                            </div>
                        </div>

                        <div class="col-sm-6">
                            <div class="card">
                                <div class="card-body">
                                    <h5 class="card-title">Edycja użytokowników</h5>
                                    <p class="card-text">Usuwanie oraz zmiana uprawnień urzytkowników</p>
                                    <a href="/admin-users" class="btn btn-primary">Przejdź dalej</a>
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


