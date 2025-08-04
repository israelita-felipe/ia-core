package com.ia.core.view.properties;

import java.io.Serializable;

/**
 * Interface de propriedade que indica que pode ser copiado um objeto do tipo
 * <T>.
 *
 * @author Israel Araújo <T> O tipo do objeto a ser copiado.
 * @param <T> Tipo do parâmetro.
 */
public interface HasObjectCoping<T extends Serializable>
  extends HasClassType {

  /**
   * @param object Objeto a ser copiado
   * @return Um novo objeto T copiado a partir do objeto original.
   */
  T copyObject(T object);

}
