package com.ia.core.view.client;

import com.ia.core.service.dto.DTO;
import com.ia.core.view.properties.AutoCastable;

import java.io.Serializable;

/**
 * Interface base para o cliente.
 *
 * @param <D> Tipo do {@link DTO}
 * @author Israel Araújo
 */
public interface BaseClient<D extends Serializable> extends AutoCastable {

}
