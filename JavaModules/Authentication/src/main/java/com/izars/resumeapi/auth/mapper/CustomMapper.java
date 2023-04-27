package com.izars.resumeapi.auth.mapper;

import com.izars.resumeapi.auth.config.MyUserDetails;
import com.izars.resumeapi.auth.domain.user.UserResponse;
import com.izars.resumeapi.auth.model.User;
import com.izars.resumeapi.auth.utils.StringToUUIDOrikaConverter;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.converter.builtin.PassThroughConverter;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
@Primary
public class CustomMapper extends ConfigurableMapper {
    @Override
    protected void configure(MapperFactory factory) {
        factory.getConverterFactory().registerConverter(new PassThroughConverter(LocalDate.class));
        factory.getConverterFactory().registerConverter(new PassThroughConverter(LocalDateTime.class));
        factory.getConverterFactory().registerConverter(new StringToUUIDOrikaConverter());

        factory.classMap(User.class, UserResponse.class)
                .byDefault().mapNulls(false).register();
        factory.classMap(User.class, MyUserDetails.class)
                .byDefault().mapNulls(false).register();
    }
}
