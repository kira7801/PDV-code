package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.ArrayList;
import CONTROLLER.VendaController;
import model.ItemVenda;
import model.Produto;

public class TelaPDV extends JFrame {

    private VendaController controller = new VendaController();
    private ArrayList<Object[]> historicoVendas = new ArrayList<>();
    private double saldoCaixaTotal = 0;

    private static final Color COLOR_UI_BG = new Color(240, 243, 246);
    private static final Color COLOR_HEADER_BG = new Color(20, 40, 80);
    private static final Color COLOR_PANEL_CONTENT_BG = Color.WHITE;
    private static final Color COLOR_RIGHT_PANEL_BG = new Color(43, 48, 53);
    private static final Color COLOR_TEXT_ON_DARK = new Color(200, 205, 210);
    private static final Color COLOR_TABLE_HEADER_BG = new Color(230, 235, 240);

    private static final Color COLOR_ACCENT_BLUE = new Color(0, 120, 230);
    private static final Color COLOR_ACCENT_GREY = new Color(110, 115, 120);
    private static final Color COLOR_ACCENT_RED = new Color(215, 55, 65);
    private static final Color COLOR_ACCENT_PURPLE = new Color(120, 60, 190);
    private static final Color COLOR_ACCENT_GREEN = new Color(45, 165, 75);
    private static final Color COLOR_ACCENT_CYAN = new Color(20, 155, 175);

    private JTextField txtNome, txtCpf, txtDesc, txtCodigoProd, txtQtdProd, txtPesquisa;
    private JLabel lblTotalGrande;
    private DefaultTableModel modelCarrinho;
    private JTextArea txtAreaEstoque;

