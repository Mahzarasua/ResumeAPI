package com.izars.resumeapi.auth.utils;


import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.converter.BidirectionalConverter;
import ma.glasnost.orika.metadata.Type;

import java.util.UUID;

public class StringToUUIDOrikaConverter extends BidirectionalConverter<String, UUID> {

    @Override
    public UUID convertTo(String s, Type<UUID> type, MappingContext mappingContext) {
        return UUID.fromString(s);
    }

    @Override
    public String convertFrom(UUID uuid, Type<String> type, MappingContext mappingContext) {
        return uuid.toString();
    }
}
