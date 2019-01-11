<#include "header.ftlh">
    <div class="container">
        <div class="jumbotron jumbotron-opacity">
            <h1 class="display-4">Zaloguj</h1>
            <p class="lead">Zaloguj się przy użyciu Twojego konta w naszej aplikacji.</p>
            <hr class="my-4">
            <form method="post" action="/login">

                <div class="form-group">
                    <label for="email">Podaj email: *</label>
                    <input type="email" name="email" id="email" class="form-control" required>
                </div>

                <div class="form-group">
                    <label for="inputPassword"> Podaj hasło: *</label>
                    <input type="password" name="password" id="inputPassword" class="form-control" required>
                </div>

                <input class="btn btn-primary btn-lg btn-dark" type="submit" value="zaloguj">
            </form>
        </div>
    </div>
</div>
<#include "footer.ftlh">