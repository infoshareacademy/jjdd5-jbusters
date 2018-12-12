<#include "header.ftlh">



        <div class="container  container-form">

            <h2>Podaj szczegółowe parametry mieszkania: </h2>
            <hr class="my-4">
            <form method="get" action="valuation">
                <div class="form-group">
                    <label for="Select1">Wybierz rodzaj rynku</label>
                    <select name="market-type" class="form-control" id="Select1" required>
                        <option></option>
                        <option>RYNEK WTÓRNY</option>
                        <option>RYNEK PIERWOTNY</option>
                    </select>
                </div>

                <div class="form-group">
                    <label for="Select2">Wpisz wielkość mieszkania w m2</label>
                    <input name="flat-area" type="text" class="form-control" id="Select2" required pattern="^[1-9][0-9]?[0-9]?$" title="Podaj wartość od 1 do 999">
                </div>

                <div class="form-group">
                    <label for="Select3">Wpisz numer pietra</label>
                    <input name="level" type="text" class="form-control" id="Select3" required pattern="^[0-1]?[0-9]?[0-9]?$" title="Podaj wartość od 0 do 199">
                </div>

                <#if errors??>
                    <#list errors as k, v>
                        <#if k == "flatAreaError">
                                <div class="p-3 mb-2 bg-danger text-white">${v}</div>
                        </#if>
                    </#list>
                </#if>

                <div class="form-group">
                    <label for="Select4">Czy posiadasz miejsce parkingowe ?</label>
                    <select name="parking-spot" class="form-control" id="Select4" required>
                        <option></option>
                        <option>BRAK MP</option>
                        <option>MIEJSCE NAZIEMNE</option>
                        <option>GARAŻ JEDNOSTANOWISKOWY</option>
                        <option>MIEJSCE POSTOJOWE W HALI GARAŻOWEJ</option>
                    </select>
                </div>

                <div class="form-group">
                    <label for="Select5">Wybierz poziom wykończenia twojego mieszkania</label>
                    <select name="standard-level" class="form-control" id="Select5" required>
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
                    <label for="Select6">Wybierz przedzial czasowy w jakim zostalo wybudowane Twoje mieszkanie</label>
                    <p class="small">1 - przed rokiem 1970</p>
                    <p class="small">2 - między rokiem 1970 a 1990</p>
                    <p class="small">3 - po roku 1990</p>
                    <select name="construction" class="form-control" id="Select6" required>
                        <option></option>
                        <option>1</option>
                        <option>2</option>
                        <option>3</option>>
                    </select>
                </div>

                <button type="submit" class="btn btn-primary">Oblicz</button>

                <div class="form-group hidden">
                    <label for="Select8">Dzielnica: </label>
                    <input name="district" type="text" class="form-control" id="Select8" value="${district1}">
                </div>

                <div class="form-group hidden">
                    <label for="Select8">Miasto: </label>
                    <input name="city" type="text" class="form-control" id="Select8" value="${city}">
                </div>

            </form>
        </div>

<#include "footer.ftlh">


