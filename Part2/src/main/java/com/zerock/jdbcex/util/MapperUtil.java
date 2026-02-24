package com.zerock.jdbcex.util;

import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.MatchingStrategies;

public enum MapperUtil {
    INSTANCE;

    private ModelMapper modelMapper;

    MapperUtil() {
        this.modelMapper = new ModelMapper();
        this.modelMapper.getConfiguration()
                .setFieldMatchingEnabled(true) // Getter / Setter에 의존하지 말고 변수 이름 자체를 보고 짝을 맞춰라
                .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE) // private으로 숨겨둔 변수에도 직접 접근해서 넣어라
                .setMatchingStrategy(MatchingStrategies.STRICT); // 이름이 토씨 하나 안 틀리고 100% 완벽하게 똑같을 때만 데이터를 복사
    }

    public ModelMapper get() {
        return modelMapper;
    }
}
