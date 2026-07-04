package com.ia.core.report;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRMapCollectionDataSource;
import net.sf.jasperreports.engine.design.*;
import net.sf.jasperreports.engine.type.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * Relatório dinâmico para impressão de dados em array de objetos.
 * <p>
 * Gera relatórios JasperReports dinamicamente a partir de um stream de arrays de objetos.
 * Cria automaticamente campos, bandas e elementos baseados nos cabeçalhos fornecidos.
 * Suporta exportação para PDF e XLS através da classe pai.
 * </p>
 *
 * @author IA
 * @since 1.0
 */
public class DynamicJasperObjectArrayStreamReport
  extends AbstractJasperReport<Supplier<Stream<Object[]>>> {

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

  /**
   * @param data
   */
  public DynamicJasperObjectArrayStreamReport(String title,
                                              String[] cabecalhos,
                                              Supplier<Stream<Object[]>> data) {
    super(data, title);
    this.cabecalhos = cabecalhos;
  }

  @Override
  JRDataSource dataSource()
    throws JRException {

    Collection<Map<String, ?>> registros = new ArrayList<>();

    getData().get().forEach(linha -> {
      Map<String, Object> registro = new HashMap<>();
      for (int coluna = 0; coluna < cabecalhos.length; coluna++) {
        Object valor = (coluna < linha.length) ? (linha[coluna] != null ? linha[coluna]
            .toString() : "") : "";
        registro.put(COLUMN_PREFIX + coluna, valor);
      }
      registros.add(registro);
    });

    return new JRMapCollectionDataSource(registros);
  }

  @Override
  public JasperReport report()
    throws JRException {
    // 1. Criar o design do relatório
    JasperDesign jasperDesign = createJasperDesign();

    // 2. Adicionar campos dinâmicos baseados nos cabeçalhos
    addDynamicFields(jasperDesign, cabecalhos.length);

    // 3. Criar bandas de cabeçalho e detalhe
    createDynamicBands(jasperDesign, cabecalhos);

    // 4. Compilar o relatório
    JasperReport jasperReport = JasperCompileManager
        .compileReport(jasperDesign);
    return jasperReport;
  }

  private void addDynamicFields(JasperDesign jasperDesign, int numColunas)
    throws JRException {
    for (int i = 0; i < numColunas; i++) {
      JRDesignField campo = new JRDesignField();
      campo.setName(COLUMN_PREFIX + i);
      campo.setValueClass(Object.class);
      jasperDesign.addField(campo);
    }
  }

  private void createDynamicBands(JasperDesign jasperDesign,
                                  String[] cabecalhos) {

    int larguraColuna = (getColumnWidth()
        - (getSpacing() * (cabecalhos.length - 1)))
        / Math.max(cabecalhos.length, 1);
    int posicaoX = 0;

    // Criar banda de título do relatório
    createTitle(jasperDesign, posicaoX);

    createDetails(jasperDesign, cabecalhos, larguraColuna, posicaoX);
  }

  public int getSpacing() {
    return DEFAULT_SPACING;
  }

  public int getDefaultHeight() {
    return DEFAULT_HEIGHT;
  }

  /**
   * @param jasperDesign
   * @param cabecalhos
   * @param width
   * @param columnWidth
   * @param x
   * @param spacing
   */
  public void createDetails(JasperDesign jasperDesign, String[] cabecalhos,
                            int columnWidth, int x) {
    // Criar banda de cabeçalho (títulos das colunas)
    JRDesignBand bandaCabecalho = createHeaderBand(jasperDesign);

    // Criar banda de detalhe (dados)
    JRDesignBand bandaDetalhe = createDetailsBand(jasperDesign);

    for (int i = 0; i < cabecalhos.length; i++) {
      // === ELEMENTO DO CABEÇALHO (TÍTULO) ===
      createColumnTitle(bandaCabecalho, cabecalhos, columnWidth, x, i);

      // === ELEMENTO DE DETALHE (DADOS) ===
      createColumnDetail(bandaDetalhe, columnWidth, x, i);

      x += columnWidth + getSpacing();
    }

  }

  /**
   * @param band
   * @param width
   * @param columnWidth
   * @param x
   * @param index
   */
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

    // Expressão para buscar o campo correspondente
    JRDesignExpression expressao = new JRDesignExpression();
    expressao.setText("$F{" + COLUMN_PREFIX + index + "}");
    elementoDetalhe.setExpression(expressao);

    // Adicionar borda
    elementoDetalhe.getLineBox().getTopPen().setLineWidth(BORDER_WIDTH_THIN);
    elementoDetalhe.getLineBox().getBottomPen().setLineWidth(BORDER_WIDTH_THIN);

    band.addElement(elementoDetalhe);
  }

  /**
   * @param band
   * @param cabecalhos
   * @param width
   * @param columnWidth
   * @param x
   * @param index
   */
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

    // Adicionar borda
    elementoCabecalho.getLineBox().getTopPen().setLineWidth(BORDER_WIDTH_THICK);
    elementoCabecalho.getLineBox().getBottomPen().setLineWidth(BORDER_WIDTH_THICK);

    band.addElement(elementoCabecalho);
  }

  /**
   * @param jasperDesign
   * @param width
   * @param height
   * @param x
   */
  public void createTitle(JasperDesign jasperDesign, int x) {
    JRDesignBand bandaTitulo = createTitleBand(getDefaultHeight());
    createTitleContent(bandaTitulo, x);
    jasperDesign.setTitle(bandaTitulo);
  }

  /**
   * @param band
   * @param width
   * @param height
   * @param x
   */
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
