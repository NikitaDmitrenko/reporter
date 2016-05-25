<!DOCTYPE html>

<html>
<head>
    <title>Welcome Page </title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" crossorigin="anonymous">
</head>
<body>
<div class="container">
    <form class="form-signin">
        <h2 class="form-signin-heading">Please sign in</h2>
        <label for="inputEmail" >Email address</label><br>
        <input type="email" id="inputEmail" class="form-control" placeholder="Email address" required autofocus>
        <br><label for="inputPassword" >Password</label>
        <br><input type="password" id="inputPassword" class="form-control" placeholder="Password" required>
        <div class="checkbox">
            <label>
                <input type="checkbox" value="remember-me"> Remember me
            </label>
        </div>
        <button class="btn btn-lg btn-default btn-block" type="submit">Login</button>
        <a href="clubregister.html" class="btn btn-lg btn-default btn-block">Sign up</a>
    </form>
</div>
</body>
</html>