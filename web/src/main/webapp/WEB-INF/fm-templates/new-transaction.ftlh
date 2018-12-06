<#include "header.ftlh">

        <div class="container  container-form">
            <h1 class="display-4">Wpisz dane swojego mieszkania: </h1>
            <br>
            <form>
                <div class="form-group">
                    <label for="exampleFormControlSelect1">Wpisz miasto</label>
                    <input name="city" type="text" class="form-control" id="exampleFormControlSelect1">
                </div>
                <div class="form-group">
                    <label for="exampleFormControlSelect2">Wpisz dzielnice</label>
                    <input name="distric" type="text" class="form-control" id="exampleFormControlSelect2">
                </div>
                <div class="form-group">
                    <label for="exampleFormControlSelect3">Wpisz rodzaj rynku</label>
                    <input name="type-of-market" type="text" class="form-control" id="exampleFormControlSelect3">
                </div>

                <div class="form-group">
                    <label for="exampleFormControlSelect4">Wpisz wielkość mieszkania w m2</label>
                    <input name="load-size" type="text" class="form-control" id="exampleFormControlSelect4" ">
                </div>

                <div class="form-group">
                    <label for="exampleFormControlSelect4">Czy posiadasz miejsce parkingowe ?</label>
                    <input name="parking-spot" type="text" class="form-control" id="exampleFormControlSelect4" ">
                </div>

                <div class="form-group">
                    <label for="exampleFormControlSelect5">Wpisz numer pietra</label>
                    <input name="level" type="text" class="form-control" id="exampleFormControlSelect5">
                </div>

                <div class="form-group">
                    <label for="exampleFormControlSelect6">Wybierz poziom wykończenia twojego mieszkania</label>
                    <input name="standard-level" type="text" class="form-control" id="exampleFormControlSelect6">
                </div>

                <div class="form-group">
                    <label for="exampleFormControlSelect7">Wybierz przedzial czasowy w jakim zostalo wybudowane Twoje mieszkanie</label>
                    <input name="construction-year-category" type="text" class="form-control" id="exampleFormControlSelect7">
                </div>



                <button type="submit" class="btn btn-primary">Wycen</button>
            </form>
        </div>

<#include "footer.ftlh">