package io.bayrktlihn.userservice.service;

import java.nio.charset.StandardCharsets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.util.StreamUtils;

//@Service
public class DataSetupService implements CommandLineRunner {

  @Value("classpath:h2/init.sql")
  private Resource initSql;

  private final R2dbcEntityTemplate entityTemplate;

  @Autowired
  public DataSetupService(final R2dbcEntityTemplate entityTemplate) {
    this.entityTemplate = entityTemplate;
  }

  @Override
  public void run(final String... args) throws Exception {
    final String query = StreamUtils.copyToString(initSql.getInputStream(), StandardCharsets.UTF_8);
    System.out.println(query);
    entityTemplate.getDatabaseClient()
        .sql(query)
        .then()
        .subscribe();
  }
}
