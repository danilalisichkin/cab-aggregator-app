package com.cabaggregator.rideservice.entity.conveter;

import com.cabaggregator.rideservice.entity.enums.PaymentMethod;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public final class PaymentMethodConverter {
    public static class Serializer extends JsonSerializer<PaymentMethod> {
        @Override
        public void serialize(PaymentMethod paymentMethod, JsonGenerator jsonGenerator, SerializerProvider serializers)
                throws IOException {
            jsonGenerator.writeNumber(paymentMethod.getId());
        }
    }

    public static class Deserializer extends JsonDeserializer<PaymentMethod> {
        @Override
        public PaymentMethod deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
                throws IOException {
            int id = jsonParser.getIntValue();
            return PaymentMethod.getById(id);
        }
    }
}
