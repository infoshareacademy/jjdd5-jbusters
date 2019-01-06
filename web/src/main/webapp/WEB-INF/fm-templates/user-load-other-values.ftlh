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
                    <h1 class="display-4">Wyceń mieszkanie</h1>
                    <p class="lead">Wypełnij formularz. Musisz podać parametry Twojego mieszkania..</p>
                    <form method="post" action="load-user-menu">
                        <button type="submit" class="btn btn-primary">Menu</button>
                    </form>
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

            <!--Początek formularza szczegółowych informacji o mieszkaniu-->
            <div class="container  container-form">

                <#if errors??>
                    <h2>Podaj szczegółowe parametry mieszkania: </h2>
                    <hr class="my-4">
                    <form method="get" action="valuation">

                        <#if errors['overallError']??>
                            <div class="p-3 mb-2 bg-danger text-white">${errors['overallError']}</div>
                        </#if>

                        <div class="form-group">
                            <label for="Select1">Wybierz rodzaj rynku</label>
                            <select name="market-type" class="form-control" id="Select1" required>
                                <option>${marketType}</option>
                                <option>RYNEK WTÓRNY</option>
                                <option>RYNEK PIERWOTNY</option>
                            </select>
                        </div>
                        <#if errors['marketError']??>
                            <div class="p-3 mb-2 bg-danger text-white">${errors['marketError']}</div>
                        </#if>

                        <div class="form-group">
                            <label for="Select2">Wpisz wielkość mieszkania w m2</label>
                            <input name="flat-area" type="text" class="form-control" id="Select2" required
                                   pattern="^[1-9][0-9]?[0-9]?$" title="Podaj wartość od 1 do 999" value="${flatArea}"
                                   oninvalid="this.setCustomValidity('Wpisana wartość jest nieprawidłowa!')"
                                   oninput="this.setCustomValidity('')">
                        </div>

                        <#if errors['flatAreaError']??>
                            <div class="p-3 mb-2 bg-danger text-white">${errors['flatAreaError']}</div>
                        </#if>

                        <div class="form-group">
                            <label for="Select3">Wpisz numer pietra</label>
                            <input name="level" type="text" class="form-control" id="Select3" required
                                   pattern="^[0-1]?[0-9]?[0-9]?$" title="Podaj wartość od 0 do 199" value="${level}"
                                   oninvalid="this.setCustomValidity('Wpisana wartość jest nieprawidłowa!')"
                                   oninput="this.setCustomValidity('')">
                        </div>

                        <#if errors['levelError']??>
                            <div class="p-3 mb-2 bg-danger text-white">${errors['levelError']}</div>
                        </#if>

                        <div class="form-group">
                            <label for="Select4">Czy posiadasz miejsce parkingowe ?</label>
                            <select name="parking-spot" class="form-control" id="Select4" required>
                                <option>${parkingSpot}</option>
                                <option>BRAK MP</option>
                                <option>MIEJSCE NAZIEMNE</option>
                                <option>GARAŻ JEDNOSTANOWISKOWY</option>
                                <option>MIEJSCE POSTOJOWE W HALI GARAŻOWEJ</option>
                            </select>
                        </div>

                        <div class="form-group">
                            <label for="Select5">Wybierz poziom wykończenia twojego mieszkania</label>
                            <select name="standard-level" class="form-control" id="Select5" required>
                                <option>${standardLevel}</option>
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
                                <option>${construction}</option>
                                <option>1</option>
                                <option>2</option>
                                <option>3</option>
                                >
                            </select>

                            <#if errors['constructionYearError']??>
                                <div class="p-3 mb-2 bg-danger text-white">${errors['constructionYearError']}</div>
                            </#if>
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

                <#else >

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
                            <input name="flat-area" type="text" class="form-control" id="Select2" required
                                   pattern="^[1-9][0-9]?[0-9]?$" title="Podaj wartość od 1 do 999"
                                   oninvalid="this.setCustomValidity('Wpisana wartość jest nieprawidłowa!')"
                                   oninput="this.setCustomValidity('')">
                        </div>



                        <div class="form-group">
                            <label for="Select3">Wpisz numer pietra</label>
                            <input name="level" type="text" class="form-control" id="Select3" required
                                   pattern="^[0-1]?[0-9]?[0-9]?$" title="Podaj wartość od 0 do 199"
                                   oninvalid="this.setCustomValidity('Wpisana wartość jest nieprawidłowa!')"
                                   oninput="this.setCustomValidity('')">
                        </div>



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
                            <select name="construction" class="form-control" id="Select6" required>
                                <option></option>
                                <option value="1">przed rokiem 1970</option>
                                <option value="2">między rokiem 1970 a 1990</option>
                                <option value="3">po roku 1990</option>
                                >
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
                </#if>
            </div>
            <!--Koniec formularza szczegółowych informacji o mieszkaniu-->
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


