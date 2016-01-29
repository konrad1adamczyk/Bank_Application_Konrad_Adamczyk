<!DOCTYPE HTML>
<html lang="eng">
<head>
	<meta charset="utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
	
	<title>Remove Account</title>

	<link rel="stylesheet" href="style.css" type="text/css" />
	<link rel="stylesheet" href="css/fontello.css" type="text/css" />

	<link href='http://fonts.googleapis.com/css?family=Lato|Josefin+Sans&subset=latin,latin-ext' rel='stylesheet' type='text/css'>
	
	<script src="timer.js"></script>
		
</head>

<body onload="odliczanie();">
	
	<div id="container">
	
		<div class="rectangle">
			<div id="logo" class="tilelinkhtml5">Logged Client: <%=session.getAttribute("clientName")%></div>
			<div id="zegar">     12:00:00</div>
			<div style="clear: both;"></div>
		</div>
		
		<div class="square">
			<div class="tile1">
				<a href="index.html" class="tilelink"><i class="icon-logout"></i><br />Log Out</a>
			</div>

			<div class="tile1">
				<a href="setActiveAccount.jsp" class="tilelink"><i class="icon-ok-squared"></i><br />Set Active Account</a>
			</div>

			<div style="clear:both;"></div>

			<div class="tile3">
				<a href="addAccount.jsp" class="tilelink"><i class="icon-plus-squared"></i><br />Add Account</a>
			</div>
			<div class="tile6">
				<a href="removeAccount.jsp" class="tilelink"><i class="icon-minus-squared"></i><br />Remove Account</a>
			</div>
			<div style="clear:both;"></div>
			
			<div class="tile4">
				<i>Talk is cheap. Show me the code!</i><br />- Linus Torvalds, Linux crator
			</div>
		</div>
		<div class="square">
			<div class="tile5">
				Remove account <br /><br />
				Lorem ipsum dolor sit amet, consectetur adipiscing elit. In pulvinar ipsum mauris, ac elementum felis convallis non. Duis aliquam felis vel dignissim tincidunt. In tellus mauris, tempor rutrum blandit in, efficitur ac quam. Mauris eu orci scelerisque, placerat eros id, condimentum justo. Suspendisse ac mattis orci, at mollis orci. 
			</div>
			<div class="deposit">
				<a href="deposite.jsp" class="operationlink"><i class="icon-download-outline"></i><br />Deposit</a>
			</div>
			<div class="withdraw">
				<a href="withdraw.jsp" class="operationlink"><i class=" icon-upload-outline"></i><br />Withdraw</a>
			</div>
			<div class="transfer">
				<a href="transfer.jsp" class="operationlink"><i class=" icon-export"></i><br />Transfer</a>
			</div>
			<div class="something">
				<a href="http://google.com" class="operationlink"><i class=" icon-money"></i><br />Something</a>
			</div>
			<div style="clear:both;"></div>
		</div>
		<div style="clear: both;"></div>
		
		<div class="rectangle2">2016 &copy; Konrad Adamczyk - Bank Application. If you like this page contact me <i class="icon-mail-alt"></i> konrad1adamczyk@gmail.com</div>
	
	</div>
	
</body>
</html>