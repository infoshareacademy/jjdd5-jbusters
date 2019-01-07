<footer>&copy; 2018 JBusters</footer>

<script src="js/jquery-3.3.1.slim.min.js"></script>
<script src="js/bootstrap.min.js"></script>

<script>
    function signOut() {
        console.log("logging out ...");
        gapi.auth.authorize(
                {
                    'client_id': "210558406372-1i31kqkfer8n3pa5dsdkl3udifff4k9v.apps.googleusercontent.com",
                    'scope': "profile email",
                    'immediate': false,
                    cookie_policy: 'single_host_origin',
                    response_type: 'token id_token'
                },
                function (authResult) {
                    gapi.auth.signOut();
                    gapi.auth.disconnect();
                }
        );
        console.log("redirecting to logout servlet");
        window.location.href = '/logout';
    }
</script>
</body>
</html>