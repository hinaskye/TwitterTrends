<!DOCTYPE html>
<html>
<head>
	<title>Twitter Trends</title>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<!--Bootstrap v3.3.7-->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	<link rel="stylesheet" href="css/home.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>

<nav class="navbar navbar-inverse">
  <div class="container-fluid">
    <div class="navbar-header">
      <a class="navbar-brand" href="#">Twitter Trends</a>
    </div>
    <ul class="nav navbar-nav">
      <li class="active"><a href="#">Home</a></li>
      <li><a href="AboutUs.html">About Us</a></li>
    </ul>
  </div>
</nav>

<div class="container wrapper">
	<br>
	<div class="row bg-white">
		<div class="row">
			<h1 class="text-center text-primary">Twitter Trends</h1>
		</div>
		<div class="row">
			<div class="panel panel-default panel-body col-sm-12 col-md-12">
			<form action=""> <!--insert php page for action-->
				<h2 class="text-primary">Upload a category</h2>
				<p class="text-danger">(only .csv type formats e.g. text files in the form of item1, item2, item3)</p>
				<input type="file" name="category_file" accept="text/csv">
				<input type="submit" class="btn-danger">
			</form>
			</div>
		</div>
		<div class="row">
			<div class="col-sm-12 col-md-6 padding-x">
			<hr>
			<h2 class="text-primary">Rankings</h2>
			<ol>
				<?php
					$rankings = file('https://s3-ap-southeast-2.amazonaws.com/cc2017twittertrends/Ranking.txt');
					$count = 1;
					foreach($rankings as $ranking)
					{
						if($count>10)
						{
							break;
						}
						if($count==1)
						{
							echo "<li><h3>".$ranking."</h3></li>";
						}
						if($count==2)
						{
							echo "<li><h4>".$ranking."</h4></li>";
						}
						if($count>2)
						{
							echo "<li>".$ranking."</li>";
						}
						$count++;
					}
				?>
			</ol>
			<hr>
			</div>
			<div class="col-sm-12 col-md-6 padding-x">
				<hr>
				<h2 class="text-primary">Top Tweets</h2>
				<img src="images/Top Tweets.png" class="img-responsive img-rounded" alt="Sample Top Tweets">
				<hr>
			</div>
		</div>
	</div>
</div>

<footer class="container-fluid text-center">
	<p> Copyright © Twitter Trends 2017. All right reserved.</p>
</footer>

</body>
</html>