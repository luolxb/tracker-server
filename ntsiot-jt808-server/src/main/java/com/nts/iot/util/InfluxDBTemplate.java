package com.nts.iot.util;

import com.nts.iot.config.InfluxConfig;
import lombok.extern.slf4j.Slf4j;
import org.influxdb.InfluxDB;
import org.influxdb.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

//@Slf4j
//@Configuration
public class InfluxDBTemplate {
    @Autowired
    private InfluxDB influxDB;

    @Autowired
    private InfluxConfig influxdbConfig;

//    @Autowired
//    private EquipmentMoveRunningTimeS equipmentRunningTimeS;


    /**
     * @Title: createDB
     * @Description: 创建数据库
     * @return: void
     */
    @SuppressWarnings("deprecation")
    public void createDB( String dbName ) {
        influxDB.createDatabase( dbName );
    }

    /**
     * @Title: deleteDB
     * @Description: 删除数据库
     * @return: void
     */
    @SuppressWarnings("deprecation")
    public void deleteDB( String dbName ) {
        influxDB.deleteDatabase( dbName );
    }

    /**
     * @Title: ping
     * @Description:  测试是否连接正常
     * @return: boolean
     */
    public boolean ping() {
        boolean isConnected = false;
        Pong pong;
        try {
            pong = influxDB.ping();
            if (pong != null) {
                isConnected = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isConnected;
    }

    /**
     * @Title: createRetentionPolicy
     * @Description: 创建自定义保留策略创建自定义保留策略
     * @return: void
     */
    public void createRetentionPolicy(String policyName, String duration, int replication, Boolean isDefault) {
        String sql = String.format("CREATE RETENTION POLICY \"%s\" ON \"%s\" DURATION %s REPLICATION %s ", policyName,
                influxdbConfig.getINFLUXDB_DB(), duration, replication);
        if (isDefault) {
            sql = sql + " DEFAULT";
        }
        this.query(sql);
    }

    /**
     * @Title: createDefaultRetentionPolicy
     * @Description:   创建默认的保留策略   策略名：default，保存天数：30天，保存副本数量：1
     * @return: void
     */
    public void createDefaultRetentionPolicy() {
        String command = String.format("CREATE RETENTION POLICY \"%s\" ON \"%s\" DURATION %s REPLICATION %s DEFAULT",
                "default", influxdbConfig.getINFLUXDB_DB(), "30d", 1);
        this.query(command);
    }

    /**
     * @Title: query
     * @Description:查询
     * @return: QueryResult
     */
    public QueryResult query(String command ) {
        return influxDB.query(new Query(command, influxdbConfig.getINFLUXDB_DB()));
    }

    /**
     * @Title: insert
     * @Description:   插入数据
     * @return: void
     */
    public void insert(String measurement, Map<String, String> tags, Map<String, Object> fields, long time,
                       TimeUnit timeUnit) {
        Point.Builder builder = Point.measurement(measurement);
        builder.tag(tags);
        builder.fields(fields);
        if (0 != time) {
            builder.time(time, timeUnit);
        }
        influxDB.write( influxdbConfig.getINFLUXDB_DB(), influxdbConfig.getINFLUXDB_DB() , builder.build());
    }
    /**
     * @Title: insertPoint
     * @Description:   插入数据
     * @return: void
     */
    public void insertPoint(Point point) {
        influxDB.write(point);
    }

    /**
     * @Title: batchInsert
     * @Description:   批量写入测点
     * @return: void
     */
    public void batchInsert( BatchPoints batchPoints ) {
        influxDB.write(batchPoints);
    }

    /**
     * @Title: batchInsert
     * @Description:   批量写入数据    要保存的数据（调用BatchPoints.lineProtocol()可得到一条record）
     * @return: void
     */
    public void batchInsert(final String database, final String retentionPolicy, final InfluxDB.ConsistencyLevel consistency,
                            final List<String> records) {
        influxDB.write(database, retentionPolicy, consistency, records);
    }


    /**
     * @Title: deleteMeasurementData
     * @Description:  删除表数据
     * @return: String
     */
    public String deleteMeasurementData(String command) {
        QueryResult result = influxDB.query(new Query(command, influxdbConfig.getINFLUXDB_DB()));
        return result.getError();
    }

    /**
     * @Title: close
     * @Description:  关闭数据库
     * @return: void
     */
    public void close() {
        influxDB.close();
    }

    /**
     * @Title: pointBuilder
     * @Description:  构建Point
     * @return: Point
     */
    public Point pointBuilder(String measurement, Map<String, String> tags, Map<String, Object> fields) {
        Point point = Point.measurement(measurement).tag(tags).fields(fields).build();
        return point;
    }

    /**
     * @Title: pointBuilder
     * @Description:  构建Point
     * @return: Point
     */
    public Point pointBuilderInTime(String measurement, long time, Map<String, String> tags, Map<String, Object> fields) {
        Point point = Point.measurement(measurement).time(time, TimeUnit.MILLISECONDS).tag(tags).fields(fields).build();
        return point;
    }

}