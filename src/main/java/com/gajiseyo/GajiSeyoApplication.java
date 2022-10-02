package com.gajiseyo;

import com.gajiseyo.modules.member.repository.MemberRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class GajiSeyoApplication {

    public static void main(String[] args) {
        SpringApplication.run(GajiSeyoApplication.class, args);
    }

    @Bean
    @Profile("local")
    public TestDataInit testDataInit(MemberRepository memberRepository) {
        return new TestDataInit(memberRepository);
    }

}
