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
                                    <hr class="my-4">
                                    <p class="card-text">Wysłanie korespondencji do subskrybentów wraz z załączonym raportem.</p>
                                    <a href="/generate-email-report" class="btn btn-primary" style="float: left">Wyślij teraz</a>
                                    <p class="text-muted btn"><em>${sentStatus}</em></p>
                                    <br>
                                    <hr class="my-4">
                                    <p class="card-text">Ustaw termin cyklicznej wysyłki korespondencji.</p>
                                    <form method="get" action="address">
                                        <select name="construction" class="form-control" id="Select1" required>
                                            <option selected>Wybierz dzień tygodnia</option>
                                            <option value="2">Poniedziałek</option>
                                            <option value="3">Wtorek</option>
                                            <option value="4">Środa</option>
                                            <option value="5">Czwartek</option>
                                            <option value="6">Piątek</option>
                                            <option value="7">Sobota</option>
                                            <option value="1">Niedziela</option>
                                        </select>
                                        <br>
                                        <select name="construction" class="form-control" id="Select1" required>
                                            <option selected>Wybierz dzień tygodnia</option>
                                            <option value="2">Poniedziałek</option>
                                            <option value="3">Wtorek</option>
                                            <option value="4">Środa</option>
                                            <option value="5">Czwartek</option>
                                            <option value="6">Piątek</option>
                                            <option value="7">Sobota</option>
                                            <option value="1">Niedziela</option>
                                        </select>
                                        <br>
                                            <button type="submit" class="btn btn-primary" style="float: left">Zapisz</button>
                                        <p class="text-muted btn"><em>test</em></p>
                                    </form>
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
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<#include "include-user-footer.ftlh">


