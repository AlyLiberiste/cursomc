package com.alyliberiste.cursomc;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.alyliberiste.cursomc.domain.Categoria;
import com.alyliberiste.cursomc.domain.Cidade;
import com.alyliberiste.cursomc.domain.Cliente;
import com.alyliberiste.cursomc.domain.Endereco;
import com.alyliberiste.cursomc.domain.Estado;
import com.alyliberiste.cursomc.domain.ItemPedido;
import com.alyliberiste.cursomc.domain.Pagamento;
import com.alyliberiste.cursomc.domain.PagamentoComBoleto;
import com.alyliberiste.cursomc.domain.PagamentoComCartao;
import com.alyliberiste.cursomc.domain.Pedido;
import com.alyliberiste.cursomc.domain.Produto;
import com.alyliberiste.cursomc.domain.enums.EstadoPagamento;
import com.alyliberiste.cursomc.domain.enums.TipoCliente;
import com.alyliberiste.cursomc.repositories.CategoriaRepository;
import com.alyliberiste.cursomc.repositories.CidadeRepository;
import com.alyliberiste.cursomc.repositories.ClienteRepository;
import com.alyliberiste.cursomc.repositories.EnderecoRepository;
import com.alyliberiste.cursomc.repositories.EstadoRepository;
import com.alyliberiste.cursomc.repositories.ItemPedidoRepository;
import com.alyliberiste.cursomc.repositories.PagamentoRepository;
import com.alyliberiste.cursomc.repositories.PedidoRepository;
import com.alyliberiste.cursomc.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner{
	
	//dependencias
	@Autowired
	private CategoriaRepository categoriaRepository;
	@Autowired
	private ProdutoRepository produtoRepository;
	@Autowired
	private EstadoRepository estadoRepository;
	@Autowired
	private CidadeRepository cidadeRepository;
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private EnderecoRepository enderecoRepository;
	@Autowired
	private PedidoRepository pedidoRepository;
	@Autowired
	private PagamentoRepository pagamentoRepository;
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	
	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		//instanciando 2 cat
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		
		//instanciando 3 produtos
		
		Produto p1 = new Produto(null,"Computador", 2000.00);
		Produto p2 = new Produto(null,"Impressora", 800.00);
		Produto p3 = new Produto(null,"Mouse", 80.00);
		
		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));
		
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));
			
		//salvar cat e produtos no BD
		//salvar sempre quem é independente
		categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
		produtoRepository.saveAll(Arrays.asList(p1, p2, p3));

		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");
		
		Cidade c1 = new Cidade(null, "Uberlândia", est1);
		Cidade c2 = new Cidade(null, "São Paulo", est2);
		Cidade c3 = new Cidade(null, "Campinas", est2);
		
		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2, c3));
		
		estadoRepository.saveAll(Arrays.asList(est1, est2));
		cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));
		
		Cliente cli1 = new Cliente(null, "Maria Silva", "maria@gmail.com", "36378912377", TipoCliente.PESSOAFISICA );
		
		cli1.getTelefones().addAll(Arrays.asList("27363323", "938383939"));
		
		Endereco e1 = new Endereco(null, "Rua 28 de Maio", "300", "Apto 202", "Cidade Jd","13560311", cli1, c1);
		Endereco e2 = new Endereco(null, "Avenida Brasil", "100", "Sala 10", "Zona Norte", "13310550", cli1, c2);
		
		cli1.getEnderecos().addAll(Arrays.asList(e1, e2));
		
		clienteRepository.saveAll(Arrays.asList(cli1));
		enderecoRepository.saveAll(Arrays.asList(e1, e2));
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm"); // obj auxiliar p/ instante do pedido
		
		Pedido ped1 = new Pedido(null, sdf.parse("30/09/2017 10:32"), cli1, e1);
		Pedido ped2 = new Pedido(null, sdf.parse("10/10/2017 19:35"), cli1, e2);
		//deixei campo pagamento vazio pq deixei pedido e pagamto ind
		
		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pagto1);
		
		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("20/10/2017 00:00"), null);
		ped2.setPagamento(pagto2);
		
		cli1.getPedidos().addAll(Arrays.asList(ped1, ped2)); //associar cli1 a seus pedidos
		
		pedidoRepository.saveAll(Arrays.asList(ped1, ped2));
		pagamentoRepository.saveAll(Arrays.asList(pagto1, pagto2));
		
		ItemPedido ip1 = new ItemPedido(ped1, p1,0.00, 1, 2000.00);
		ItemPedido ip2 = new ItemPedido(ped1, p3, 0.00, 2, 80.00);
		ItemPedido ip3 = new ItemPedido(ped2, p2, 100.00, 1, 800.00);
		
		ped1.getItens().addAll(Arrays.asList(ip1, ip2));
		ped2.getItens().addAll(Arrays.asList(ip3));
		
		p1.getItens().addAll(Arrays.asList(ip1));
		p2.getItens().addAll(Arrays.asList(ip3));
		p3.getItens().addAll(Arrays.asList(ip2));
		
		itemPedidoRepository.saveAll(Arrays.asList(ip1, ip2, ip3));	
		

	}

}
