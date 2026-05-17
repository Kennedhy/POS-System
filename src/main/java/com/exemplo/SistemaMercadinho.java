package com.exemplo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;

public class SistemaMercadinho extends JFrame {

    private JTextField txtCodigoPDV, txtQuantidadePDV;
    private JTable tabelaPDV;
    private DefaultTableModel modeloTabelaPDV;
    private JLabel lblTotalGeralPDV;

    private JTextField txtCadCodigo, txtCadDescricao, txtCadPreco, txtCadQuantidade;
    private JLabel lblCadFotoCaminho;

    private JComboBox<String> cbEditarSelecionarProduto;
    private JTextField txtEditarPreco, txtEditarQuantidadeAdicionar;

    private JTable tableEstoque;
    private DefaultTableModel modeloTabelaEstoque;
    private JLabel lblPainelFotoEstoque;

    public SistemaMercadinho() {
        setTitle("Sistema Comercial - Controle de Vendas e Estoque");
        setSize(1000, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane abasPrincipais = new JTabbedPane();

        abasPrincipais.addTab("Frente de Loja (PDV)", criarPainelPDV());
        abasPrincipais.addTab("Gestão de Produtos", criarPainelGestaoProdutos());
        abasPrincipais.addTab("Ver Estoque", criarPainelEstoque());

        add(abasPrincipais);

        alimentarInterfaceComDadosExemplo();
    }

    private JPanel criarPainelPDV() {
        JPanel painel = new JPanel(new BorderLayout(10, 10));

        JPanel painelEntrada = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        painelEntrada.setBorder(BorderFactory.createTitledBorder("Operação de Caixa"));
        painelEntrada.add(new JLabel("Código:"));
        txtCodigoPDV = new JTextField(12);
        painelEntrada.add(txtCodigoPDV);
        painelEntrada.add(new JLabel("Qtd:"));
        txtQuantidadePDV = new JTextField(4);
        txtQuantidadePDV.setText("1");
        painelEntrada.add(txtQuantidadePDV);
        painelEntrada.add(new JButton("Adicionar"));

        String[] colunasPDV = {"Item", "Código", "Descrição", "Qtd", "Preço Un.", "Total"};
        modeloTabelaPDV = new DefaultTableModel(colunasPDV, 0);
        tabelaPDV = new JTable(modeloTabelaPDV);
        JScrollPane scroll = new JScrollPane(tabelaPDV);

        JPanel painelDireito = new JPanel(new BorderLayout(10, 10));
        painelDireito.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel caixaTotal = new JPanel(new GridLayout(2, 1));
        JLabel lblTextoTotal = new JLabel("TOTAL DA COMPRA:", SwingConstants.CENTER);
        lblTextoTotal.setFont(new Font("Arial", Font.BOLD, 14));
        lblTotalGeralPDV = new JLabel("R$ 0,00", SwingConstants.CENTER);
        lblTotalGeralPDV.setFont(new Font("Arial", Font.BOLD, 40));
        lblTotalGeralPDV.setForeground(new Color(39, 174, 96));
        caixaTotal.add(lblTextoTotal);
        caixaTotal.add(lblTotalGeralPDV);

        JPanel caixaBotoes = new JPanel(new GridLayout(3, 1, 5, 5));
        String[] formas = {"Dinheiro", "Cartão", "Pix"};
        caixaBotoes.add(new JComboBox<>(formas));

        JButton btnFinalizar = new JButton("Finalizar Venda");
        btnFinalizar.setBackground(new Color(46, 204, 113));
        btnFinalizar.setForeground(Color.WHITE);
        caixaBotoes.add(btnFinalizar);

        painelDireito.add(caixaTotal, BorderLayout.NORTH);
        painelDireito.add(caixaBotoes, BorderLayout.SOUTH);

        painel.add(painelEntrada, BorderLayout.NORTH);
        painel.add(scroll, BorderLayout.CENTER);
        painel.add(painelDireito, BorderLayout.EAST);

        return painel;
    }

    private JPanel criarPainelGestaoProdutos() {
        JPanel painelPai = new JPanel(new BorderLayout());
        painelPai.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTabbedPane subAbasGestao = new JTabbedPane();
        subAbasGestao.setTabPlacement(JTabbedPane.TOP);

        subAbasGestao.addTab("Adicionar Produto Novo", criarSubAbaNovoProduto());
        subAbasGestao.addTab("Editar / Repor Produto Existente", criarSubAbaEditarProduto());

        painelPai.add(subAbasGestao, BorderLayout.CENTER);
        return painelPai;
    }

    private JPanel criarSubAbaNovoProduto() {
        JPanel painel = new JPanel(new GridBagLayout());
        painel.setBorder(BorderFactory.createTitledBorder("Formulário de Cadastro Inédito"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        painel.add(new JLabel("Código de Barras:"), gbc);
        gbc.gridx = 1; txtCadCodigo = new JTextField(20);
        painel.add(txtCadCodigo, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        painel.add(new JLabel("Descrição / Nome:"), gbc);
        gbc.gridx = 1; txtCadDescricao = new JTextField(20);
        painel.add(txtCadDescricao, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        painel.add(new JLabel("Preço Unitário (R$):"), gbc);
        gbc.gridx = 1; txtCadPreco = new JTextField(20);
        painel.add(txtCadPreco, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        painel.add(new JLabel("Quantidade Inicial:"), gbc);
        gbc.gridx = 1; txtCadQuantidade = new JTextField(20);
        txtCadQuantidade.setText("0");
        painel.add(txtCadQuantidade, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        painel.add(new JLabel("Foto do Produto:"), gbc);
        gbc.gridx = 1;
        JPanel painelFoto = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        JButton btnFoto = new JButton("Escolher Arquivo...");
        lblCadFotoCaminho = new JLabel("Nenhum arquivo selecionado");
        lblCadFotoCaminho.setFont(new Font("Arial", Font.ITALIC, 11));
        painelFoto.add(btnFoto);
        painelFoto.add(lblCadFotoCaminho);
        painel.add(painelFoto, gbc);

        gbc.gridx = 1; gbc.gridy = 5;
        JButton btnSalvarNovo = new JButton("Salvar Novo Produto");
        btnSalvarNovo.setBackground(new Color(52, 152, 219));
        btnSalvarNovo.setForeground(Color.WHITE);
        painel.add(btnSalvarNovo, gbc);

        return painel;
    }

    private JPanel criarSubAbaEditarProduto() {
        JPanel painel = new JPanel(new GridBagLayout());
        painel.setBorder(BorderFactory.createTitledBorder("Atualização de Cadastro e Entrada de Carga"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 10, 12, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        painel.add(new JLabel("Selecione o Produto:"), gbc);
        gbc.gridx = 1;
        cbEditarSelecionarProduto = new JComboBox<>();
        painel.add(cbEditarSelecionarProduto, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        painel.add(new JLabel("Novo Preço de Venda (R$):"), gbc);
        gbc.gridx = 1;
        txtEditarPreco = new JTextField(20);
        painel.add(txtEditarPreco, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        painel.add(new JLabel("Adicionar Qtd ao Estoque (+):"), gbc);
        gbc.gridx = 1;
        txtEditarQuantidadeAdicionar = new JTextField(20);
        txtEditarQuantidadeAdicionar.setText("0");
        painel.add(txtEditarQuantidadeAdicionar, gbc);

        gbc.gridx = 1; gbc.gridy = 3;
        JButton btnAtualizar = new JButton("Atualizar Cadastro / Repor Estoque");
        btnAtualizar.setBackground(new Color(46, 204, 113));
        btnAtualizar.setForeground(Color.WHITE);
        painel.add(btnAtualizar, gbc);

        return painel;
    }

    private JPanel criarPainelEstoque() {
        JPanel painel = new JPanel(new BorderLayout(10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] colunasEstoque = {"Código", "Descrição do Produto", "Preço", "Qtd. Disponível"};
        modeloTabelaEstoque = new DefaultTableModel(colunasEstoque, 0);
        tableEstoque = new JTable(modeloTabelaEstoque);
        JScrollPane scrollEstoque = new JScrollPane(tableEstoque);

        JPanel painelLateralFoto = new JPanel(new BorderLayout());
        painelLateralFoto.setBorder(BorderFactory.createTitledBorder("Visualização da Foto"));
        painelLateralFoto.setPreferredSize(new Dimension(220, 0));

        lblPainelFotoEstoque = new JLabel("Selecione um item", SwingConstants.CENTER);
        lblPainelFotoEstoque.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        painelLateralFoto.add(lblPainelFotoEstoque, BorderLayout.CENTER);

        painel.add(scrollEstoque, BorderLayout.CENTER);
        painel.add(painelLateralFoto, BorderLayout.EAST);

        return painel;
    }

    private void alimentarInterfaceComDadosExemplo() {
        modeloTabelaEstoque.addRow(new Object[]{"789111", "Arroz Integral Tio João 1kg", "R$ 6,50", "20"});
        modeloTabelaEstoque.addRow(new Object[]{"789222", "Feijão Carioca Camil 1kg", "R$ 8,20", "35"});
        modeloTabelaEstoque.addRow(new Object[]{"789333", "Óleo de Soja Liza 900ml", "R$ 5,90", "8"});

        cbEditarSelecionarProduto.addItem("-- Escolha o produto para atualizar --");
        cbEditarSelecionarProduto.addItem("789111 - Arroz Integral Tio João 1kg");
        cbEditarSelecionarProduto.addItem("789222 - Feijão Carioca Camil 1kg");
        cbEditarSelecionarProduto.addItem("789333 - Óleo de Soja Liza 900ml");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SistemaMercadinho().setVisible(true));
    }
}