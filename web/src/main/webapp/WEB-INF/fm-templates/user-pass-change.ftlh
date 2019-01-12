<#include "include-user-header.ftlh">
<div class="container">
    <br><br>

    <div class="jumbotron jumbotron-opacity">

        <div class="row my-border-bootom">


            <div class="col-8">
                <h1 class="display">Zmiana hasła</h1>
            </div>
        </div>
        <div class="jumbotron jumbotron-opacity-1">
            <#if passwordSuccess??>
                <div class="alert alert-success">
                    <strong>${passwordSuccess}</strong>
                </div>
            </#if>
            <#if passwordFail??>
                <div class="alert alert-danger">
                    <strong>${passwordFail}</strong>
                </div>
            </#if>

            <form method="post" action="pass-change">

                <div class="form-group">
                    <label for="inputPassword"> Podaj stare hasło: *</label>
                    <input type="password" name="oldPassword" id="inputPassword" class="form-control" required>
                </div>

                <div class="form-group">
                    <label for="inputPassword">Wpisze nowe hasło: *</label>
                    <input type="password" name="newPassword" id="inputPassword" class="form-control" required>
                </div>

                <div class="form-group">
                    <label for="inputPassword2">Powtórz nowe hasło: *</label>
                    <input type="password" name="newPassword2" id="inputPassword2" class="form-control" required>
                </div>

                <input class="btn btn-primary" type="submit" value="Zapisz" name="upload" id="upload"/>
                <a href="javascript:history.back()" type="button" class="btn btn-secondary">Wróć</a>
            </form>
        </div>
    </div>

</div>
</div>

<#include "include-user-footer.ftlh">