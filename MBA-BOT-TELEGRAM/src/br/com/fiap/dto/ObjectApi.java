package br.com.fiap.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor
public class ObjectApi {
	
	private String status;
	private Integer totalResults;
	private List<Article> articles;
	
}
