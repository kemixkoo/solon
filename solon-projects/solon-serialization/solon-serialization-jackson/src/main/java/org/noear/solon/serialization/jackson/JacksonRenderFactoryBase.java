/*
 * Copyright 2017-2024 noear.org and authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.noear.solon.serialization.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;
import org.noear.solon.core.convert.Converter;
import org.noear.solon.serialization.JsonRenderFactory;

import java.io.IOException;
import java.util.Date;

/**
 * @author noear
 * @since 1.5
 */
public abstract class JacksonRenderFactoryBase implements JsonRenderFactory {

    public abstract ObjectMapper config();

    protected SimpleModule module;

    protected void registerModule() {
        if (module != null) {
            config().registerModule(module);
        }
    }


    public <T> void addEncoder(Class<T> clz, JsonSerializer<T> encoder) {
        if (module == null) {
            module = new SimpleModule();
        }

        module.addSerializer(clz, encoder);
    }

    @Override
    public <T> void addConvertor(Class<T> clz, Converter<T, Object> converter) {
        if (clz == Date.class) {
            addEncoder(Date.class, new DateSerializer() {
                public void serialize(Date date, JsonGenerator out, SerializerProvider sp) throws IOException {
                    if (this._customFormat == null) {
                        writeDefaultValue(converter, clz.cast(date), out);
                    } else {
                        super.serialize(date, out, sp);
                    }

                }
            });
        } else {
            addEncoder(clz, new JsonSerializer<T>() {
                @Override
                public void serialize(T source, JsonGenerator out, SerializerProvider sp) throws IOException {
                    writeDefaultValue(converter, source, out);
                }
            });
        }
    }

    private static <T> void writeDefaultValue(Converter<T, Object> converter, T source, JsonGenerator out) throws IOException {
        Object val = converter.convert(source);

        if (val == null) {
            out.writeNull();
        } else if (val instanceof String) {
            out.writeString((String) val);
        } else if (val instanceof Number) {
            if (val instanceof Integer || val instanceof Long) {
                out.writeNumber(((Number) val).longValue());
            } else {
                out.writeNumber(((Number) val).doubleValue());
            }
        } else {
            throw new IllegalArgumentException("The result type of the converter is not supported: " + val.getClass().getName());
        }
    }
}
