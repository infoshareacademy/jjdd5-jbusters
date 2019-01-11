<#include "header.ftlh">
    <div class="container">
        <div class="jumbotron jumbotron-opacity">
            <h1 class="display-4">Rejestracja</h1>
            <p class="lead">Załóż konto lub zaloguj sie przy pomocy <b>GOOGLE</b>.</p>
            <a href="login-user.html" class="btn btn-primary">Loguj w Google</a>
            <hr class="my-4">
            <form method="post" action="/add-user">

                <div class="form-group">
                    <label for="email">Email: *</label>
                    <input type="email" name="email" id="email" class="form-control" required>
                </div>

                <div class="form-group">
                    <label for="inputPassword">Hasło: *</label>
                    <input type="password" name="password" id="inputPassword" class="form-control" required>
                </div>

                <div class="form-group">
                    <label for="inputPassword2">Powtórz hasło: *</label>
                    <input type="password" name="password2" id="inputPassword2" class="form-control" required>
                </div>

                <div class="form-group">
                    <label for="inputName">Imię:</label>
                    <input type="text" name="name" id="inputName" class="form-control">
                </div>

                <div class="form-group">
                    <label for="inputSurname">Nazwisko:</label>
                    <input type="text" name="surname" id="inputSurname" class="form-control">
                </div>

                <input class="btn btn-primary btn-lg btn-dark" type="submit" value="załóż konto">
            </form>
        </div>
    </div>

</div>
<#include "footer.ftlh">