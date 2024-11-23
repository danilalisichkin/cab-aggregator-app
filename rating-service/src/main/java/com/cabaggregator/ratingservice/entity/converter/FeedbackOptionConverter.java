package com.cabaggregator.ratingservice.entity.converter;

import com.cabaggregator.ratingservice.entity.enums.FeedbackOption;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.IOException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class FeedbackOptionConverter {
    public static class Serializer extends JsonSerializer<FeedbackOption> {
        @Override
        public void serialize(FeedbackOption feedbackOption, JsonGenerator jsonGenerator, SerializerProvider serializers)
                throws IOException {
            jsonGenerator.writeNumber(feedbackOption.ordinal());
        }
    }

    public static class Deserializer extends JsonDeserializer<FeedbackOption> {
        @Override
        public FeedbackOption deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
                throws IOException {
            int id = jsonParser.getIntValue();
            return FeedbackOption.values()[id];
        }
    }
}
