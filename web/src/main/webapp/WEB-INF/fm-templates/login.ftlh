<html>
<head>
    <meta charset="UTF-8">
    <title>JBusters</title>
    <!-- Bootstrap core CSS -->
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link href="css/style.css" rel="stylesheet">

    <!-- Google -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
    <script src="https://apis.google.com/js/platform.js" async defer></script>
    <script src="https://apis.google.com/js/client.js" async defer></script>
    <meta name="google-signin-scope" content="profile email">
    <meta name="google-signin-client_id">

    <script>
        function signOut() {
            console.log("logging out ...");
            gapi.auth.authorize(
                {
                    'client_id': "210558406372-1i31kqkfer8n3pa5dsdkl3udifff4k9v.apps.googleusercontent.com",
                    'scope': "profile email",
                    'immediate': false,
                    cookie_policy: 'single_host_origin',
                    response_type: 'token id_token'
                },
                function (authResult) {
                    gapi.auth.signOut();
                    gapi.auth.disconnect();
                }
            );
            console.log("redirecting to logout servlet");
            window.location.href = 'logout';
        }
    </script>

</head>
<body>
<div class="wrap-user">
    <header>
        <div class="container-fluid">
            <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
                <a class="navbar-brand" href="#">JBusters</a>
                <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav"
                        aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
            </nav>
        </div>
    </header>

    <div class="container">
        <br><br>

        <div class="jumbotron jumbotron-opacity">
            <h1 class="display-4">${sessionName} 😎</h1>
            <p class="lead">Witamy w naszej wspaniałej wyceniarce. Skorzystaj z poniższych funkcjonalności.</p>
            <hr class="my-4">
            <div class="row">
                <div class="col-4">
                    <div class="card" style="width: 18rem;">
                        <div class="card-body">
                            <p class="card-text">Zostałeś zalogowany za pomoca googla. 😂</p>
                        </div>
                        <ul class="list-group list-group-flush">
                            <li class="list-group-item">Twój email: <b>${sessionEmail}</b></li>
                        </ul>
                        <div class="card-body">
                            <a href="index.html" class="card-link" onclick="signOut();">Wyloguj</a>
                        </div>
                    </div>

                </div>
                <div class="col-8">

                    <div class="row">
                        <div class="col-sm-6">
                            <div class="card">
                                <div class="card-body">
                                    <h5 class="card-title">Wyceń mieszkanie</h5>
                                    <p class="card-text">Wypełnij formularz. Będziesz musiał podać nam parametry Twojego mieszkania.</p>
                                    <a href="/load-city" class="btn btn-primary">Przejdz dalej</a>
                                </div>
                            </div>
                        </div>
                        <br>
                        <div class="col-sm-6">
                            <div class="card">
                                <div class="card-body">
                                    <h5 class="card-title">Załaduj plik</h5>
                                    <p class="card-text">Załaduj Twój plik z wcześniej wycenionymi mieszkaniami</p>
                                    <a href="uploadFile.html" class="btn btn-primary">Przejdz dalej</a>
                                </div>
                            </div>
                        </div>
                        <br>
                        <div class="col-sm-6">
                            <div class="card">
                                <div class="card-body">
                                    <h5 class="card-title">Pobierz statystyki</h5>
                                    <p class="card-text">Generuje plik pdf ze statystykami</p>
                                    <a href="/generate-report" class="btn btn-primary">Przejdz dalej</a>
                                </div>
                            </div>
                        </div>
                        <br>
                        <div class="col-sm-6">
                            <div class="card">
                                <div class="card-body">
                                    <h5 class="card-title">Twoje mieszkania</h5>
                                    <p class="card-text">Sprawdź listę mieszkań, które zapisałes w naszym systemie</p>
                                    <a href="/my-flats" class="btn btn-primary">Przejdz dalej</a>
                                </div>
                            </div>
                        </div>

                    </div>
                </div>

            </div>
        </div>



        </div>


    </div>
</div>
<footer>&copy; 2018 JBusters</footer>

<script src="js/jquery-3.3.1.slim.min.js"></script>
<script src="js/bootstrap.min.js"></script>

</body>
</html>


