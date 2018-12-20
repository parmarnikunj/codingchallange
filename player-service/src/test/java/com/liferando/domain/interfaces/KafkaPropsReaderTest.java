package com.liferando.domain.interfaces;

import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

public class KafkaPropsReaderTest {

    @Test
    public void check_that_sys_prop_reading_works() {
        String systemPropName = KafkaPropsReader.toSystemPropName("bootstrap.servers");

        assertThat(systemPropName).isNotNull();
        assertThat(systemPropName).isEqualTo("BOOTSTRAP_SERVERS");

        systemPropName = KafkaPropsReader.toSystemPropName("client.id");
        assertThat(systemPropName).isEqualTo("CLIENT_ID");
    }
}