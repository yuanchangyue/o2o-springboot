package com.changyue.o2o.web.shopadmin;

import com.changyue.o2o.dto.UserProductMapExecution;
import com.changyue.o2o.entity.*;
import com.changyue.o2o.service.ProductSellDailyService;
import com.changyue.o2o.service.UserProductMapService;
import com.changyue.o2o.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @program: o2o-springboot
 * @description: 用户消费商品管理控制器
 * @author: 袁阊越
 * @create: 2019-11-02 00:28
 */
@RestController
@RequestMapping("/shopadmin")
public class UserProductMapController {

    @Autowired
    private UserProductMapService listProductSellDilly;

    @Autowired
    private ProductSellDailyService productSellDailyService;


    @GetMapping("/listuserproductmapbyshop")
    private Map<String, Object> listUserProductMapByShop(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();

        int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
        int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
<<<<<<< HEAD
=======

>>>>>>> 4084400726203979b8a84d9e1a5b40f88ba6a7c5
        Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
        if ((pageIndex > -1) && (pageSize > -1) && (currentShop != null) && (currentShop.getShopId() != null)) {
            UserProductMap userProductMap = new UserProductMap();
            userProductMap.setShop(currentShop);
            String productName = HttpServletRequestUtil.getString(request, "productName");
            if (!StringUtils.isEmpty(productName)) {
                Product product = new Product();
                product.setProductName(productName);
                userProductMap.setProduct(product);
            }

            UserProductMapExecution userProductMapExecution = listProductSellDilly.listUserProductMap(userProductMap, pageIndex, pageSize);
            modelMap.put("userProductMapPageInfo", userProductMapExecution.getUserProductMapPageInfo());
            modelMap.put("success", true);

        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty pageSize and pageIndex");
        }
        return modelMap;
    }

    @GetMapping("/listproductselldailyinfobyshop")
    private Map<String, Object> listProductSellDailyInfoByShop(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();

        Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");

        if ((currentShop != null) || (currentShop.getShopId() != null)) {
            ProductSellDaily productSellDailyCondition = new ProductSellDaily();
            productSellDailyCondition.setShop(currentShop);

            Calendar calendar = Calendar.getInstance();
            //昨天的日期
            calendar.add(Calendar.DATE, -1);
            Date endTime = calendar.getTime();
            //七天前的日期
            calendar.add(Calendar.DATE, -6);

            Date beginTime = calendar.getTime();
            List<ProductSellDaily> productSellDailyList = productSellDailyService.listProductSellDilly(productSellDailyCondition, beginTime, endTime);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            //商品名称 不重复使用hashSet
            HashSet<String> legendDate = new HashSet<>();
            //x轴的数据
            HashSet<String> xData = new HashSet<>();
            //定义的eCharts
            List<EchartSeries> series = new ArrayList<>();
            //日销量的列表
            List<Integer> totalList = new ArrayList<>();

            String currentProductName = "";

            for (int i = 0; i < productSellDailyList.size(); i++) {

                ProductSellDaily productSellDaily = productSellDailyList.get(i);
                legendDate.add(productSellDaily.getProduct().getProductName());
                xData.add(dateFormat.format(productSellDaily.getCreateTime()));

                if (!currentProductName.equals(productSellDaily.getProduct().getProductName()) &&
                        !productSellDaily.getProduct().getProductName().isEmpty()) {
                    //如果currentProductName 不是获取的productSellDaily的值
                    //说明当前商品的近几天日销量已经统计完成 (商品名称，对应的统计的日期，当日的销量)
                    EchartSeries echartSeries = new EchartSeries();
                    echartSeries.setName(currentProductName);
                    echartSeries.setData(totalList.subList(0, totalList.size()));
                    series.add(echartSeries);
                    //重置totalList
                    totalList = new ArrayList<>();
                    //变换
                    currentProductName = productSellDaily.getProduct().getProductName();
                    //继续添加值
                    totalList.add(productSellDaily.getTotal());
                } else {
                    totalList.add(productSellDaily.getTotal());
                    currentProductName = productSellDaily.getProduct().getProductName();
                }
<<<<<<< HEAD
=======

>>>>>>> 4084400726203979b8a84d9e1a5b40f88ba6a7c5
                //列表的最后 把最后的信息加上
                if (i == productSellDailyList.size() - 1) {
                    EchartSeries echartSeries = new EchartSeries();
                    echartSeries.setName(currentProductName);
                    echartSeries.setData(totalList.subList(0, totalList.size()));
                    series.add(echartSeries);
                }
<<<<<<< HEAD
=======

>>>>>>> 4084400726203979b8a84d9e1a5b40f88ba6a7c5
            }

            modelMap.put("series", series);
            modelMap.put("legendDate", legendDate);

<<<<<<< HEAD

            List<EchartXAxis> xAxis = new ArrayList<>();
            EchartXAxis exa = new EchartXAxis();
            exa.setData(xData);

=======
            List<EchartXAxis> xAxis = new ArrayList<>();
            EchartXAxis exa = new EchartXAxis();
            exa.setData(xData);
>>>>>>> 4084400726203979b8a84d9e1a5b40f88ba6a7c5
            xAxis.add(exa);

            modelMap.put("success", true);
            modelMap.put("xAxis", xAxis);

<<<<<<< HEAD
        } else {
=======
        }else{
>>>>>>> 4084400726203979b8a84d9e1a5b40f88ba6a7c5
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty shopId");
        }

        return modelMap;
    }

}
