<html>
<head>
    <meta charset="UTF-8">
    <title>JBusters</title>
    <!-- Bootstrap core CSS -->
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link href="css/style.css" rel="stylesheet">
</head>
<body>
<div class="wrap">
    <header>
        <div class="container-fluid">
            <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
                <a class="navbar-brand" href="index.html">JBusters</a>
                <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav"
                        aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="navbarNav">
                    <ul class="navbar-nav">
                        <li class="nav-item active">
                            <a class="nav-link" href="/index.html">Home <span class="sr-only">(current)</span></a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="/load-city">${menu_valuation}</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="/upload-file">${menu_upload}</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="/my-flats">${menu_my_apartments}</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="/add-user-form">${menu_create_account}</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="/select-login-form">${menu_login}</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="/language?lang=pl"><img src="img/poland-flag-icon-64.png" alt="pl" height="22" width="38"></a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="/language?lang=en"><img src="img/united-kingdom-flag-icon-64.png" alt="en" height="22" width="38"></a>
                        </li>
                    </ul>
                </div>
            </nav>
        </div>
    </header>

    <div class="container">
        <div class="jumbotron jumbotron-opacity">
            <h1 class="display-4">${home_headline}</h1>
            <p class="lead">${home_subheadline}</p>
            <hr class="my-4">
            <a class="btn btn-primary btn-lg btn-dark" href="/load-city" role="button">${home_button_valuation}</a>
        </div>
    </div>
</div>
<footer>&copy; 2018 JBusters</footer>

<script src="js/jquery-3.3.1.slim.min.js"></script>
<script src="js/bootstrap.min.js"></script>

</body>
</html>