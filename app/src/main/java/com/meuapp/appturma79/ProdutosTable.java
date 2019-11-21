package com.meuapp.appturma79;


public class ProdutosTable {

    int CODIGO_PRODUTO;
    String NOME;
    String TIPO;
    String VALIDADE;

    public ProdutosTable(int CODIGO_PRODUTO,
                         String NOME,
                         String TIPO,
                         String VALIDADE) {

        super();

        this.CODIGO_PRODUTO = CODIGO_PRODUTO;
        this.NOME = NOME;
        this.TIPO = TIPO;
        this.VALIDADE = VALIDADE;

    }

    public  ProdutosTable(){
        CODIGO_PRODUTO = 0;
        NOME = null;
        TIPO = null;
        VALIDADE = null ;

    }


    public int getCODIGO_PRODUTO() {
        return CODIGO_PRODUTO;
    }

    public void setCODIGO_PRODUTO(int CODIGO_PRODUTO) {
        this.CODIGO_PRODUTO = CODIGO_PRODUTO;
    }




    public String getNOME() {
        return NOME;
    }

    public void setNOME(String NOME) {
        this.NOME = NOME;
    }




    public String getTIPO() {
        return TIPO;
    }

    public void setTIPO(String TIPO) {
        this.TIPO = TIPO;
    }




    public String getVALIDADE() {
        return VALIDADE;
    }

    public void setVALIDADE(String VALIDADE) {
        this.VALIDADE = VALIDADE;
    }
}

