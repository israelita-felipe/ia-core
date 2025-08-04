// Generated from Leitura.g4 by ANTLR 4.13.2
package com.ia.core.grammar;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.RuntimeMetaData;
import org.antlr.v4.runtime.Vocabulary;
import org.antlr.v4.runtime.VocabularyImpl;
import org.antlr.v4.runtime.atn.ATN;
import org.antlr.v4.runtime.atn.ATNDeserializer;
import org.antlr.v4.runtime.atn.LexerATNSimulator;
import org.antlr.v4.runtime.atn.PredictionContextCache;
import org.antlr.v4.runtime.dfa.DFA;

@SuppressWarnings({ "all", "warnings", "unchecked", "unused", "cast",
    "CheckReturnValue", "this-escape" })
public class LeituraLexer
  extends Lexer {
  static {
    RuntimeMetaData.checkVersion("4.13.2", RuntimeMetaData.VERSION);
  }

  protected static final DFA[] _decisionToDFA;
  protected static final PredictionContextCache _sharedContextCache = new PredictionContextCache();
  public static final int NUMERO = 1, TEXTO = 2, E = 3, NOVO_CAP_TOKEN = 4,
      INTERVAL_TOKEN = 5, COMMA_TOKEN = 6, WS = 7;
  public static String[] channelNames = { "DEFAULT_TOKEN_CHANNEL",
      "HIDDEN" };

  public static String[] modeNames = { "DEFAULT_MODE" };

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
  public static final String _serializedATN = "\u0004\u0000\u0007(\u0006\uffff\uffff\u0002\u0000\u0007\u0000\u0002\u0001"
      + "\u0007\u0001\u0002\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004"
      + "\u0007\u0004\u0002\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0001\u0000"
      + "\u0004\u0000\u0011\b\u0000\u000b\u0000\f\u0000\u0012\u0001\u0001\u0004"
      + "\u0001\u0016\b\u0001\u000b\u0001\f\u0001\u0017\u0001\u0002\u0001\u0002"
      + "\u0001\u0003\u0001\u0003\u0001\u0004\u0001\u0004\u0001\u0005\u0001\u0005"
      + "\u0001\u0006\u0004\u0006#\b\u0006\u000b\u0006\f\u0006$\u0001\u0006\u0001"
      + "\u0006\u0000\u0000\u0007\u0001\u0001\u0003\u0002\u0005\u0003\u0007\u0004"
      + "\t\u0005\u000b\u0006\r\u0007\u0001\u0000\u0006\u0001\u000009\u0003\u0000"
      + "  AZaz\u0001\u0000;;\u0001\u0000--\u0002\u0000,,::\u0002\u0000\t\n\r\r"
      + "*\u0000\u0001\u0001\u0000\u0000\u0000\u0000\u0003\u0001\u0000\u0000\u0000"
      + "\u0000\u0005\u0001\u0000\u0000\u0000\u0000\u0007\u0001\u0000\u0000\u0000"
      + "\u0000\t\u0001\u0000\u0000\u0000\u0000\u000b\u0001\u0000\u0000\u0000\u0000"
      + "\r\u0001\u0000\u0000\u0000\u0001\u0010\u0001\u0000\u0000\u0000\u0003\u0015"
      + "\u0001\u0000\u0000\u0000\u0005\u0019\u0001\u0000\u0000\u0000\u0007\u001b"
      + "\u0001\u0000\u0000\u0000\t\u001d\u0001\u0000\u0000\u0000\u000b\u001f\u0001"
      + "\u0000\u0000\u0000\r\"\u0001\u0000\u0000\u0000\u000f\u0011\u0007\u0000"
      + "\u0000\u0000\u0010\u000f\u0001\u0000\u0000\u0000\u0011\u0012\u0001\u0000"
      + "\u0000\u0000\u0012\u0010\u0001\u0000\u0000\u0000\u0012\u0013\u0001\u0000"
      + "\u0000\u0000\u0013\u0002\u0001\u0000\u0000\u0000\u0014\u0016\u0007\u0001"
      + "\u0000\u0000\u0015\u0014\u0001\u0000\u0000\u0000\u0016\u0017\u0001\u0000"
      + "\u0000\u0000\u0017\u0015\u0001\u0000\u0000\u0000\u0017\u0018\u0001\u0000"
      + "\u0000\u0000\u0018\u0004\u0001\u0000\u0000\u0000\u0019\u001a\u0005.\u0000"
      + "\u0000\u001a\u0006\u0001\u0000\u0000\u0000\u001b\u001c\u0007\u0002\u0000"
      + "\u0000\u001c\b\u0001\u0000\u0000\u0000\u001d\u001e\u0007\u0003\u0000\u0000"
      + "\u001e\n\u0001\u0000\u0000\u0000\u001f \u0007\u0004\u0000\u0000 \f\u0001"
      + "\u0000\u0000\u0000!#\u0007\u0005\u0000\u0000\"!\u0001\u0000\u0000\u0000"
      + "#$\u0001\u0000\u0000\u0000$\"\u0001\u0000\u0000\u0000$%\u0001\u0000\u0000"
      + "\u0000%&\u0001\u0000\u0000\u0000&\'\u0006\u0006\u0000\u0000\'\u000e\u0001"
      + "\u0000\u0000\u0000\u0004\u0000\u0012\u0017$\u0001\u0006\u0000\u0000";

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
    return new String[] { "NUMERO", "TEXTO", "E", "NOVO_CAP_TOKEN",
        "INTERVAL_TOKEN", "COMMA_TOKEN", "WS" };
  }

  private static String[] makeSymbolicNames() {
    return new String[] { null, "NUMERO", "TEXTO", "E", "NOVO_CAP_TOKEN",
        "INTERVAL_TOKEN", "COMMA_TOKEN", "WS" };
  }

  public LeituraLexer(CharStream input) {
    super(input);
    _interp = new LexerATNSimulator(this, _ATN, _decisionToDFA,
                                    _sharedContextCache);
  }

  @Override
  public ATN getATN() {
    return _ATN;
  }

  @Override
  public String[] getChannelNames() {
    return channelNames;
  }

  @Override
  public String getGrammarFileName() {
    return "Leitura.g4";
  }

  @Override
  public String[] getModeNames() {
    return modeNames;
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
}
