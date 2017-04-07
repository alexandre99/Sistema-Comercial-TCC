package br.com.sistemacomercialerp.entidade;

public enum Estado {

	SELECIONE("SELECIONE"), AC("AC"), AL("AL"), AP("AP"), AM("AM"), BA("BA"), CE(
			"CE"), DF("DF"), ES("ES"), GO("GO"), MA("MA"), MT("MT"), MS("MS"), MG(
			"MG"), PARA("PA"), PARAIBA("PB"), PR("PR"), PE("PE"), PI("PI"), RJ(
			"RJ"), RN("RN"), RS("RS"), RO("RO"), RR("RR"), SC("SC"), SP("SP"), SE(
			"SE"), TO("TO");

	private String descricao;

	Estado(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

}
