<#include "header-admin.ftlh">
<div class="container">
    <br><br>

    <div class="jumbotron jumbotron-opacity">

        <div class="row my-border-bootom">
            <div class="col-8">
                <h1 class="display">Edycja użytkownika</h1>
            </div>
        </div>
        <div class="jumbotron jumbotron-opacity-1">
            <p class="lead"><b>Użytkownik</b></p>
            <form method="post" action="editUser?id=${userToEdit.userId}">

                <div class="form-group">
                    <label for="Select1"><b>Id użytkownika:</b></label>
                    <input name="city" type="text" class="form-control" id="Select1" value="${userToEdit.userId}"
                           disabled>
                </div>

                <div class="form-group">
                    <label for="Select2"><b>Email:</b></label>
                    <input name="district" type="text" class="form-control" id="Select2" value="${userToEdit.userEmail}"
                           disabled>
                </div>

                <div class="form-group">
                    <label for="Select3"><b>Imię</b></label>
                    <input name="market-type" type="text" class="form-control" id="Select3"
                           value="${userToEdit.userName}"
                           disabled>
                </div>

                <div class="form-group">
                    <label for="Select4"><b>Nazwisko</b></label>
                    <input name="flat-area" type="text" class="form-control" id="Select4"
                           value="${userToEdit.userSurname}"
                           disabled>
                </div>


                <div class="form-group">
                    <label for="Select5">Uprawnienia użytkownika</label>
                    <#if user.userId = userToEdit.userId>
                        <input name="flat-area" type="text" class="form-control" id="Select4"
                               value="ADMIN" disabled>
                    <#else >
                        <select name="role" class="form-control" id="Select5" required>
                            <#if userToEdit.userRole = 1>
                                <option>ADMIN</option>
                            <#elseif userToEdit.userRole = 2>
                                <option>Podstawowe</option>
                            </#if>
                            <option>ADMIN</option>
                            <option>Podstawowe</option>
                        </select>
                    </#if>
                </div>
                <input class="btn btn-primary" type="submit" value="Zapisz" name="upload" id="upload"/>
                <a href="/admin-users" type="button" class="btn btn-secondary">Wróć</a>
            </form>
        </div>
    </div>

</div>
</div>

<#include "include-user-footer.ftlh">