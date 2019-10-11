package com.changyue.o2o.service.impl;

import com.changyue.o2o.dao.ProductDao;
import com.changyue.o2o.dao.ProductImgDao;
import com.changyue.o2o.dto.ImageHolder;
import com.changyue.o2o.dto.ProductExecution;
import com.changyue.o2o.emums.ProductStateEnum;
import com.changyue.o2o.entity.Product;
import com.changyue.o2o.entity.ProductImg;
import com.changyue.o2o.exceptions.ProductOperationException;
import com.changyue.o2o.service.ProductService;
import com.changyue.o2o.util.ImageUtil;
import com.changyue.o2o.util.PathUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @program: shopping
 * @description:
 * @author: ChangYue
 * @create: 2019-04-10 17:30
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;

    @Autowired
    private ProductImgDao productImgDao;

    /**
     * 添加商品
     *
     * @param product        商品品信息
     * @param thumbnail      缩略图
     * @param productImgList 图片集合
     * @return 产品异常包装类
     * @throws ProductOperationException 商品操作异常
     */
    @Override
    @Transactional
    public ProductExecution addProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgList) throws ProductOperationException, IOException {

        if (product != null && product.getShop() != null && product.getShop().getShopId() != null) {
            try {
                //==== 第一步： 初始化商品信息 =====
                product.setCreateTime(new Date());
                product.setLastEditTime(new Date());
                product.setEnableStatus(1);

                //==== 第二步：处理缩略图 ====
                if (thumbnail != null) {
                    addThumbnail(product, thumbnail);
                }

                //==== 第三步：往数据库存入product 拿到productId ====
                int insertProductId = productDao.insertProduct(product);
                if (insertProductId <= 0) {
                    throw new ProductOperationException("创建商品失败");
                }

            } catch (Exception e) {
                throw new ProductOperationException("创建商品失败");
            }

            if (productImgList.size() > 0) {
                //==== 第四步：批量添加图片 ====
                addProductImgList(product, productImgList);
            }

            return new ProductExecution(ProductStateEnum.SUCCESS, product);
        } else {
            return new ProductExecution(ProductStateEnum.EMPTY);
        }
    }

    /**
     * 修改商品
     *
     * @param product        商品信息
     * @param thumbnail      缩略图
     * @param productImgList 图片集合
     * @return 产品类异常包装类
     * @throws ProductOperationException 商品操作异常
     */
    @Override
    public ProductExecution modifyProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgList) throws IOException {
        //==== 第一步：根据前端传递过来的值 分别处理 ====
        if (product != null && product.getProductId() != null && product.getShop() != null) {
            product.setLastEditTime(new Date());
            //==== 第二步：处理图  ====

            //如果存在图先删除图片再添加图片
            if (thumbnail != null) {
                Product p = productDao.queryProductById(product.getProductId());
                if (p.getImgAddr() != null) {
                    ImageUtil.deleteImgFileOrPath(p.getImgAddr());
                }
                try {
                    addThumbnail(product, thumbnail);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            //如果存在商品详情图，则将原来删除 在添加新的图片
            if (productImgList.size() > 0) {
                deleteProductImgList(product.getProductId());
                addProductImgList(product, productImgList);
            }

            //==== 第三步：更新数据库商品信息 ====
            try {
                int effectNum = productDao.updateProduct(product);
                if (effectNum <= 0) {
                    throw new ProductOperationException("商品更新失败！");
                }
                return new ProductExecution(ProductStateEnum.SUCCESS);
            } catch (ProductOperationException e) {
                throw new ProductOperationException("商品更新失败！" + e.toString());
            }
        } else {
            return new ProductExecution(ProductStateEnum.EMPTY);
        }
    }

    /**
     * 删除商品的全部图片
     *
     * @param productId 商品id
     */
    private void deleteProductImgList(Long productId) {

        List<ProductImg> productImgs = productImgDao.queryProductImgList(productId);
        //删除本地的图片
        for (ProductImg productImg : productImgs) {
            ImageUtil.deleteImgFileOrPath(productImg.getImgAddr());
        }
        //删除数据库的图片
        productImgDao.deleteProductImgByProductId(productId);

    }

    /**
     * 通过商品id 查询商品信息
     *
     * @param productId 商品Id
     * @return 商品信息
     */
    @Override
    public Product queryProductById(Long productId) {
        return productDao.queryProductById(productId);
    }

    /**
     * 根据商铺Id 查询当前商铺下的全部的商品
     *
     * @param shopId 商品Id
     * @return 全部的商品
     */
    @Override
    public List<Product> queryProductAtAll(Long shopId) {
        return productDao.queryAllProduct(shopId);
    }

    /**
     * 查询商品全部信息（分页）
     *
     * @param productCondition 查询的条件
     * @param pageIndex        开始的页码
     * @param pageSize         显示多少商品
     * @return dto
     */
    @Override
    public ProductExecution getProductList(Product productCondition, Integer pageIndex, Integer pageSize) {

        // 分页
        PageHelper.startPage(pageIndex, pageSize);
        List<Product> productList = productDao.queryProductListByPageHelper(productCondition);
        PageInfo<Product> productPageInfo = new PageInfo<Product>(productList);

        ProductExecution productExecution = new ProductExecution();
        productExecution.setProductPageInfo(productPageInfo);

        return productExecution;
    }

    /**
     * 批量处理图片 并添加图片
     */
    private void addProductImgList(Product product, List<ImageHolder> productImgList) throws IOException {

        String dest = PathUtils.getShopImagePath(product.getShop().getShopId());

        ArrayList<ProductImg> productImgs = new ArrayList<>();
        for (ImageHolder imageHolder : productImgList) {
            String imgAddr = ImageUtil.generateNormalImg(imageHolder, dest);
            ProductImg productImg = new ProductImg();
            productImg.setProductId(product.getProductId());
            productImg.setImgAddr(imgAddr);
            productImg.setCreateTime(new Date());
            productImgs.add(productImg);
        }

        if (productImgs.size() > 0) {
            try {
                int effect = productImgDao.batchInsertProductImg(productImgs);
                if (effect <= 0) {
                    throw new ProductOperationException("商品详情图创建失败");
                }
            } catch (ProductOperationException e) {
                throw new ProductOperationException("商品详情图创建失败" + e.getMessage());
            }
        }

    }

    /**
     * 添加product收略图
     */
    private void addThumbnail(Product product, ImageHolder thumbnail) throws IOException {
        String path = PathUtils.getShopImagePath(product.getShop().getShopId());
        String thumbnailPath = ImageUtil.generateThumbnails(thumbnail, path);
        product.setImgAddr(thumbnailPath);
    }

}
























