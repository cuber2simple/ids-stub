package org.cuber.stub.repo;

import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

public interface Mapper<DTO,KEY> {
    /**
     * 插入
     * @param dto
     * @return
     */
    int create(DTO dto);

    /**
     * 按照主键查找
     * @param key
     * @return
     */
    DTO retrieveById(KEY key);

    /**
     * 分页查询
     * @param obj
     * @return
     */
    Page<DTO> retrievePage(Map obj);

    /**
     * 根据主键更新全部
     * @param dto
     * @return
     */
    int updateById(DTO dto);

    /**
     * 根据传入的值更新,只要不为空就更新
     * @param dto
     * @return
     */
    int updateDynamic(DTO dto);

    /**
     * 查询全部
     * @return
     */
    List<DTO> retrieveAll();


    /**
     * 根据主键删除
     * @param key
     * @return
     */
    int deleteById(KEY key);
}
