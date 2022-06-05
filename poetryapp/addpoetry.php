<?php

$servername = "localhost";
$username = "root";
$password ="";
$db = "poetrydb";

$conn = new mysqli($servername,$username,$password,$db);

if($conn->connect_error){
		die("connection failed: ".$conn->connect_error);
}

$POETRY = $_POST['poetry'];
$POET_NAME = $_POST['poet_name'];

$query = "INSERT INTO poetry(poetry_data,poet_name)VALUES('$POETRY','$POET_NAME')";

$result = $conn->query($query);

if($result==1){
	$response = array("status"=>"1","message"=>"Poetry successfully inserted");
}
else {
	$response = array("status"=>"0","message"=>"Poetry not successfully inserted");
}

echo json_encode($response);

?>