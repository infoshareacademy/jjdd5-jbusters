<html>
<head>
    <title>JBusters</title>

    <!-- Bootstrap core CSS -->
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link href="css/style.css" rel="stylesheet">

</head>
<body>

    <header>
        <div class="container-fluid">
            <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
                <a class="navbar-brand" href="#">JBuster</a>
                <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="navbarNav">
                    <ul class="navbar-nav">
                        <li class="nav-item active">
                            <a class="nav-link" href="/home">Home <span class="sr-only">(current)</span></a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="/new-transaction">Wycen mieszkanie</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="#">Historia</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link disabled" href="#">Zaloguj</a>
                        </li>
                    </ul>
                </div>
            </nav>
        </div>
    </header>

        <div class="container bg-white">
            <br>
            <br>
            <br>
            <form>
                <div class="form-group">
                    <label for="exampleFormControlSelect1">Wybierz miasto</label>
                    <select name="city" class="form-control" id="exampleFormControlSelect1">
                        <option></option>
                        <option>Gdansk</option>
                        <option>Gdynia</option>
                        <option>Sopot</option>
                    </select>
                </div>
                <div class="form-group">
                    <label for="exampleFormControlSelect2">Wybierz dzielnice</label>
                    <select name="distric" class="form-control" id="exampleFormControlSelect2">
                        <option></option>
                        <option>Wrzeszcz</option>
                        <option>Oliwa</option>
                        <option>Orlowo</option>
                        <option>Chylonia</option>
                    </select>
                </div>
                <div class="form-group">
                    <label for="exampleFormControlSelect3">Wpisz nazwe ulicy</label>
                    <input name="street" type="text" class="form-control" id="exampleFormControlSelect3">
                </div>

                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value="" id="defaultCheck1">
                    <label class="form-check-label" for="defaultCheck1">
                        Rynek pierwotny
                    </label>
                </div>

                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value="" id="defaultCheck2">
                    <label class="form-check-label" for="defaultCheck2">
                        Rynek wtorny
                    </label>
                </div>
                <div class="form-group">
                    <label for="exampleFormControlSelect4">Wpisz wielkosc mieszkania w m2</label>
                    <input name="loadSize" type="text" class="form-control" id="exampleFormControlSelect4" ">
                </div>

                <div class="form-group">
                    <label for="exampleFormControlSelect5">Czy posiadasz miejsce parkingowe ?</label>
                    <select class="form-control" id="exampleFormControlSelect5">
                        <option>Brak miejsca</option>
                        <option>Miejsce naziemne</option>
                        <option>Garaz jednostanowiskowy</option>
                        <option>Miejsce w hali garazowej</option>
                    </select>
                </div>

                <div class="form-group">
                    <label for="exampleFormControlSelect5">Wpisz numer pietra</label>
                    <input name="level" type="text" class="form-control" id="exampleFormControlSelect5">
                </div>

                <div class="form-group">
                    <label for="exampleFormControlSelect6">Wybierz poziom wykonczenia twojego mieszkania</label>
                    <select class="form-control" id="exampleFormControlSelect6">
                        <option></option>
                        <option>Wysoki</option>
                        <option>Bardzo dobry</option>
                        <option>Dobry</option>
                        <option>Przecietny</option>
                        <option>Niski</option>
                        <option>Deweloperski/Do wykonczenia</option>
                    </select>
                </div>

                <div class="form-group">
                    <label for="exampleFormControlSelect7">Wybierz przedzial czasowy w jakim zostalo wybudowane Twoje mieszkanie</label>
                    <select name="distric" class="form-control" id="exampleFormControlSelect7">
                        <option></option>
                        <option>przed rokiem 1970</option>
                        <option>między rokiem 1970 a 1990</option>
                        <option>po roku 1990</option>
                    </select>
                </div>

                <div class="form-group">
                    <label for="exampleFormControlSelect8">Wpisz wartosc za jaka zostala sprzedana Twoja nieruchomosc</label>
                    <input name="loadSize" type="text" class="form-control" id="exampleFormControlSelect8" ">
                </div>

                <div class="form-group">
                    <label for="exampleFormControlSelect9">Wpisz rok budowy mieszkania</label>
                    <input name="loadSize" type="text" class="form-control" id="exampleFormControlSelect9" ">
                </div>

                <button type="submit" class="btn btn-primary">Wycen</button>
            </form>
        </div>
        <br>
        <br>
        <br>
        <br>
        <br>
        <br>


<footer>&copy; 2018 JBusters</footer>

<script src="js/jquery-3.3.1.slim.min.js"></script>
<script src="js/bootstrap.min.js"></script>
</body>
</html>