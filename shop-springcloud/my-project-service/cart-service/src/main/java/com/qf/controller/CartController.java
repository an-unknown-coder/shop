package com.qf.controller;

import com.qf.constant.RedisConstant;
import com.qf.dto.ResultBean;
import com.qf.dto.TProductCartDTO;
import com.qf.entity.TProduct;
import com.qf.mapper.TProductMapper;
import com.qf.util.RedisUtil;
import com.qf.vo.CartItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.*;

@RestController
@RequestMapping("cart")
public class CartController {
    /**
     * 三种情况
     * 1> 当前没有购物车
     * 需要新建购物车 然后把商品添加到购物车中 然后 放到redis 里面
     * 2> 有购物车 但是没有商品
     * 先从redis  中获取购物车  把商品添加到购物车 然后存到redis 里面
     * <p>
     * 3> 购物车里面有商品
     * 先从redis 中获取购物车 数量进行相加 再存到redis 中
     */
    @Autowired
    private RedisTemplate redisTemplate;

    @Resource
    private TProductMapper tProductMapper;

    /**
     * 给购物车添加商品
     */
    @RequestMapping("getCart/{id}/{productId}/{count}")
    public ResultBean dealCart(@PathVariable String id,
                               @PathVariable Long productId,
                               @PathVariable int count) {
        String redisKey = RedisUtil.getRedisKey(RedisConstant.USER_CART_PRE, id);

        Object o = redisTemplate.opsForValue().get(redisKey);
        if (o == null) {
            // 说明是第一种情况
            CartItem cartItem = new CartItem();
            cartItem.setProductId(productId);
            cartItem.setCount(count);
            cartItem.setUpdateTime(new Date());

            // 存到购物车中
            List<CartItem> carts = new ArrayList<CartItem>();
            carts.add(cartItem);
            //存到redis 中
            redisTemplate.opsForValue().set(redisKey, carts);
            return ResultBean.success(carts, "添加购物车成功！！");
        }


        // 有购物车
        List<CartItem> carts = (List<CartItem>) o;


        for (CartItem cart : carts) {
            if (cart.getProductId().longValue() == productId.longValue()) {
                //当前用户有购物车 且购物车有商品.

                //添加数量
                cart.setCount(cart.getCount() + count);
                //设置时间
                cart.setUpdateTime(new Date());

                //购物车商品已经更新 再存到redis 中
                redisTemplate.opsForValue().set(redisKey, carts);
                return ResultBean.success(carts, "添加购物车成功");
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
        redisTemplate.opsForValue().set(redisKey, carts);
        return ResultBean.success(carts, "添加购物车成功");
    }

    /**
     * 清空购物车
     */
    @RequestMapping("del/{uuid}")
    public ResultBean delCart(@PathVariable String uuid) {
        String redisKey = RedisUtil.getRedisKey(RedisConstant.USER_CART_PRE, uuid);
        redisTemplate.delete(redisKey);
        return ResultBean.success("清空购物车成功");
    }

    /**
     * 更新购物车
     */
    @RequestMapping("update/{id}/{productId}/{count}")
    public ResultBean updateCart(@PathVariable String id,
                                 @PathVariable Long productId,
                                 @PathVariable int count) {

        String redisKey = RedisUtil.getRedisKey(RedisConstant.USER_CART_PRE, id);

        Object o = redisTemplate.opsForValue().get(redisKey);
        if (o != null) {
            //有购物车
            List<CartItem> carts = (List<CartItem>) o;
            // 拿到购物车中的商品
            for (CartItem cart : carts) {

                if (cart.getProductId().longValue() == productId.longValue()) {
                    if (count<1){
                        carts.remove(cart);
                    }else {
                        cart.setCount(count);
                    }
                    // 在存入redis 中
                    redisTemplate.opsForValue().set(redisKey, carts);
                    return ResultBean.success(carts, "更新购物车成功");
                }
            }
        }
        return ResultBean.success("当前客户没有购物车");
    }

    // 查看购物车
    @RequestMapping("show/{uuid}")
    public ResultBean showCart(@PathVariable String uuid) {
        if (uuid != null && !"".equals(uuid)) {
            //有购物车
            String redisKey = RedisUtil.getRedisKey(RedisConstant.USER_CART_PRE, uuid);
            Object o = redisTemplate.opsForValue().get(redisKey);
            if (o != null) {
                List<CartItem> carts = (List<CartItem>) o;
                List<TProductCartDTO> productCartDTOS = new ArrayList<>();
                for (CartItem cart : carts) {
                    //去redis 中取
                    String productKey = RedisUtil.getRedisKey(RedisConstant.PRODUCT_PRE, cart.getProductId().toString());
                    //拿到商品属性
                    TProduct product = (TProduct) redisTemplate.opsForValue().get(productKey);
                    if (product == null) {
                        // 去数据库拿 然后存redis
                        product = tProductMapper.selectByPrimaryKey(cart.getProductId());
                        redisTemplate.opsForValue().set(productKey, product);
                    }
                    // 有商品
                    TProductCartDTO tProductCartDTO = new TProductCartDTO();

                    tProductCartDTO.setProduct(product);
                    tProductCartDTO.setCount(cart.getCount());
                    tProductCartDTO.setUpdateTime(cart.getUpdateTime());

                    //存到集合中
                    productCartDTOS.add(tProductCartDTO);
                }
                // 添加订单排序
                productCartDTOS.sort((o1, o2) -> (int) (o2.getUpdateTime().getTime() - o1.getUpdateTime().getTime()));
                return ResultBean.success(productCartDTOS);
            }

        }
        return ResultBean.error("当前用户没有购物车");
    }

    //合并购物车
    @RequestMapping("merge/{uuid}/{uid}")
    public ResultBean merge(@PathVariable String uuid, @PathVariable String uid) {
        /**
         合并
         1.未登录状态下没有购物车==》合并成功
         2.未登录状态下有购物车，但已登录状态下没有购物车==》把未登录的变成已登录的
         3.未登录状态下有购物车，但已登录状态下也有购物车，而且购物车中的商品有重复==》难点！
         */

        // 首先要获得两种情况下的购物车

        String noLoginRedisKey = RedisUtil.getRedisKey(RedisConstant.USER_CART_PRE, uuid);
        String loginRedisKey = RedisUtil.getRedisKey(RedisConstant.USER_CART_PRE, uid);
        //未登陆下的购物车
        Object noLoginO = redisTemplate.opsForValue().get(noLoginRedisKey);
        //登陆下的购物车
        Object loginO = redisTemplate.opsForValue().get(loginRedisKey);

        if (noLoginO == null) {
            //1.未登录下没有购物车
            return ResultBean.success("未登录状态下无购物车,无需合并");
        }
        if (loginO == null) {
            //2.未登录状态下有购物车，但已登录状态下没有购物车==》把未登录的变成已登录的
            redisTemplate.opsForValue().set(loginRedisKey, noLoginO);
            //删除未登录下的购物车
            redisTemplate.delete(noLoginRedisKey);
            return ResultBean.success(noLoginO, "合并成功");
        }

        //3.登陆状态下有购物车，但已登陆下也有购物车 需要合并

        List<CartItem> noLoginCarts = (List<CartItem>) noLoginO;
        List<CartItem> loginCarts = (List<CartItem>) loginO;
        Map<Long, CartItem> map = new HashMap<>();
        for (CartItem noLoginCartItem : noLoginCarts) {
            map.put(noLoginCartItem.getProductId(), noLoginCartItem);
        }
        //此时map中就有所有的未登录状态下的购物车的商品
        //存入已登录状态下购物车的商品
        for (CartItem loginCartItem : loginCarts) {
            //尝试去检查下map中该商品是否已存在
            CartItem currentCartItem = map.get(loginCartItem.getProductId());
            if (currentCartItem != null) {
                //已存在
                currentCartItem.setCount(currentCartItem.getCount() + loginCartItem.getCount());
                //时间 必然是未登录状态下的更近
            } else {
                //不存在，直接放
                map.put(loginCartItem.getProductId(), loginCartItem);
            }
        }
        //此时Map中存放的数据就是合并之后的购物车
        //删除未登录状态下的购物车
        redisTemplate.delete(noLoginRedisKey);
        //把新的购物车存入到redis中
        Collection<CartItem> values = map.values();
        List<CartItem> newCarts = new ArrayList<>(values);
        redisTemplate.opsForValue().set(loginRedisKey, newCarts);
        return ResultBean.success(newCarts, "合并成功");
    }
}


