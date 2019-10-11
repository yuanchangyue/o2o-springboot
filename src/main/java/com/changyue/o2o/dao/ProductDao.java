package com.changyue.o2o.dao;

import com.changyue.o2o.entity.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductDao {

    /**
     * 添加产品
     *
     * @param product 商品的信息
     * @return 插入的商品的ID
     */
    int insertProduct(Product product);

    /**
     * 更新商品
     *
     * @param product 商品的信息
     * @return 影响行数
     */
    int updateProduct(Product product);

    /**
     * 根据商品ID 查询商品
     *
     * @param productId 商品ID
     * @return 商品实体类
     */
    Product queryProductById(long productId);

    /**
     * 根据商铺Id 查询当前商铺下的全部的商品
     *
     * @param shopId 商品Id
     * @return 全部的商品
     */
    List<Product> queryAllProduct(@Param("shopId") Long shopId);


    /**
     * 查询商品的分页数据
     *
     * @param productCondition 商品
     * @param rowIndex         从哪一页开始
     * @param pageSize         页数
     * @return 全部的商品
     */
    List<Product> queryProductList(@Param("productCondition") Product productCondition, @Param("rowIndex") Integer rowIndex, @Param("pageSize") Integer pageSize);

    /**
     * 查询商品的分页数据
     *
     * @param productCondition 商品
     * @return 全部的商品
     */
    List<Product> queryProductListByPageHelper(@Param("productCondition") Product productCondition);


    /**
     * 查询商铺下有多少商品
     *
     * @return 商品的数量
     */
    int queryProductCount(@Param("productCondition") Product productCondition);

    /**
     * 删除商品的类别的时候需要更新商品的类别为空
     *
     * @param productCategoryId 商品类别id
     */
    int updateProductCategoryToNull(long productCategoryId);


}
