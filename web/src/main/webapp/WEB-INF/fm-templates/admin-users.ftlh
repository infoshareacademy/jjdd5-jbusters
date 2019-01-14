<#include "header-admin.ftlh">
<div class="container">
    <br><br>

    <div class="jumbotron jumbotron-opacity">

        <div class="row">
            <div class="col-8">
                <h1 class="display">Lista użytkowników</h1>
                <p class="lead">Licba użytkowników w bazie: <b>${size}</b></p>
            </div>
        </div>

        <table class="table table-striped">
            <thead>
            <tr>
                <th scope="col">Id użytkownika</th>
                <th scope="col">Email</th>
                <th scope="col">Imię</th>
                <th scope="col">Nazwisko</th>
                <th scope="col">Uprawnienia</th>
            </tr>
            </thead>
            <tbody>
            <#list usersList as u>

            <tr>
                <td><h5>${u.userId}</h5></td>
                <td><h5>${u.userEmail}</h5></td>
                <td><h5>${u.userName}</h5></td>
                <td><h5>${u.userSurname}</h5></td>
                    <#if u.userRole = 1>
                        <td><h5>ADMIN</h5></td>
                    <#elseif u.userRole = 2>
                        <td><h5>Podstawowe</h5></td>
                    </#if>
                <td>
                    <a href="/admin-users/editUser?id=${u.userId}" class="btn btn-dark">Edytuj</a>
                </td>
                <td>
                    <button type="button" class="btn btn-danger" data-toggle="modal"
                            data-target="#exampleModal-${u.userId}">Usuń</button>

                    <!-- Modal -->
                    <div class="modal fade" id="exampleModal-${u.userId}" tabindex="-1" role="dialog"
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
                                    <p>Czy na pewno chcesz usunąć tego użytkownika?</p>
                                    <p>Zmiany będą nieodwracalne!</p>
                                </div>
                                <div class="modal-footer">
                                    <form method="post" action="admin-users?id=${u.userId}">
                                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Zamknij
                                        </button>
                                        <button type="submit" class="btn btn-danger">Usuń</button>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </td>
            </tr>
            </#list>
            </tbody>
        </table>
    </div>

</div>

</div>
</div>

<#include "include-user-footer.ftlh">


