package br.com.fiap.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter @AllArgsConstructor
public class ObjectApi {
	
	private String status;
	private Integer totalResults;
	private List<Article> articles;
	
}
