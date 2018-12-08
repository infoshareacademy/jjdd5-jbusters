<#include "header.ftlh">
    <div class="container  container-form">
        <h1 class="display-4">Wpisz dane swojego mieszkania: </h1>
        <br>
        <form method="get" action="/valuation">

            <div class="form-group">
                <label for="exampleFormControlSelect1">Wybierz miasto</label>
                <select name="city" class="form-control" id="exampleFormControlSelect1">
                    <option></option>
                    <option>Gdynia</option>
                    <option>Sopot</option>
                </select>
            </div>

            <div class="form-group">
                <label for="exampleFormControlSelect2">Wybierz dzielnice</label>
                <select name="district" class="form-control" id="exampleFormControlSelect2">
                    <option></option>
                    <option>Chylonia</option>
                    <option>Oksywie</option>
                    <option>Redłowo</option>
                    <option>Mały Kack</option>
                </select>
            </div>

            <div class="form-group">
                <label for="exampleFormControlSelect3">Wybierz rodzaj rynku</label>
                <select name="market-type" class="form-control" id="exampleFormControlSelect3">
                    <option></option>
                    <option>RYNEK WTÓRNY</option>
                    <option>RYNEK PIERWOTNY</option>
                </select>
            </div>

            <div class="form-group">
                <label for="exampleFormControlSelect4">Wpisz wielkość mieszkania w m2</label>
                <input name="flat-area" type="text" class="form-control" id="exampleFormControlSelect4" ">
            </div>

            <div class="form-group">
                <label for="exampleFormControlSelect6">Wpisz numer pietra</label>
                <input name="level" type="text" class="form-control" id="exampleFormControlSelect6">
            </div>

            <div class="form-group">
                <label for="exampleFormControlSelect5">Czy posiadasz miejsce parkingowe ?</label>
                <select name="parking-spot" class="form-control" id="exampleFormControlSelect5">
                    <option></option>
                    <option>BRAK MP</option>
                    <option>MIEJSCE NAZIEMNE</option>
                    <option>GARAŻ JEDNOSTANOWISKOWY</option>
                    <option>MIEJSCE POSTOJOWE W HALI GARAŻOWEJ</option>
                </select>
            </div>

            <div class="form-group">
                <label for="exampleFormControlSelect7">Wybierz poziom wykończenia twojego mieszkania</label>
                <select name="standard-level" class="form-control" id="exampleFormControlSelect7">
                    <option></option>
                    <option>WYSOKI</option>
                    <option>BARDZO DOBRY</option>
                    <option>DOBRY</option>
                    <option>PRZECIĘTNY</option>
                    <option>NISKI</option>
                    <option>DEWELOPERSKI/DO WYKOŃCZENIA</option>
                </select>
            </div>
            <br>
            <div class="form-group">
                <label for="exampleFormControlSelect8">Wybierz przedzial czasowy w jakim zostalo wybudowane Twoje
                    mieszkanie</label>
                <p class="small">1 - przed rokiem 1970</p>
                <p class="small">2 - między rokiem 1970 a 1990</p>
                <p class="small">3 - po roku 1990</p>
                <select name="construction" class="form-control" id="exampleFormControlSelect8">
                    <option></option>
                    <option>1</option>
                    <option>2</option>
                    <option>3</option>
                    >
                </select>
            </div>

            <button type="submit" class="btn btn-primary">Wyceń</button>
        </form>

    </div>

    </div>
<#include "footer.ftlh">