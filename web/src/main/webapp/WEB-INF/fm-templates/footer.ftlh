</div>
<footer>&copy; 2018 JBusters</footer>

    <#--<script src="js/jquery-3.3.1.slim.min.js"></script>-->
    <script src="js/bootstrap.min.js"></script>

<!-- ClockPicker script -->
<script src="js/bootstrap-clockpicker.min.js"></script>

<script>
    //google callback. This function will redirect to our login servlet
    function onSignIn(googleUser) {
        var profile = googleUser.getBasicProfile();
        console.log('ID: ' + profile.getId());
        console.log('Name: ' + profile.getName());
        console.log('Image URL: ' + profile.getImageUrl());
        console.log('Email: ' + profile.getEmail());
        console.log('id_token: ' + googleUser.getAuthResponse().id_token);
        //do not post all above info to the server because that is not secure.
        //just send the id_token
        var redirectUrl = 'login';
        //using jquery to post data dynamically
        var form = $('<form action="' + redirectUrl + '" method="post">' +
            '<input type="text" name="id_token" value="' +
            googleUser.getAuthResponse().id_token + '" />' +
            '</form>');
        $('body').append(form);
        form.submit();
    }
</script>

</body>
</html>