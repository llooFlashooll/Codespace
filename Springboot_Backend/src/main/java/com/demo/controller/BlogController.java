package com.demo.controller;


import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.demo.common.lang.Result;
import com.demo.common.lang.dto.SaveBlogDto;
import com.demo.entity.Blog;
import com.demo.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author w_57
 * @since 2020-11-27
 */
@RestController
@RequestMapping("/blog")
public class BlogController {
    @Autowired
    BlogService blogService;

    @GetMapping("/getByBlogId")
    public Result getByBlogId(@RequestParam("blogId")int blogId){
        Blog blog=blogService.getById(blogId);
        if(blog==null){
            Blog ret=new Blog();
            ret.setUid("0");
            ret.setContent("");
            ret.setBlogId(0);
            ret.setBlogTitle("");
            return Result.succ(ret);
        }
        return Result.succ(blog);
    }

    @GetMapping("/getByUserId")
    public Result getByUserId(@RequestParam("userId")String userId,
                              @RequestParam("pagenum") int pagenum,
                              @RequestParam("pagesize")int pagesize){
        List<Blog> blogList=blogService.list(new QueryWrapper<Blog>().eq("uid",userId));

        Collections.reverse(blogList);

        int total=blogList.size();
        List<Blog> retList=new ArrayList<>();
        for(int i=Math.min(total,(pagenum-1)*pagesize);i<Math.min(total,pagenum*pagesize);i++){
            retList.add(blogList.get(i));
        }
        return Result.succ(MapUtil.builder()
                .put("blogList",retList)
                .put("total",total)
                .map());
    }

    @GetMapping("/blogList")
    public Result getBlogList(@RequestParam("pagenum") int pagenum,
                              @RequestParam("pagesize")int pagesize){
        List<Blog> blogList=blogService.list();
        Collections.reverse(blogList);
        int total=blogList.size();
        List<Blog> retList=new ArrayList<>();
        for(int i=Math.min(total-1,(pagenum-1)*pagesize);i<Math.min(total,pagenum*pagesize);i++){
            retList.add(blogList.get(i));
        }
        return Result.succ(MapUtil.builder()
                .put("blogList",retList)
                .put("total",total)
                .map());
    }

    @PostMapping("/save")
    public Result save(@Validated @RequestBody SaveBlogDto saveBlogDto){
        Blog blog=blogService.getById(saveBlogDto.getBlogId());//新建的blog id传过来时默认为0
        if(blog==null){//新建博客
            Blog newblog=new Blog();
            newblog.setBlogId(blogService.count()+1);
            newblog.setContent(saveBlogDto.getContent());
            newblog.setUid(saveBlogDto.getUid());
            newblog.setBlogTitle(saveBlogDto.getBlogTitle());
            System.out.println(newblog);
            blogService.save(newblog);
        }else{//修改原有博客
            blog.setContent(saveBlogDto.getContent());
            blog.setBlogTitle(saveBlogDto.getBlogTitle());
            blogService.saveOrUpdate(blog);
        }
        return Result.succ(null,"博客更新成功");
    }
}
