package com.changyue.o2o.web.superadmin;

import com.changyue.o2o.entity.Area;
import com.changyue.o2o.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @program: shopping
 * @description:
 * @author: ChangYue
 * @create: 2019-03-12 21:37
 */

@Controller
@RequestMapping(value = "/superadmin")
public class AreaController {

    @Autowired
    private AreaService areaService;

    @RequestMapping(value = "/arealist",method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> listArea() {

        Map<String, Object> modelMap = new HashMap<>();
        List<Area> areaList = new ArrayList<>();
        try {
            areaList = areaService.getAreaList();
            modelMap.put("rows",areaList);
            modelMap.put("total",areaList.size());
        } catch (Exception e) {
            e.printStackTrace();
            modelMap.put("success",false);
            modelMap.put("errMeg",e.toString());
        }

        return modelMap;
    }


}
