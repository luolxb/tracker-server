package com.nts.iot.modules.system.dto;

import lombok.Data;
import java.io.Serializable;

/**
* @author jie
* @date 2019-04-10
*/
@Data
public class DictDetailDTO implements Serializable {

    private Long id;


    private Long dictId;

    /**
     * 字典标签
     */
    private String label;

    /**
     * 字典值
     */
    private String value;

    /**
     * 排序
     */
    private String sort;

    /**
     * 字典id
     */
    private String dictName;
}