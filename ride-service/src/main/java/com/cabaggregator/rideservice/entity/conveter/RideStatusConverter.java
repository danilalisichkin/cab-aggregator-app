package com.cabaggregator.rideservice.entity.conveter;

import com.cabaggregator.rideservice.entity.enums.RideStatus;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public final class RideStatusConverter {
    public static class Serializer extends JsonSerializer<RideStatus> {
        @Override
        public void serialize(RideStatus rideStatus, JsonGenerator jsonGenerator, SerializerProvider serializers)
                throws IOException {
            jsonGenerator.writeNumber(rideStatus.getId());
        }
    }

    public static class Deserializer extends JsonDeserializer<RideStatus> {
        @Override
        public RideStatus deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
                throws IOException {
            int id = jsonParser.getIntValue();
            return RideStatus.getById(id);
        }
    }
}
