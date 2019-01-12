<#include "include-user-header.ftlh">
<div class="container">
    <br><br>

    <div class="jumbotron jumbotron-opacity">

        <div class="row my-border-bootom">


            <div class="col-8">
                <h1 class="display">Edycja konta</h1>
            </div>
        </div>
        <div class="jumbotron jumbotron-opacity-1">
            <#if updateStatus??>
                <#if updateStatus['updateSuccess']??>
                    <div class="alert alert-success">
                        <strong>${updateStatus['updateSuccess']}</strong>
                    </div>
                </#if>
                <#if updateStatus['updateFailed']??>
                    <div class="alert alert-danger">
                        <strong>${updateStatus['updateFailed']}</strong>
                    </div>
                </#if>
                <#if updateStatus['emailSuccess']??>
                    <div class="alert alert-success">
                        <strong>${updateStatus['emailSuccess']}</strong>
                    </div>
                </#if>
                <#if updateStatus['emailFailed']??>
                    <div class="alert alert-danger">
                        <strong>${updateStatus['emailFailed']}</strong>
                    </div>
                </#if>
            </#if>
            <form method="post" action="acc-edit">

                <div class="form-group">
                    <label for="Select2"><b>Email:</b></label>
                    <input name="email" type="text" class="form-control" id="Select2" value="${user.userEmail}">
                </div>

                <div class="form-group">
                    <label for="Select3"><b>Imię</b></label>
                    <input name="name" type="text" class="form-control" id="Select3" value="${user.userName}">
                </div>

                <div class="form-group">
                    <label for="Select4"><b>Nazwisko</b></label>
                    <input name="surname" type="text" class="form-control" id="Select4" value="${user.userSurname}">
                </div>

                <input class="btn btn-primary" type="submit" value="Zapisz" name="upload" id="upload"/>
                <a href="/load-user-menu" type="button" class="btn btn-secondary">Wróć</a>
                <a href="/acc-edit/pass-change" type="button" class="btn btn-secondary">Zmiana hasła</a>
            </form>
            <button type="button" class="btn btn-danger float-right" data-toggle="modal"
                    data-target="#exampleModal">Usuń moje konto
            </button>

            <!-- Modal -->
            <div class="modal fade" id="exampleModal" tabindex="-1" role="dialog"
                 aria-labelledby="exampleModalLabel" aria-hidden="true">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="exampleModalLabel">Uwaga!</h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body">
                            <p>Czy na pewno chcesz usunąć swoje konto?</p>
                            <p>Zmiany będą nieodwracalne!</p>
                        </div>
                        <div class="modal-footer">
                            <form method="post" action="delete-account">
                                <button type="button" class="btn btn-secondary" data-dismiss="modal">Zamknij
                                </button>
                                <button type="submit" class="btn btn-danger">Usuń moje konto</button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>

        </div>
    </div>

</div>
</div>

<#include "include-user-footer.ftlh">