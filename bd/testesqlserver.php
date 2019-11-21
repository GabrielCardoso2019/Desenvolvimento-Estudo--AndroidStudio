<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <title>Document</title>
</head>
<body>
    

	
	
	<?php
	
try{	
			$conn;
			$servidor = "10.38.40.194";
			$instancia = "";
			$porta = 1433;
			$database = "TI_79_RENAN_AUGUSTO";
			$usuario = "TI_79";
			$senha = "sala21";
	
		$conn = new PDO( "sqlsrv:Server={$servidor}\\{$instancia},{$porta};Database={$database}", $usuario, $senha );
	
		}  
	catch(Exception $e)  
	{   
		die( print_r( $e->getMessage() ) );   
	}  

	$query = $conn->prepare("SELECT @@VERSION");
	$query->execute();
	$resultado = $query->fetchAll();
	echo $resultado ['0'] ['0'] ;

    $result = $conn->query ('SELECT * FROM dbo.PRODUTOS');
?>
   <table border="1" width="600">
   <th>Codigo</th>
   <th>Nome</th>
   <th>Tipo</th>
   <th>Quantidade</th>
   <th>Valor</th>
   
    
    <?php
    while ( $row = $result->fetch(PDO::FETCH_ASSOC)) {
       ?>
        
        <?php
        echo "<tr><td>". $row ['CODIGO_PRODUTO']."</td><td>".$row['NOME']."</td><td>" .$row['TIPO']."</td><td>" .$row['QUANTIDADE']."</td><td>" .$row['VALOR']."</td></tr>";
               
    }
		
?>
</table>	
	
</body>
</html>	