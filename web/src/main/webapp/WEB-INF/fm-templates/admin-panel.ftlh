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

                                    <p class="card-text">Ustaw termin cyklicznej wysyłki korespondencji.</p>

                                    <form method="get" action="scheduler">
                                        <div class="input-group clockpicker">
                                            <input name="time" type="text" class="form-control" value="Wybierz porę dnia" id="SelectTime" required
                                                   autocomplete="off"
                                                   oninvalid="this.setCustomValidity('Proszę ustawić porę dnia w celu zapisu!')"
                                                   oninput="this.setCustomValidity('')">
                                            <div class="input-group-addon input-group-append">
                                                <span class="input-group-text">
                                                    <span class="far fa-clock"></span>
                                                </span>

                                            </div>
                                        </div>
                                        <br>
                                        <select name="day" type="text" class="form-control" id="SelectDay" required
                                                oninvalid="this.setCustomValidity('Proszę wybrać dzień z listy w celu zapisu!')"
                                                oninput="this.setCustomValidity('')">
                                            <option selected="true" disabled="true" value="">Wybierz dzień tygodnia</option>
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
                                        <p class="text-muted btn"><em>${scheduleStatus}</em></p>
                                    </form>

                                    <hr class="my-4">

                                    <p class="card-text">Wysłanie korespondencji do subskrybentów wraz z załączonym raportem.</p>
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
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<#include "include-user-footer.ftlh">


