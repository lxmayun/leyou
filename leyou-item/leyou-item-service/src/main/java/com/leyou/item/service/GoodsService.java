package com.leyou.item.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leyou.common.pojo.PageResult;
import com.leyou.item.mapper.BrandMapper;
import com.leyou.item.mapper.SpuDetailMapper;
import com.leyou.item.mapper.SpuMapper;
import com.leyou.pojo.Brand;
import com.leyou.pojo.Spu;
import com.leyou.pojo.SpuBo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GoodsService {
    @Autowired
    SpuMapper spuMapper;

    @Autowired
    SpuDetailMapper spuDetailMapper;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private BrandMapper brandMapper;

    /**
     * 分页查询商品列表
     * @param key
     * @param saleable
     * @param page
     * @param rows
     * @return
     */
    public PageResult<SpuBo> querySpuBoByPage(String key, Boolean saleable, Integer page, Integer rows) {
        Example example = new Example(Spu.class);
        Example.Criteria criteria = example.createCriteria();
        //查询条件
        criteria.andLike("title","%"+key+"%");

        //添加过滤条件
        criteria.andEqualTo("saleable", saleable);

        //添加分页
        PageHelper.startPage(page,rows);
        //执行查询
        System.out.println(key+ "/"+saleable+ "/"+page+ "/"+rows);
        List<Spu> spus = this.spuMapper.selectByExample(example);
        System.out.println(spus);
        PageInfo<Spu> pageInfo = new PageInfo<>(spus);
        //spu集合转话未spubo集合
        //实现方式1
        List<SpuBo> spuBos= spus.stream().map(spu -> {
            SpuBo spuBo = new SpuBo();
            // copy共同属性的值到新的对象
            BeanUtils.copyProperties(spu, spuBo);
            Brand brand = this.brandMapper.selectByPrimaryKey(spu.getBrandId());
            spuBo.setBname(brand.getName());
            List<String> names = this.categoryService.queryNamesByIds(Arrays.asList(spu.getCid1(), spu.getCid2(), spu.getCid3()));
            spuBo.setCname(StringUtils.join(names, "-"));
            return spuBo;
        }).collect(Collectors.toList());

        //实现方式2 foreach
//        List<SpuBo> spuBos = new ArrayList<>();
//        spus.forEach(spu->{
//            SpuBo spuBo = new SpuBo();
//            // copy共同属性的值到新的对象
//            BeanUtils.copyProperties(spu, spuBo);
//            // 查询分类名称
//            List<String> names = this.categoryService.queryNamesByIds(Arrays.asList(spu.getCid1(), spu.getCid2(), spu.getCid3()));
//            spuBo.setCname(StringUtils.join(names, "/"));
//
//            // 查询品牌的名称
//            spuBo.setBname(this.brandMapper.selectByPrimaryKey(spu.getBrandId()).getName());
//
//            spuBos.add(spuBo);
//        });

        //返回PageResult<SpuBos>

        return  new PageResult<>(pageInfo.getTotal(), spuBos);

    }
}
