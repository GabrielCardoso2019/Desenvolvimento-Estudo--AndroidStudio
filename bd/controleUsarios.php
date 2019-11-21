<?php
require_once("dbcontroller.php");
require_once("SimpleRest.php");

class ClienteRestHandler extends SimpleRest
{
 
public function adicionarCliente ()
         {
    
            $nome       = $_GET ["nome"];
            $endereco   = $_GET ["txtEndereco"];  
            $cep        = $_GET ["txtCep"];
            $bairro     = $_GET ["txtBairro"];
            
    
    // Instanciar a classe DBController	
			$dbcontroller = new DBController();
    
            
            $query="Declare @codigo int 
            set @codigo =(select top(1) Codigo_Cliente from Cliente order by Codigo_Cliente desc)+1;
            select @codigo as 'Codigo';";
    
        $codigo = $dbcontroller->executeBuscaCodigoSelectQuery($query);
    
    
        $query ="insert into Cliente(Codigo_Cliente,NOME,ENDERECO,CEP,BAIRRO)
        values ('{$codigo}','{$nome}','{$endereco}','{$cep}','{$bairro}')";
    
        //echo $query;
    
                    
			$rawData = $dbcontroller->executeQuery($query);
		
				//Verificar se o retorno está "vazio"
			if(empty($rawData))
			{
				$statusCode = 404;
				$rawData = array('success' => 0);		
			} else {
				$statusCode = 200;
			}
	
			$requestContentType = 'application/json';//$_POST['HTTP_ACCEPT'];
			$this ->setHttpHeaders($requestContentType, $statusCode);
			$result = $rawData;
					
			if(strpos($requestContentType,'application/json') !== false)
			{
				$response = $this->encodeJson($result);
				echo $response;
			}
    
    
    
          }    
    
public function pesquisarCliente ()
         {
    
    
    $nome = $_GET ["txtpesquisarcliente"];
    
    // $query = "Select * from Cliente";
    
    $query = "Select * from Cliente where NOME like '%{$nome}%'";
    
    //echo $query;
    
    // Instanciar a classe DBController	
			$dbcontroller = new DBController();
			$rawData = $dbcontroller->executeSelectQuery($query);
		
				//Verificar se o retorno está "vazio"
			if(empty($rawData))
			{
				$statusCode = 404;
				$rawData = array('success' => 0);		
			} else {
				$statusCode = 200;
			}
	
			$requestContentType = 'application/json';//$_POST['HTTP_ACCEPT'];
			$this ->setHttpHeaders($requestContentType, $statusCode);
			$result = $rawData;
					
			if(strpos($requestContentType,'application/json') !== false)
			{
				$response = $this->encodeJson($result);
				echo $response;
			}
    
    
    
          }
    
    public function encodeJson($responseData) 
	{
		$jsonResponse = json_encode($responseData);
		return $jsonResponse;		
	}
    
    
}

if (isset($_GET ["nome"]))
{
    
 $cliente = new ClienteRestHandler();   
 $cliente -> adicionarCliente ();  
    
}
// Se o parametro txtpesquisarcliente estiver preenchido, instancia a classe e executa
// o método pesquisarCliente()
if (isset($_GET ["txtpesquisarcliente"]))
{
    
 $cliente = new ClienteRestHandler();   
 $cliente -> pesquisarCliente ();  
    
}

?>
