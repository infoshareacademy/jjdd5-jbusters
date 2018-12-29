<html>
<head>
    <title>JBusters</title>
    <!-- Bootstrap core CSS -->
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link href="css/style.css" rel="stylesheet">

    <!-- Google -->
    <script src="//ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
    <script src="https://apis.google.com/js/platform.js" async defer></script>
    <meta name="google-signin-scope" content="profile email">
    <meta name="google-signin-client_id" content="210558406372-1i31kqkfer8n3pa5dsdkl3udifff4k9v.apps.googleusercontent.com">
</head>
<body>
<div class="wrap">
    <header>
        <div class="container-fluid">
            <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
                <a class="navbar-brand" href="#">JBusters</a>
                <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav"
                        aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="navbarNav">
                    <ul class="navbar-nav">
                        <li class="nav-item">
                            <a class="nav-link" href="/index.html">Home <span class="sr-only">(current)</span></a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="/load-city">Wyceń mieszkanie</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="uploadFile.html">Załaduj plik</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="/my-flats">Moje mieszkania</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="/generate-report">Pobierz statystyki</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="add-user.html">Załóż konto</a>
                        </li>
                        <li class="nav-item">
                            <a href="index.html" onclick="signOut();">Sign out</a>
                            <script>
                                function signOut() {
                                    var auth2 = gapi.auth2.getAuthInstance();
                                    auth2.signOut().then(function () {
                                        console.log('User signed out.');
                                    });
                                }
                            </script>
                        </li>
                    </ul>
                </div>
            </nav>
        </div>
    </header>

<#include "footer.ftlh">