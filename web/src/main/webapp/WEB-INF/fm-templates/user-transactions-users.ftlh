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
<div class="wrap-user">
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
                            <a href="login-user.html" class="nav-link">Menu</a>
                        </li>
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

            <div class="row">
                <div class="col-8">

                </div>

                <div class="col-4">
                    <div class="card" style="width: 18rem;">
                        <ul class="list-group list-group-flush">
                            <li class="list-group-item">Zalogowano: <b>${sessionEmail}</b></li>
                        </ul>
                        <div class="card-body">
                            <a href="/logout" class="card-link" onclick="signOut();">Wyloguj</a>
                        </div>
                    </div>

                </div>

            </div>

            <hr class="my-4">

            <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.19/css/jquery.dataTables.min.css">
            <script type="text/javascript" charset="utf8" src="https://code.jquery.com/jquery-3.3.1.js"></script>
            <script type="text/javascript" charset="utf8"
                    src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.min.js"></script>

            <#if error?? >

                <div class="container">
                    <div class="jumbotron jumbotron-opacity">
                        <h1 class="display-10"><p>${error} </p></h1>
                        <hr class="my-4">
                        <a class="btn btn-secondary btn-lg btn-dark" href="uploadFile.html" role="button">Załaduj plik</a>
                        <a class="btn btn-secondary btn-lg btn-dark" href="/load-city" role="button">Wyceń mieszkanie</a>
                    </div>
                </div>

            <#elseif flats?has_content>
                <script>
                    $(document).ready(function () {
                        $('#flats').DataTable({
                                "dom": '<"top"iflp<"clear">>rt<"bottom"iflp<"clear">>'
                            }
                        );
                    });
                </script>

                <table id="flats" class="display nowrap" style="width: 100%">
                    <thead>
                    <th></th>
                    <th>Nazwa</th>
                    <th>Miasto</th>
                    <th>Dzielnica</th>
                    <th>Ulica</th>
                    <th>Rodzaj rynku</th>
                    <th>Wielkość</th>
                    <th>Piętro</th>
                    <th>Miejsce postojowe</th>
                    <th>Poziom wykończenia</th>
                    <th>Rok budowy</th>
                    </thead>

                    <tbody>

                    <#list flats as flat>

                        <#if flat.important>
                            <tr style="background: lawngreen">
                        <#else>
                            <tr>
                        </#if>

                        <td>
                            <a class="btn btn-primary btn-lg btn-dark active" role="button"
                               aria-pressed="true" href="/valuation?city=${flat.city}&district=${flat.district}&market-type=${flat.typeOfMarket}&flat-area=${flat.flatArea}&level=${flat.level}&parking-spot=${flat.parkingSpot}&standard-level=${flat.standardLevel}&construction=${flat.constructionYearCategory}">
                                Wyceń ponownie</a>
                        </td>

                        <td>${flat.transactionName}</td>
                        <td>${flat.city}</td>
                        <td>${flat.district}</td>
                        <td>${flat.street}</td>
                        <td>${flat.typeOfMarket}</td>
                        <td>${flat.flatArea}m<sup>2</sup></td>
                        <td>${flat.level}</td>
                        <td>${flat.parkingSpot}</td>
                        <td>${flat.standardLevel}</td>
                        <#if flat.constructionYearCategory = 1>
                            <td>przed 1970r</td>
                        <#elseif  flat.constructionYearCategory = 2>
                            <td>1970 - 1990r</td>
                        <#elseif  flat.constructionYearCategory = 3>
                            <td>po roku 1990</td>
                        </#if>
                        </tr>

                    </#list>
                    </tbody>
                </table>

                <div class="jumbotron jumbotron-opacity" style="margin: 10px, 30%, 0, 30%">
                    <div style="text-align: center">
                        <form method="GET" action="/saveAs">
                            <input class="btn btn-primary btn-lg btn-dark active" type="submit" value="Pobierz listę mieszkań">
                        </form>
                        <hr class="my-4">
                        <form method="POST" action="/saveAs">

                            <!-- Large modal -->
                            <button type="button" class="btn btn-primary" data-toggle="modal" data-target=".bd-example-modal-lg">Wyczyść listę</button>

                            <div class="modal fade bd-example-modal-lg" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
                                <div class="modal-dialog modal-lg">
                                    <div class="modal-content">
                                        <div class="jumbotron">
                                            <h1 class="display-4">Uważaj!!!😕</h1>
                                            <p class="lead">Operacja "Wyszczyść listę" usuwa z bieżącej sesji transakcje, które załadowałeś."</p>
                                            <hr class="my-4">
                                            <p>Jeżeli chcesz kontynuować kliknij <b>"Wyczyść listę"</b> w przeciwnym razie naciśnij <b>"Esc"</b> na swojej klawiaturze.</p>
                                            <input class="btn btn-primary btn-lg btn-dark active" type="submit" value="Wyczyść listę">
                                        </div>
                                    </div>
                                </div>
                            </div>

                        </form>
                    </div>
                </div>

            <#else>
                <div class="container">
                    <div class="jumbotron jumbotron-opacity">
                        <h1 class="display-10">Twój plik zawiera błędy i nie jesteśmy w stanie go odczytać. Przykro nam.</h1>
                        <hr class="my-4">
                    </div>
                </div>
            </#if>

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


