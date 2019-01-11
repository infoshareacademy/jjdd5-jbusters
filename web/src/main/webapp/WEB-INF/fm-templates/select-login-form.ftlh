<#include "header.ftlh">
    <div class="container">
        <div class="jumbotron jumbotron-opacity jumbotron-login">
            <h1 class="display-4">Zaloguj się do naszej aplikacji.</h1>
            <p class="lead">Logując się otrzymasz dodatkowe funkcjonalności. Wybierz jeden z dwóch sposobów logowania poniżej...</p>
            <hr class="my-4">
            <div class="card-deck">
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title">Google</h5>
                        <p class="card-text">Jeżeli posiadasz konto w Google możesz je wykoszystać do logowania. Wykorzystaj opcje poniżej.</p>
                        <a class="btn btn-lg g-signin2 lead" data-onsuccess="onSignIn"></a>
                        <br><br>
                        <a href="https://accounts.google.com/signup/v2/webcreateaccount?continue=https%3A%2F%2Fwww.google.pl%2F&hl=en&flowName=GlifWebSignIn&flowEntry=SignUp" class="card-link add-user-link">Załóż konto</a>
                    </div>
                </div>
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title">Nasze konto</h5>
                        <p class="card-text">Jeżeli założyłes konto w naszym serwisie wykorzystaj opcje poniżej.</p>
                        <a href="form-login" class="btn btn-info card-link">Zaloguj się</a><br><br>
                        <a href="/add-user-form" class="card-link add-user-link">Załóż konto</a>
                    </div>
                </div>
            </div>

        </div>
    </div>
</div>
<#include "footer.ftlh">