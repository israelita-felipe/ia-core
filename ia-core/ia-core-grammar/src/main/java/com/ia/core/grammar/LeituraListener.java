// Generated from Leitura.g4 by ANTLR 4.13.2
package com.ia.core.grammar;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link LeituraParser}.
 */
public interface LeituraListener
  extends ParseTreeListener {
  /**
   * Enter a parse tree produced by {@link LeituraParser#capitulo}.
   * 
   * @param ctx the parse tree
   */
  void enterCapitulo(LeituraParser.CapituloContext ctx);

  /**
   * Enter a parse tree produced by {@link LeituraParser#composicaoVersiculos}.
   * 
   * @param ctx the parse tree
   */
  void enterComposicaoVersiculos(LeituraParser.ComposicaoVersiculosContext ctx);

  /**
   * Enter a parse tree produced by {@link LeituraParser#intervaloVersiculo}.
   * 
   * @param ctx the parse tree
   */
  void enterIntervaloVersiculo(LeituraParser.IntervaloVersiculoContext ctx);

  /**
   * Enter a parse tree produced by {@link LeituraParser#leitura}.
   * 
   * @param ctx the parse tree
   */
  void enterLeitura(LeituraParser.LeituraContext ctx);

  /**
   * Enter a parse tree produced by {@link LeituraParser#leituras}.
   * 
   * @param ctx the parse tree
   */
  void enterLeituras(LeituraParser.LeiturasContext ctx);

  /**
   * Enter a parse tree produced by {@link LeituraParser#livro}.
   * 
   * @param ctx the parse tree
   */
  void enterLivro(LeituraParser.LivroContext ctx);

  /**
   * Enter a parse tree produced by {@link LeituraParser#program}.
   * 
   * @param ctx the parse tree
   */
  void enterProgram(LeituraParser.ProgramContext ctx);

  /**
   * Enter a parse tree produced by {@link LeituraParser#versiculo}.
   * 
   * @param ctx the parse tree
   */
  void enterVersiculo(LeituraParser.VersiculoContext ctx);

  /**
   * Enter a parse tree produced by {@link LeituraParser#versiculos}.
   * 
   * @param ctx the parse tree
   */
  void enterVersiculos(LeituraParser.VersiculosContext ctx);

  /**
   * Exit a parse tree produced by {@link LeituraParser#capitulo}.
   * 
   * @param ctx the parse tree
   */
  void exitCapitulo(LeituraParser.CapituloContext ctx);

  /**
   * Exit a parse tree produced by {@link LeituraParser#composicaoVersiculos}.
   * 
   * @param ctx the parse tree
   */
  void exitComposicaoVersiculos(LeituraParser.ComposicaoVersiculosContext ctx);

  /**
   * Exit a parse tree produced by {@link LeituraParser#intervaloVersiculo}.
   * 
   * @param ctx the parse tree
   */
  void exitIntervaloVersiculo(LeituraParser.IntervaloVersiculoContext ctx);

  /**
   * Exit a parse tree produced by {@link LeituraParser#leitura}.
   * 
   * @param ctx the parse tree
   */
  void exitLeitura(LeituraParser.LeituraContext ctx);

  /**
   * Exit a parse tree produced by {@link LeituraParser#leituras}.
   * 
   * @param ctx the parse tree
   */
  void exitLeituras(LeituraParser.LeiturasContext ctx);

  /**
   * Exit a parse tree produced by {@link LeituraParser#livro}.
   * 
   * @param ctx the parse tree
   */
  void exitLivro(LeituraParser.LivroContext ctx);

  /**
   * Exit a parse tree produced by {@link LeituraParser#program}.
   * 
   * @param ctx the parse tree
   */
  void exitProgram(LeituraParser.ProgramContext ctx);

  /**
   * Exit a parse tree produced by {@link LeituraParser#versiculo}.
   * 
   * @param ctx the parse tree
   */
  void exitVersiculo(LeituraParser.VersiculoContext ctx);

  /**
   * Exit a parse tree produced by {@link LeituraParser#versiculos}.
   * 
   * @param ctx the parse tree
   */
  void exitVersiculos(LeituraParser.VersiculosContext ctx);
}
