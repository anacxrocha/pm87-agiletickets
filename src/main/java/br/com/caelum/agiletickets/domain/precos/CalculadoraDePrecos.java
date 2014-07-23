package br.com.caelum.agiletickets.domain.precos;

import java.math.BigDecimal;

import br.com.caelum.agiletickets.models.Sessao;
import br.com.caelum.agiletickets.models.TipoDeEspetaculo;

public class CalculadoraDePrecos {

	public static BigDecimal calcula(Sessao sessao, Integer quantidade) {
		BigDecimal preco;
		double porcentagemDeIngressosReservados = (sessao.getTotalIngressos() - sessao.getIngressosReservados())/ sessao.getTotalIngressos().doubleValue();
		preco = sessao.getPreco();
		switch (sessao.getEspetaculo().getTipo()) {
			case CINEMA: case SHOW:
				if( porcentagemDeIngressosReservados <= 0.05) 
					preco = preco.add(calculaPorcentagem(preco,0.10));				
				break;
			case BALLET:
				if(porcentagemDeIngressosReservados <= 0.50)  
					preco = sessao.getPreco().add(sessao.getPreco().multiply(BigDecimal.valueOf(0.20)));
				if(sessao.getDuracaoEmMinutos() > 60)
					preco = preco.add(calculaPorcentagem(sessao.getPreco(),0.10));
				break;
			case ORQUESTRA:
				if(porcentagemDeIngressosReservados <= 0.50)  
					preco = preco.add(calculaPorcentagem(preco,0.20));
				if(sessao.getDuracaoEmMinutos() > 60)
					preco = preco.add(calculaPorcentagem(sessao.getPreco(),0.10));
				break;
			default:
			
		}		
		return preco.multiply(BigDecimal.valueOf(quantidade));

	}
	
	private static BigDecimal calculaPorcentagem (BigDecimal base, double  porcentagem){
		return base.multiply(BigDecimal.valueOf(porcentagem));
	}

}