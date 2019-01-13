<#include "header.ftlh">

     <div class="container  container-form">
        <form method="get" action="load-other-values">

            <div class="form-group">
                <label for="Select1">Miasto: </label>
                <input name="city" type="text" class="form-control" id="Select1" value="${city}" disabled>
            </div>

            <div class="form-group">
            <label for="Select2">Wybierz dzielnice</label>
                <select name="district" class="form-control" id="Select2" required>
                    <option></option>
                    <#list district as d>
                    <option>${d}</option>
                    </#list>
                </select>
            </div>
            <button type="submit" class="btn btn-primary">Dalej</button>

            <div class="form-group hidden">
                <label for="Select1">Miasto: </label>
                <input name="city" type="text" class="form-control" id="Select1" value="${city}">
            </div>
        </form>

         <div class="row ">
             <div class="col col-lg-8 float-right">
                 <div class="card bg-light mb-3 float-right">
                     <div class="card-header"><b>Brak twojego miasta lub dzielnicy?</b></div>
                     <div class="card-body">
                         <p class="card-text">Jeśli twojego miasta nie ma na liście, daj nam o tym znać! Postaramy się wkrótce
                             uzupełnić naszą bazę!</p>
                         <a href=/send-info class="btn btn-primary float-right">Wyślij wiadomość</a>
                     </div>
                 </div>
             </div>
         </div>
    </div>

<#include "footer.ftlh">