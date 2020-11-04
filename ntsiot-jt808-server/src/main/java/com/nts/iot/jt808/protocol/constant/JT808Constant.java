/*******************************************************************************
 * @(#)JT808Constant.java 2019-04-28
 *
 * Copyright 2019 Liaoning RNS Technology Co., Ltd.All rights reserved.
 * RNS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.nts.iot.jt808.protocol.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *     常量
 * </p>
 *
 * @author <a href="mailto:gaop@rnstec.com">Gaven</a>
 * @version ntsiot 1.0 $ 2019-04-28 16:22
 */
public class JT808Constant {

    private static Map<String, String> descMap = new HashMap<>();

    private static Map<Integer, String> errorMsgMap = new HashMap<>();

    private static Map<String, String> paramMap = new HashMap<>();

    private static Map<Integer, String> fieldTypeMap = new HashMap<>();

    private static boolean new808Protocol;

    public static String getDesc(String id) {
        if (descMap.isEmpty()) {
            init();
        }
        return "" + descMap.get(id);
    }

    public static String getParamType(int field) {
        if (fieldTypeMap.isEmpty()) {
            init();
        }
        return "" + fieldTypeMap.get(field);
    }

    public static String getParamDescr(String id) {
        if (paramMap.isEmpty()) {
            setParamMap();
        }
        return "" + paramMap.get(id);
    }

    public static String descrSocketError(int ErrorCode) {
        if (errorMsgMap.isEmpty()) {
            setErrorMsg();
        }
        return "" + errorMsgMap.get(ErrorCode);
    }

    private static void setErrorMsg() {
        errorMsgMap.put(995, "连接已关闭");
        errorMsgMap.put(10004, "操作被取消");
        errorMsgMap.put(10013, "请求的地址是一个广播地址，但不设置标志");
        errorMsgMap.put(10014, "无效的参数");
        errorMsgMap.put(10022, "套接字没有绑定，无效的地址，或听不调用之前接受");
        errorMsgMap.put(10024, "没有更多的文件描述符，接受队列是空的");
        errorMsgMap.put(10035, "套接字是非阻塞的，指定的操作将阻止");
        errorMsgMap.put(10036, "一个阻塞的Winsock操作正在进行中");
        errorMsgMap.put(10037, "操作完成，没有阻塞操作正在进行中");
        errorMsgMap.put(10038, "描述符不是一个套接字");
        errorMsgMap.put(10039, "目标地址是必需的");
        errorMsgMap.put(10040, "数据报太大，无法进入缓冲区，将被截断");
        errorMsgMap.put(10041, "指定的端口是为这个套接字错误类型");
        errorMsgMap.put(10042, "股权不明，或不支持的");
        errorMsgMap.put(10043, "指定的端口是不支持");
        errorMsgMap.put(10044, "套接字类型不支持在此地址族");
        errorMsgMap.put(10045, " Socket是不是一个类型，它支持面向连接的服务");
        errorMsgMap.put(10047, "地址族不支持");
        errorMsgMap.put(10048, "地址在使用中");
        errorMsgMap.put(10049, "地址是不是可以从本地机器");
        errorMsgMap.put(10050, "网络子系统失败");
        errorMsgMap.put(10051, "网络可以从这个主机在这个时候不能达到");
        errorMsgMap.put(10052, "连接超时设置SO_KEEPALIVE时");
        errorMsgMap.put(10053, "连接被中止，由于超时或其他故障");
        errorMsgMap.put(10054, "连接被重置连接被远程端重置远程端");
        errorMsgMap.put(10055, "无缓冲区可用空间");
        errorMsgMap.put(10056, "套接字已连接");
        errorMsgMap.put(10057, "套接字未连接");
        errorMsgMap.put(10058, "套接字已关闭");
        errorMsgMap.put(10060, "尝试连接超时");
        errorMsgMap.put(10061, "连接被强制拒绝");
        errorMsgMap.put(10101, "监听服务已关闭");
        errorMsgMap.put(10201, "套接字已创建此对象");
        errorMsgMap.put(10202, "套接字尚未创建此对象");
        errorMsgMap.put(11001, "权威的答案：找不到主机");
        errorMsgMap.put(11002, "非权威的答案：找不到主机");
        errorMsgMap.put(11003, "非可恢复的错误");
        errorMsgMap.put(11004, "有效的名称，没有请求类型的数据记录");
    }

