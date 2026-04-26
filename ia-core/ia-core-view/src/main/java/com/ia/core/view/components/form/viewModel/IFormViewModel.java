package com.ia.core.view.components.form.viewModel;

import com.ia.core.service.dto.properties.HasPropertyChangeSupport;
import com.ia.core.view.components.IViewModel;
import com.ia.core.view.components.properties.HasTranslator;
import com.ia.core.view.properties.HasModel;

import java.io.Serializable;

/**
 * @author Israel Araújo
 * @param <T> Tipo do dado do formulário
 */
public interface IFormViewModel<T extends Serializable>
  extends HasTranslator, HasModel<T>, IViewModel<T>,
  HasPropertyChangeSupport {

}
