/** Produtos - com.wordpress.model */
package com.agendaconsulta.model;

import java.io.Serializable;

import javax.persistence.*;

/**
 * @author - Felipe
 * @since - 08/08/2012
 */
@Entity
@Table(name = "FOTO")
public class Foto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "FOTO_ID")
	private long id;
	@Lob
	@Column(name = "FOTO_IMAGEM", columnDefinition = "LONGBLOB")
	private byte[] imagem;
	@Column(name = "FOTO_DESCRICAO")
	private String descricao;
	@ManyToOne
	@JoinColumn(name = "PROD_ID")
	private Produto produto;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public byte[] getImagem() {
		return imagem;
	}

	public void setImagem(byte[] imagem) {
		this.imagem = imagem;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}
}