<#if user.userRole == 1 >
    <#include "header-admin.ftlh">
<#elseif user.userRole == 2>
    <#include "include-user-header.ftlh">
</#if>

<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.19/css/jquery.dataTables.min.css">
<script type="text/javascript" charset="utf8" src="https://code.jquery.com/jquery-3.3.1.js"></script>
<script type="text/javascript" charset="utf8"
        src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.min.js"></script>

<div class="jumbotron-opacity-1 jumbotron-fluid">
    <br>
    <div class="row">
        <div class="col-2 my-border">
            <div class="nav flex-column nav-pills" id="v-pills-tab" role="tablist" aria-orientation="vertical">
                <a class="nav-link active" id="v-pills-home-tab" data-toggle="pill" href="#v-pills-home" role="tab"
                   aria-controls="v-pills-home" aria-selected="true">Wycena mieszkania</a>
                <a class="nav-link" id="v-pills-profile-tab" data-toggle="pill" href="#v-pills-profile" role="tab"
                   aria-controls="v-pills-profile" aria-selected="false">Parametry Twojego mieszkania</a>
                <a class="nav-link" id="v-pills-messages-tab" data-toggle="pill" href="#v-pills-messages" role="tab"
                   aria-controls="v-pills-messages" aria-selected="false">Zapis mieszkania</a>
            </div>
        </div>
        <div class="col-10">
            <div class="tab-content" id="v-pills-tabContent">
                <div class="tab-pane fade show active" id="v-pills-home" role="tabpanel"
                     aria-labelledby="v-pills-home-tab">


                    <ul class="nav nav-pills mb-3" id="pills-tab" role="tablist">
                        <li class="nav-item">
                            <a class="nav-link active" id="pills-home-tab" data-toggle="pill" href="#pills-home"
                               role="tab" aria-controls="pills-home" aria-selected="true">Wartość</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" id="pills-profile-tab" data-toggle="pill" href="#pills-profile"
                               role="tab" aria-controls="pills-profile" aria-selected="false">Lista transakcji</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" id="pills-contact-tab" data-toggle="pill" href="#pills-contact"
                               role="tab" aria-controls="pills-contact" aria-selected="false">Sczegóły</a>
                        </li>
                    </ul>
                    <div class="tab-content" id="pills-tabContent">
                        <div class="tab-pane fade show active" id="pills-home" role="tabpanel"
                             aria-labelledby="pills-home-tab">

                            <div class="jumbotron jumbotron-opacity-1 my-border-top">

                                <h1 class="display">Wartość Twojego mieszkania to:</h1>
                                <hr class="my-4">
                                <h1 class="display"><b><span style="color: #007bff">${price}</span></b><span
                                            style="color: #343a40"> ${currencyCode} za m<sup>2</sup></span></h1>
                                <h1 class="display"><b><span style="color: #007bff">${priceTotal}</span></b><span
                                            style="color: #343a40"> ${currencyCode} za całe mieszkanie</span></h1>
                            </div>
                        </div>
                        <div class="tab-pane fade" id="pills-profile" role="tabpanel"
                             aria-labelledby="pills-profile-tab">
                            <div class="jumbotron jumbotron-opacity my-border-top" style="width: auto">
                                <hr class="my-4">
                                <script>
                                    $(document).ready(function () {
                                        $('#flats').DataTable({
                                                "dom": '<"top"iflp<"clear">>rt<"bottom"iflp<"clear">>',
                                                "language": {
                                                    "url": "js/dataTable.json"
                                                }
                                            }
                                        );
                                    });
                                </script>
                                <p class="lead">Do wyceny Twojego mieszkania wykorzystano
                                    <b>${listTransactionUseValuation?size}</b> transakcje</p>
                                <br>
                                <p>Lista wykorzystanych transakcji:</p>
                                <table id="flats" class="display nowrap" style="width: 400px">
                                    <thead>
                                    <tr>
                                        <th scope="col">Miasto</th>
                                        <th scope="col">Dzielnica</th>
                                        <th scope="col">Ulica</th>
                                        <th scope="col">Rodzaj rynku</th>
                                        <th scope="col">Metraż</th>
                                        <th scope="col">Piętro</th>
                                        <th scope="col">Parking</th>
                                        <th scope="col">Standard</th>
                                        <th scope="col">Rok budowy</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <#list listTransactionUseValuation as transaction >
                                        <tr>
                                            <td>${transaction.city}</td>
                                            <td>${transaction.district}</td>
                                            <td>${transaction.street}</td>
                                            <td>${transaction.typeOfMarket}</td>
                                            <td>${transaction.flatArea}</td>
                                            <td>${transaction.level}</td>
                                            <td>${transaction.parkingSpot}</td>
                                            <td>${transaction.standardLevel}</td>
                                            <#if transaction.constructionYearCategory = 1>
                                                <td>przed 1970r</td>
                                            <#elseif  transaction.constructionYearCategory = 2>
                                                <td>1970 - 1990r</td>
                                            <#elseif  transaction.constructionYearCategory = 3>
                                                <td>po roku 1990</td>
                                            </#if>
                                        </tr>

                                    </#list>

                                    </tbody>
                                </table>
                            </div>
                        </div>
                        <div class="tab-pane fade" id="pills-contact" role="tabpanel"
                             aria-labelledby="pills-contact-tab">
                            <div class="jumbotron jumbotron-opacity my-border-top" style="width: 1200px">
                                <h3 class="lead">Wartości nieruchomości uwzględnionych przy wycenie twojego
                                    mieszkania</h3>
                                <hr class="my-4">
                                <br>
                                <table class="table table-striped">
                                    <thead class="thead-dark">
                                    <tr>
                                        <th scope="col">Minimalna</th>
                                        <th scope="col">Średnia</th>
                                        <th scope="col">Maksymalna</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <tr>
                                        <td><b>${minimumPrice} ${currencyCode} za m<sup>2</sup></b></td>
                                        <td><b>${averagePrice} ${currencyCode} za m<sup>2</sup></b></td>
                                        <td><b>${maxPrice} ${currencyCode} za m<sup>2</sup></b></td>
                                    </tr>
                                    </tbody>
                                </table>
                                <hr class="my-4">
                            </div>
                        </div>
                    </div>

                </div>
                <div class="tab-pane fade" id="v-pills-profile" role="tabpanel" aria-labelledby="v-pills-profile-tab">

                    <div class="jumbotron jumbotron-opacity-1 my-border-top">
                        <form method="get" action="valuation">

                            <div class="form-group">
                                <label for="Select1"><b>Miasto:</b></label>
                                <input name="city" type="text" class="form-control" id="Select1" value="${city}"
                                       disabled>
                            </div>

                            <div class="form-group">
                                <label for="Select2"><b>Dzielnica:</b></label>
                                <input name="district" type="text" class="form-control" id="Select2"
                                       value="${district1}" disabled>
                            </div>

                            <div class="form-group">
                                <label for="Select3"><b>Rodzaj rynku</b></label>
                                <input name="market-type" type="text" class="form-control" id="Select3"
                                       value="${marketType}" disabled>
                            </div>

                            <div class="form-group">
                                <label for="Select4"><b>Wielkość mieszkania w m2</b></label>
                                <input name="flat-area" type="text" class="form-control" id="Select4"
                                       value="${flatArea}" disabled>
                            </div>

                            <div class="form-group">
                                <label for="Select5"><b>Numer pietra</b></label>
                                <input name="level" type="text" class="form-control" id="Select5" value="${level}"
                                       disabled>
                            </div>

                            <div class="form-group">
                                <label for="Select6"><b>Miejsce parkingowe</b></label>
                                <input name="parking-spot" type="text" class="form-control" id="Select6"
                                       value="${parkingSpot}" disabled>
                            </div>

                            <div class="form-group">
                                <label for="Select7"><b>Poziom wykończenia mieszkania</b></label>
                                <input name="standard-level" type="text" class="form-control" id="Select7"
                                       value="${standardLevel}" disabled>
                            </div>

                            <div class="form-group">
                                <label for="Select8"><b>Przedział czasowy w jakim zostało wybudowane
                                        mieszkanie</b></label>
                                <#if construction = 1 >
                                    <input name="construction" type="text" class="form-control" id="Select8"
                                           value="przed rokiem 1970" disabled>
                                <#elseif construction = 2>
                                    <input name="construction" type="text" class="form-control" id="Select8"
                                           value="między rokiem 1970 a 1990" disabled>
                                <#elseif construction = 3>
                                    <input name="construction" type="text" class="form-control" id="Select8"
                                           value="po roku 1990" disabled>
                                </#if>
                            </div>
                        </form>
                    </div>
                </div>

                <div class="tab-pane fade" id="v-pills-messages" role="tabpanel" aria-labelledby="v-pills-messages-tab">

                    <div class="jumbotron jumbotron-opacity-1 my-border-top">

                        <form method="GET" action="/add-transaction">
                            <div class="form-group">
                                <label for="Select10">Ustaw opis mieszkania</label>
                                <input name="description" type="text" class="form-control" id="Select10" required>
                            </div>

                            <div class="form-group">
                                <label for="Select11">Podaj nazwę ulicy</label>
                                <input name="street" type="text" class="form-control" id="Select11" required>
                            </div>

                            <div class="form-group">
                                <label for="Select12">Podaj rok budowy</label>
                                <input name="construction-year" type="text" class="form-control" id="Select12" required
                                       pattern="^(18|19|20)\d{2}$"
                                       oninvalid="this.setCustomValidity('Wpisana wartość jest nieprawidłowa!')"
                                       oninput="this.setCustomValidity('')">
                            </div>

                            <div class="form-group">
                                <label for="Select13">Chcesz wyróżnić mieszkanie ?</label>
                                <select name="important" class="form-control" id="Select13" required>
                                    <option>nie</option>
                                    <option>tak</option>
                                </select>
                            </div>
                            <input class="btn btn-primary" type="submit" value="Zapisz" name="upload" id="upload"/>

                        </form>

                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!--Koniec formularza szczegółowych informacji o mieszkaniu-->

</div>
</div>
<#include "include-user-footer.ftlh">


