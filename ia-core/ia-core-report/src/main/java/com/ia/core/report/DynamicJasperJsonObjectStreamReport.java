package com.ia.core.report;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRMapCollectionDataSource;
import net.sf.jasperreports.engine.design.*;
import net.sf.jasperreports.engine.type.*;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Relatório dinâmico para impressão de dados JSON.
 * <p>
 * Gera relatórios JasperReports dinamicamente a partir de um stream de strings JSON.
 * Cria automaticamente campos, bandas e elementos baseados nos cabeçalhos fornecidos.
 * Suporta exportação para PDF e XLS através da classe pai.
 * </p>
 *
 * @author IA
 * @since 1.0
 */
public class DynamicJasperJsonObjectStreamReport
  extends AbstractJasperReport<Stream<String>> {

  private static final String COLUMN_PREFIX = "COLUNA_";
  private static final float BORDER_WIDTH_THIN = 0.5f;
  private static final float BORDER_WIDTH_THICK = 1f;
  private static final float TITLE_FONT_SIZE = 14f;
  private static final int HEADER_COLOR_RGB = 200;
  private static final int DEFAULT_SPACING = 0;
  private static final int DEFAULT_HEIGHT = 20;
  private static final int HEADER_HEIGHT_EXTRA = 10;
  private static final int PAGE_WIDTH = 595;
  private static final int PAGE_HEIGHT = 842;
  private static final int LEFT_MARGIN = 20;
  private static final int RIGHT_MARGIN = 20;
  private static final int TOP_MARGIN = 30;
  private static final int BOTTOM_MARGIN = 30;
  private static final int COLUMN_WIDTH = 555;

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
          registro.put(COLUMN_PREFIX + coluna,
                       valor != null ? valor.toString() : "");
        }

        registros.add(registro);
      } catch (Exception e) {
        // Em caso de erro na conversão JSON, cria registro vazio
        Map<String, Object> registro = new HashMap<>();
        for (int coluna = 0; coluna < cabecalhos.length; coluna++) {
          registro.put(COLUMN_PREFIX + coluna, "");
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
      campo.setName(COLUMN_PREFIX + i);
      campo.setValueClass(String.class);
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
    return DEFAULT_SPACING;
  }

  public int getDefaultHeight() {
    return DEFAULT_HEIGHT;
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
    expressao.setText("$F{" + COLUMN_PREFIX + index + "}");
    elementoDetalhe.setExpression(expressao);

    elementoDetalhe.getLineBox().getTopPen().setLineWidth(BORDER_WIDTH_THIN);
    elementoDetalhe.getLineBox().getBottomPen().setLineWidth(BORDER_WIDTH_THIN);

    band.addElement(elementoDetalhe);
  }

  public void createColumnTitle(JRDesignBand band, String[] cabecalhos,
                                int columnWidth, int x, int index) {
    JRDesignStaticText elementoCabecalho = new JRDesignStaticText();
    elementoCabecalho.setX(x);
    elementoCabecalho.setY(0);
    elementoCabecalho.setWidth(columnWidth);
    elementoCabecalho.setHeight(getDefaultHeight() + HEADER_HEIGHT_EXTRA);
    elementoCabecalho.setText(cabecalhos[index]);
    elementoCabecalho.setHorizontalTextAlign(HorizontalTextAlignEnum.LEFT);
    elementoCabecalho.setVerticalTextAlign(VerticalTextAlignEnum.MIDDLE);
    elementoCabecalho.setBackcolor(new Color(HEADER_COLOR_RGB, HEADER_COLOR_RGB, HEADER_COLOR_RGB));
    elementoCabecalho.setMode(ModeEnum.OPAQUE);

    elementoCabecalho.getLineBox().getTopPen().setLineWidth(BORDER_WIDTH_THICK);
    elementoCabecalho.getLineBox().getBottomPen().setLineWidth(BORDER_WIDTH_THICK);

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
    elementoTitulo.setFontSize(TITLE_FONT_SIZE);
    elementoTitulo.setX(x);
    elementoTitulo.setY(0);
    elementoTitulo.setWidth(getColumnWidth());
    elementoTitulo.setHeight(getDefaultHeight() + HEADER_HEIGHT_EXTRA);
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
    bandaCabecalho.setHeight(getDefaultHeight() + HEADER_HEIGHT_EXTRA);
    jasperDesign.setColumnHeader(bandaCabecalho);
    return bandaCabecalho;
  }

  public JRDesignBand createTitleBand(final int lineHeight) {
    JRDesignBand bandaTitulo = new JRDesignBand();
    bandaTitulo.setHeight(lineHeight + HEADER_HEIGHT_EXTRA);
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
    return COLUMN_WIDTH;
  }

  public int getBottomMargin() {
    return BOTTOM_MARGIN;
  }

  public int getTopMargin() {
    return TOP_MARGIN;
  }

  public int getRightMargin() {
    return RIGHT_MARGIN;
  }

  public int getLeftMargin() {
    return LEFT_MARGIN;
  }

  public int getPageHeight() {
    return PAGE_HEIGHT;
  }

  public int getPageWidth() {
    return PAGE_WIDTH;
  }

  @Override
  public String getReportPath() {
    throw new IllegalArgumentException("Este relatório deve ser gerado dinamicamente");
  }
}
