<?php
require_once("dbcontroller.php");
require_once("SimpleRest.php");


class ProdutosRestHandler extends SimpleRest 
{
	   
    
    public function IncluirProdutos ()
	{
		
		if (isset($_POST["txtNomeProdutos"])) 
		{
			$nome	= $_POST["txtNomeProdutos"];
			$unidade	= $_POST["txtUnidade"];
		  $validade	= $_POST["txtValidade"];
		
			$query ="declare @CODIGO_PRODUTO INT SET @CODIGO_PRODUTO = (SELECT TOP(1) CODIGO_PRODUTO FROM PRODUTOS ORDER BY CODIGO_PRODUTO DESC )+1
                
                if @CODIGO_PRODUTO is null
                    
                    begin
                    
                    set @CODIGO_PRODUTO = 1
                    
                    end
                    
                    insert into PRODUTOS (CODIGO_PRODUTO, 
                                         NOME,
                                         TIPO,
                                         VALIDADE) VALUES (@CODIGO_PRODUTO,'{$nome}','{$unidade}','{$validade}')";
                    
           // echo $query;
                    
                    
						
			
			// Instanciar a classe DBController		
			$dbcontroller = new DBController();
			
			$rawData = $dbcontroller->executeQuery($query);
		
				//Verificar se o retorno está "vazio"
			if(empty($rawData))
			{
				$statusCode = 404;
				$rawData = array('sucesso' => 0);		
			} else {
               	$statusCode = 200;
                //$rawData = array('sucesso' => 1);
			}
			
			$requestContentType = /*'application/json';*/  $_POST['HTTP_ACCEPT'];
			$this ->setHttpHeaders($requestContentType, $statusCode);
			//$result = $rawData;
			
			$result["RetornoDados"] = $rawData;
					
			if(strpos($requestContentType,'application/json') !== false)
			{
				$responses = $this->encodeJson($result);
				echo $responses;
			}
		
		}
	}


	
	public function ListarProdutos ()
	{
		if (isset($_POST["txtLista"])) 
		{
			$listar	= $_POST["txtLista"];
			
			// Instanciar a classe DBController		
			$dbcontroller = new DBController();
		
			$query ="select * from PRODUTOS where NOME like '%{$listar}%' ";
				
            echo $query;
				
							
			$rawData = $dbcontroller->executeSelectQuery($query);
		
				//Verificar se o retorno está "vazio"
			if(empty($rawData))
			{
				$statusCode = 404;
				$rawData = array('sucesso' => 0);		
			} else {
				$statusCode = 200;
			}
	
			$requestContentType = /*'application/json';*/ $_POST['HTTP_ACCEPT'];
			$this ->setHttpHeaders($requestContentType, $statusCode);
			//$result = $rawData;
			$result["RetornoDados"] = $rawData;
								
			if(strpos($requestContentType,'application/json') !== false)
			{
				$response = $this->encodeJson($result);
				echo $response;
			}
		
		}
	}
	
		
	
	public function encodeJson($responseData) 
	{
		$jsonResponse = json_encode($responseData);
		return $jsonResponse;		
	}

		
}	


    

if(isset($_GET["page_key"]))
	{
			
		$produtos = new ProdutosRestHandler() ;
		$produtos -> IncluirProdutos();
			
	}
	
	if(isset($_GET["page_lista"]))
	{
			
		$produtos = new ProdutosRestHandler() ;
		$produtos -> ListarProdutos();
			
	}
	
	

		

?>






