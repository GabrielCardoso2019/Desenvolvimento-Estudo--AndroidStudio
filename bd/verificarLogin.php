<?php
require_once("dbcontroller.php");
require_once("SimpleRest.php");


class LoginRestHandler extends SimpleRest 
{
	   
    
    public function appLogin ()
	{
		
		if (isset($_POST["txtUsuario"])) 
		{
			$nome	= $_POST["txtUsuario"];
			$senha	= $_POST["txtSenha"];
		
			$query ="
			declare @logado int,@usuario varchar(30)
			set @logado =(Select COUNT(*) from tblogiin where Nomeusuario ='{$nome}' 
			and senhausuario='{$senha}');
			if (@logado=0)
				begin
				set @logado=2
				end
			else
				begin
				if (@logado=1)
					begin
					set @logado =(Select COUNT(*) from tblogiin where logado='1' and NomeUsuario='{$nome}' and senhausuario='{$senha}');
					end
				end
				
				if (@logado=1)
					begin
						set @logado=3
					end
				else
					begin
					if (@logado=0)
						begin
						set @logado=4
						set @usuario = '{$nome}'
					 end
					end
				select @logado as 'logado', @usuario as 'usuario'
				if (@logado=4)
					begin
					update tblogiin
					set logado='1'
					where NomeUsuario='{$nome}' and senhausuario='{$senha}'
					end";
						
			
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
			
			$requestContentType = $_POST['HTTP_ACCEPT'];
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


	
	public function appDesconectarLogin ()
	{
		if (isset($_POST["txtLogout"])) 
		{
			$nome	= $_POST["txtLogout"];
			
			// Instanciar a classe DBController		
			$dbcontroller = new DBController();
		
			$query ="declare @logado int
				set @logado =(SELECT COUNT(*) FROM tbLogiin where Logado='1' and NomeUsuario='{$nome}');
				 if(@logado=1)
				  begin
                  set @logado=5
                  select @logado as 'logado' ;
                  
                  end
                  
                  
                 
                 if(@logado=5)
				  begin
				   update tbLogiin
				   set Logado='0'
				   where NomeUsuario='{$nome}'
				 end  ";
				
				
							
			$rawData = $dbcontroller->executeSelectQuery($query);
		
				//Verificar se o retorno está "vazio"
			if(empty($rawData))
			{
				$statusCode = 404;
				$rawData = array('logado' => 0);		
			} else {
				$statusCode = 200;
			}
	
			$requestContentType = $_POST['HTTP_ACCEPT'];
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
			
		$usuario = new LoginRestHandler() ;
		$usuario -> appLogin();
			
	}
	
	if(isset($_GET["page_sair"]))
	{
			
		$usuario = new LoginRestHandler() ;
		$usuario -> appDesconectarLogin();
			
	}
	
	

		

?>






