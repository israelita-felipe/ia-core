package com.ia.core.service.dto.filter;

/**
 * Tradutor de filtro
 *
 * @author Israel Araújo
 */
@SuppressWarnings("javadoc")
public class FilterRequestTranslator {
  public static final String FILTER_REQUEST_CLASS = FilterRequestDTO.class
      .getCanonicalName();
  public static final String FILTER_REQUEST = "filter.request";
  public static final String VALUE = "filter.request.value";
  public static final String KEY = "filter.request.key";
  public static final String NEGATE = "filter.request.negate";
  public static final String OPERATOR = "filter.request.operator";
  public static final String FIELD_TYPE = "filter.request.fieldType";
  public static final String TEXT_FILTER = "filter.request.text.filter";
  public static final String DOUBLE_FILTER = "filter.request.double.filter";
  public static final String INTEGER_FILTER = "filter.request.integer.filter";
  public static final String LONG_FILTER = "filter.request.long.filter";
  public static final String BOOLEAN_FILTER = "filter.request.boolean.filter";
  public static final String CHARACTER_FLTER = "filter.request.character.filter";
  public static final String DATE_FILTER = "filter.request.date.filter";
  public static final String TIME_FILTER = "filter.request.time.filter";
  public static final String DATE_TIME_FILTER = "filter.request.date.time.filter";
  public static final String ENUM_FILTER = "filter.request.enum.filter";

  /**
   * Campos de ajuda
   */
  public static final class HELP {
    public static final String FILTER_REQUEST = "filter.request.help";
    public static final String VALUE = "filter.request.help.value";
    public static final String KEY = "filter.request.help.key";
    public static final String OPERATOR = "filter.request.help.operator";
    public static final String FIELD_TYPE = "filter.request.help.fieldType";
  }
}
