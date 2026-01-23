package com.ia.core.report;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Stream;

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
 *
 */
public class DynamicJasperObjectArrayStreamReport
  extends AbstractJasperReport<Supplier<Stream<Object[]>>> {

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
        registro.put("COLUNA_" + coluna, valor);
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
      campo.setName("COLUNA_" + i);
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

  /**
   * @return
   */
  public int getSpacing() {
    return 0;
  }

  /**
   * @return
   */
  public int getDefaultHeight() {
    return 20;
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
    expressao.setText("$F{COLUNA_" + index + "}");
    elementoDetalhe.setExpression(expressao);

    // Adicionar borda
    elementoDetalhe.getLineBox().getTopPen().setLineWidth(0.5f);
    elementoDetalhe.getLineBox().getBottomPen().setLineWidth(0.5f);

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
    elementoCabecalho.setHeight(getDefaultHeight() + 10);
    elementoCabecalho.setText(cabecalhos[index]);
    elementoCabecalho.setHorizontalTextAlign(HorizontalTextAlignEnum.LEFT);
    elementoCabecalho.setVerticalTextAlign(VerticalTextAlignEnum.MIDDLE);
    elementoCabecalho.setBackcolor(new Color(200, 200, 200)); // Cinza claro
    elementoCabecalho.setMode(ModeEnum.OPAQUE);

    // Adicionar borda
    elementoCabecalho.getLineBox().getTopPen().setLineWidth(1f);
    elementoCabecalho.getLineBox().getBottomPen().setLineWidth(1f);

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
    elementoTitulo.setFontSize(14f);
    elementoTitulo.setX(x);
    elementoTitulo.setY(0);
    elementoTitulo.setWidth(getColumnWidth());
    elementoTitulo.setHeight(getDefaultHeight() + 10);

    band.addElement(elementoTitulo);
  }

  /**
   * @param jasperDesign
   * @param lineHeight
   * @return
   */
  public JRDesignBand createDetailsBand(JasperDesign jasperDesign) {
    JRDesignBand bandaDetalhe = new JRDesignBand();
    bandaDetalhe.setHeight(getDefaultHeight());
    ((JRDesignSection) jasperDesign.getDetailSection())
        .addBand(bandaDetalhe);
    return bandaDetalhe;
  }

  /**
   * @param jasperDesign
   * @param lineHeight
   * @return
   */
  public JRDesignBand createHeaderBand(JasperDesign jasperDesign) {
    JRDesignBand bandaCabecalho = new JRDesignBand();
    bandaCabecalho.setHeight(getDefaultHeight() + 10);
    jasperDesign.setColumnHeader(bandaCabecalho);
    return bandaCabecalho;
  }

  /**
   * @param lineHeight
   * @return
   */
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
    jasperDesign.setColumnWidth(getColumnWidth()); // Largura disponível após
                                                   // margens

    return jasperDesign;
  }

  /**
   * @return
   */
  public int getColumnWidth() {
    return 555;
  }

  /**
   * @return
   */
  public int getBottomMargin() {
    return 30;
  }

  /**
   * @return
   */
  public int getTopMargin() {
    return 30;
  }

  /**
   * @return
   */
  public int getRightMargin() {
    return 20;
  }

  /**
   * @return
   */
  public int getLeftMargin() {
    return 20;
  }

  /**
   * @return
   */
  public int getPageHeight() {
    return 842;
  }

  /**
   * @return
   */
  public int getPageWidth() {
    return 595;
  }

  @Override
  public String getReportPath() {
    throw new IllegalArgumentException("Este relatório deve ser gerado dinamicamente");
  }

}
