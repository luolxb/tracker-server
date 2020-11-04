package com.nts.iot.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * influx 配置
 */
@Slf4j
//@Configuration
@Data
public class InfluxConfig {
    @Value("${influx.host}")
    private String INFLUXDB_HOST;
    @Value("${influx.user}")
    private String INFLUXDB_USER;
    @Value("${influx.password}")
    private String INFLUXDB_PASS;
    @Value("${influx.database}")
    private String INFLUXDB_DB;

    @Bean
    public InfluxDB influxDbBuild() {
        InfluxDB influxDB = InfluxDBFactory.connect(INFLUXDB_HOST, INFLUXDB_USER, INFLUXDB_PASS);
        if(!influxDB.databaseExists(INFLUXDB_DB)){
            influxDB.createDatabase(INFLUXDB_DB);
        }
        influxDB.setDatabase(INFLUXDB_DB);
        //log.info("influxDb 启动完毕");
        return influxDB;
    }
}
