package model;

import java.util.ArrayList;

public class Venda {
    private ArrayList<ItemVenda> itens;
    private String nomeCliente;
    private String cpfCliente;

    public Venda() {
        itens = new ArrayList<>();
        nomeCliente = "";
        cpfCliente = "";
    }

    public void adicionarItem(ItemVenda item) {
        itens.add(item);
    }

    public double calcularTotal() {
        double total = 0;
        for(ItemVenda item : itens) {
            total += item.getSubtotal();
        }
        return total;
    }

    public ArrayList<ItemVenda> getItens() {
        return itens;
    }

    public String getNomeCliente() { return nomeCliente; }
    public void setNomeCliente(String nomeCliente) { this.nomeCliente = nomeCliente; }

    public String getCpfCliente() { return cpfCliente; }
    public void setCpfCliente(String cpfCliente) { this.cpfCliente = cpfCliente; }
}