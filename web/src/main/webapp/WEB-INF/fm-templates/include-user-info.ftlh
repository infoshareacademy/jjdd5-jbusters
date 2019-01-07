
<div class="col-4">
    <div class="card" style="width: 18rem;">
        <div class="card-body">
            <p class="card-text"><b>Twoje konto:</b></p>
        </div>
        <ul class="list-group list-group-flush">
            <li class="list-group-item">Imię i nazwisko: <b>${sessionName}</b></li>
        </ul>
        <ul class="list-group list-group-flush">
            <li class="list-group-item">Twój email: <b>${sessionEmail}</b></li>
        </ul>
        <div class="card-body">
            <a href="/logout" class="card-link" onclick="signOut();">Wyloguj</a>
        </div>
    </div>

</div>