    public TelaPDV() {
        setTitle("SISTEMA FRENTE DE LOJA - PDV");
        setSize(1280, 800);
        setMinimumSize(new Dimension(1024, 720));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(COLOR_UI_BG);
        setLayout(new BorderLayout());

        JPanel topo = new JPanel(new BorderLayout());
        topo.setBackground(COLOR_HEADER_BG);
        topo.setPreferredSize(new Dimension(0, 55));
        topo.setBorder(BorderFactory.createEmptyBorder(0, 25, 0, 25));

        JLabel lblLogo = new JLabel("PDV - PONTO DE VENDA");
        lblLogo.setForeground(Color.WHITE);
        lblLogo.setFont(new Font("Segoe UI", Font.BOLD, 18));

        JLabel lblOp = new JLabel("OPERADOR DE CAIXA: " + (TelaLogin.operadorLogado != null ? TelaLogin.operadorLogado : "ADMIN"));
        lblOp.setForeground(Color.WHITE);
        lblOp.setFont(new Font("Segoe UI", Font.BOLD, 13));

        topo.add(lblLogo, BorderLayout.WEST);
        topo.add(lblOp, BorderLayout.EAST);

        JPanel centroContainer = new JPanel(new GridLayout(2, 1, 0, 15));
        centroContainer.setBackground(COLOR_UI_BG);
        centroContainer.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel pnlCarrinho = new JPanel(new BorderLayout());
        pnlCarrinho.setBackground(COLOR_PANEL_CONTENT_BG);
        pnlCarrinho.setBorder(createStyledTitledBorder("CARRINHO DE COMPRAS (ITENS DA VENDA)"));

        String[] colunas = {"ITEM", "PRODUTO", "QTD", "PREÇO UNIT", "SUBTOTAL"};
        modelCarrinho = new DefaultTableModel(colunas, 0);
        JTable tabela = new JTable(modelCarrinho);
        tabela.setRowHeight(28);
        tabela.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tabela.setGridColor(new Color(235, 235, 235));
        tabela.setShowGrid(true);
        tabela.setSelectionBackground(new Color(220, 235, 250));

        JTableHeader cartHeader = tabela.getTableHeader();
        cartHeader.setFont(new Font("Segoe UI", Font.BOLD, 13));
        cartHeader.setBackground(COLOR_TABLE_HEADER_BG);
        cartHeader.setForeground(Color.BLACK);
        cartHeader.setPreferredSize(new Dimension(0, 32));
        pnlCarrinho.add(new JScrollPane(tabela), BorderLayout.CENTER);

        JPanel pnlEstoqueVis = new JPanel(new BorderLayout());
        pnlEstoqueVis.setBackground(COLOR_PANEL_CONTENT_BG);
        pnlEstoqueVis.setBorder(createStyledTitledBorder("CONSULTA DE PRODUTOS / ESTOQUE DISPONÍVEL"));

        JPanel pnlBusca = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 10));
        pnlBusca.setBackground(COLOR_PANEL_CONTENT_BG);
        JLabel searchLabel = new JLabel("Pesquisar (Nome ou Código):");
        searchLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        txtPesquisa = new JTextField(25);
        txtPesquisa.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtPesquisa.setPreferredSize(new Dimension(0, 30));
        JButton btnPesquisar = createStyledButton("Filtrar", COLOR_ACCENT_GREY, 110, 30);
        pnlBusca.add(searchLabel);
        pnlBusca.add(txtPesquisa);
        pnlBusca.add(btnPesquisar);
        pnlEstoqueVis.add(pnlBusca, BorderLayout.NORTH);

        txtAreaEstoque = new JTextArea();
        txtAreaEstoque.setEditable(false);
        txtAreaEstoque.setFont(new Font("Monospaced", Font.PLAIN, 13));
        txtAreaEstoque.setMargin(new Insets(12, 12, 12, 12));
        pnlEstoqueVis.add(new JScrollPane(txtAreaEstoque), BorderLayout.CENTER);

        centroContainer.add(pnlCarrinho);
        centroContainer.add(pnlEstoqueVis);

        JPanel direita = new JPanel();
        direita.setBackground(COLOR_RIGHT_PANEL_BG);
        direita.setLayout(new GridBagLayout());
        direita.setBorder(new EmptyBorder(25, 25, 25, 25));
        direita.setPreferredSize(new Dimension(380, 0));

        GridBagConstraints rgbc = new GridBagConstraints();
        rgbc.fill = GridBagConstraints.HORIZONTAL;
        rgbc.weightx = 1.0;
        rgbc.insets = new Insets(0, 0, 5, 0);

        addFormElement(direita, rgbc, "CÓDIGO DO PRODUTO:", txtCodigoProd = new JTextField(), 0);
        addFormElement(direita, rgbc, "QUANTIDADE:", txtQtdProd = new JTextField("1"), 2);

        JButton btnAddProd = createStyledButton("ADICIONAR PRODUTO", COLOR_ACCENT_BLUE, 0, 42);
        rgbc.gridy = 4; rgbc.insets = new Insets(10, 0, 25, 0);
        direita.add(btnAddProd, rgbc);

        addFormElement(direita, rgbc, "NOME DO CLIENTE:", txtNome = new JTextField(), 5);
        addFormElement(direita, rgbc, "CPF DO CLIENTE:", txtCpf = new JTextField(), 7);

        JButton btnCad = createStyledButton("VINCULAR CLIENTE", COLOR_ACCENT_GREY, 0, 38);
        rgbc.gridy = 9; rgbc.insets = new Insets(10, 0, 25, 0);
        direita.add(btnCad, rgbc);

        addFormElement(direita, rgbc, "DESCONTO (%):", txtDesc = new JTextField("0"), 10);

        rgbc.gridy = 12; rgbc.weighty = 1.0; rgbc.fill = GridBagConstraints.NONE;
        rgbc.anchor = GridBagConstraints.SOUTH; rgbc.insets = new Insets(15, 0, 0, 0);

        JPanel pnlTotal = new JPanel(new BorderLayout());
        pnlTotal.setBackground(Color.BLACK);
        pnlTotal.setBorder(new LineBorder(COLOR_ACCENT_GREEN, 2));
        pnlTotal.setPreferredSize(new Dimension(320, 105));

        JLabel lblT = new JLabel(" TOTAL R$");
        lblT.setForeground(COLOR_ACCENT_GREEN);
        lblT.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblT.setBorder(new EmptyBorder(6, 10, 0, 0));

        lblTotalGrande = new JLabel("0.00 ");
        lblTotalGrande.setForeground(Color.YELLOW);
        lblTotalGrande.setFont(new Font("Monospaced", Font.BOLD, 38));
        lblTotalGrande.setHorizontalAlignment(SwingConstants.RIGHT);

        pnlTotal.add(lblT, BorderLayout.NORTH);
        pnlTotal.add(lblTotalGrande, BorderLayout.CENTER);
        direita.add(pnlTotal, rgbc);

        JPanel inferior = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        inferior.setBackground(new Color(220, 225, 230));
        inferior.setPreferredSize(new Dimension(0, 75));

        JButton btnNovaVenda = createStyledButton("NOVA VENDA", COLOR_ACCENT_CYAN, 160, 45);
        JButton btnCancelarVenda = createStyledButton("CANCELAR VENDA", COLOR_ACCENT_RED, 160, 45);
        JButton btnVerCaixa = createStyledButton("VER CAIXA", COLOR_ACCENT_GREY, 160, 45);
        JButton btnHist = createStyledButton("HISTÓRICO", COLOR_ACCENT_PURPLE, 160, 45);
        JButton btnFinalizar = createStyledButton("FINALIZAR VENDA", COLOR_ACCENT_GREEN, 160, 45);

        inferior.add(btnNovaVenda);
        inferior.add(btnCancelarVenda);
        inferior.add(btnVerCaixa);
        inferior.add(btnHist);
        inferior.add(btnFinalizar);

        atualizarPainelEstoque();

        btnAddProd.addActionListener(e -> {
            try {
                int cod = Integer.parseInt(txtCodigoProd.getText().trim());
                int qtd = Integer.parseInt(txtQtdProd.getText().trim());

                boolean sucesso = controller.adicionarProdutoPorCodigo(cod, qtd);
                if (sucesso) {
                    Produto p = controller.buscarProduto(String.valueOf(cod));
                    int rowNum = modelCarrinho.getRowCount() + 1;
                    modelCarrinho.addRow(new Object[]{rowNum, p.getNome(), qtd, String.format("R$ %.2f", p.getPreco()), String.format("R$ %.2f", (p.getPreco() * qtd))});

                    lblTotalGrande.setText(String.format("%.2f ", controller.total()));
                    txtCodigoProd.setText("");
                    txtQtdProd.setText("1");
                    atualizarPainelEstoque();
                } else {
                    JOptionPane.showMessageDialog(this, "Produto não localizado ou quantidade indisponível!", "Aviso", JOptionPane.WARNING_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Insira dados válidos para a operação.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnPesquisar.addActionListener(e -> {
            String termo = txtPesquisa.getText().trim();
            if(termo.isEmpty()) {
                atualizarPainelEstoque();
            } else {
                Produto p = controller.buscarProduto(termo);
                if(p != null) {
                    txtAreaEstoque.setText("CÓDIGO\tPRODUTO\t\tPREÇO\tESTOQUE EM BANCO\n");
                    txtAreaEstoque.append("------------------------------------------------------------\n");
                    txtAreaEstoque.append(String.format("%d\t%-15s\tR$ %.2f\t%d UN\n", p.getCodigo(), p.getNome(), p.getPreco(), p.getEstoque()));
                } else {
                    txtAreaEstoque.setText("\n   Nenhum registro encontrado.");
                }
            }
        });

        btnCad.addActionListener(e -> {
            String nome = txtNome.getText().trim();
            String cpf = txtCpf.getText().trim();
            if (!nome.isEmpty()) {
                controller.getVenda().setNomeCliente(nome);
                controller.getVenda().setCpfCliente(cpf);
                JOptionPane.showMessageDialog(this, "Cliente vinculado com sucesso!");
            } else {
                JOptionPane.showMessageDialog(this, "O preenchimento do Nome é obrigatório.", "Erro", JOptionPane.WARNING_MESSAGE);
            }
        });

        btnFinalizar.addActionListener(e -> {
            if(controller.total() == 0) {
                JOptionPane.showMessageDialog(this, "Operação indisponível: carrinho vazio.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            double subtotal = controller.total();
            double pctDesconto = 0;
            try { pctDesconto = Double.parseDouble(txtDesc.getText().trim()); } catch(Exception ex){}

            double valorDesconto = subtotal * (pctDesconto / 100);
            double totalComDesconto = subtotal - valorDesconto;

            String pagoStr = JOptionPane.showInputDialog(this, String.format("Subtotal: R$ %.2f\nDesconto: R$ %.2f\nTotal Geral: R$ %.2f\n\nValor Pago pelo Cliente:", subtotal, valorDesconto, totalComDesconto));
            if (pagoStr == null) return;

            try {
                double valorPago = Double.parseDouble(pagoStr.trim());
                if (valorPago < totalComDesconto) {
                    JOptionPane.showMessageDialog(this, "Valor digitado é inferior ao total cobrado.", "Erro de Pagamento", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                double troco = controller.troco(valorPago, totalComDesconto);
                saldoCaixaTotal += totalComDesconto;

                String txtN = txtNome.getText().trim();
                String txtC = txtCpf.getText().trim();
                if (!txtN.isEmpty()) controller.getVenda().setNomeCliente(txtN);
                if (!txtC.isEmpty()) controller.getVenda().setCpfCliente(txtC);

                String nomeCli = controller.getVenda().getNomeCliente().isEmpty() ? "Consumidor Final" : controller.getVenda().getNomeCliente();
                String cpfCli = controller.getVenda().getCpfCliente().isEmpty() ? "Não Informado" : controller.getVenda().getCpfCliente();
                String opAtual = TelaLogin.operadorLogado != null ? TelaLogin.operadorLogado : "ADMIN";

                StringBuilder cupom = new StringBuilder();
                cupom.append("=========== COMPROVANTE DE VENDA ===========\n");
                cupom.append("Cliente: ").append(nomeCli).append("\n");
                cupom.append("CPF: ").append(cpfCli).append("\n");
                cupom.append("Operador: ").append(opAtual).append("\n");
                cupom.append("--------------------------------------------\n");
                for (ItemVenda iv : controller.getVenda().getItens()) {
                    cupom.append(String.format("%s (x%d) -> R$ %.2f\n", iv.getProduto().getNome(), iv.getQuantidade(), iv.getSubtotal()));
                }
                cupom.append("--------------------------------------------\n");
                cupom.append(String.format("Subtotal:       R$ %.2f\n", subtotal));
                cupom.append(String.format("Desconto:       R$ %.2f\n", valorDesconto));
                cupom.append(String.format("TOTAL:          R$ %.2f\n", totalComDesconto));
                cupom.append(String.format("Valor Pago:     R$ %.2f\n", valorPago));
                cupom.append(String.format("TROCO:          R$ %.2f\n", troco));
                cupom.append("============================================");

                JTextArea areaCupom = new JTextArea(cupom.toString());
                areaCupom.setFont(new Font("Monospaced", Font.PLAIN, 13));
                areaCupom.setEditable(false);
                JOptionPane.showMessageDialog(this, new JScrollPane(areaCupom), "EMISSÃO DE CUPOM FISCAL", JOptionPane.INFORMATION_MESSAGE);

                historicoVendas.add(new Object[]{nomeCli, String.format("R$ %.2f", totalComDesconto), opAtual});
                limparTudo();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Formato numérico não reconhecido.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnNovaVenda.addActionListener(e -> limparTudo());

        btnCancelarVenda.addActionListener(e -> {
            if(controller.total() > 0) {
                int op = JOptionPane.showConfirmDialog(this, "Confirmar o cancelamento completo desta venda?", "Atenção", JOptionPane.YES_NO_OPTION);
                if(op == JOptionPane.YES_OPTION) {
                    limparTudo();
                    JOptionPane.showMessageDialog(this, "A operação foi anulada.");
                }
            }
        });

        btnVerCaixa.addActionListener(e -> {
            JPanel pnlCard = new JPanel(new GridBagLayout());
            pnlCard.setBackground(Color.WHITE);
            pnlCard.setBorder(BorderFactory.createCompoundBorder(
                    new LineBorder(new Color(220, 220, 220), 1),
                    new EmptyBorder(25, 40, 25, 40)
            ));

            GridBagConstraints g = new GridBagConstraints();
            g.gridx = 0; g.fill = GridBagConstraints.CENTER;

            g.gridy = 0; g.insets = new Insets(0, 0, 15, 0);
            JLabel lblTitulo = new JLabel("FECHAMENTO PARCIAL");
            lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 12));
            lblTitulo.setForeground(new Color(120, 130, 140));
            pnlCard.add(lblTitulo, g);

            g.gridy = 1; g.insets = new Insets(0, 0, 5, 0);
            JLabel lblSaldo = new JLabel(String.format("R$ %.2f", saldoCaixaTotal));
            lblSaldo.setFont(new Font("Segoe UI", Font.BOLD, 36));
            lblSaldo.setForeground(new Color(20, 40, 80));
            pnlCard.add(lblSaldo, g);

            g.gridy = 2; g.insets = new Insets(5, 0, 0, 0);
            JLabel lblStatus = new JLabel("Balanço Total em Espécie");
            lblStatus.setFont(new Font("Segoe UI", Font.ITALIC, 13));
            lblStatus.setForeground(COLOR_ACCENT_GREEN);
            pnlCard.add(lblStatus, g);

            JOptionPane.showMessageDialog(this, pnlCard, "Movimentação Financeira", JOptionPane.PLAIN_MESSAGE);
        });

        btnHist.addActionListener(e -> {
            if (historicoVendas.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Sem registros de transações no histórico.", "Histórico Vazio", JOptionPane.INFORMATION_MESSAGE);
            } else {
                String[] hColunas = {"CLIENTE", "VALOR TOTAL", "OPERADOR"};
                DefaultTableModel hModel = new DefaultTableModel(hColunas, 0);
                for (Object[] linha : historicoVendas) {
                    hModel.addRow(linha);
                }
                JTable hTabela = new JTable(hModel);
                hTabela.setRowHeight(24);
                hTabela.setFont(new Font("Segoe UI", Font.PLAIN, 12));
                JTableHeader hHeader = hTabela.getTableHeader();
                hHeader.setFont(new Font("Segoe UI", Font.BOLD, 12));
                hHeader.setBackground(COLOR_TABLE_HEADER_BG);

                JScrollPane scroll = new JScrollPane(hTabela);
                scroll.setPreferredSize(new Dimension(480, 250));
                JOptionPane.showMessageDialog(this, scroll, "RELAÇÃO DE VENDAS", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        add(topo, BorderLayout.NORTH);
        add(centroContainer, BorderLayout.CENTER);
        add(direita, BorderLayout.EAST);
        add(inferior, BorderLayout.SOUTH);
    }

    private TitledBorder createStyledTitledBorder(String title) {
        TitledBorder tb = BorderFactory.createTitledBorder(new LineBorder(new Color(200, 205, 210), 1), title);
        tb.setTitleFont(new Font("Segoe UI", Font.BOLD, 13));
        tb.setTitleColor(Color.BLACK);
        return tb;
    }

    private JButton createStyledButton(String text, Color baseColor, int width, int height) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setForeground(Color.WHITE);
        btn.setBackground(baseColor);
        btn.setOpaque(true);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        if (width > 0 && height > 0) {
            btn.setPreferredSize(new Dimension(width, height));
        }
        return btn;
    }

    private void addFormElement(JPanel panel, GridBagConstraints gbc, String labelText, JTextField field, int gridy) {
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.BOLD, 12));
        label.setForeground(COLOR_TEXT_ON_DARK);
        gbc.gridy = gridy;
        panel.add(label, gbc);

        field.setFont(new Font("Segoe UI", Font.BOLD, 14));
        field.setForeground(Color.BLACK);
        field.setBackground(Color.WHITE);
        field.setCaretColor(Color.BLACK);
        field.setPreferredSize(new Dimension(320, 34));
        field.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(80, 85, 90), 1),
                BorderFactory.createEmptyBorder(2, 8, 2, 8)
        ));

        gbc.gridy = gridy + 1;
        panel.add(field, gbc);
    }

    private void atualizarPainelEstoque() {
        txtAreaEstoque.setText("CÓDIGO\tPRODUTO\t\tPREÇO\tESTOQUE EM BANCO\n");
        txtAreaEstoque.append("------------------------------------------------------------\n");
        for (Produto p : controller.getEstoqueDisponivel()) {
            txtAreaEstoque.append(String.format("%d\t%-15s\tR$ %.2f\t%d UN\n", p.getCodigo(), p.getNome(), p.getPreco(), p.getEstoque()));
        }
    }

    private void limparTudo() {
        controller.novaVenda();
        modelCarrinho.setRowCount(0);
        lblTotalGrande.setText("0.00 ");
        txtCodigoProd.setText("");
        txtQtdProd.setText("1");
        txtNome.setText("");
        txtCpf.setText("");
        txtDesc.setText("0");
        txtPesquisa.setText("");
        atualizarPainelEstoque();
    }
}