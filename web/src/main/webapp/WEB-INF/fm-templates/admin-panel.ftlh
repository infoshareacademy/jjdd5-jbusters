<#include "header-admin.ftlh">

    <div class="container">
        <br><br>

        <div class="jumbotron jumbotron-opacity">
            <div class="row my-border-bootom">
                <div class="col-8">
                    <h1 class="display">Panel administracyjny ⚙</h1>
                </div>
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
                                    <#if isSchedulerOn>
                                    <a href="/scheduler-stop" class="btn btn-primary" style="float: left">Zatrzymaj</a>
                                        <p class="text-muted btn"><em>Następna wysyłka: ${setDate}</em></p>
                                    </#if>
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
                                    <h5 class="card-title">Waluta</h5>
                                    <hr class="my-4">
                                    <p class="card-text">Załaduj listę aktualnych kursów walut i wybierz, który ma zostać użyty na stronie.</p>
                                    <a href="/exrates" class="btn btn-primary">Załaduj</a>

                                    <#if exRatesMapSize =  0>

                                        <hr class="my-4">
                                        <p class="card-text"><em>Brak kursów, załaduj listę...</em></p>
                                        <p class="card-text"><em>Domyślny kurs: PLN=1.0000</em></p>

                                    </#if>

                                    <#if exRatesMapSize gt 0>

                                        <hr class="my-4">
                                        <h5 class="card-title"><em>Aktualna waluta: ${exRatesGetCurrent}</em></h5>

                                        <form method="get" action="selectexrate">
                                            <select name="ratecombined" type="text" class="form-control" id="SelectRate" required
                                                    oninvalid="this.setCustomValidity('Proszę wybrać walutę z listy!')"
                                                    oninput="this.setCustomValidity('')">
                                                <option selected="true" disabled="true" value="">Wybierz walutę</option>
                                                <option value="PLN1.0000">PLN=1.0000</option>
                                                <#list exRatesMap as key, value>
                                                <option value="${key}${value}">${key}=${value}</option>
                                                </#list>
                                            </select>
                                            <br>
                                            <button type="submit" class="btn btn-primary" style="float: left">Zapisz</button>
                                        </form>

                                        <br>
                                        <hr class="my-4">
                                        <ul style="list-style-type: disc">
                                            <li><p class="card-text"><em>Data aktualizacji kursów:<br>${exRatesMapDate}</em></p></li>
                                            <br>
                                            <li><p class="card-text"><em>Kursy pobrane z:<br>${exRatesURL}</em></p></li>
                                        </ul>

                                    </#if>
                                </div>
                            </div>
                        </div>



                    </div>
                    <div  class="row">
                        <div class="col-sm-6">
                            <div class="card">
                                <div class="card-body">
                                    <h5 class="card-title">Edycja użytkowników</h5>
                                    <p class="card-text">Usuwanie oraz zmiana uprawnień użytkowników</p>
                                    <a href="/admin-users" class="btn btn-primary">Przejdź dalej</a>
                                </div>
                            </div>
                        </div>

                        <div class="col-sm-6">
                            <div class="card">
                                <div class="card-body">
                                    <h5 class="card-title">Sugestie</h5>
                                    <p class="card-text">Sugestie miast i dzielnic Twoich użytkowników</p>
                                    <a href="/load-suggestions" class="btn btn-primary">Przejdź dalej</a>
                                </div>
                            </div>
                        </div>


                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- ClockPicker script -->
<script src="../../js/bootstrap-clockpicker.min.js"></script>
<script>
    $('.clockpicker').clockpicker(
        {
            placement: "bottom",
            align: "left",
            autoclose: true
        })
        .find('input').change(function(){
        console.log(this.value);
    });
</script>

<#include "include-user-footer.ftlh">


