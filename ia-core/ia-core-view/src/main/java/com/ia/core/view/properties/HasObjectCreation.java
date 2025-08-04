package com.ia.core.view.properties;

import java.io.Serializable;

/**
 * Interface de propriedade que indica que pode ser criado um objeto do tipo
 * <T>.
 *
 * @author Israel Araújo <T> O tipo do objeto a ser criado.
 * @param <T> Tipo do parâmetro.
 */
public interface HasObjectCreation<T extends Serializable>
  extends HasClassType {

  /**
   * @return Um novo objeto T.
   */
  T createNewObject();

}
