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
</head>
<body>
<div class="wrap-admin">
    <header>
        <div class="container-fluid">
            <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
                <a class="navbar-brand" href="#">JBusters -></a>
                <a class="navbar-brand" href="#">użytkownik: ${sessionName}</a>
                <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav"
                        aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="navbarNav">
                    <ul class="navbar-nav">
                        <li class="nav-item">
                            <a href="/logout" class="nav-link" onclick="signOut();">Wyloguj</a>
                        </li>
                    </ul>
                </div>
            </nav>
        </div>
    </header>

    <div class="container">
        <br><br>

        <div class="jumbotron jumbotron-opacity">

            <div class="row my-border-bootom">
                <div class="col-8">
                    <h1 class="display-4">Witaj w naszej wyceniarce 😎</h1>
                    <p class="lead">Skorzystaj z poniższych funkcjonalności...</p>
                </div>

                <div class="col-4">
                    <div class="card text-white bg-secondary mb-3" style="max-width: 18rem;">
                        <div class="card-header"><b>Administrator:</b></div>
                        <div class="card-body">
                            <h5 class="card-title">${sessionName}</h5>
                            <h5 class="card-title">${sessionEmail}</h5>
                            <a href="/logout" class="card-link" onclick="signOut();">Wyloguj</a>
                        </div>
                    </div>
                </div>

            </div>
            <br>
            <br>
            <div class="row">

                <div class="col-12">

                    <div class="row">
                        <div class="col-sm-6">
                            <div class="card">
                                <div class="card-body">
                                    <h5 class="card-title">Panel administracyjny</h5>
                                    <h1>⚙️</h1>
                                    <p class="card-text"></p>
                                    <a href="/admin-panel" class="btn btn-primary">Przejdz dalej</a>
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-6">
                            <div class="card">
                                <div class="card-body">
                                    <h5 class="card-title">Wyceń mieszkanie</h5>
                                    <p class="card-text">Wypełnij formularz. Będziesz musiał podać nam parametry Twojego
                                        mieszkania.</p>
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
                                    <a href="/upload-file" class="btn btn-primary">Przejdz dalej</a>
                                </div>
                            </div>
                        </div>
                        <br>
                        <div class="col-sm-6">
                            <div class="card">
                                <div class="card-body">
                                    <h5 class="card-title">Pobierz statystyki</h5>
                                    <p class="card-text">Generuje plik pdf ze statystykami</p>
                                    <a class="btn btn-primary" data-toggle="modal"
                                       data-target="#download">Pobierz</a>


                                    <!-- Modal -->
                                    <div class="modal fade" id="download" tabindex="-1" role="dialog"
                                         aria-labelledby="exampleModalLabel" aria-hidden="true">
                                        <div class="modal-dialog" role="document">
                                            <div class="modal-content">
                                                <div class="modal-header">
                                                    <h5 class="modal-title" id="exampleModalLabel">Pobierz PDF</h5>
                                                    <button type="button" class="close" data-dismiss="modal"
                                                            aria-label="Close">
                                                        <span aria-hidden="true">&times;</span>
                                                    </button>
                                                </div>
                                                <div class="modal-body">
                                                    Czy chcesz zapisać plik .pdf na Swoim dysku?
                                                </div>
                                                <div class="modal-footer">
                                                    <a type="button" class="btn btn-secondary"
                                                       data-dismiss="modal">Nie
                                                    </a>
                                                    <a href="/generate-report" type="button"
                                                       class="btn btn-primary">Tak</a>
                                                </div>
                                            </div>
                                        </div>
                                    </div>

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
        window.location.href = '/logout';
    }
</script>
</body>
</html>


