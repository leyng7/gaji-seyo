package com.gajiseyo;

import com.gajiseyo.modules.item.repository.ItemRepository;
import com.gajiseyo.modules.member.repository.MemberRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@EnableJpaAuditing
@SpringBootApplication
public class GajiSeyoApplication {

    public static void main(String[] args) {
        SpringApplication.run(GajiSeyoApplication.class, args);
    }

    @Bean
    @Profile("local")
    public TestDataInit testDataInit(MemberRepository memberRepository, ItemRepository itemRepository) {
        return new TestDataInit(memberRepository, itemRepository);
    }

}
