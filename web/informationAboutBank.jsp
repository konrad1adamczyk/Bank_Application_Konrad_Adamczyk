<!DOCTYPE HTML>
<html lang="eng">
<head>
	<meta charset="utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
	
	<title>Information about the Bank</title>

	<link rel="stylesheet" href="style.css" type="text/css" />
	<link rel="stylesheet" href="css/fontello.css" type="text/css" />

	<link href='http://fonts.googleapis.com/css?family=Lato|Josefin+Sans&subset=latin,latin-ext' rel='stylesheet' type='text/css'>
	
	<script src="timer.js"></script>
		
</head>

<body onload="odliczanie();">
	
	<div id="container">

		<div class="rectangle">
			<div id="logo"><a href="index.jsp" class="tilelinkhtml5">Bank Application</a></div>
			<div id="zegar">     12:00:00</div>
			<div style="clear: both;"></div>
		</div>

		<div class="square">
			<div class="tile1">
				<a href="login.jsp" class="tilelink" ><i class="icon-user"></i><br />Log In</a>
			</div>
			<div class="tile1">
				<a href="addNewClient.jsp" class="tilelink" ><i class="icon-user-plus"></i><br />Add New Client</a>
			</div>
			<div style="clear:both;"></div>

			<div class="tile2">
				<a href="removeClient.jsp" class="tilelink"><i class="icon-user-times"></i><br />Remove Client</a>
			</div>
			<div class="tile3">
				<a href="informationAboutBank.jsp" class="tilelink"><i class="icon-bank"></i><br />Inforamtion about Bank</a>
			</div>
			<div style="clear:both;"></div>

			<div class="tile4">
				<i>Talk is cheap. Show me the code!</i><br />- Linus Torvalds, Linux crator
			</div>
		</div>
		<div class="square">
			<div class="tile5a">
				<table border="1">
					<tr>
						<th colspan=2 style="font-weight:bold;">Bank Statistics for MyBank</th>
					</tr>
					<tr>
						<td colspan=2 >Overall amount of<br />our clients:  </td>
						<td colspan=2 >54</td>
					</tr>
					<tr>
						<td colspan=2 >Overall sum on the<br />clients accounts:  </td>
						<td colspan=2 >54456541354$</td>
					</tr>
				</table>
				<br />
				<table border="1">
					<tr>
						<td colspan=2 style="font-weight:bold;">List of Clients by city: </td>
					</tr>
					<tr>
						<td colspan=2 >New York</td>
						<td colspan=2 >John Smith<br />John Smith</td>
					</tr>
					<tr>
						<td colspan=2 >London</td>
						<td colspan=2 >Mary Crafford<br />John Smith</td>
					</tr>
					<tr>
						<td colspan=2 >Rome</td>
						<td colspan=2 >Giovanni Balducci<br />John Smith</td>
					</tr>
				</table>
			</div>
		</div>
		<div style="clear: both;"></div>
		
		<div class="rectangle2">2016 &copy; Konrad Adamczyk - Bank Application. If you like this page contact me <i class="icon-mail-alt"></i> konrad1adamczyk@gmail.com</div>
	
	</div>
	
</body>
</html>