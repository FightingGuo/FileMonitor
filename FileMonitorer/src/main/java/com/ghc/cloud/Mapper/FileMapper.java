package com.ghc.cloud.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ghc.cloud.entity.UniqueFile;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Date 2022/12/28 /17:28
 * @Author guohc
 * @Description
 */
@Mapper
public interface FileMapper extends BaseMapper<UniqueFile> {
}
