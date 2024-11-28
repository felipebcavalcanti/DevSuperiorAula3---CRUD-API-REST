package com.devsuperior.dscommerce.services;

import com.devsuperior.dscommerce.services.exceptions.DataBaseException;
import com.devsuperior.dscommerce.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dscommerce.dto.ProductDTO;
import com.devsuperior.dscommerce.entities.Product;
import com.devsuperior.dscommerce.repository.ProductRepository;

@Service
public class ProductService {
	@Autowired
	private ProductRepository repository;

	@Transactional(readOnly = true)
	public ProductDTO findById(Long id) {
		Product product = repository.findById(id)
				// funcao pra customizar no console o retorno, foi criada essa
				// ResourceNotFoundException,
				// e esse ''orElseThrow'' e funcao java.
				.orElseThrow(() -> new ResourceNotFoundException("Recurso nao encontrado"));
		return new ProductDTO(product);
	}

	// Essa funcao aqui é para pegar todos os itens do DB.
//    @Transactional(readOnly = true)
//    public List<ProductDTO> findAll(){
//        List<Product> result = repository.findAll();
//        return result.stream().map(x -> new ProductDTO(x)).toList();
//    }
	// Essa funcao aqui e pra pegar as paginacoes, usando a Stream Page do Spring.
	@Transactional(readOnly = true)
	public Page<ProductDTO> findAll(Pageable pageable) {
		Page<Product> result = repository.findAll(pageable);
		return result.map(x -> new ProductDTO(x));
	}

	@Transactional
	public ProductDTO insert(ProductDTO dto) {
		Product entity = new Product(); // Cria o produto
		copyToEntity(dto, entity); // copia o produto para o dto
		entity = repository.save(entity); // salva a entidade(produto)
		return new ProductDTO(entity); // retorna em forma de DTO a entidade
	}

	@Transactional
	public ProductDTO update(ProductDTO dto, Long id) {
		try {
			Product entity = repository.getReferenceById(id); // criar um produto e esse produto vai ser igual ao
																// repositorio + referencia
			copyToEntity(dto, entity); // copia o produto pelo id
			entity = repository.save(entity); // a entidade ela vai ser igual a repository + save dela mesmo.
			return new ProductDTO(entity); // retorna o produto DTO(padrao) da entity
		} catch (EntityNotFoundException e) { // aqui vai o nome do error que da no console.
			throw new ResourceNotFoundException("Recurso Não Encontrado");
		}

	}

	// Para deletar via id.
	@Transactional(propagation = Propagation.SUPPORTS)
	public void delete(Long id) {
		if (!repository.existsById(id)) {
			throw new ResourceNotFoundException("Recurso não encontrado");
		}
		try {
			repository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataBaseException("Falha de integridade referencial");
		}
	}
	// funçao auxiliar para um codigo mais limpo.

	private void copyToEntity(ProductDTO dto, Product entity) {
		entity.setDescription(dto.getDescription());
		entity.setName(dto.getName());
		entity.setPrice(dto.getPrice());
		entity.setImgUrl(dto.getImgUrl());
	}
}
