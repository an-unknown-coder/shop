package com.qf.controller;


import com.qf.dto.ResultBean;
import com.qf.entity.CartInfo;
import com.qf.entity.TOrder;
import com.qf.entity.TProductbBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Controller
public class AddressController {

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("order")
    public String getAddress(Model model){

        ResultBean addresssBean = restTemplate.getForObject("http://order-service/orderAddress/queryAll", ResultBean.class);

        model.addAttribute("addressResult", addresssBean);

        return "order";
    }


    /**
     * 支付
     * @return
     *
     *
     */

    @PostMapping("orderPay")
    public String getPay(TOrder order, HttpServletRequest request,Model model){

        Object user = request.getAttribute("user");


        return "success";
    }

    /**
     * 创建订单
     */
    public String AddOrder(TOrder order,Object user ,Model model){

        /**
         * 判断用户是否登陆
         */
        if (user==null){
            return "用户未登录,请重新登陆";

        }

        /**
         * 获取购物车中商品列表
         */

        CartInfo cartInfo=null;

        List<TProductbBean> productList = cartInfo.getProductList();

        if (cartInfo == null || cartInfo.getProductList().size() == 0) {
            throw new NullPointerException("购物车中没有可支付的商品!");
        }

        //TODO 检测商品是否都有库存,如果没有库存需要提醒用户

        for (TProductbBean tp : productList) {
            Long pid = tp.getPid();
            Integer nums = restTemplate.getForObject("http://order-service/orderAddress/queryOneInvenTory?id="+pid, Integer.class);

            if (nums<=0){

                return "库存不足,请重新选购";
            }

        }






        //TODO 库存不足，则提示用户某些商品的库存不足，请重新选购
        //TODO 获取配送方式




        return null;
    }



}
