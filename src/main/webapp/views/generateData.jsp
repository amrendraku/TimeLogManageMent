<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Generate Data</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet">
<style>
body {
	background-color: #f8f9fa;
	margin: 0;
	padding: 0;
}

.container {
	margin-top: 50px;
}

.options-list {
	list-style-type: none;
	padding-left: 0;
}

.options-list li {
	margin-bottom: 10px;
}

.form-container {
	background-color: #fff;
	border-radius: 5px;
	box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
	padding: 20px;
}

.larger-font {
	font-size: 1.2rem;
}
</style>
</head>
<body>

	<div class="container">
		<div class="row">
			<div class="col-md-6">
				<ul class="options-list">
					<li><a href="#" id="uploadDataLink" class="larger-font">Upload
							Data</a></li>
					<li><a href="#" id="generateDataLink" class="larger-font">Generate
							Data</a></li>
				</ul>
			</div>
			<div class="col-md-6">
				<div class="form-container" id="uploadDataSection">
					<h2 class="larger-font">Upload Data</h2>
					<form id="uploadDataForm">
						<div class="mb-3">
							<label for="monthSelect" class="form-label">Month:</label> <select
								class="form-select" id="monthSelect" name="monthSelect" required>
								<option value="">Select month</option>
								<option value="jan">January</option>
								<option value="feb">February</option>
								<option value="mar">March</option>
								<option value="apr">April</option>
								<option value="may">May</option>
								<option value="jun">June</option>
								<option value="jul">July</option>
								<option value="aug">August</option>
								<option value="sep">September</option>
								<option value="oct">October</option>
								<option value="nov">November</option>
								<option value="dec">December</option>
							</select>
						</div>
						
						<button type="submit" class="btn btn-primary">Submit</button>
					</form>
				</div>
			</div>
		</div>
	</div>

	<script>
      
    </script>

</body>
</html>
