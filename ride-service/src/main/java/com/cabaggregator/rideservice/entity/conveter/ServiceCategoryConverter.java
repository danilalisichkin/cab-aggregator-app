package com.cabaggregator.rideservice.entity.conveter;

import com.cabaggregator.rideservice.entity.enums.ServiceCategory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public final class ServiceCategoryConverter {
    public static class Serializer extends JsonSerializer<ServiceCategory> {
        @Override
        public void serialize(ServiceCategory serviceCategory, JsonGenerator jsonGenerator, SerializerProvider serializers)
                throws IOException {
            jsonGenerator.writeNumber(serviceCategory.getId());
        }
    }

    public static class Deserializer extends JsonDeserializer<ServiceCategory> {
        @Override
        public ServiceCategory deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
                throws IOException {
            int id = jsonParser.getIntValue();
            return ServiceCategory.getById(id);
        }
    }
}
