package com.changyue.o2o.web.shopadmin;

import com.changyue.o2o.dto.ImageHolder;
import com.changyue.o2o.dto.ProductExecution;
import com.changyue.o2o.emums.ProductStateEnum;
import com.changyue.o2o.entity.PersonInfo;
import com.changyue.o2o.entity.Product;
import com.changyue.o2o.entity.ProductCategory;
import com.changyue.o2o.entity.Shop;
import com.changyue.o2o.exceptions.ProductOperationException;
import com.changyue.o2o.service.ProductCategoryService;
import com.changyue.o2o.service.ProductService;
import com.changyue.o2o.util.CodeUtil;
import com.changyue.o2o.util.HttpServletRequestUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: shopping
 * @description: 商品管理控制器
 * @author: ChangYue
 * @create: 2019-04-10 19:18
 */
@CrossOrigin
@RequestMapping(value = "/shopadmin")
@Controller
public class ProductManagementController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductCategoryService productCategoryService;

    private static final int IMG_MAX_COUNT = 6;

    /**
     * 查询全部的商品信息(分页)
     */
    @GetMapping("/getproductlistbyshop")
    @ResponseBody
    public Map<String, Object> getProductListByShop(HttpServletRequest request) {

        Map<String, Object> modelMap = new HashMap<>();

        int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
        int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
        Long shopId = HttpServletRequestUtil.getLong(request, "shopId");


        //从登陆session中拿到用户信息
        PersonInfo user = (PersonInfo) request.getSession().getAttribute("user");
        Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");

        if (shopId > -1L) {
            currentShop.setShopId(shopId);
        }

        if ((pageIndex > -1) && (pageSize >= -1) && (currentShop != null) && (currentShop.getShopId() != null)) {

            long productCategoryId = HttpServletRequestUtil.getLong(request, "productCategoryId");
            String productName = HttpServletRequestUtil.getString(request, "productName");

            //整合product条件
            Product productCondition = compactProductCondition(currentShop.getShopId(), productCategoryId, productName);
            ProductExecution pe = productService.getProductList(productCondition, pageIndex, pageSize);

            modelMap.put("productPageInfo", pe.getProductPageInfo());
            modelMap.put("user", user);
            modelMap.put("success", true);

        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "pageSize or pageSize or ShopId is empty");
        }

        return modelMap;
    }

    private Product compactProductCondition(Long shopId, long productCategoryId, String productName) {
        Product product = new Product();
        Shop shop = new Shop();
        shop.setShopId(shopId);
        product.setShop(shop);
        if (productCategoryId > 0) {
            ProductCategory productCategory = new ProductCategory();
            productCategory.setProductCategoryId(productCategoryId);
            product.setProductCategory(productCategory);
        }
        if (productName != null) {
            product.setProductName(productName);
        }
        return product;
    }

    /**
     * 查询全部的商品信息
     */
    @GetMapping("/getproductlist")
    @ResponseBody
    public Map<String, Object> getProductList(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();

        Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");

        if (currentShop != null) {
            List<Product> products = productService.queryProductAtAll(currentShop.getShopId());
            if (products != null && products.size() > 0) {
                modelMap.put("success", true);
                modelMap.put("products", products);
                return modelMap;
            } else {
                modelMap.put("success", false);
                modelMap.put("errMsg", "当前商铺下没有商品信息");
                return modelMap;
            }
        } else {
            throw new ProductOperationException("商铺信息不正确！");
        }
    }

    /**
     * 查询单个商品信息
     *
     * @param productId 商品Id
     */
    @RequestMapping(value = "/getproductbyid", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getProductById(@RequestParam("productId") Long productId) {
        Map<String, Object> modelMap = new HashMap<>();
        if (productId > -1) {
            Product product = productService.queryProductById(productId);
            if (product != null) {
                List<ProductCategory> productCategoryList = productCategoryService.getProductCategoryList(product.getShop().getShopId());
                modelMap.put("success", true);
                modelMap.put("product", product);
                modelMap.put("productCategoryList", productCategoryList);
            } else {
                modelMap.put("success", false);
                modelMap.put("errMsg", "没有该商品");
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "商品Id为空");
        }
        return modelMap;
    }

    /**
     * 修改商品
     */
    @RequestMapping(value = "/modifyproduct", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> modifyProduct(HttpServletRequest request) throws IOException {
        Map<String, Object> modelMap = new HashMap<>();


        //==================== 1. 验证码处理 =======================================

        //商品是否下架
        boolean statusChange = HttpServletRequestUtil.getBoolean(request, "statusChange");

        if (CodeUtil.checkVerityCode(request) && !statusChange) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "输入错误的验证码!");
            return modelMap;
        }

        //====================2. 处理前端传递的product数据===========================
        Product product = null;
        String productStr = HttpServletRequestUtil.getString(request, "productStr");

        //处理商品信息
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            product = objectMapper.readValue(productStr, Product.class);
        } catch (IOException e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }

        //处理商品图片
        ImageHolder thumbnail = null;
        ArrayList<ImageHolder> productImgList = new ArrayList<>();
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());

        try {
            if (!statusChange) {
                //前端传递中是否存在文件流
                if (multipartResolver.isMultipart(request)) {
                    thumbnail = handleImg((MultipartHttpServletRequest) request, productImgList);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", "上传图片不能为空");
                    return modelMap;
                }
            }
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }

        //====================3. 利用拿到的商品信息更新数据库===========================
        if (product != null) {
            try {

                Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
                Shop shop = new Shop();
                shop.setShopId(currentShop.getShopId());
                product.setShop(shop);

                ProductExecution productExecution = productService.modifyProduct(product, thumbnail, productImgList);

                if (productExecution.getState() == ProductStateEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", productExecution.getStateInfo());
                }

            } catch (RuntimeException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
                return modelMap;
            }

        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入商品信息");
            return modelMap;
        }

        return modelMap;
    }

    /**
     * 处理图片
     */
    private ImageHolder handleImg(MultipartHttpServletRequest request, ArrayList<ImageHolder> productImgList) throws IOException {
        MultipartHttpServletRequest multipartHttpServletRequest;
        ImageHolder thumbnail = null;
        multipartHttpServletRequest = request;
        //取出文件流
        CommonsMultipartFile thumbnailFile = (CommonsMultipartFile) multipartHttpServletRequest.getFile("thumbnail");
        if (thumbnailFile != null) {
            //创建缩略图
            thumbnail = new ImageHolder(thumbnailFile.getOriginalFilename(), thumbnailFile.getInputStream());
        }
        for (int i = 0; i < IMG_MAX_COUNT; i++) {
            //依次取出图片
            CommonsMultipartFile productImg = (CommonsMultipartFile) multipartHttpServletRequest.getFile("productImg" + i);
            if (productImg != null) {
                ImageHolder imageHolder = new ImageHolder(productImg.getOriginalFilename(), productImg.getInputStream());
                productImgList.add(imageHolder);
            } else {
                break;
            }
        }
        return thumbnail;
    }

    /**
     * 添加商品
     */
    @RequestMapping(value = "/addproduct", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> addProduct(HttpServletRequest request) throws IOException {
        Map<String, Object> modelMap = new HashMap<>();

        //==================== 1. 验证码处理 =======================================

        if (CodeUtil.checkVerityCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "输入错误的验证码？");
            return modelMap;
        }

        //====================2. 处理前端传递的product数据===========================

        Product product = null;
        //接收前端传递过来的product的参数
        String productStr = HttpServletRequestUtil.getString(request, "productStr");

        ObjectMapper mapper = new ObjectMapper();
        try {
            //前端传递过来的product json数据转换为product实体
            product = mapper.readValue(productStr, Product.class);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
            return modelMap;
        }

        ImageHolder thumbnail = null;
        ArrayList<ImageHolder> productImgList = new ArrayList<>();
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());

        try {
            //前端传递中是否存在文件流
            if (multipartResolver.isMultipart(request)) {
                thumbnail = handleImg((MultipartHttpServletRequest) request, productImgList);
            } else {
                modelMap.put("success", false);
                modelMap.put("errMsg", "上传图片不能为空");
                return modelMap;
            }
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }

        //============================= 3.添加到数据库===============================

        if (product != null && thumbnail != null && productImgList.size() > 0) {

            try {
                Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
                Shop shop = new Shop();
                shop.setShopId(currentShop.getShopId());
                product.setShop(shop);

                ProductExecution pe = productService.addProduct(product, thumbnail, productImgList);

                if (pe.getState() == ProductStateEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", pe.getStateInfo());
                    return modelMap;
                }
            } catch (ProductOperationException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
                return modelMap;
            }

        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入商品信息!");
            return modelMap;
        }
        return modelMap;
    }


}
