<#include "header.ftlh">

<div class="container">
    <br>
    <br>
    <br>
    <div class="row">
        <div class="col-3">
            <div class="nav flex-column nav-pills" id="v-pills-tab" role="tablist" aria-orientation="vertical">
                <a class="nav-link active" id="v-pills-home-tab" data-toggle="pill" href="#v-pills-home" role="tab"
                   aria-controls="v-pills-home" aria-selected="true">Wycena mieszkania</a>
                <a class="nav-link" id="v-pills-profile-tab" data-toggle="pill" href="#v-pills-profile" role="tab"
                   aria-controls="v-pills-profile" aria-selected="false">Parametry Twojego mieszkania</a>
                <a class="nav-link" id="v-pills-messages-tab" data-toggle="pill" href="#v-pills-messages" role="tab"
                   aria-controls="v-pills-messages" aria-selected="false">Zapis mieszkania</a>
            </div>
        </div>
        <div class="col-9">
        <div class="tab-content" id="v-pills-tabContent">
        <div class="tab-pane fade show active" id="v-pills-home" role="tabpanel" aria-labelledby="v-pills-home-tab">


            <ul class="nav nav-pills mb-3" id="pills-tab" role="tablist">
                <li class="nav-item">
                    <a class="nav-link active" id="pills-home-tab" data-toggle="pill" href="#pills-home" role="tab" aria-controls="pills-home" aria-selected="true">Wartość</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" id="pills-profile-tab" data-toggle="pill" href="#pills-profile" role="tab" aria-controls="pills-profile" aria-selected="false">Lista transakcji</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" id="pills-contact-tab" data-toggle="pill" href="#pills-contact" role="tab" aria-controls="pills-contact" aria-selected="false">Sczegóły</a>
                </li>
            </ul>
            <div class="tab-content" id="pills-tabContent">
                <div class="tab-pane fade show active" id="pills-home" role="tabpanel" aria-labelledby="pills-home-tab">

                    <div class="jumbotron jumbotron-opacity">

                        <h1 class="display-4">Wartość Twojego mieszkania za m<sup>2</sup> to:</h1>
                        <hr class="my-4">
                        <h1 id="price" class="display-4">${price} zł</h1>
                        <hr class="my-4">

                    </div>


                </div>
                <div class="tab-pane fade" id="pills-profile" role="tabpanel" aria-labelledby="pills-profile-tab">

                    <hr class="my-4">
                    <p class="lead">Do wyceny Twojego mieszkania wykorzystano <b>${listTransactionUseValuation?size}</b> transakcje</p>
                    <br>
                    <p>Lista wykorzystanych transakcji:</p>
                    <table class="table table-striped table-dark">
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
                <div class="tab-pane fade" id="pills-contact" role="tabpanel" aria-labelledby="pills-contact-tab">
                    <div class="jumbotron jumbotron-opacity">
                        <p class="lead">Szczegółowe informacje wyceny</p>
                        <hr class="my-4">
                    <br>
                    <table class="table table-striped table-dark">
                        <thead>
                        <tr>
                            <th scope="col">minimalna wartość wyceny</th>
                            <th scope="col">maksymalna wartość wyceny</th>
                            <th scope="col">średnia wartość wyceny</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td>${minimumPrice}</td>
                            <td>${maxPrice}</td>
                            <td>${averagePrice}</td>
                        </tr>
                        </tbody>
                    </table>
                        <hr class="my-4">
                    </div>
                </div>
            </div>

        </div>
        <div class="tab-pane fade" id="v-pills-profile" role="tabpanel" aria-labelledby="v-pills-profile-tab">

        <div class="jumbotron jumbotron-opacity-1">
            <p class="lead"><b>Twoje mieszkanie:</b> </p>
        <form method="get" action="valuation">

        <div class="form-group">
    <label for="Select1"><b>Miasto:</b></label>
    <input name="city" type="text" class="form-control" id="Select1" value="${city}" disabled>
        </div>

        <div class="form-group">
    <label for="Select2"><b>Dzielnica:</b></label>
    <input name="district" type="text" class="form-control" id="Select2" value="${district1}" disabled>
        </div>

        <div class="form-group">
    <label for="Select3"><b>Rodzaj rynku</b></label>
    <input name="market-type" type="text" class="form-control" id="Select3" value="${market_type}" disabled>
        </div>

        <div class="form-group">
    <label for="Select4"><b>Wielkość mieszkania w m2</b></label>
    <input name="flat-area" type="text" class="form-control" id="Select4" value="${flat_area}" disabled>
        </div>

        <div class="form-group">
    <label for="Select5"><b>Numer pietra</b></label>
    <input name="level" type="text" class="form-control" id="Select5" value="${level}" disabled>
        </div>

        <div class="form-group">
    <label for="Select6"><b>Miejsce parkingowe</b></label>
    <input name="parking-spot" type="text" class="form-control" id="Select6" value="${parking_spot}" disabled>
        </div>

        <div class="form-group">
    <label for="Select7"><b>Poziom wykończenia mieszkania</b></label>
    <input name="standard-level" type="text" class="form-control" id="Select7" value="${standard_level}" disabled>
        </div>

        <div class="form-group">
    <label for="Select8"><b>Przedział czasowy w jakim zostało wybudowane mieszkanie</b></label>
    <p class="small">1 - przed rokiem 1970</p>
    <p class="small">2 - między rokiem 1970 a 1990</p>
    <p class="small">3 - po roku 1990</p>
    <input name="construction" type="text" class="form-control" id="Select8" value="${construction}" disabled>
        </div>
        </form>
        </div>
        </div>

                <div class="tab-pane fade" id="v-pills-messages" role="tabpanel" aria-labelledby="v-pills-messages-tab">

                    <div class="jumbotron jumbotron-opacity-1">

                    <form method="POST" action="/valuation">
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
                            <input name="construction-year" type="text" class="form-control" id="Select12" required >
                        </div>

                        <div class="form-group">
                            <label for="Select13">Chcesz wyróżnić mieszkanie ?</label>
                            <select name="important" class="form-control" id="Select13" required>
                                <option>nie</option>
                                <option>tak</option>
                            </select>
                        </div>
                        <input class="btn btn-primary" type="submit" value="Zapisz w pliku" name="upload" id="upload"/>

                    </form>


                        </div>


                    </div>
                </div>
            </div>
        </div>
        </div>

<script src="js/jquery-3.3.1.slim.min.js"></script>
<#include "footer.ftlh">