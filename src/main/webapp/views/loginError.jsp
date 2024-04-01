<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>Login Page</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet">
<style>
body {
	height: 100vh;
	display: flex;
	align-items: center;
	justify-content: center;
	background-color: #f8f9fa;
}

.login-container {
	width: 400px;
	padding: 20px;
	border-radius: 5px;
	box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
	background-color: #fff;
}
</style>
</head>
<body>
	<div class="login-container">
		<h2 class="text-center mb-4">Login</h2>
		<form action="/loginresult" method="POST">
			<h4><b> ${error} </b>!</h4>
			<div class="mb-3">
				<label for="username" class="form-label">Username:</label> <input
					type="text" class="form-control" id="username" name="username"
					required>
			</div>
			<div class="mb-3">
				<label for="password" class="form-label">Password:</label> <input
					type="password" class="form-control" id="password" name="password"
					required>
			</div>
			<div class="d-grid">
				<button type="submit" class="btn btn-primary btn-block">Login</button>
			</div>
		</form>

	</div>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
