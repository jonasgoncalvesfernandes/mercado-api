package com.jonas.mercado.mercado_api.entity;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "vendas")
public class Venda {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private LocalDateTime dataHora;
    @Column(nullable = false)
    private BigDecimal valorTotal;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FormaPagamento formaPagamento;
    private String operadorNome;
    @ManyToOne @JoinColumn(name = "caixa_operacao_id")
    private CaixaOperacao caixaOperacao;
    @JsonManagedReference
    @OneToMany(mappedBy = "venda", cascade = CascadeType.ALL)
    private List<ItemVenda> itens = new ArrayList<>();

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public LocalDateTime getDataHora() { return dataHora; }
    public void setDataHora(LocalDateTime d) { this.dataHora = d; }
    public BigDecimal getValorTotal() { return valorTotal; }
    public void setValorTotal(BigDecimal v) { this.valorTotal = v; }
    public FormaPagamento getFormaPagamento() { return formaPagamento; }
    public void setFormaPagamento(FormaPagamento f) { this.formaPagamento = f; }
    public String getOperadorNome() { return operadorNome; }
    public void setOperadorNome(String v) { this.operadorNome = v; }
    public CaixaOperacao getCaixaOperacao() { return caixaOperacao; }
    public void setCaixaOperacao(CaixaOperacao c) { this.caixaOperacao = c; }
    public List<ItemVenda> getItens() { return itens; }
    public void setItens(List<ItemVenda> itens) { this.itens = itens; }
}
