package br.com.sistemacomercialerp.controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.util.Date;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartModel;

import br.com.sistemacomercialerp.servico.PedidoServico;
import br.com.sistemacomercialerp.util.LoggerUtil;

@Named
@RequestScoped
public class DashboardController implements Serializable {

	private static final long serialVersionUID = 1179884416737331025L;

	@Inject
	private PedidoServico servicoPedido;

	@Inject
	private DateFormat simpleDate;
	private LineChartModel model;

	public void inicializar() {
		model = new LineChartModel();
		model.setTitle("Vendas");
		model.setLegendPosition("e");
		model.setAnimate(true);
		model.setShowDatatip(true);

		model.getAxes().put(AxisType.X, new CategoryAxis());

		Axis yAxis = model.getAxis(AxisType.Y);
		yAxis.setMin(0);

		gerarGrafico("Todos as Vendas", null);
		gerarGrafico("Minhas Vendas", UsuarioLoginController.getUsuarioLogado());
	}

	public void gerarGrafico(String rotulo, String loginUsuario) {
		try {
			Map<Date, BigDecimal> valoresPorData = servicoPedido
					.valoresTotaisPorData(loginUsuario);

			ChartSeries series = new ChartSeries(rotulo);

			for (Date data : valoresPorData.keySet()) {
				series.set(simpleDate.format(data), valoresPorData.get(data));
				LoggerUtil.registrarLoggerInfo(
						getClass(),
						simpleDate.format(data) + " "
								+ valoresPorData.get(data));
			}

			model.addSeries(series);

		} catch (Exception e) {
			LoggerUtil.registrarLoggerErro(
					getClass(),
					"Erro ao gerar gráfico: " + e.getMessage() + " "
							+ e.getCause());
		}
	}

	public LineChartModel getModel() {
		return model;
	}
}
