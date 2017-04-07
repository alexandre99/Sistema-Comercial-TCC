package br.com.sistemacomercialerp.util;

public class MensagemPadrao extends ConfiguracaoMensagem {

	public static void mensagemSalvar() {
		String msg = "Cadastro realizado com sucesso!";
		addMensagemInfo(msg);
	}

	public static void mensagemCancelamentoPedido() {
		String msg = "Pedido cancelado com sucesso!";
		addMensagemInfo(msg);
	}

	public static void mensagemCancelamentoBalancoEstoque() {
		String msg = "Balanço de estoque cancelado com sucesso!";
		addMensagemInfo(msg);
	}
	
	public static void mensagemErroSalvar(String mensagem) {
		String msg = "Nao foi possavel gravar o registro: " + mensagem;
		addMensagemError(msg);
	}

	public static void mensagemErroCarregarParametro(String msgErro) {
		String msg = "Nao foi possavel carregar o parametro: " + msgErro;
		addMensagemError(msg);
	}

	
	public static void mensagemErroAoLogar() {
		String msg = "Usuário e/ou Senha inválidos!";
		addMensagemError(msg);
	}

	public static void mensagemErroGerarRelatorio(String msgErro) {
		String msg = "Nao foi possavel gerar o relatorio " + msgErro;
		addMensagemError(msg);
	}

	public static void mensagemSemRegistroDeDadosRelatorio() {
		String msg = "Nao existe nenhum registro de dados";
		addMensagemInfo(msg);
	}

	public static void mensagemExclusaoComSucesso(String mensagem) {
		String msg = mensagem + " excluado com sucesso";
		addMensagemInfo(msg);
	}

	public static void mensagemErroValidacao(String mensagem) {
		String msg = mensagem;
		addMensagemWarn(msg);
	}

	public static void mensagemErroItemJaExistenteNaLista(String mensagem) {
		String msg = mensagem;
		addMensagemError(msg);
	}
	
	
}
