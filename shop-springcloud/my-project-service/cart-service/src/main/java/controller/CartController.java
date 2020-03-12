package controller;


import com.qf.constant.RedisConstant;
import com.qf.dto.ResultBean;
import com.qf.util.RedisUtil;
import com.qf.vo.CartItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("cart")
public class CartController {
    /**
     * 三种情况
     *  1> 当前没有购物车
     *       需要新建购物车 然后把商品添加到购物车中 然后 放到redis 里面
     *  2> 有购物车 但是没有商品
     *      先从redis  中获取购物车  把商品添加到购物车 然后存到redis 里面
     *
     *  3> 购物车里面有商品
     *   先从redis 中获取购物车 数量进行相加 再存到redis 中
     */
    @Autowired
    private RedisTemplate redisTemplate;


    @RequestMapping("getCart")
    public ResultBean dealCart(String uuid,Long productId,int count){


        String redisKey = RedisUtil.getRedisKey(RedisConstant.USER_CART_PRE, uuid);

        Object o = redisTemplate.opsForValue().get(redisKey);
        if (o == null){
            // 说明是第一种情况
            CartItem cartItem = new CartItem();
            cartItem.setProductId(productId);
            cartItem.setCount(count);
            cartItem.setUpdateTime(new Date());

            // 存到购物车中
            List<CartItem> carts = new ArrayList<CartItem>();
            carts.add(cartItem);
            //存到redis 中
            redisTemplate .opsForValue().set(redisKey,carts);
            return ResultBean.success(carts,"添加购物车成功！！");
        }


        // 有购物车
        List<CartItem> carts= (List<CartItem>) o;


        for (CartItem cart : carts) {
            if (cart.getProductId().longValue() == productId.longValue()){
                //当前用户有购物车 且购物车有商品.

                //添加数量
                cart.setCount(cart.getCount()+count);
                //设置时间
                cart.setUpdateTime(new Date());

                //购物车商品已经更新 再存到redis 中
                redisTemplate.opsForValue().set(redisKey,carts);
                return ResultBean.success(carts,"添加购物车成功");
            }



        }

        // 有购物车 但是没商品
        // 封装购物车商品对象
        CartItem cartItem = new CartItem();
        cartItem.setProductId(productId);
        cartItem.setCount(count);
        cartItem.setUpdateTime(new Date());
        carts.add(cartItem);

        // 存到redis中
        redisTemplate .opsForValue().set(redisKey,carts);
        return ResultBean.success(carts,"添加购物车成功");


    }

    /**
     * 清空购物车
     * @param uuid
     * @return
     */
    @RequestMapping("del")
    public ResultBean delCart(String uuid){

        String redisKey = RedisUtil.getRedisKey(RedisConstant.USER_CART_PRE, uuid);

        redisTemplate.delete(redisKey);
        return ResultBean.success("清空购物车成功");
    }


    /**
     * 更新购物车
     */

    @RequestMapping("update")
    public ResultBean updateCart(String uuid,Long productId,int count){

        String redisKey = RedisUtil.getRedisKey(RedisConstant.USER_CART_PRE, uuid);

        Object o = redisTemplate.opsForValue().get(redisKey);

        if(o !=null){
            //有购物车
            List<CartItem> carts = (List<CartItem>) o;
            // 拿到购物车中的商品
            for (CartItem cart : carts) {

                if (cart.getProductId().longValue() == productId.longValue()){
                    cart.setCount(count);
                    cart.setUpdateTime(new Date());

                    // 在存入redis 中

                    redisTemplate.opsForValue().set(redisKey,cart);
                    return ResultBean.success(carts,"更新购物车成功");
                }
            }
        }
        return ResultBean.success("当前客户没有购物车");


    }
}


