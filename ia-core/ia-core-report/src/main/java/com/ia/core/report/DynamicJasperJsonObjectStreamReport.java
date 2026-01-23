package com.ia.core.report;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRMapCollectionDataSource;
import net.sf.jasperreports.engine.design.JRDesignBand;
import net.sf.jasperreports.engine.design.JRDesignExpression;
import net.sf.jasperreports.engine.design.JRDesignField;
import net.sf.jasperreports.engine.design.JRDesignSection;
import net.sf.jasperreports.engine.design.JRDesignStaticText;
import net.sf.jasperreports.engine.design.JRDesignTextField;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.type.HorizontalTextAlignEnum;
import net.sf.jasperreports.engine.type.ModeEnum;
import net.sf.jasperreports.engine.type.StretchTypeEnum;
import net.sf.jasperreports.engine.type.TextAdjustEnum;
import net.sf.jasperreports.engine.type.VerticalTextAlignEnum;

/**
 * Relatório dinâmico para impressão de dados JSON
 */
public class DynamicJasperJsonObjectStreamReport
  extends AbstractJasperReport<Stream<String>> {

  private final String[] cabecalhos;
  private final ObjectMapper objectMapper;

  /**
   * @param title      Título do relatório
   * @param cabecalhos Cabeçalhos das colunas
   * @param data       Stream de strings JSON
   */
  public DynamicJasperJsonObjectStreamReport(String title,
                                             String[] cabecalhos,
                                             Stream<String> data) {
    super(data, title);
    this.cabecalhos = cabecalhos;
    this.objectMapper = new ObjectMapper();
  }

  @Override
  JRDataSource dataSource()
    throws JRException {
    Collection<Map<String, ?>> registros = new ArrayList<>();

    getData().forEach(jsonString -> {
      try {
        // Converte a string JSON para um Map
        Map<String, Object> jsonMap = objectMapper
            .readValue(jsonString,
                       new TypeReference<Map<String, Object>>() {
                       });

        Map<String, Object> registro = new HashMap<>();

        // Para cada cabeçalho, busca o valor correspondente no JSON
        for (int coluna = 0; coluna < cabecalhos.length; coluna++) {
          String chave = cabecalhos[coluna];
          Object valor = jsonMap.get(chave);
          registro.put("COLUNA_" + coluna,
                       valor != null ? valor.toString() : "");
        }

        registros.add(registro);
      } catch (Exception e) {
        // Em caso de erro na conversão JSON, cria registro vazio
        Map<String, Object> registro = new HashMap<>();
        for (int coluna = 0; coluna < cabecalhos.length; coluna++) {
          registro.put("COLUNA_" + coluna, "");
        }
        registros.add(registro);
      }
    });

    return new JRMapCollectionDataSource(registros);
  }

  @Override
  public JasperReport report()
    throws JRException {
    JasperDesign jasperDesign = createJasperDesign();
    addDynamicFields(jasperDesign, cabecalhos.length);
    createDynamicBands(jasperDesign, cabecalhos);

    JasperReport jasperReport = JasperCompileManager
        .compileReport(jasperDesign);
    return jasperReport;
  }

  private void addDynamicFields(JasperDesign jasperDesign, int numColunas)
    throws JRException {
    for (int i = 0; i < numColunas; i++) {
      JRDesignField campo = new JRDesignField();
      campo.setName("COLUNA_" + i);
      campo.setValueClass(String.class); // Agora sempre String
      jasperDesign.addField(campo);
    }
  }

  private void createDynamicBands(JasperDesign jasperDesign,
                                  String[] cabecalhos) {
    int larguraColuna = (getColumnWidth()
        - (getSpacing() * (cabecalhos.length - 1)))
        / Math.max(cabecalhos.length, 1);
    int posicaoX = 0;

    createTitle(jasperDesign, posicaoX);
    createDetails(jasperDesign, cabecalhos, larguraColuna, posicaoX);
  }

  public int getSpacing() {
    return 0;
  }

  public int getDefaultHeight() {
    return 20;
  }

  public void createDetails(JasperDesign jasperDesign, String[] cabecalhos,
                            int columnWidth, int x) {
    JRDesignBand bandaCabecalho = createHeaderBand(jasperDesign);
    JRDesignBand bandaDetalhe = createDetailsBand(jasperDesign);

    for (int i = 0; i < cabecalhos.length; i++) {
      createColumnTitle(bandaCabecalho, cabecalhos, columnWidth, x, i);
      createColumnDetail(bandaDetalhe, columnWidth, x, i);
      x += columnWidth + getSpacing();
    }
  }

  public void createColumnDetail(JRDesignBand band, int columnWidth, int x,
                                 int index) {
    JRDesignTextField elementoDetalhe = new JRDesignTextField();
    elementoDetalhe.setTextAdjust(TextAdjustEnum.STRETCH_HEIGHT);
    elementoDetalhe.setStretchType(StretchTypeEnum.ELEMENT_GROUP_HEIGHT);
    elementoDetalhe.setX(x);
    elementoDetalhe.setY(0);
    elementoDetalhe.setWidth(columnWidth);
    elementoDetalhe.setHeight(getDefaultHeight());
    elementoDetalhe.setHorizontalTextAlign(HorizontalTextAlignEnum.LEFT);
    elementoDetalhe.setVerticalTextAlign(VerticalTextAlignEnum.TOP);

    JRDesignExpression expressao = new JRDesignExpression();
    expressao.setText("$F{COLUNA_" + index + "}");
    elementoDetalhe.setExpression(expressao);

    elementoDetalhe.getLineBox().getTopPen().setLineWidth(0.5f);
    elementoDetalhe.getLineBox().getBottomPen().setLineWidth(0.5f);

    band.addElement(elementoDetalhe);
  }

  public void createColumnTitle(JRDesignBand band, String[] cabecalhos,
                                int columnWidth, int x, int index) {
    JRDesignStaticText elementoCabecalho = new JRDesignStaticText();
    elementoCabecalho.setX(x);
    elementoCabecalho.setY(0);
    elementoCabecalho.setWidth(columnWidth);
    elementoCabecalho.setHeight(getDefaultHeight() + 10);
    elementoCabecalho.setText(cabecalhos[index]);
    elementoCabecalho.setHorizontalTextAlign(HorizontalTextAlignEnum.LEFT);
    elementoCabecalho.setVerticalTextAlign(VerticalTextAlignEnum.MIDDLE);
    elementoCabecalho.setBackcolor(new Color(200, 200, 200));
    elementoCabecalho.setMode(ModeEnum.OPAQUE);

    elementoCabecalho.getLineBox().getTopPen().setLineWidth(1f);
    elementoCabecalho.getLineBox().getBottomPen().setLineWidth(1f);

    band.addElement(elementoCabecalho);
  }

  public void createTitle(JasperDesign jasperDesign, int x) {
    JRDesignBand bandaTitulo = createTitleBand(getDefaultHeight());
    createTitleContent(bandaTitulo, x);
    jasperDesign.setTitle(bandaTitulo);
  }

  public void createTitleContent(JRDesignBand band, int x) {
    JRDesignStaticText elementoTitulo = new JRDesignStaticText();
    elementoTitulo.setHorizontalTextAlign(HorizontalTextAlignEnum.CENTER);
    elementoTitulo.setVerticalTextAlign(VerticalTextAlignEnum.MIDDLE);
    elementoTitulo.setBold(true);
    elementoTitulo.setText(getTitle());
    elementoTitulo.setFontSize(14f);
    elementoTitulo.setX(x);
    elementoTitulo.setY(0);
    elementoTitulo.setWidth(getColumnWidth());
    elementoTitulo.setHeight(getDefaultHeight() + 10);
    band.addElement(elementoTitulo);
  }

  public JRDesignBand createDetailsBand(JasperDesign jasperDesign) {
    JRDesignBand bandaDetalhe = new JRDesignBand();
    bandaDetalhe.setHeight(getDefaultHeight());
    ((JRDesignSection) jasperDesign.getDetailSection())
        .addBand(bandaDetalhe);
    return bandaDetalhe;
  }

  public JRDesignBand createHeaderBand(JasperDesign jasperDesign) {
    JRDesignBand bandaCabecalho = new JRDesignBand();
    bandaCabecalho.setHeight(getDefaultHeight() + 10);
    jasperDesign.setColumnHeader(bandaCabecalho);
    return bandaCabecalho;
  }

  public JRDesignBand createTitleBand(final int lineHeight) {
    JRDesignBand bandaTitulo = new JRDesignBand();
    bandaTitulo.setHeight(lineHeight + 10);
    return bandaTitulo;
  }

  private JasperDesign createJasperDesign()
    throws JRException {
    JasperDesign jasperDesign = new JasperDesign();
    jasperDesign.setName(getTitle());
    jasperDesign.setPageWidth(getPageWidth());
    jasperDesign.setPageHeight(getPageHeight());
    jasperDesign.setLeftMargin(getLeftMargin());
    jasperDesign.setRightMargin(getRightMargin());
    jasperDesign.setTopMargin(getTopMargin());
    jasperDesign.setBottomMargin(getBottomMargin());
    jasperDesign.setColumnWidth(getColumnWidth());
    return jasperDesign;
  }

  public int getColumnWidth() {
    return 555;
  }

  public int getBottomMargin() {
    return 30;
  }

  public int getTopMargin() {
    return 30;
  }

  public int getRightMargin() {
    return 20;
  }

  public int getLeftMargin() {
    return 20;
  }

  public int getPageHeight() {
    return 842;
  }

  public int getPageWidth() {
    return 595;
  }

  @Override
  public String getReportPath() {
    throw new IllegalArgumentException("Este relatório deve ser gerado dinamicamente");
  }
}
