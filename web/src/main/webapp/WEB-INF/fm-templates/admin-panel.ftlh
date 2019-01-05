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
                    <h1 class="display-4">Panel administracyjny ⚙</h1>
                    <p class="lead">Skorzystaj z poniższych funkcjonalności...</p>
                    <form method="post" action="load-user-menu">
                        <button type="submit" class="btn btn-primary">Menu</button>
                    </form>
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
                                    <h5 class="card-title">Raporty e-mail</h5>
                                    <p class="card-text">Wysłanie cyklicznej korespondencji do subskrybentów wraz z załączonym raportem.</p>
                                    <a href="/generate-email-report" class="btn btn-primary" style="float: left">Wyślij teraz</a>
                                    <p class="text-muted btn"><em>${sentStatus}</em></p>
                                </div>
                            </div>
                        </div>

                        <div class="col-sm-6">
                            <div class="card">
                                <div class="card-body">
                                    <h5 class="card-title">Opcja...</h5>
                                    <p class="card-text">Tekst...</p>
                                    <a href="/load-city" class="btn btn-primary">Przejdz dalej</a>
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


