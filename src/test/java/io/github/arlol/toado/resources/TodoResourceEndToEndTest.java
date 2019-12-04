package io.github.arlol.toado.resources;

import static org.assertj.core.api.Assertions.assertThat;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.Response;

import org.junit.ClassRule;
import org.junit.Test;

import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.client.JerseyClientConfiguration;
import io.dropwizard.testing.junit.DropwizardAppRule;
import io.dropwizard.util.Duration;
import io.github.arlol.toado.ToadoApplication;
import io.github.arlol.toado.ToadoConfiguration;

public class TodoResourceEndToEndTest {

    @ClassRule
    public static final DropwizardAppRule<ToadoConfiguration> RULE = new DropwizardAppRule<ToadoConfiguration>(
            ToadoApplication.class);

    @Test
    public void findByIdNonExisting() {
        JerseyClientConfiguration configuration = new JerseyClientConfiguration();
        configuration.setTimeout(Duration.seconds(5));
        configuration.setConnectionTimeout(Duration.seconds(5));
        configuration.setConnectionRequestTimeout(Duration.seconds(5));
        Client client = new JerseyClientBuilder(RULE.getEnvironment()).using(configuration).build("test client");

        Response response = client.target(String.format("http://localhost:%d/todos/1", RULE.getLocalPort())).request()
                .get();

        assertThat(response.getStatus()).isEqualTo(404);
    }

}
