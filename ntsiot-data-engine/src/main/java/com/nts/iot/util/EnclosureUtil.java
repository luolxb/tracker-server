/*******************************************************************************
 * @(#)EnclosureUtil.java 2019-05-16
 *
 * Copyright 2019 Liaoning RNS Technology Co., Ltd.All rights reserved.
 * RNS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.nts.iot.util;

import com.nts.iot.dto.CollectMessage;
import com.nts.iot.dto.Fence;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * </p>
 *
 * @author <a href="mailto:gaop@rnstec.com">Gaven</a>
 * @version cyoubike 1.0 $ 2019-05-16 16:58
 */
public class EnclosureUtil {

    /**
     * 地球半径
     */
    private static double EARTH_RADIUS = 6378138.0;

    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

    /**
     * 计算是否在圆上（单位/千米）
     *
     * @param radius 圆半径
     * @param lng1   圆经度
     * @param lat1   圆纬度
     * @param lng2   点经度
     * @param lat2   点纬度
     * @return
     */
    public static boolean isInCircle(double radius, double lng1, double lat1, double lng2, double lat2) {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lng1) - rad(lng2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000) / 10000;
        // 不在圆上
        if (s > radius) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 是否在矩形区域内
     *
     * @param lat    测试点经度
     * @param lng    测试点纬度
     * @param minLat 纬度范围限制1
     * @param maxLat 纬度范围限制2
     * @param minLng 经度限制范围1
     * @param maxLng 经度范围限制2
     * @return boolean
     * @throws
     * @Title: isInArea
     * @Description:
     */
    public static boolean isInRectangleArea(double lat, double lng, double minLat, double maxLat, double minLng, double maxLng) {
        // 如果在纬度的范围内
        if (isInRange(lat, minLat, maxLat)) {
            if (minLng * maxLng > 0) {
                if (isInRange(lng, minLng, maxLng)) {
                    return true;
                } else {
                    return false;
                }
            } else {
                if (Math.abs(minLng) + Math.abs(maxLng) < 180) {
                    if (isInRange(lng, minLng, maxLng)) {
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    double left = Math.max(minLng, maxLng);
                    double right = Math.min(minLng, maxLng);
                    if (isInRange(lng, left, 180) || isInRange(lng, right, -180)) {
                        return true;
                    } else {
                        return false;
                    }
                }
            }
        } else {
            return false;
        }
    }

    /**
     * 判断是否在经纬度范围内
     *
     * @param point
     * @param left
     * @param right
     * @return boolean
     * @throws
     * @Title: isInRange
     * @Description:
     */
    public static boolean isInRange(double point, double left, double right) {
        if (point >= Math.min(left, right) && point <= Math.max(left, right)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断点是否在多边形内
     *
     * @param point 测试点
     * @param pts   多边形的点
     * @return boolean
     * @throws
     * @Title: IsPointInPoly
     * @Description:
     */
    public static boolean isInPolygon(Point2D.Double point, List<Point2D.Double> pts) {

        int N = pts.size();
        boolean boundOrVertex = true;
        // 交叉点数量
        int intersectCount = 0;
        // 浮点类型计算时候与0比较时候的容差
        double precision = 2e-10;
        // 临近顶点
        Point2D.Double p1, p2;
        // 当前点
        Point2D.Double p = point;

        p1 = pts.get(0);
        for (int i = 1; i <= N; ++i) {
            if (p.equals(p1)) {
                return boundOrVertex;
            }

            p2 = pts.get(i % N);
            if (p.x < Math.min(p1.x, p2.x) || p.x > Math.max(p1.x, p2.x)) {
                p1 = p2;
                continue;
            }

            // 射线穿过算法
            if (p.x > Math.min(p1.x, p2.x) && p.x < Math.max(p1.x, p2.x)) {
                if (p.y <= Math.max(p1.y, p2.y)) {
                    if (p1.x == p2.x && p.y >= Math.min(p1.y, p2.y)) {
                        return boundOrVertex;
                    }

                    if (p1.y == p2.y) {
                        if (p1.y == p.y) {
                            return boundOrVertex;
                        } else {
                            ++intersectCount;
                        }
                    } else {
                        double xinters = (p.x - p1.x) * (p2.y - p1.y) / (p2.x - p1.x) + p1.y;
                        if (Math.abs(p.y - xinters) < precision) {
                            return boundOrVertex;
                        }

                        if (p.y < xinters) {
                            ++intersectCount;
                        }
                    }
                }
            } else {
                if (p.x == p2.x && p.y <= p2.y) {
                    Point2D.Double p3 = pts.get((i + 1) % N);
                    if (p.x >= Math.min(p1.x, p3.x) && p.x <= Math.max(p1.x, p3.x)) {
                        ++intersectCount;
                    } else {
                        intersectCount += 2;
                    }
                }
            }
            p1 = p2;
        }
        // 偶数在多边形外
        if (intersectCount % 2 == 0) {
            return false;
        } else { //奇数在多边形内
            return true;
        }
    }

    /**
     * 监控车辆是否在电子围栏内
     */
    public static List<CollectMessage> checkRail(List<Fence> railList, List<CollectMessage> collectMessageList) {
        List<CollectMessage> resultList = new ArrayList<>();
        // 判断围栏坐标List是否为空
        if (railList != null && railList.size() > 0) {
            for (int i = 0; i < railList.size(); i++) {
                // 状态为关闭  fence_status_004
                if (railList.get(i).getStatus().equals("fence_status_004")) {
                    continue;
                }
                // 获得一个围栏的 List<Point2D.Double>
                List<Point2D.Double> pdList = getPDList(railList.get(i).getCoordinate().split(";"));
                // 车辆信息LIst不为空
                if (collectMessageList != null && collectMessageList.size() > 0) {
                    for (int j = 0; j < collectMessageList.size(); j++) {
                        Point2D.Double pd = getPD(collectMessageList.get(j));
                        Boolean flag = isInPolygon(pd, pdList);
                        // 在此围栏中返回true，不在返回false
                        if (!flag) {
                            // fence_status_001  出界
                            if (railList.get(i).getStatus().equals("fence_status_001") ) {
                                collectMessageList.get(j).setAlertType("fence_status_001");
                                resultList.add(collectMessageList.get(j));
                            }
                            break;
                        } else {
                            // fence_status_002  入界
                            if (railList.get(i).getStatus().equals("fence_status_002") ) {
                                collectMessageList.get(j).setAlertType("fence_status_002");
                                resultList.add(collectMessageList.get(j));
                            }
                            break;
                        }
                    }
                }
            }
        }
        return resultList;
    }

    /**
     * 监控车辆是否在电子围栏内
     */
    public static Boolean checkRail(List<String> railList, CollectMessage collectMessage) {
        Boolean flag = false;
        // 判断围栏坐标List是否为空
        if (railList != null && railList.size() > 0) {
            for (int i = 0; i < railList.size(); i++) {
                // 获得一个围栏的 List<Point2D.Double>
                List<Point2D.Double> pdList = getPDList(railList.get(i).split(";"));
                Point2D.Double pd = getPD(collectMessage);
                // 在此围栏中返回true，不在返回false
                flag = isInPolygon(pd, pdList);
            }
        }
        return flag;
    }

    /**
     * 获得List<Point2D.Double>
     *
     * @param dotArray 点数组
     * @return
     */
    private static List<Point2D.Double> getPDList(String[] dotArray) {
        List<Point2D.Double> pdList = new ArrayList<>();
        for (int i = 0; i < dotArray.length; i++) {
            String[] dot = dotArray[i].split(",");
            Point2D.Double pd = new Point2D.Double();
            pd.setLocation(Double.parseDouble(dot[0]), Double.parseDouble(dot[1]));
            pdList.add(pd);
        }
        return pdList;
    }


    /**
     * 获得Point2D.Double
     *
     * @param collectMessage 车辆信息
     * @return
     */
    private static Point2D.Double getPD(CollectMessage collectMessage) {
        Point2D.Double pd = new Point2D.Double();
        pd.setLocation(Double.parseDouble(collectMessage.getLongitude()), Double.parseDouble(collectMessage.getLatitude()));
        return pd;
    }

    /**
     * 校验虚拟装
     *
     * @param coordinateAndRadius 虚拟装 经度，纬度，半径(米)
     * @param collectMessageList  车辆信息list
     * @return
     */
    public static List<CollectMessage> checkVirtualPile(String coordinateAndRadius, List<CollectMessage> collectMessageList) {
        // 返回结果
        List<CollectMessage> resultList = new ArrayList<>();
        // 车辆信息判空
        if (collectMessageList != null && collectMessageList.size() > 0) {
            // 循环车辆信息
            for (int i = 0; i < collectMessageList.size(); i++) {
                String[] coordinateAndRadiusArray = coordinateAndRadius.split(",");
                Boolean flag = isInCircle(
                        Double.parseDouble(coordinateAndRadiusArray[2]) / 1000,
                        Double.parseDouble(coordinateAndRadiusArray[0]),
                        Double.parseDouble(coordinateAndRadiusArray[1]),
                        Double.parseDouble(collectMessageList.get(i).getLongitude()),
                        Double.parseDouble(collectMessageList.get(i).getLatitude())
                );
                // 出圈车辆
                if (!flag) {
                    resultList.add(collectMessageList.get(i));
                }
            }
        }
        return resultList;
    }

    /**
     * 判断是否在圈内
     *
     * @param point               经度，纬度，半径
     * @param collectMessagePoint 经度，纬度
     * @return
     */
    public static Boolean checkPoint(String radius, String point, String collectMessagePoint) {
        String[] pointArray = point.split(",");
        String[] collectMessagePointArray = collectMessagePoint.split(",");
        return isInCircle(Double.parseDouble(radius),
                Double.parseDouble(pointArray[0]),
                Double.parseDouble(pointArray[1]),
                Double.parseDouble(collectMessagePointArray[0]),
                Double.parseDouble(collectMessagePointArray[1])
        );
    }

    public static void main(String[] args) {
        /**
         * 测试画多边形判断坐标
         */
//        List<Point2D.Double> pdList = new ArrayList<>();
//
//        Point2D.Double pd1 = new Point2D.Double();
//        pd1.setLocation(121.357779, 31.217651);
//        pdList.add(pd1);
//        Point2D.Double pd2 = new Point2D.Double();
//        pd2.setLocation(121.357744, 31.21841);
//        pdList.add(pd2);
//        Point2D.Double pd3 = new Point2D.Double();
//        pd3.setLocation(121.358291, 31.217854);
//        pdList.add(pd3);
//
//        Point2D.Double pd = new Point2D.Double();
//        pd.setLocation(21.357779, 31.217651);
//
//        System.out.println(isInPolygon(pd, pdList));

        /**
         * 测试画圆判断坐标
         */
//        System.out.println(isInCircle(500, 121.357779, 31.217651, 121.358779, 31.217651));
    }
}
