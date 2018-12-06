<#include "header.ftlh">

        <div class="container  container-form">
            <h1 class="display-4">Wpisz dane swojego mieszkania: </h1>
            <br>
            <form>
                <div class="form-group">
                    <label for="exampleFormControlSelect1">Wybierz miasto</label>
                    <select name="city" class="form-control" id="exampleFormControlSelect1">
                        <option></option>
                        <option>Gdansk</option>
                        <option>Gdynia</option>
                        <option>Sopot</option>
                    </select>
                </div>
                <div class="form-group">
                    <label for="exampleFormControlSelect2">Wybierz dzielnice</label>
                    <select name="distric" class="form-control" id="exampleFormControlSelect2">
                        <option></option>
                        <option>Wrzeszcz</option>
                        <option>Oliwa</option>
                        <option>Orlowo</option>
                        <option>Chylonia</option>
                    </select>
                </div>
                <div class="form-group">
                    <label for="exampleFormControlSelect3">Wpisz nazwę ulicy</label>
                    <input name="street" type="text" class="form-control" id="exampleFormControlSelect3">
                </div>

                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value="" id="defaultCheck1">
                    <label class="form-check-label" for="defaultCheck1">
                        Rynek pierwotny
                    </label>
                </div>

                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value="" id="defaultCheck2">
                    <label class="form-check-label" for="defaultCheck2">
                        Rynek wtórny
                    </label>
                </div>
                <br>
                <div class="form-group">
                    <label for="exampleFormControlSelect4">Wpisz wielkość mieszkania w m2</label>
                    <input name="loadSize" type="text" class="form-control" id="exampleFormControlSelect4" ">
                </div>

                <div class="form-group">
                    <label for="exampleFormControlSelect5">Czy posiadasz miejsce parkingowe ?</label>
                    <select class="form-control" id="exampleFormControlSelect5">
                        <option>Brak miejsca</option>
                        <option>Miejsce naziemne</option>
                        <option>Garaz jednostanowiskowy</option>
                        <option>Miejsce w hali garazowej</option>
                    </select>
                </div>

                <div class="form-group">
                    <label for="exampleFormControlSelect5">Wpisz numer pietra</label>
                    <input name="level" type="text" class="form-control" id="exampleFormControlSelect5">
                </div>

                <div class="form-group">
                    <label for="exampleFormControlSelect6">Wybierz poziom wykończenia twojego mieszkania</label>
                    <select class="form-control" id="exampleFormControlSelect6">
                        <option></option>
                        <option>Wysoki</option>
                        <option>Bardzo dobry</option>
                        <option>Dobry</option>
                        <option>Przecietny</option>
                        <option>Niski</option>
                        <option>Deweloperski/Do wykonczenia</option>
                    </select>
                </div>

                <div class="form-group">
                    <label for="exampleFormControlSelect7">Wybierz przedzial czasowy w jakim zostalo wybudowane Twoje mieszkanie</label>
                    <select name="distric" class="form-control" id="exampleFormControlSelect7">
                        <option></option>
                        <option>przed rokiem 1970</option>
                        <option>między rokiem 1970 a 1990</option>
                        <option>po roku 1990</option>
                    </select>
                </div>

                <div class="form-group">
                    <label for="exampleFormControlSelect8">Wpisz wartosc za jaka zostala sprzedana Twoja nieruchomosc</label>
                    <input name="loadSize" type="text" class="form-control" id="exampleFormControlSelect8" ">
                </div>

                <div class="form-group">
                    <label for="exampleFormControlSelect9">Wpisz rok budowy mieszkania</label>
                    <input name="loadSize" type="text" class="form-control" id="exampleFormControlSelect9" ">
                </div>

                <button type="submit" class="btn btn-primary">Wycen</button>
            </form>
        </div>

<#include "footer.ftlh">