<footer>&copy; 2018 JBusters</footer>

<#--<script src="js/jquery-3.3.1.slim.min.js"></script>-->
<script src="../../js/bootstrap.min.js"></script>

<!-- ClockPicker script -->
<script src="../../js/bootstrap-clockpicker.min.js"></script>

<script>
    $('.clockpicker').clockpicker()
        .find('input').change(function(){
        // TODO: time changed
        console.log(this.value);
    });
    $('#demo-input').clockpicker({
        autoclose: true
    });

    if (something) {
        // Manual operations (after clockpicker is initialized).
        $('#demo-input').clockpicker('show') // Or hide, remove ...
            .clockpicker('toggleView', 'minutes');
    }
</script>
</body>
</html>