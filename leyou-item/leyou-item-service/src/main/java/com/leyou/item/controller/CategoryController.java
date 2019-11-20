package com.leyou.item.controller;

import com.leyou.item.service.CategoryService;
import com.leyou.pojo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("list")
    public ResponseEntity<List<Category>> queryCategoriesByPid(@RequestParam(value = "pid",defaultValue = "0") Long pid){
        if (pid == null || pid.longValue() < 0){
            // 响应400，相当于ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            return ResponseEntity.badRequest().build();
        }
        List<Category> categories = this.categoryService.queryCategoriesByPid(pid);
        if(categories.isEmpty()){
            // 响应404
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(categories);

    }

    @PostMapping("add")
    public ResponseEntity addCategories(@RequestBody Category category){
        long parentid = category.getParentId();
        String name = category.getName();

        this.categoryService.addCategories(category);
        return new ResponseEntity<>("新增成功", HttpStatus.OK);
    }

    @GetMapping("delete")
    public ResponseEntity  deleteCategoriesByPid(@RequestParam(value = "id") Long id){
        if (id == null || id.longValue() < 0){
            // 响应400，相当于ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            return ResponseEntity.badRequest().build();
        }
        this.categoryService.deleteCategoriesByid(id);

        return new ResponseEntity<>("删除成功", HttpStatus.OK);

    }

    /**
     * 通过品牌id查询商品分类
     * @param bid
     * @return
     */
    @GetMapping("bid/{bid}")
    public ResponseEntity<List<Category>> queryByBrandId(@PathVariable("bid") Long bid) {
        List<Category> list = this.categoryService.queryByBrandId(bid);
        if (list == null || list.size() < 1) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        System.out.println(
                list
        );
        return ResponseEntity.ok(list);
    }
}
