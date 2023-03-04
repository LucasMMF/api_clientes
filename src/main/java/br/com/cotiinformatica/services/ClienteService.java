package br.com.cotiinformatica.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cotiinformatica.entities.Cliente;
import br.com.cotiinformatica.repositories.IClienteRepository;

@Service
public class ClienteService {

	// Injeção de dependência
	@Autowired
	private IClienteRepository clienteRepository;

	// Método para realizar o cadastro de um cliente
	public void cadastrar(Cliente cliente) {

		// Verificar se o email do cliente já está cadastrado no banco de dados
		if (clienteRepository.findByEmail(cliente.getEmail()) != null)
			throw new IllegalArgumentException("O email informado já está cadastrado. Tente outro.");

		// Verificar se o cpf do cliente já está cadastrado no banco de dados
		if (clienteRepository.findByCpf(cliente.getCpf()) != null)
			throw new IllegalArgumentException("O cpf informado já está cadastrado. Tente outro.");

		// Gerando a data de cadastro do cliente
		cliente.setDataCadastro(new Date());

		// Gravando no banco de dados
		clienteRepository.save(cliente);
	}

	// Método para realizar a edição de um cliente
	public void atualizar(Cliente cliente) {

		// Buscando o cliente no banco de dados através do id
		Optional<Cliente> optional = clienteRepository.findById(cliente.getIdCliente());

		// Verificar se o cliente existe no banco de dados
		if (optional.isEmpty())
			throw new IllegalArgumentException("Cliente não encontrado. Verifique o ID informado.");

		// Buscar o cliente no banco de dados através do Cpf
		Cliente clientePorCpf = clienteRepository.findByCpf(cliente.getCpf());
		if (clientePorCpf != null && clientePorCpf.getIdCliente() != cliente.getIdCliente())
			throw new IllegalArgumentException("O CPF informado já está cadastrado para outro cliente. Tente outro.");

		// Buscar o cliente no banco de dados através do Email
		Cliente clientePorEmail = clienteRepository.findByEmail(cliente.getEmail());
		if (clientePorEmail != null && clientePorEmail.getIdCliente() != cliente.getIdCliente())
			throw new IllegalArgumentException("O Email informado já está cadastrado para outro cliente. Tente outro.");

		cliente.setDataCadastro(optional.get().getDataCadastro());

		// Atualizando o cliente no banco de dados
		clienteRepository.save(cliente);
	}

	// Método para excluir o cliente
	public Cliente excluir(Integer idCliente) {

		// Buscar o cliente no banco de dados através do id
		Optional<Cliente> optional = clienteRepository.findById(idCliente);

		// Verificando se o cliente foi encontrado
		if (optional.isEmpty())
			throw new IllegalArgumentException("Cliente não encontrado. Verifique o ID informado.");

		// Capturando o cliente
		Cliente cliente = optional.get();

		// Excluindo o cliente no banco de dados
		clienteRepository.delete(cliente);

		// Retornando os dados do cliente
		return cliente;
	}

	// Método para consultar todos os clientes
	public List<Cliente> consultarTodos() {
		// Buscar os clientes cadastrados no banco de dados
		return clienteRepository.findAll();
	}

	// Método para consultar 1 cliente através do id
	public Cliente obterPorId(Integer idCliente) {

		// Buscar o cliente através do ID
		Optional<Cliente> optional = clienteRepository.findById(idCliente);

		// Verificar se o cliente foi encontrado
		if (optional.isPresent())
			// Retornando os dados do cliente
			return optional.get();

		return null;
	}

}
