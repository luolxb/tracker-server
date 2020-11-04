/*******************************************************************************
 * @(#)SessionManager.java 2019-05-01
 *
 * Copyright 2019 Liaoning RNS Technology Co., Ltd.All rights reserved.
 * RNS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.nts.iot.session;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

/**
 * <p>
 * </p>
 *
 * @author <a href="mailto:gaop@rnstec.com">Gaven</a>
 * @version ntsiot 1.0 $ 2019-05-01 11:03
 */
public enum  SessionManager {

    INSTANCE;

    /**
     * netty生成的sessionID和Session的对应关系
     */
    private Map<String, Session> sessionIdMap;

    /**
     * 终端号和netty生成的sessionID的对应关系
     */
    private Map<String, String> terminalMap;

    SessionManager() {
        this.sessionIdMap = new ConcurrentHashMap<>();
        this.terminalMap = new ConcurrentHashMap<>();
    }


    public boolean containsKey(String sessionId) {
        return sessionIdMap.containsKey(sessionId);
    }

    public boolean containsSession(Session session) {
        return sessionIdMap.containsValue(session);
    }

    public Session getBySessionId(String sessionId) {
        return sessionIdMap.get(sessionId);
    }

    public Session getByMobileNumber(String mobileNumber) {
        String sessionId = this.terminalMap.get(mobileNumber.toLowerCase());
        if (sessionId == null) {
            return null;
        }
        return this.getBySessionId(sessionId);
    }

    public synchronized Session put(String key, Session value) {
        if (value.getTerminalId() != null && !"".equals(value.getTerminalId().trim())) {
            this.terminalMap.put(value.getTerminalId(), value.getId());
        }
        return sessionIdMap.put(key, value);
    }

    public synchronized Session removeBySessionId(String sessionId) {
        if (sessionId == null) {
            return null;
        }
        Session session = sessionIdMap.remove(sessionId);
        if (session == null) {
            return null;
        }
        if (session.getTerminalId() != null) {
            this.terminalMap.remove(session.getTerminalId());
        }
        return session;
    }

    public Set<String> keySet() {
        return sessionIdMap.keySet();
    }

    public void forEach(BiConsumer<? super String, ? super Session> action) {
        sessionIdMap.forEach(action);
    }

    public Set<Map.Entry<String, Session>> entrySet() {
        return sessionIdMap.entrySet();
    }

    public List<Session> toList() {
        return this.sessionIdMap.entrySet().stream().map(e -> e.getValue()).collect(Collectors.toList());
    }

}
