package com.nts.iot.modules.miniApp.rest;

import com.nts.iot.modules.system.model.ShufflingFigure;
import com.nts.iot.modules.system.service.ShufflingFigureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 轮播图查询
 */
@RestController
@RequestMapping("ma")
public class MaShufflingFigureController {
    @Autowired
    private ShufflingFigureService shufflingFigureService;

    /**
     * 根据辖区id查询轮播图
     *
     * @param jurisdiction
     * @return
     */
    @GetMapping(value = "/shufflingFigure/getUrl")
    public List<ShufflingFigure> findShufflingFigure(String jurisdiction) {
        return shufflingFigureService.findShufflingFigure(jurisdiction);
    }

    /**
     *  获得详细内容
     * @param id
     * @return
     */
    @GetMapping(value = "/shufflingFigure/getContent")
    public ShufflingFigure getContent(String id) {
        return shufflingFigureService.getContent(id);
    }
}
