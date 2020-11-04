package com.nts.iot.modules.miniApp.quartztask;

import com.nts.iot.modules.miniApp.model.AppointmentManager;
import com.nts.iot.modules.miniApp.service.AppointmentService;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @Author: hamsun
 * @Description:
 * @Date: 2019/10/30 20:09
 */
@Service
public class SMSService {
    @Autowired
    private AppointmentService appointmentService;

    public void sendSMS(String phone, String content) {
        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;

        HashMap<String, String> param = new HashMap<>();
        param.put("userId", "JS3755");
        param.put("password", "159369");
        param.put("pszMobis", phone);
        param.put("pszMsg", content);
        param.put("pszSubPort", "******");
        SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMddHHmmss");
        param.put("Attime", sdf.format(new Date()));
        try {
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost("http://61.145.229.26:8086/MWGate/wmgw.asmx/MongateCsSpSendSmsNew");
            // 创建参数列表
            if (param != null) {
                List<NameValuePair> paramList = new ArrayList<>();
                for (String key : param.keySet()) {
                    paramList.add(new BasicNameValuePair(key, param.get(key)));
                }
                // 模拟表单
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList, "utf-8");
                httpPost.setEntity(entity);
            }
            // 执行http请求
            response = httpClient.execute(httpPost);
            EntityUtils.toString(response.getEntity(), "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public void sendSMSBatch(List<AppointmentManager> appointmentManagers){
        for (AppointmentManager appointmentManager:appointmentManagers){
            HashMap<String, Long> stringIntegerHashMap = appointmentService.countStatusByjurisdictions(appointmentManager.getJurisdiction());
            Long accepted = stringIntegerHashMap.get("accepted");
            Long not_accepted = stringIntegerHashMap.get("not_accepted");
            Long all = accepted+not_accepted;
            String content = "您管理的辖区，截至目前，预约登记已受理人数"+accepted+",未受理人数"+
                    not_accepted +",总预约登记人数"+all;
            sendSMS(appointmentManager.getPhone(),content);

        }
    }
}