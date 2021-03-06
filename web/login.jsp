<!DOCTYPE HTML>
<html lang="eng">
<head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />

    <title>Login to our bank</title>

    <link rel="stylesheet" href="style.css" type="text/css" />
    <link rel="stylesheet" href="css/fontello.css" type="text/css" />

    <link href='http://fonts.googleapis.com/css?family=Lato|Josefin+Sans&subset=latin,latin-ext' rel='stylesheet' type='text/css'>

    <script src="timer.js"></script>
    <script type="text/javascript" src="validation.js"></script>

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
            <a href="${pageContext.request.contextPath}/addNewClient.jsp" class="tilelink" ><i class="icon-user-plus"></i><br />Add New Client</a>
        </div>
        <div style="clear:both;"></div>

        <div class="tile2">
            <a href="${pageContext.request.contextPath}/removeClient.jsp" class="tilelink"><i class="icon-user-times"></i><br />Remove Client</a>
        </div>
        <div class="tile3">
            <a href="${pageContext.request.contextPath}/bankStatistics" class="tilelink"><i class="icon-bank"></i><br />Inforamtion about Bank</a>
        </div>
        <div style="clear:both;"></div>

        <div class="tile4">
            <i>Talk is cheap. Show me the code!</i><br />- Linus Torvalds, Linux crator
        </div>

    </div>
    <div class="square">
        <div class="tile5a">

            <form id="form" action="/login" method="POST" target="_self">
                <table>
                    <tr><td colspan=2 style="font-weight:bold;"></br>Please, fill the form:</td></tr>

                    <td>Name:</td>
                    <td><input type="text" name="clientName" id="name"/></td>
                    <td id="nameError" class="error"></td>

                </tr><tr><td>
                        <input type="submit" onclick="return checkForm();" value="Submit">
                    </td></tr>
                </table>
            </form>



        </div>
        <div style="clear: both;"></div>

        <div class="rectangle2">2016 &copy; Konrad Adamczyk - Bank Application. If you like this page contact me <i class="icon-mail-alt"></i> konrad1adamczyk@gmail.com</div>

    </div>

</body>
</html>