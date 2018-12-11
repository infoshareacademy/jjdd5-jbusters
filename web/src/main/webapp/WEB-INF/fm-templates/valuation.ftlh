<#include "header.ftlh">

<div class="container">
    <br>
    <br>
    <br>
    <div class="row">
        <div class="col-3">
            <div class="nav flex-column nav-pills" id="v-pills-tab" role="tablist" aria-orientation="vertical">
                <a class="nav-link active" id="v-pills-home-tab" data-toggle="pill" href="#v-pills-home" role="tab" aria-controls="v-pills-home" aria-selected="true">Wycena mieszkania</a>
                <a class="nav-link" id="v-pills-profile-tab" data-toggle="pill" href="#v-pills-profile" role="tab" aria-controls="v-pills-profile" aria-selected="false">Parametry Twojego mieszkania</a>
                <a class="nav-link" id="v-pills-messages-tab" data-toggle="pill" href="#v-pills-messages" role="tab" aria-controls="v-pills-messages" aria-selected="false">Zapis mieszkania</a>
            </div>
        </div>
        <div class="col-9">
            <div class="tab-content" id="v-pills-tabContent">
                <div class="tab-pane fade show active" id="v-pills-home" role="tabpanel" aria-labelledby="v-pills-home-tab">

                    <div class="jumbotron jumbotron-opacity">

                            <h1 class="display-4">Wartość Twojego mieszkania za m<sup>2</sup> to:</h1>
                            <hr class="my-4">
                            <h1 id="price" class="display-4">${price} zł</h1>
                            <hr class="my-4">

                    </div>

                </div>
                <div class="tab-pane fade" id="v-pills-profile" role="tabpanel" aria-labelledby="v-pills-profile-tab">

                    <div class="jumbotron jumbotron-opacity-1">
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

    </div>

<script src="js/jquery-3.3.1.slim.min.js"></script>
<#include "footer.ftlh">