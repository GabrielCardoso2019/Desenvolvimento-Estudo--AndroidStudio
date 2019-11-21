<?php

	class DBController 
	{
	
		public  $conn;
		public 	$servidor = "10.38.40.194";
		public 	$instancia = "";
		public 	$porta = 1433;
		public 	$database = "TI_79_RENAN_AUGUSTO";
		public 	$usuario = "ti_79";
		public 	$senha = "sala21";
	
	function connectDB() 
	{
		try
		{
			$this->conn = new PDO( "sqlsrv:Server={$this->servidor}\\{$this->instancia},{$this->porta};Database={$this->database}", $this->usuario, $this->senha );
		
			}  
		catch(Exception $e)  
		{   
			die( print_r( $e->getMessage() ) );   
		}  

	}
	
	
	function executeQuery($query) 
	{
			$conn = $this->connectDB();    
			$result = $this->conn->prepare($query);
			//$result->execute();
			
		if ($result->execute()) 
		{ 
			$result = array('sucesso'=>1);
		return $result;
		}



		}	
		
		function executeSelectQuery($query) 
		{
			$conn = $this->connectDB(); 
			$result = $this->conn->query($query);

	//		echo $query;
						
			while ( $row = $result->fetch( PDO::FETCH_ASSOC ) )
			{ 
			$resultset[] = $row;
			
		}	  
		if(!empty($resultset))
			
		//print_r(array_values ($resultset));
			return $resultset;
		}
		
		
		function executeBuscaCodigoSelectQuery($query) 
		{
		
			$conn = $this->connectDB(); 
			$result = $this->conn->query($query);
				
			$row = $result->fetch( PDO::FETCH_ASSOC ) ;
				return $row['Codigo'];
		}
		
		
		function executeBuscaLoginQuery($query) 
		{
		
			$conn = $this->connectDB(); 
			$result = $this->conn->query($query);
				
			$row = $result->fetch( PDO::FETCH_ASSOC ) ;
				return $row['Codigo'];
		}


	}	
		
		
		
		
?>		
		
		
	