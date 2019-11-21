package com.meuapp.appturma79;

public class UsuariosTable {

    int CodigoUsuario;
    String NomeUsuario;
    String SenhaUsuario;
    int Logado;


    public UsuariosTable (int CodigoUsuario,String NomeUsuario,
                          String SenhaUsuario, int Logado){
        super();
        this.CodigoUsuario = CodigoUsuario;
        this.NomeUsuario   = NomeUsuario;
        this.SenhaUsuario  = SenhaUsuario;
        this.Logado        = Logado;
    }

    public UsuariosTable(){
        CodigoUsuario = 0;
        NomeUsuario   = null;
        SenhaUsuario  = null;
        Logado        = 0;
    }

    public int getCodigoUsuario() {
        return CodigoUsuario;
    }

    public void setCodigoUsuario(int codigoUsuario) {
        CodigoUsuario = codigoUsuario;
    }

    public String getNomeUsuario() {
        return NomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        NomeUsuario = nomeUsuario;
    }

    public String getSenhaUsuario() {
        return SenhaUsuario;
    }

    public void setSenhaUsuario(String senhaUsuario) {
        SenhaUsuario = senhaUsuario;
    }

    public int getLogado() {
        return Logado;
    }

    public void setLogado(int logado) {
        Logado = logado;
    }
}
