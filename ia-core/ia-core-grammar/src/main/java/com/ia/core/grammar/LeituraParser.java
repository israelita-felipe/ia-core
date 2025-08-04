// Generated from Leitura.g4 by ANTLR 4.13.2
package com.ia.core.grammar;

import java.util.List;

import org.antlr.v4.runtime.NoViableAltException;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.RuntimeMetaData;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.Vocabulary;
import org.antlr.v4.runtime.VocabularyImpl;
import org.antlr.v4.runtime.atn.ATN;
import org.antlr.v4.runtime.atn.ATNDeserializer;
import org.antlr.v4.runtime.atn.ParserATNSimulator;
import org.antlr.v4.runtime.atn.PredictionContextCache;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.TerminalNode;

@SuppressWarnings({ "all", "warnings", "unchecked", "unused", "cast",
    "CheckReturnValue", "this-escape" })
public class LeituraParser
  extends Parser {
  @SuppressWarnings("CheckReturnValue")
  public static class CapituloContext
    extends ParserRuleContext {
    public CapituloContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof LeituraListener)
        ((LeituraListener) listener).enterCapitulo(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof LeituraListener)
        ((LeituraListener) listener).exitCapitulo(this);
    }

    @Override
    public int getRuleIndex() {
      return RULE_capitulo;
    }

    public TerminalNode NUMERO() {
      return getToken(LeituraParser.NUMERO, 0);
    }
  }

  @SuppressWarnings("CheckReturnValue")
  public static class ComposicaoVersiculosContext
    extends ParserRuleContext {
    public ComposicaoVersiculosContext(ParserRuleContext parent,
                                       int invokingState) {
      super(parent, invokingState);
    }

    public TerminalNode E() {
      return getToken(LeituraParser.E, 0);
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof LeituraListener)
        ((LeituraListener) listener).enterComposicaoVersiculos(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof LeituraListener)
        ((LeituraListener) listener).exitComposicaoVersiculos(this);
    }

    @Override
    public int getRuleIndex() {
      return RULE_composicaoVersiculos;
    }

    public IntervaloVersiculoContext intervaloVersiculo() {
      return getRuleContext(IntervaloVersiculoContext.class, 0);
    }

    public VersiculoContext versiculo() {
      return getRuleContext(VersiculoContext.class, 0);
    }

    public VersiculosContext versiculos() {
      return getRuleContext(VersiculosContext.class, 0);
    }
  }
  @SuppressWarnings("CheckReturnValue")
  public static class IntervaloVersiculoContext
    extends ParserRuleContext {
    public IntervaloVersiculoContext(ParserRuleContext parent,
                                     int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof LeituraListener)
        ((LeituraListener) listener).enterIntervaloVersiculo(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof LeituraListener)
        ((LeituraListener) listener).exitIntervaloVersiculo(this);
    }

    @Override
    public int getRuleIndex() {
      return RULE_intervaloVersiculo;
    }

    public TerminalNode INTERVAL_TOKEN() {
      return getToken(LeituraParser.INTERVAL_TOKEN, 0);
    }

    public List<VersiculoContext> versiculo() {
      return getRuleContexts(VersiculoContext.class);
    }

    public VersiculoContext versiculo(int i) {
      return getRuleContext(VersiculoContext.class, i);
    }
  }
  @SuppressWarnings("CheckReturnValue")
  public static class LeituraContext
    extends ParserRuleContext {
    public LeituraContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    public CapituloContext capitulo() {
      return getRuleContext(CapituloContext.class, 0);
    }

    public TerminalNode COMMA_TOKEN() {
      return getToken(LeituraParser.COMMA_TOKEN, 0);
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof LeituraListener)
        ((LeituraListener) listener).enterLeitura(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof LeituraListener)
        ((LeituraListener) listener).exitLeitura(this);
    }

    @Override
    public int getRuleIndex() {
      return RULE_leitura;
    }

    public LivroContext livro() {
      return getRuleContext(LivroContext.class, 0);
    }

    public VersiculosContext versiculos() {
      return getRuleContext(VersiculosContext.class, 0);
    }
  }
  @SuppressWarnings("CheckReturnValue")
  public static class LeiturasContext
    extends ParserRuleContext {
    public LeiturasContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    public CapituloContext capitulo() {
      return getRuleContext(CapituloContext.class, 0);
    }

    public TerminalNode COMMA_TOKEN() {
      return getToken(LeituraParser.COMMA_TOKEN, 0);
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof LeituraListener)
        ((LeituraListener) listener).enterLeituras(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof LeituraListener)
        ((LeituraListener) listener).exitLeituras(this);
    }

    @Override
    public int getRuleIndex() {
      return RULE_leituras;
    }

    public LeituraContext leitura() {
      return getRuleContext(LeituraContext.class, 0);
    }

    public LeiturasContext leituras() {
      return getRuleContext(LeiturasContext.class, 0);
    }

    public List<TerminalNode> NOVO_CAP_TOKEN() {
      return getTokens(LeituraParser.NOVO_CAP_TOKEN);
    }

    public TerminalNode NOVO_CAP_TOKEN(int i) {
      return getToken(LeituraParser.NOVO_CAP_TOKEN, i);
    }

    public VersiculosContext versiculos() {
      return getRuleContext(VersiculosContext.class, 0);
    }
  }

  @SuppressWarnings("CheckReturnValue")
  public static class LivroContext
    extends ParserRuleContext {
    public LivroContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof LeituraListener)
        ((LeituraListener) listener).enterLivro(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof LeituraListener)
        ((LeituraListener) listener).exitLivro(this);
    }

    @Override
    public int getRuleIndex() {
      return RULE_livro;
    }

    public TerminalNode NUMERO() {
      return getToken(LeituraParser.NUMERO, 0);
    }

    public TerminalNode TEXTO() {
      return getToken(LeituraParser.TEXTO, 0);
    }
  }

  @SuppressWarnings("CheckReturnValue")
  public static class ProgramContext
    extends ParserRuleContext {
    public ProgramContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof LeituraListener)
        ((LeituraListener) listener).enterProgram(this);
    }

    public TerminalNode EOF() {
      return getToken(LeituraParser.EOF, 0);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof LeituraListener)
        ((LeituraListener) listener).exitProgram(this);
    }

    @Override
    public int getRuleIndex() {
      return RULE_program;
    }

    public LeiturasContext leituras() {
      return getRuleContext(LeiturasContext.class, 0);
    }
  }

  @SuppressWarnings("CheckReturnValue")
  public static class VersiculoContext
    extends ParserRuleContext {
    public VersiculoContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof LeituraListener)
        ((LeituraListener) listener).enterVersiculo(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof LeituraListener)
        ((LeituraListener) listener).exitVersiculo(this);
    }

    @Override
    public int getRuleIndex() {
      return RULE_versiculo;
    }

    public TerminalNode NUMERO() {
      return getToken(LeituraParser.NUMERO, 0);
    }
  }

  @SuppressWarnings("CheckReturnValue")
  public static class VersiculosContext
    extends ParserRuleContext {
    public VersiculosContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    public ComposicaoVersiculosContext composicaoVersiculos() {
      return getRuleContext(ComposicaoVersiculosContext.class, 0);
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof LeituraListener)
        ((LeituraListener) listener).enterVersiculos(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof LeituraListener)
        ((LeituraListener) listener).exitVersiculos(this);
    }

    @Override
    public int getRuleIndex() {
      return RULE_versiculos;
    }

    public IntervaloVersiculoContext intervaloVersiculo() {
      return getRuleContext(IntervaloVersiculoContext.class, 0);
    }

    public VersiculoContext versiculo() {
      return getRuleContext(VersiculoContext.class, 0);
    }
  }

  static {
    RuntimeMetaData.checkVersion("4.13.2", RuntimeMetaData.VERSION);
  }

  protected static final DFA[] _decisionToDFA;
  protected static final PredictionContextCache _sharedContextCache = new PredictionContextCache();

  public static final int NUMERO = 1, TEXTO = 2, E = 3, NOVO_CAP_TOKEN = 4,
      INTERVAL_TOKEN = 5, COMMA_TOKEN = 6, WS = 7;
  public static final int RULE_program = 0, RULE_livro = 1,
      RULE_leituras = 2, RULE_leitura = 3, RULE_capitulo = 4,
      RULE_versiculos = 5, RULE_intervaloVersiculo = 6,
      RULE_composicaoVersiculos = 7, RULE_versiculo = 8;

  public static final String[] ruleNames = makeRuleNames();

  private static final String[] _LITERAL_NAMES = makeLiteralNames();

  private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();

  public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES,
                                                                 _SYMBOLIC_NAMES);

  /**
   * @deprecated Use {@link #VOCABULARY} instead.
   */
  @Deprecated
  public static final String[] tokenNames;

  static {
    tokenNames = new String[_SYMBOLIC_NAMES.length];
    for (int i = 0; i < tokenNames.length; i++) {
      tokenNames[i] = VOCABULARY.getLiteralName(i);
      if (tokenNames[i] == null) {
        tokenNames[i] = VOCABULARY.getSymbolicName(i);
      }

      if (tokenNames[i] == null) {
        tokenNames[i] = "<INVALID>";
      }
    }
  }

  public static final String _serializedATN = "\u0004\u0001\u0007L\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"
      + "\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"
      + "\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002"
      + "\b\u0007\b\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0001\u0001\u0001"
      + "\u0001\u0001\u0003\u0001\u0019\b\u0001\u0001\u0002\u0001\u0002\u0001\u0002"
      + "\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002"
      + "\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002"
      + "\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0003\u0002.\b\u0002"
      + "\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0004"
      + "\u0001\u0004\u0001\u0005\u0001\u0005\u0001\u0005\u0003\u0005:\b\u0005"
      + "\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0007\u0001\u0007"
      + "\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007"
      + "\u0003\u0007H\b\u0007\u0001\b\u0001\b\u0001\b\u0000\u0000\t\u0000\u0002"
      + "\u0004\u0006\b\n\f\u000e\u0010\u0000\u0000I\u0000\u0012\u0001\u0000\u0000"
      + "\u0000\u0002\u0018\u0001\u0000\u0000\u0000\u0004-\u0001\u0000\u0000\u0000"
      + "\u0006/\u0001\u0000\u0000\u0000\b4\u0001\u0000\u0000\u0000\n9\u0001\u0000"
      + "\u0000\u0000\f;\u0001\u0000\u0000\u0000\u000eG\u0001\u0000\u0000\u0000"
      + "\u0010I\u0001\u0000\u0000\u0000\u0012\u0013\u0003\u0004\u0002\u0000\u0013"
      + "\u0014\u0005\u0000\u0000\u0001\u0014\u0001\u0001\u0000\u0000\u0000\u0015"
      + "\u0016\u0005\u0001\u0000\u0000\u0016\u0019\u0005\u0002\u0000\u0000\u0017"
      + "\u0019\u0005\u0002\u0000\u0000\u0018\u0015\u0001\u0000\u0000\u0000\u0018"
      + "\u0017\u0001\u0000\u0000\u0000\u0019\u0003\u0001\u0000\u0000\u0000\u001a"
      + ".\u0003\u0006\u0003\u0000\u001b\u001c\u0003\u0006\u0003\u0000\u001c\u001d"
      + "\u0005\u0004\u0000\u0000\u001d\u001e\u0003\u0004\u0002\u0000\u001e.\u0001"
      + "\u0000\u0000\u0000\u001f \u0003\u0006\u0003\u0000 !\u0005\u0004\u0000"
      + "\u0000!\"\u0003\b\u0004\u0000\"#\u0005\u0006\u0000\u0000#$\u0003\n\u0005"
      + "\u0000$.\u0001\u0000\u0000\u0000%&\u0003\u0006\u0003\u0000&\'\u0005\u0004"
      + "\u0000\u0000\'(\u0003\b\u0004\u0000()\u0005\u0006\u0000\u0000)*\u0003"
      + "\n\u0005\u0000*+\u0005\u0004\u0000\u0000+,\u0003\u0004\u0002\u0000,.\u0001"
      + "\u0000\u0000\u0000-\u001a\u0001\u0000\u0000\u0000-\u001b\u0001\u0000\u0000"
      + "\u0000-\u001f\u0001\u0000\u0000\u0000-%\u0001\u0000\u0000\u0000.\u0005"
      + "\u0001\u0000\u0000\u0000/0\u0003\u0002\u0001\u000001\u0003\b\u0004\u0000"
      + "12\u0005\u0006\u0000\u000023\u0003\n\u0005\u00003\u0007\u0001\u0000\u0000"
      + "\u000045\u0005\u0001\u0000\u00005\t\u0001\u0000\u0000\u00006:\u0003\u0010"
      + "\b\u00007:\u0003\f\u0006\u00008:\u0003\u000e\u0007\u000096\u0001\u0000"
      + "\u0000\u000097\u0001\u0000\u0000\u000098\u0001\u0000\u0000\u0000:\u000b"
      + "\u0001\u0000\u0000\u0000;<\u0003\u0010\b\u0000<=\u0005\u0005\u0000\u0000"
      + "=>\u0003\u0010\b\u0000>\r\u0001\u0000\u0000\u0000?@\u0003\u0010\b\u0000"
      + "@A\u0005\u0003\u0000\u0000AB\u0003\n\u0005\u0000BH\u0001\u0000\u0000\u0000"
      + "CD\u0003\f\u0006\u0000DE\u0005\u0003\u0000\u0000EF\u0003\n\u0005\u0000"
      + "FH\u0001\u0000\u0000\u0000G?\u0001\u0000\u0000\u0000GC\u0001\u0000\u0000"
      + "\u0000H\u000f\u0001\u0000\u0000\u0000IJ\u0005\u0001\u0000\u0000J\u0011"
      + "\u0001\u0000\u0000\u0000\u0004\u0018-9G";

  public static final ATN _ATN = new ATNDeserializer()
      .deserialize(_serializedATN.toCharArray());

  static {
    _decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
    for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
      _decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
    }
  }

  private static String[] makeLiteralNames() {
    return new String[] { null, null, null, "'.'" };
  }

  private static String[] makeRuleNames() {
    return new String[] { "program", "livro", "leituras", "leitura",
        "capitulo", "versiculos", "intervaloVersiculo",
        "composicaoVersiculos", "versiculo" };
  }

  private static String[] makeSymbolicNames() {
    return new String[] { null, "NUMERO", "TEXTO", "E", "NOVO_CAP_TOKEN",
        "INTERVAL_TOKEN", "COMMA_TOKEN", "WS" };
  }

  public LeituraParser(TokenStream input) {
    super(input);
    _interp = new ParserATNSimulator(this, _ATN, _decisionToDFA,
                                     _sharedContextCache);
  }

  public final CapituloContext capitulo()
    throws RecognitionException {
    CapituloContext _localctx = new CapituloContext(_ctx, getState());
    enterRule(_localctx, 8, RULE_capitulo);
    try {
      enterOuterAlt(_localctx, 1);
      {
        setState(52);
        match(NUMERO);
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  public final ComposicaoVersiculosContext composicaoVersiculos()
    throws RecognitionException {
    ComposicaoVersiculosContext _localctx = new ComposicaoVersiculosContext(_ctx,
                                                                            getState());
    enterRule(_localctx, 14, RULE_composicaoVersiculos);
    try {
      setState(71);
      _errHandler.sync(this);
      switch (getInterpreter().adaptivePredict(_input, 3, _ctx)) {
      case 1:
        enterOuterAlt(_localctx, 1); {
        setState(63);
        versiculo();
        setState(64);
        match(E);
        setState(65);
        versiculos();
      }
        break;
      case 2:
        enterOuterAlt(_localctx, 2); {
        setState(67);
        intervaloVersiculo();
        setState(68);
        match(E);
        setState(69);
        versiculos();
      }
        break;
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  @Override
  public ATN getATN() {
    return _ATN;
  }

  @Override
  public String getGrammarFileName() {
    return "Leitura.g4";
  }

  @Override
  public String[] getRuleNames() {
    return ruleNames;
  }

  @Override
  public String getSerializedATN() {
    return _serializedATN;
  }

  @Override
  @Deprecated
  public String[] getTokenNames() {
    return tokenNames;
  }

  @Override

  public Vocabulary getVocabulary() {
    return VOCABULARY;
  }

  public final IntervaloVersiculoContext intervaloVersiculo()
    throws RecognitionException {
    IntervaloVersiculoContext _localctx = new IntervaloVersiculoContext(_ctx,
                                                                        getState());
    enterRule(_localctx, 12, RULE_intervaloVersiculo);
    try {
      enterOuterAlt(_localctx, 1);
      {
        setState(59);
        versiculo();
        setState(60);
        match(INTERVAL_TOKEN);
        setState(61);
        versiculo();
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  public final LeituraContext leitura()
    throws RecognitionException {
    LeituraContext _localctx = new LeituraContext(_ctx, getState());
    enterRule(_localctx, 6, RULE_leitura);
    try {
      enterOuterAlt(_localctx, 1);
      {
        setState(47);
        livro();
        setState(48);
        capitulo();
        setState(49);
        match(COMMA_TOKEN);
        setState(50);
        versiculos();
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  public final LeiturasContext leituras()
    throws RecognitionException {
    LeiturasContext _localctx = new LeiturasContext(_ctx, getState());
    enterRule(_localctx, 4, RULE_leituras);
    try {
      setState(45);
      _errHandler.sync(this);
      switch (getInterpreter().adaptivePredict(_input, 1, _ctx)) {
      case 1:
        enterOuterAlt(_localctx, 1); {
        setState(26);
        leitura();
      }
        break;
      case 2:
        enterOuterAlt(_localctx, 2); {
        setState(27);
        leitura();
        setState(28);
        match(NOVO_CAP_TOKEN);
        setState(29);
        leituras();
      }
        break;
      case 3:
        enterOuterAlt(_localctx, 3); {
        setState(31);
        leitura();
        setState(32);
        match(NOVO_CAP_TOKEN);
        setState(33);
        capitulo();
        setState(34);
        match(COMMA_TOKEN);
        setState(35);
        versiculos();
      }
        break;
      case 4:
        enterOuterAlt(_localctx, 4); {
        setState(37);
        leitura();
        setState(38);
        match(NOVO_CAP_TOKEN);
        setState(39);
        capitulo();
        setState(40);
        match(COMMA_TOKEN);
        setState(41);
        versiculos();
        setState(42);
        match(NOVO_CAP_TOKEN);
        setState(43);
        leituras();
      }
        break;
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  public final LivroContext livro()
    throws RecognitionException {
    LivroContext _localctx = new LivroContext(_ctx, getState());
    enterRule(_localctx, 2, RULE_livro);
    try {
      setState(24);
      _errHandler.sync(this);
      switch (_input.LA(1)) {
      case NUMERO:
        enterOuterAlt(_localctx, 1); {
        setState(21);
        match(NUMERO);
        setState(22);
        match(TEXTO);
      }
        break;
      case TEXTO:
        enterOuterAlt(_localctx, 2); {
        setState(23);
        match(TEXTO);
      }
        break;
      default:
        throw new NoViableAltException(this);
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  public final ProgramContext program()
    throws RecognitionException {
    ProgramContext _localctx = new ProgramContext(_ctx, getState());
    enterRule(_localctx, 0, RULE_program);
    try {
      enterOuterAlt(_localctx, 1);
      {
        setState(18);
        leituras();
        setState(19);
        match(EOF);
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }
  public final VersiculoContext versiculo()
    throws RecognitionException {
    VersiculoContext _localctx = new VersiculoContext(_ctx, getState());
    enterRule(_localctx, 16, RULE_versiculo);
    try {
      enterOuterAlt(_localctx, 1);
      {
        setState(73);
        match(NUMERO);
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }
  public final VersiculosContext versiculos()
    throws RecognitionException {
    VersiculosContext _localctx = new VersiculosContext(_ctx, getState());
    enterRule(_localctx, 10, RULE_versiculos);
    try {
      setState(57);
      _errHandler.sync(this);
      switch (getInterpreter().adaptivePredict(_input, 2, _ctx)) {
      case 1:
        enterOuterAlt(_localctx, 1); {
        setState(54);
        versiculo();
      }
        break;
      case 2:
        enterOuterAlt(_localctx, 2); {
        setState(55);
        intervaloVersiculo();
      }
        break;
      case 3:
        enterOuterAlt(_localctx, 3); {
        setState(56);
        composicaoVersiculos();
      }
        break;
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }
}
