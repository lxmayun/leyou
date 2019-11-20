package com.leyou.item.mapper;

import com.leyou.pojo.Brand;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;

public interface BrandMapper extends Mapper<Brand> {

    /**
     * 新增商品分类和品牌中间表数据
     * @param cid 商品分类id
     * @param bid 品牌id
     * @return
     */
    @Insert("INSERT INTO tb_category_brand(category_id, brand_id) VALUES (#{cid},#{bid})")
    int insertCategoryAndBrand(@Param("cid") Long cid, @Param("bid") Long bid);



    @Update("UPDATE tb_category_brand SET category_id = #{cid} WHERE brand_id = #{bid}")
    int updateCategoryAndBrand(@Param("cid") Long cid, @Param("bid") Long bid);

    /**
     * 删除商品分类和品牌中间表数据
     * @param bid 品牌id
     * @return
     */
    @Delete("DELETE  FROM  tb_category_brand where  brand_id = #{bid}")
    int deleteCategoryAndBrand(@Param("bid") Long bid);
}
