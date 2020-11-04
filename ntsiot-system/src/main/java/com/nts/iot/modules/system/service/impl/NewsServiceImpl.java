package com.nts.iot.modules.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nts.iot.modules.miniApp.model.Picture;
import com.nts.iot.modules.miniApp.service.PictureService;
import com.nts.iot.modules.system.dao.NewsMapper;
import com.nts.iot.modules.system.model.News;
import com.nts.iot.modules.system.service.NewsService;
import com.nts.iot.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author: zhc@rnstec.com
 * @Date: 2019/6/12 09:33
 * @Description:
 */
@Service
public class NewsServiceImpl extends ServiceImpl<NewsMapper, News> implements NewsService {

    @Autowired
    private PictureService pictureService;

    @Override
    public Map queryAll(String title, List<String> jurisdictions, Pageable pageable) {
        Page<News> page = new Page(pageable.getPageNumber() + 1, pageable.getPageSize());
        IPage<News> pageResult = baseMapper.selectByPage(page, title, jurisdictions);
        return PageUtil.toPage(pageResult);
    }

    @Override
    public void saveNew(News news) {
        int result = baseMapper.insert(news);
        if (result > 0) {
            Picture picture = new Picture();
            //pk
            picture.setPk(news.getId());
            picture.setPath(news.getUrlLink());
            picture.setType("NEWS_IMG");
            pictureService.saveFile(picture);
        }
    }

    @Override
    public void deleteById(Long id) {
        int result = baseMapper.deleteById(id);
        if (result > 0) {
            this.deletePicture(id);
        }
    }

    @Override
    public News getByNewId(Long id) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        News news = baseMapper.getByNewId(id);
        if (news != null) {
            news.setTime(df.format(new Date(news.getCreateTime())));
        }
        return news;
    }

    @Override
    public void updateNewsById(News news) {
        // 修改图片地址
        Picture picture = this.getPicByPkId(news.getId());
        if (picture != null) {
            picture.setPath(news.getUrlLink());
            pictureService.updateById(picture);
        }
        baseMapper.updateById(news);

    }

    /**
     * 删除关联图片
     *
     * @param newId
     */
    void deletePicture(Long newId) {
        QueryWrapper<Picture> wrapper = new QueryWrapper<>();
        wrapper.eq("pk", newId);
        wrapper.eq("type", "NEWS_IMG");
        pictureService.remove(wrapper);
    }

    @Override
    public List<News> getNewsByDeptId(Long deptId) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        List<News> newsList = baseMapper.getNewsByDeptId(deptId);
        for (News news : newsList) {
            news.setTime(df.format(new Date(news.getCreateTime())));
        }
        return newsList;
    }

    /**
     * 根据pkid查找
     *
     * @param pkId
     * @return
     */
    private Picture getPicByPkId(Long pkId) {
        Picture pic = new Picture();
        QueryWrapper<Picture> wrapper = new QueryWrapper<>();
        wrapper.eq("pk", pkId);
        List<Picture> list = pictureService.list(wrapper);
        if (list != null && list.size() > 0) {
            pic = list.get(0);
        }
        return pic;
    }
}
