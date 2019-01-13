<html>
<head>
    <meta charset="UTF-8">
    <title>JBusters</title>
    <!-- Bootstrap core CSS -->
    <link href="../../css/bootstrap.min.css" rel="stylesheet">
    <link href="../../css/style.css" rel="stylesheet">

    <!-- Google -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
    <script src="https://apis.google.com/js/platform.js" async defer></script>
    <script src="https://apis.google.com/js/client.js" async defer></script>
    <meta name="google-signin-scope" content="profile email">
    <meta name="google-signin-client_id" content="210558406372-1i31kqkfer8n3pa5dsdkl3udifff4k9v.apps.googleusercontent.com">

    <!-- ClockPicker Stylesheet -->
    <link rel="stylesheet" type="text/css" href="css/bootstrap-clockpicker.min.css">

    <!-- Font Awesome -->
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.6.3/css/all.css" integrity="sha384-UHRtZLI+pbxtHCWp1t77Bi1L4ZtiqrqD80Kn4Z8NTSRyMA2Fd33n5dQ8lWUE00s/" crossorigin="anonymous">

</head>
<body>
<div class="wrap-user">
    <header>
        <div class="container-fluid">
            <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
                <a class="navbar-brand" href="/load-user-menu">JBusters</a>
                <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>

                <div class="collapse navbar-collapse" id="navbarSupportedContent">
                    <ul class="navbar-nav mr-auto">
                        <li class="nav-item">
                                <a class="nav-link" href="/load-user-menu">Twoje menu</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="/load-city">Wyceń mieszkanie</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="/upload-file">Załaduj plik</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="/my-flats">Twój plik CSV</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" data-toggle="modal"
                               data-target="#download-header-menu">Pobierz statystyki</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="/show-transaction">Twoje mieszkania</a>
                        </li>
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                Zalogowano: ${user.userEmail}
                            </a>
                            <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                                <a class="dropdown-item" href="/admin-panel">Panel administracyjny</a>
                                <div class="dropdown-divider"></div>
                                <a class="dropdown-item" href="/logout">Wyloguj</a>
                            </div>
                        </li>
                    </ul>
                </div>
            </nav>
        </div>
    </header>

    <!-- Modal -->
    <div class="modal fade" id="download-header-menu" tabindex="-1" role="dialog"
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
                    Czy chcesz zapisać raport ze statystkami na swoim dysku?
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
