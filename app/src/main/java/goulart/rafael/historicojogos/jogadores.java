package goulart.rafael.historicojogos;

public class jogadores {
    private int id, pife, banco, uno;
    private String nome, dataEntrada;

    public jogadores(int id, String nome, String dataEntrada, int pife, int banco, int uno) {
        this.id = id;
        this.nome = nome;
        this.dataEntrada = dataEntrada;
        this.pife = pife;
        this.banco = banco;
        this.uno = uno;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPife() {
        return pife;
    }

    public void setPife(int pife) {
        this.pife = pife;
    }

    public int getBanco() {
        return banco;
    }

    public void setBanco(int banco) {
        this.banco = banco;
    }

    public int getUno() {
        return uno;
    }

    public void setUno(int uno) {
        this.uno = uno;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDataEntrada() {
        return dataEntrada;
    }

    public void setDataEntrada(String dataEntrada) {
        this.dataEntrada = dataEntrada;
    }
}