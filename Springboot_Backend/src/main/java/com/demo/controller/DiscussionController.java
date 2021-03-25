package com.demo.controller;


import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.demo.common.lang.Result;
import com.demo.common.lang.dto.AddDiscussionDto;
import com.demo.common.lang.dto.VerifyCodeDto;
import com.demo.entity.Discussion;
import com.demo.service.DiscussionService;
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
 * @since 2020-12-14
 */
@RestController
@RequestMapping("/discussion")
public class DiscussionController {

    @Autowired
    DiscussionService discussionService;

    @GetMapping("/all")
    public Result all(@RequestParam("pagenum") int pagenum,
                      @RequestParam("pagesize")int pagesize,
                      @RequestParam("problemId")int problemId){
        List<Discussion> discussions=discussionService
                .list(new QueryWrapper<Discussion>().eq("problemId",problemId));
        Collections.reverse(discussions);

        int total=discussions.size();
        List<Discussion> retList=new ArrayList<>();
        for(int i=Math.min(total-1,(pagenum-1)*pagesize);i<Math.min(total,pagenum*pagesize);i++){
            retList.add(discussions.get(i));
        }
        return Result.succ(MapUtil.builder()
                .put("discussionList",retList)
                .put("total",total)
                .map());
    }

    @PostMapping("/add")
    public Result add(@Validated @RequestBody AddDiscussionDto addDiscussionDto){
        Discussion discussion=new Discussion();
        discussion.setContent(addDiscussionDto.getContent());
        discussion.setDiscussionUid(addDiscussionDto.getDiscussionUid());
        discussion.setProblemId(addDiscussionDto.getProblemId());
        discussion.setDiscussionId(discussionService.count()+1);
        discussionService.save(discussion);
        return Result.succ(null);
    }
}
