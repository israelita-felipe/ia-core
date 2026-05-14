package com.ia.core.view.config.feign;

import feign.Response;
import feign.Util;
import feign.codec.Decoder;
import tools.jackson.databind.JacksonModule;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.json.JsonMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.Collections;

/**
 * Decoder para Feign usando Jackson 3.x (tools.jackson).
 * <p>
 * O JacksonDecoder padrão do Feign 13.x usa Jackson 2.x (com.fasterxml.jackson),
 * mas o Spring Cloud OpenFeign 5.x migrou para Jackson 3.x. Este decoder
 * permite o uso de módulos como PageJacksonModule e SortJacksonModule que
 * agora dependem de Jackson 3.x.
 * </p>
 *
 * @author Israel Araújo
 */
public class Jackson3Decoder implements Decoder {

    private final ObjectMapper mapper;

    public Jackson3Decoder() {
        this(Collections.emptyList());
    }

    public Jackson3Decoder(Iterable<JacksonModule> modules) {
        JsonMapper.Builder builder = JsonMapper.builder();
        for (JacksonModule module : modules) {
            builder.addModule(module);
        }
        this.mapper = builder.build();
    }

    public Jackson3Decoder(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public Object decode(Response response, Type type) throws IOException {
        if (response.status() == 404 || response.status() == 204) {
            return Util.emptyValueOf(type);
        }
        if (response.body() == null) {
            return null;
        }
        Reader reader = response.body().asReader(response.charset());
        if (!reader.markSupported()) {
            reader = new BufferedReader(reader, 1);
        }
        try {
            reader.mark(1);
            if (reader.read() == -1) {
                return null;
            }
            reader.reset();
            return mapper.readValue(reader, mapper.constructType(type));
        } catch (Exception e) {
            if (e.getCause() != null && e.getCause() instanceof IOException) {
                throw (IOException) e.getCause();
            }
            throw e;
        }
    }
}