    private static void init() {
        descMap.put("0x0001", "终端通用应答");
        descMap.put("0x0003", "终端注销");
        descMap.put("0x8001", "平台通用应答");
        descMap.put("0x0002", "终端心跳");
        descMap.put("0x0100", "终端注册");
        descMap.put("0x8100", "终端注册应答");
        descMap.put("0x0101", "终端注销");
        descMap.put("0x0102", "终端鉴权");
        descMap.put("0x8103", "设置终端参数");
        descMap.put("0x8104", "查询终端参数");
        descMap.put("0x0104", "查询终端参数应答");
        descMap.put("0x8105", "终端控制");
        descMap.put("0x0200", "位置信息汇报");
        descMap.put("0x8201", "位置信息查询");
        descMap.put("0x0201", "位置信息查询应答");
        descMap.put("0x8202", "临时位置跟踪控制");
        descMap.put("0x8300", "文本信息下发");
        descMap.put("0x8301", "事件设置");
        descMap.put("0x0301", "事件报告");
        descMap.put("0x8302", "提问下发");
        descMap.put("0x0302", "提问应答");
        descMap.put("0x8303", "信息点播菜单设置");
        descMap.put("0x0303", "信息点播/取消");
        descMap.put("0x8802", "存储多媒体数据检索");
        descMap.put("0x0802", "存储多媒体数据检索应答");
        descMap.put("0x8803", "存储多媒体数据上传");
        descMap.put("0x8804", "录音开始命令");
        descMap.put("0x8900", "数据下行透传");
        descMap.put("0x0900", "数据上行透传");
        descMap.put("0x8304", "信息服务");
        descMap.put("0x8400", "电话回拨");
        descMap.put("0x8401", "设置电话本");
        descMap.put("0x8500", "车辆控制");
        descMap.put("0x0500", "车辆控制应答");
        descMap.put("0x8600", "设置圆形区域");
        descMap.put("0x8601", "删除圆形区域");
        descMap.put("0x8602", "设置矩形区域");
        descMap.put("0x8603", "删除矩形区域");
        descMap.put("0x8604", "设置多边形区域");
        descMap.put("0x8605", "删除多边形区域");
        descMap.put("0x8606", "设置路线");
        descMap.put("0x8607", "删除路线");
        descMap.put("0x8700", "行车记录仪数据采集命令");
        descMap.put("0x0700", "行车记录仪数据上报");
        descMap.put("0x8701", "行车记录仪参数下达命令");
        descMap.put("0x0701", "电子运单上报");
        descMap.put("0x0702", "驾驶员身份信息采集上报");
        descMap.put("0x0800", "多媒体事件信息上报");
        descMap.put("0x0801", "多媒体数据上传");
        descMap.put("0x8800", "多媒体数据上传应答");
        descMap.put("0x8801", "摄像头立即拍摄命令");
        descMap.put("0x0901", "数据压缩上报");
        descMap.put("0x8A00", "平台RSA公钥");
        descMap.put("0x0A00", "终端RSA公钥");

        fieldTypeMap.put(0x0001, "DWORD");
        fieldTypeMap.put(0x0002, "DWORD");
        fieldTypeMap.put(0x0003, "DWORD");
        fieldTypeMap.put(0x0004, "DWORD");
        fieldTypeMap.put(0x0005, "DWORD");
        fieldTypeMap.put(0x0006, "DWORD");
        fieldTypeMap.put(0x0007, "DWORD");
        fieldTypeMap.put(0x0010, "STRING");
        fieldTypeMap.put(0x0011, "STRING");
        fieldTypeMap.put(0x0012, "STRING");
        fieldTypeMap.put(0x0013, "STRING");
        fieldTypeMap.put(0x0014, "STRING");
        fieldTypeMap.put(0x0015, "STRING");
        fieldTypeMap.put(0x0016, "STRING");
        fieldTypeMap.put(0x0017, "STRING");
        fieldTypeMap.put(0x0018, "DWORD");
        fieldTypeMap.put(0x0019, "DWORD");
        fieldTypeMap.put(0x0020, "DWORD");
        fieldTypeMap.put(0x0021, "DWORD");
        fieldTypeMap.put(0x0022, "DWORD");
        fieldTypeMap.put(0x0027, "DWORD");
        fieldTypeMap.put(0x0028, "DWORD");
        fieldTypeMap.put(0x0029, "DWORD");
        fieldTypeMap.put(0x002C, "DWORD");
        fieldTypeMap.put(0x002D, "DWORD");
        fieldTypeMap.put(0x002E, "DWORD");
        fieldTypeMap.put(0x002F, "DWORD");
        fieldTypeMap.put(0x0030, "DWORD");
        fieldTypeMap.put(0x00031, "WORD");
        fieldTypeMap.put(0x0040, "STRING");
        fieldTypeMap.put(0x0041, "STRING");
        fieldTypeMap.put(0x0042, "STRING");
        fieldTypeMap.put(0x0043, "STRING");
        fieldTypeMap.put(0x0044, "STRING");
        fieldTypeMap.put(0x0045, "DWORD");
        fieldTypeMap.put(0x0046, "DWORD");
        fieldTypeMap.put(0x0047, "DWORD");
        fieldTypeMap.put(0x0048, "STRING");
        fieldTypeMap.put(0x0049, "STRING");
        fieldTypeMap.put(0x0050, "DWORD");
        fieldTypeMap.put(0x0051, "DWORD");
        fieldTypeMap.put(0x0052, "DWORD");
        fieldTypeMap.put(0x0053, "DWORD");
        fieldTypeMap.put(0x0054, "DWORD");
        fieldTypeMap.put(0x0055, "DWORD");
        fieldTypeMap.put(0x0056, "DWORD");
        fieldTypeMap.put(0x0057, "DWORD");
        fieldTypeMap.put(0x0058, "DWORD");
        fieldTypeMap.put(0x0059, "DWORD");
        fieldTypeMap.put(0x005A, "DWORD");
        fieldTypeMap.put(0x0070, "DWORD");
        fieldTypeMap.put(0x0071, "DWORD");
        fieldTypeMap.put(0x0072, "DWORD");
        fieldTypeMap.put(0x0073, "DWORD");
        fieldTypeMap.put(0x0074, "DWORD");
        fieldTypeMap.put(0x0080, "DWORD");
        fieldTypeMap.put(0x0081, "WORD");
        fieldTypeMap.put(0x0082, "WORD");
        fieldTypeMap.put(0x0083, "STRING");
        fieldTypeMap.put(0x0084, "BYTE");
        fieldTypeMap.put(0x0094, "BYTE");
        fieldTypeMap.put(0x0095, "DWORD");
        fieldTypeMap.put(0x0100, "DWORD");
        fieldTypeMap.put(0x0101, "DWORD");
        fieldTypeMap.put(0x0102, "DWORD");
        fieldTypeMap.put(0x0103, "BYTE");
        fieldTypeMap.put(0x0104, "BYTE");
        fieldTypeMap.put(0x0105, "STRING");
        fieldTypeMap.put(0x0106, "DWORD");
        fieldTypeMap.put(0x0107, "DWORD");
        fieldTypeMap.put(0x0108, "DWORD");
        fieldTypeMap.put(0x0109, "DWORD");
        fieldTypeMap.put(0x010a, "DWORD");
        fieldTypeMap.put(0x010b, "DWORD");
        fieldTypeMap.put(0x010c, "BYTE");
        fieldTypeMap.put(0x010d, "WORD");
        fieldTypeMap.put(0x010e, "WORD");
        fieldTypeMap.put(0x010f, "WORD");
    }

    private static void setParamMap() {
        paramMap.put("0x0001", "终端心跳发送间隔(s)");
        paramMap.put("0x0002", "TCP应答超时时间(s)");
        paramMap.put("0x0003", "TCP消息重传次数");
        paramMap.put("0x0004", "UDP应答超时时间(s)");
        paramMap.put("0x0013", "主服务器IP地址");
        paramMap.put("0x0017", "备份服务器IP地址");
        paramMap.put("0x0018", "服务器TCP端口");
        paramMap.put("0x0019", "服务器UDP端口");
        paramMap.put("0x0055", "最高车速,单位km/h");
        paramMap.put("0x0056", "超速持续时间(s)");
        paramMap.put("0x0080", "里程表读数");
        paramMap.put("0x0081", "所在省域ID");
        paramMap.put("0x0082", "所在市域ID");
        paramMap.put("0x0083", "机动车号牌");
        paramMap.put("0x0084", "车辆颜色");
    }

    public static boolean isNew808Protocol() {
        new808Protocol = true;
        return new808Protocol;
    }

    public static void setNew808Protocol(boolean new808Protocol) {
        JT808Constant.new808Protocol = new808Protocol;
    }
}
