package br.com.cotiinformatica.services;

import java.util.Date;

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

		// Verificando se os campos foram preenchidos
		if (cliente.getNome() == null || cliente.getNome().trim().length() == 0) {
			throw new IllegalArgumentException("Por favor, informe o nome do cliente.");
		} else if (cliente.getCpf() == null || cliente.getCpf().trim().length() == 0) {
			throw new IllegalArgumentException("Por favor, informe o cpf do cliente.");
		} else if (cliente.getEmail() == null || cliente.getEmail().trim().length() == 0) {
			throw new IllegalArgumentException("Por favor, informe o email do cliente.");
		} else if (cliente.getTelefone() == null || cliente.getTelefone().trim().length() == 0) {
			throw new IllegalArgumentException("Por favor, informe o telefone do cliente.");
		}

		// Gerando a data de cadastro do cliente
		cliente.setDataCadastro(new Date());

		// Gravando no banco de dados
		clienteRepository.save(cliente);
	}

}
