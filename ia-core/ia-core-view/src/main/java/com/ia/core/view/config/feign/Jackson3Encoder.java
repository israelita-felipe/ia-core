package com.ia.core.view.config.feign;

import feign.RequestTemplate;
import feign.Util;
import feign.codec.EncodeException;
import feign.codec.Encoder;
import tools.jackson.databind.JacksonModule;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.json.JsonMapper;

import java.lang.reflect.Type;

/**
 * Encoder para Feign usando Jackson 3.x (tools.jackson).
 * <p>
 * O JacksonEncoder padrão do Feign 13.x usa Jackson 2.x (com.fasterxml.jackson),
 * mas o Spring Cloud OpenFeign 5.x migrou para Jackson 3.x. Este encoder
 * permite o uso de módulos como PageJacksonModule e SortJacksonModule que
 * agora dependem de Jackson 3.x.
 * </p>
 *
 * @author Israel Araújo
 */
public class Jackson3Encoder implements Encoder {

    private final ObjectMapper mapper;

    public Jackson3Encoder() {
        this(java.util.Collections.emptyList());
    }

    public Jackson3Encoder(Iterable<JacksonModule> modules) {
        JsonMapper.Builder builder = JsonMapper.builder();
        for (JacksonModule module : modules) {
            builder.addModule(module);
        }
        this.mapper = builder.build();
    }

    public Jackson3Encoder(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public void encode(Object object, Type bodyType, RequestTemplate template) {
        try {
            template.body(mapper.writeValueAsBytes(object), Util.UTF_8);
        } catch (Exception e) {
            throw new EncodeException(e.getMessage(), e);
        }
    }
}
