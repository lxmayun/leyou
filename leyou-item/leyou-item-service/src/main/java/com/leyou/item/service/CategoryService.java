package com.leyou.item.service;

import com.leyou.item.mapper.CategoryMapper;
import com.leyou.pojo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 根据parentId查询子类目
     * @param pid
     * @return
     */
    public List<Category> queryCategoriesByPid(Long pid) {
        Category record = new Category();
        record.setParentId(pid);
        return this.categoryMapper.select(record);
    }

    /**
     * 新增一条数据
     * @param record
     */
    public void addCategories(Category record){
        this.categoryMapper.insertSelective(record);
    }

    public void deleteCategoriesByid(Long id) {
        this.categoryMapper.deleteByPrimaryKey(id);
    }

    public List<Category> queryByBrandId(Long bid) {
        return this.categoryMapper.queryByBrandId(bid);
    }

    /**
     * 根据id列表查询商品名称
     * @param ids
     * @return
     */
    public List<String> queryNamesByIds(List<Long> ids){
        List<Category> list = this.categoryMapper.selectByIdList(ids);
        List<String> names = list.stream().map(category -> category.getName()).collect(Collectors.toList());

//        List<String> names = new ArrayList<>();
//        for (Category category : list) {
//            names.add(category.getName());
//        }
        return names;
    }
}
