package com.demo.service.impl;

import com.demo.entity.Problems;
import com.demo.mapper.ProblemsMapper;
import com.demo.service.ProblemsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author w_57
 * @since 2020-11-26
 */
@Service
public class ProblemsServiceImpl extends ServiceImpl<ProblemsMapper, Problems> implements ProblemsService {

}
