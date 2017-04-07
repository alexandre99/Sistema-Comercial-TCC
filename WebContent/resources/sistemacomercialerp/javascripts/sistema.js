$(document).ready(function() {
	configurarMoeda();
});

function configurarMoeda() {
	$(".moeda").maskMoney({
		decimal : ",",
		thousands : ".",
		allowZero : true
	});
}

function buscarCep() {
	var online = navigator.onLine;
	if (online == false) {
		alert("Verifique sua conexão de internet.");
	} else {
		var cep = $('#cep').val();
		if (cep !== '') {
			$.getJSON("//viacep.com.br/ws/" + cep + "/json/?callback=?",
					function(dados) {
						if (!("erro" in dados)) {

							$('#endereco').val(dados.logradouro.toUpperCase());
							$('#bairro').val(dados.bairro.toUpperCase());
							$('#cidade').val(dados.localidade.toUpperCase());
							PF('uf').selectValue(dados.uf);
							$('#pais').val('BRASIL');
							// alert(dados.uf);
						} else {
							alert("CEP não encontrado.");
						}

					});
		}

	}
}

function strToUpper(texto) {
	texto.value = texto.value.toUpperCase();
}

function calculaValorVendaAtravesMargemDeLucro() {
	var lucro = $('#lucro').val();
	var custo = $('#custo').val();
	var venda = 0.00;

	lucro = trocarValores(lucro, ".", "");
	lucro = trocarValores(lucro, ",", ".");

	custo = trocarValores(custo, ".", "");
	custo = trocarValores(custo, ",", ".");

	lucro = parseFloat(lucro);
	custo = parseFloat(custo);

	// alert(custo);

	if (lucro > 0.00 && custo > 0.00) {
		lucro = (lucro + 100.00) / 100.00;
		venda = custo * lucro;
		$('#venda').val(number_format(venda, 2, ',', '.'));

	}
}

function calcularPorcentagemLucro() {
	var custo = $('#custo').val();
	var venda = $('#venda').val();

	custo = trocarValores(custo, ".", "");
	custo = trocarValores(custo, ",", ".");

	venda = trocarValores(venda, ".", "");
	venda = trocarValores(venda, ",", ".");

	custo = parseFloat(custo);
	venda = parseFloat(venda);

	if (venda > 0.00 && custo > 0.00) {
		var margemLucro = ((venda * 100) / custo) - 100;
		$('#lucro').val(number_format(margemLucro, 2, ',', '.'));
	}
}

function saidaCampoQuantidadeEstoque() {
	var qtdItens = $('#qtdItens').val();
	if (qtdItens === "") {
		$('#qtdItens').val("0");
	} else if (qtdItens !== "0") {
		$('#qtdItens').val(trocarValores(qtdItens, ".", ","));
	}
}

function focoCampoQuantidadeEstoque() {
	var qtdItens = $('#qtdItens').val();
	if (qtdItens === "0") {
		$('#qtdItens').val("");
	}
}

function trocarValores(valorOriginal, primeiroValor, segundoValor) {
	var valorTrocado = valorOriginal.replace(primeiroValor, segundoValor);
	return valorTrocado;
}

function number_format(number, decimals, decPoint, thousandsSep) {
	decimals = decimals || 0;
	number = parseFloat(number);

	if (!decPoint || !thousandsSep) {
		decPoint = '.';
		thousandsSep = ',';
	}

	var roundedNumber = Math.round(Math.abs(number) * ('1e' + decimals)) + '';
	var numbersString = decimals ? roundedNumber.slice(0, decimals * -1)
			: roundedNumber;
	var decimalsString = decimals ? roundedNumber.slice(decimals * -1) : '';
	var formattedNumber = "";

	while (numbersString.length > 3) {
		formattedNumber += thousandsSep + numbersString.slice(-3)
		numbersString = numbersString.slice(0, -3);
	}

	return (number < 0 ? '-' : '') + numbersString + formattedNumber
			+ (decimalsString ? (decPoint + decimalsString) : '');
}
