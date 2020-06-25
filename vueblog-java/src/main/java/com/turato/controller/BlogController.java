package com.turato.controller;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.turato.common.lang.Result;
import com.turato.entity.Blog;
import com.turato.service.BlogService;
import com.turato.util.ShiroUtil;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author turato
 * @since 2020-06-1
 */
@RestController
public class BlogController {

    @Autowired
    BlogService blogService;

    @GetMapping("/blogs")
    public Result list(@RequestParam(defaultValue = "1") Integer currentPage) {
        //分页查询
        Page page = new Page(currentPage, 5);
        IPage pageData = blogService.page(page, new QueryWrapper<Blog>().orderByDesc("created"));
        //返回结果
        return Result.succ(pageData);
    }

    @GetMapping("/blog/{id}")
    public Result detail(@PathVariable(name = "id") Long id) {
        Blog blog = blogService.getById(id);
        Assert.notNull(blog, "该博客已被删除");

        return Result.succ(blog);
    }

    @RequiresAuthentication
    @PostMapping("/blog/edit")
    public Result edit(@Validated @RequestBody Blog blog) {
        // @RequiresAuthentication:需要权限才能编辑
        Blog temp = null;
        if(blog.getId() != null) {
            temp = blogService.getById(blog.getId());
            //只能删除自己的文章
            Assert.isTrue(temp.getUserId().longValue() == ShiroUtil.getProfile().getId().longValue(), "没有权限编辑");
        } else {
            temp = new Blog();
            temp.setUserId(ShiroUtil.getProfile().getId());
            temp.setCreated(LocalDateTime.now());
            temp.setStatus(0);
        }

        BeanUtil.copyProperties(blog, temp, "id", "userId", "created", "status");
        blogService.saveOrUpdate(temp);

        return Result.succ(null);
    }

    @RequiresAuthentication
    @GetMapping("/blog/delete/{id}")
    public Result delete(@PathVariable("id") Integer id) {
        Blog temp = null;
        if(id != null) {
            temp = blogService.getById(id);
            // 只能编辑自己的文章
            blogService.removeById(id);
            Assert.isTrue(temp.getUserId().longValue() == ShiroUtil.getProfile().getId().longValue(), "没有权限删除");
        } else {
            return Result.fail("文章不存在");
        }
        return Result.succ(null);
    }

    /**
     * @Author turato
     * @param words
     * @param currentPage
     * @return 封装结果
     * @Date 2020-06-22
     */
    @GetMapping("/blogs/search")
    public Result search(@RequestParam(value = "words") String words, @RequestParam(value = "currentPage",defaultValue ="1" )Integer currentPage){
        Page page = new Page(currentPage, 5);
        QueryWrapper<Blog> blogQueryWrapper = new QueryWrapper<Blog>();
        // 模糊查询 content,title,description
        blogQueryWrapper
                .like("content", words)
                .or()
                .like("title", words)
                .or()
                .like("description", words);
        IPage pageData = blogService.page(page, blogQueryWrapper);
        return Result.succ(pageData);
    }
}
