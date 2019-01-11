<#if sessionRole == 1 >
    <#include "header-admin.ftlh">
<#elseif sessionRole == 2>
    <#include "include-user-header.ftlh">
</#if>
<div class="container">
    <br><br>

    <div class="jumbotron jumbotron-opacity">

        <div class="row my-border-bootom">
            <div class="col-8">
                <h1 class="display-4">Edycja zapisanego mieszkania</h1>
            </div>
        </div>

        <div class="jumbotron jumbotron-opacity-1">
            <p class="lead"><b>Twoje mieszkanie:</b></p>
            <form method="post" action="edit-transaction?id=${transactionId}">

                <div class="form-group">
                    <label for="Select1"><b>Miasto:</b></label>
                    <input name="city" type="text" class="form-control" id="Select1" value="${city}" disabled>
                </div>

                <div class="form-group">
                    <label for="Select2"><b>Dzielnica:</b></label>
                    <input name="district" type="text" class="form-control" id="Select2" value="${district}"
                           disabled>
                </div>

                <div class="form-group">
                    <label for="Select3"><b>Rodzaj rynku</b></label>
                    <input name="market-type" type="text" class="form-control" id="Select3" value="${marketType}"
                           disabled>
                </div>

                <div class="form-group">
                    <label for="Select4"><b>Wielkość mieszkania w m2</b></label>
                    <input name="flat-area" type="text" class="form-control" id="Select4" value="${flatArea}"
                           disabled>
                </div>

                <div class="form-group">
                    <label for="Select5"><b>Numer pietra</b></label>
                    <input name="level" type="text" class="form-control" id="Select5" value="${level}" disabled>
                </div>

                <div class="form-group">
                    <label for="Select6"><b>Miejsce parkingowe</b></label>
                    <input name="parking-spot" type="text" class="form-control" id="Select6" value="${parkingSpot}"
                           disabled>
                </div>

                <div class="form-group">
                    <label for="Select7"><b>Poziom wykończenia mieszkania</b></label>
                    <input name="standard-level" type="text" class="form-control" id="Select7"
                           value="${standardLevel}" disabled>
                </div>

                <div class="form-group">
                    <label for="Select8"><b>Przedział czasowy w jakim zostało wybudowane mieszkanie</b></label>
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

                <div class="form-group">
                    <label for="Select13">Chcesz wyróżnić mieszkanie?</label>
                    <select name="important" class="form-control" id="Select13" required>
                        <option>${important}</option>
                        <option>nie</option>
                        <option>tak</option>
                    </select>
                </div>

                <div class="form-group">
                    <label for="Select14">Czy mieszkanie zostało sprzedane?</label>
                    <select name="sale" class="form-control" id="Select14" required>
                        <option>${sale}</option>
                        <option>nie</option>
                        <option>tak</option>
                    </select>
                </div>
                <input class="btn btn-primary" type="submit" value="Zapisz" name="upload" id="upload"/>
                <a href="/show-transaction" type="button" class="btn btn-secondary">Wróć</a>
            </form>
        </div>
    </div>

</div>
<#include "include-user-footer.ftlh">