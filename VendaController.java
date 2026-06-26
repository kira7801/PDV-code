package CONTROLLER;

import model.*;
import service.Caixa;
import java.util.ArrayList;

public class VendaController {
    private Venda venda = new Venda();
    private Caixa caixa = new Caixa();
    private ArrayList<Produto> estoqueDisponivel = new ArrayList<>();

    public VendaController() {
        estoqueDisponivel.add(new Produto(1, "Refrigerante", 7.50, 20));
        estoqueDisponivel.add(new Produto(2, "Chocolate", 4.00, 35));
        estoqueDisponivel.add(new Produto(3, "Água", 2.50, 50));
    }

    public ArrayList<Produto> getEstoqueDisponivel() {
        return estoqueDisponivel;
    }

    public Produto buscarProduto(String termo) {
        for (Produto p : estoqueDisponivel) {
            if (String.valueOf(p.getCodigo()).equals(termo) || p.getNome().equalsIgnoreCase(termo)) {
                return p;
            }
        }
        return null;
    }

    public boolean adicionarProdutoPorCodigo(int codigo, int qtd) {
        Produto p = buscarProduto(String.valueOf(codigo));
        if (p != null && p.getEstoque() >= qtd) {
            venda.adicionarItem(new ItemVenda(p, qtd));
            p.baixarEstoque(qtd);
            return true;
        }
        return false;
    }

    public double total() {
        return venda.calcularTotal();
    }

    public double troco(double pago, double totalComDesconto) {
        return caixa.calcularTroco(pago, totalComDesconto);
    }

    public void novaVenda() {
        venda = new Venda();
    }

    public Venda getVenda() {
        return venda;
    }
}