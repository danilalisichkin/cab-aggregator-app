package com.cabaggregator.ratingservice.entity.enums;

import com.cabaggregator.ratingservice.entity.converter.FeedbackOptionConverter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(using = FeedbackOptionConverter.Serializer.class)
@JsonDeserialize(using = FeedbackOptionConverter.Deserializer.class)
public enum FeedbackOption {
    CLEAN_SALOON,
    COMFORTABLE_RIDE,
    FRIENDLY_DRIVER,
    PUNCTUALITY,
    SAFE_RIDE,
    SMOOTH_DRIVE,
    GOOD_MUSIC
}
