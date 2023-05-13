package br.com.service;

import br.com.domain.Carrinho;
import br.com.model.entity.ProdutoEntity;
import br.com.repository.CarrinhoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarrinhoService {

    @Autowired
    private CarrinhoRepository repository;

    public void finalizarCompra(Carrinho request) {
        request.getProdutos().forEach((idProduto, qtdCompra) -> {
            ProdutoEntity produto = repository.findById(idProduto).get();

            if(qtdCompra > 0) {

                if (qtdCompra > produto.getQuantidade()) {
                    throw new RuntimeException("Quantidade em estoque insuficiente");

                } else if ((produto.getEstaEmPromocao()) && qtdCompra > 3) {
                    throw new IllegalArgumentException("Poduto em promoção não pode comprar mais que 3 unidades.");
                } else {
                    produto.setQuantidade(produto.getQuantidade() - qtdCompra);
                    repository.save(produto);
                }
            } else {
                throw new RuntimeException("Quantidade deve ser superior a zero!");
            }
        });
        //return request;
    }
}


