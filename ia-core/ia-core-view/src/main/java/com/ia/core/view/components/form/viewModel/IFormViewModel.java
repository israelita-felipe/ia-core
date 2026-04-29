package com.ia.core.view.components.form.viewModel;

import com.ia.core.service.dto.properties.HasPropertyChangeSupport;
import com.ia.core.view.components.IViewModel;
import com.ia.core.view.components.properties.HasTranslator;
import com.ia.core.view.properties.HasModel;

import java.io.Serializable;
/**
 * Model de dados para a view de i form.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a IFormViewModel
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
 */
public interface IFormViewModel<T extends Serializable>
  extends HasTranslator, HasModel<T>, IViewModel<T>,
  HasPropertyChangeSupport {

}
