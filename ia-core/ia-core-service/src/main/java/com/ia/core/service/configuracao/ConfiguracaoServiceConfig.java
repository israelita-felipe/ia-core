package com.ia.core.service.configuracao;

import com.ia.core.model.configuracao.ConfiguracaoSistema;
import com.ia.core.service.CrudBaseService;
import com.ia.core.service.configuracao.dto.ConfiguracaoSistemaDTO;
import com.ia.core.service.mapper.BaseEntityMapper;
import com.ia.core.service.mapper.SearchRequestMapper;
import com.ia.core.service.repository.BaseEntityRepository;
import com.ia.core.service.translator.Translator;
import com.ia.core.service.validators.IServiceValidator;
import lombok.Getter;
import org.springframework.context.ApplicationEventPublisher;

import java.util.List;

public class ConfiguracaoServiceConfig<T extends ConfiguracaoSistema, D extends ConfiguracaoSistemaDTO<T>> extends CrudBaseService.CrudBaseServiceConfig<T, D> {
    @Getter
    private final List<ConfigurationProvider> providers;

    public ConfiguracaoServiceConfig(BaseEntityRepository<T> repository, BaseEntityMapper<T, D> mapper, SearchRequestMapper searchRequestMapper, Translator translator, List<IServiceValidator<D>> iServiceValidators, ApplicationEventPublisher eventPublisher, List<ConfigurationProvider> providers) {
        super(repository, mapper, searchRequestMapper, translator, iServiceValidators, eventPublisher);
        this.providers = providers;
    }

    public ConfiguracaoServiceConfig(BaseEntityRepository<T> repository, BaseEntityMapper<T, D> mapper, SearchRequestMapper searchRequestMapper, Translator translator, List<IServiceValidator<D>> iServiceValidators, List<ConfigurationProvider> providers) {
        super(repository, mapper, searchRequestMapper, translator, iServiceValidators);
        this.providers = providers;
    }
}
