package project.instagram.common.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Configuration
public class QuerydslConfig {
    @PersistenceContext
    private EntityManager em;
    @Bean
    public JPAQueryFactory queryFactory(){
        return new JPAQueryFactory(em);
    }
}
