package com.xtuniversity.mall.API;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xtuniversity.mall.cache.UserCache;
import com.xtuniversity.mall.model.address.entity.Address;
import com.xtuniversity.mall.model.address.service.AddressService;
import com.xtuniversity.mall.model.base.BaseController;
import com.xtuniversity.mall.model.base.util.DateUtil;
import com.xtuniversity.mall.model.base.util.MD5Util;
import com.xtuniversity.mall.model.commodity.entity.Commodity;
import com.xtuniversity.mall.model.commodity.service.CommodityService;
import com.xtuniversity.mall.model.complaint.entity.Complaint;
import com.xtuniversity.mall.model.complaint.service.ComplaintService;
import com.xtuniversity.mall.model.order.Service.OrderService;
import com.xtuniversity.mall.model.order.entity.Order;
import com.xtuniversity.mall.model.permission.entity.Permission;
import com.xtuniversity.mall.model.permission.service.PermissionService;
import com.xtuniversity.mall.model.permissionuser.entity.PermissionUser;
import com.xtuniversity.mall.model.permissionuser.service.PerMissionUserService;
import com.xtuniversity.mall.model.type.entity.Type;
import com.xtuniversity.mall.model.type.service.TypeService;
import com.xtuniversity.mall.model.user.entity.User;
import com.xtuniversity.mall.model.user.server.UserService;
import com.xtuniversity.mall.model.util.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @author zlj
 * @introduce
 */
@RestController("apiController")
@CrossOrigin
@RequestMapping(value = "api/")
public class APIController extends BaseController {
    private final static Logger logger = LoggerFactory.getLogger(APIController.class);
    @Autowired
    UserService userService;
    @Autowired
    ComplaintService complaintService;
    @Autowired
    PerMissionUserService perMissionUserService;

    @Autowired
    PermissionService permissionService;

    @Autowired
    CommodityService commodityService;
    @Autowired
    OrderService orderService;
    @Autowired
    TypeService typeService;

    @Autowired
    AddressService addressService;

    /**
     * 用户登录接口
     *
     * @param requestDate
     * @return
     */
    @PostMapping("login")
    public String login(@RequestBody String requestDate) {
        JSONObject requestObject = JSONObject.parseObject(requestDate);
        String tel = requestObject.getString("tel");
        if (StringUtils.isBlank(tel)) {
            return ResultObject.ERROR(ErrorCode.paramError.code(), ErrorCode.paramError.value()).toJsonString();
        }
        String password = requestObject.getString("password");
        if (StringUtils.isBlank(password)) {
            return ResultObject.ERROR(ErrorCode.paramError.code(), ErrorCode.paramError.value()).toJsonString();
        }
        User user = userService.getUserByTel(tel);
        if (user == null) {
            return ResultObject.ERROR(ErrorCode.notRegister.code(), ErrorCode.notRegister.value()).toJsonString();
        }

        if (!StringUtils.equals(MD5Util.ToMd5(password),user.getPassword())) {
            return ResultObject.ERROR(ErrorCode.passwordError.code(), ErrorCode.passwordError.value()).toJsonString();
        }
        Token token = Token.init(String.valueOf(user.getId()));
        user.setToken(token.toTokenString());
        user.setLastTime(new Date());
        userService.updateUser(user);
        JSONObject resultObject = new JSONObject();
        resultObject.put("user", user);
        logger.info("{}登陆了系统", user.getId());
        return ResultObject.SUCCESS(resultObject).toJsonString();
    }

    /**
     * 用户注册
     *
     * @param requestDate
     * @return
     */
    @PostMapping("register")
    public String register(@RequestBody String requestDate) {
        JSONObject requestObject = JSONObject.parseObject(requestDate);
        String tel = requestObject.getString("tel");
        String nick = requestObject.getString("nick");
        String password = requestObject.getString("password");
        if (StringUtils.isBlank("tel") || StringUtils.isBlank("nick") || StringUtils.isBlank("password")) {
            return ResultObject.ERROR(ErrorCode.paramError.code(), ErrorCode.paramError.value()).toJsonString();
        }
        if (userService.getUserByTel(tel) != null) {
            return ResultObject.ERROR(ErrorCode.telError.code(), ErrorCode.telError.value()).toJsonString();
        }
        if (userService.getUserByNick(nick) != null) {
            return ResultObject.ERROR(ErrorCode.nickError.code(), ErrorCode.nickError.value()).toJsonString();
        }
        //获取初始权限
        List<Permission> permissions =  permissionService.getPermissionServiceByLevel(1);
        User user = new User();
        user.setCreateTime(new Date());
        user.setNick(nick);
        user.setMoney(0L);
        user.setPassword(MD5Util.ToMd5(password));
        user.setTel(tel);
        user.setStatus(1);
        user.setComCount(0);
        user.setScore(5F);
        user.setLastTime(new Date());
        Token token = Token.init("21321");
        user.setToken(token.toTokenString());
        userService.saveUser(user);
        //给用户添加初始权限
        for(Permission permission :permissions){
            PermissionUser permissionUser = new PermissionUser();
            permissionUser.setPermissionId(permission.getId());
            permissionUser.setUserId(user.getId());
            perMissionUserService.savePermissionUser(permissionUser);
        }
        JSONObject resultObject = new JSONObject();
        resultObject.put("user", user);
        System.out.println(user.toString());
        return ResultObject.SUCCESS(resultObject).toJsonString();
    }

    /**
     * 加入购物车
     */
    @PostMapping("joinShoppingCart")
    public String joinShoppingCart(@RequestBody String requestDate) {
        JSONObject requestObject = JSONObject.parseObject(requestDate);
        String token = requestObject.getString("token");
        long userId = requestObject.getLong("userId");
        if (!checkToken(token, userId)) {
            return ResultObject.ERROR(ErrorCode.tokenError.code(), ErrorCode.tokenError.value()).toJsonString();
        }
        long commodityId = requestObject.getLong("commodityId");
        int number = requestObject.getInteger("number");
        Commodity commodity = commodityService.getCommodityById(commodityId);
        User user = userService.getUserById(userId);
        if (user == null) {
            return ResultObject.ERROR(ErrorCode.notLogin.code(), ErrorCode.notLogin.value()).toJsonString();
        }
        if (number > commodity.getNumber()) {
            return ResultObject.ERROR(ErrorCode.numberError.code(), ErrorCode.numberError.value()).toJsonString();
        }
        Order order = null;
        List<Order> orders = orderService.getOrderByUserIdAndCommodityId(userId, commodityId);
        for (Order order1 : orders) {
            if (order1.getState() == 0) {
                order = order1;
                order.setNumber(order1.getNumber()+number);
                orderService.updateOrder(order);
                JSONObject resultObject = new JSONObject();
                resultObject.put("order", order);
                return ResultObject.SUCCESS(resultObject).toJsonString();
            }
        }

        order = new Order();
        order.setCommodityId(commodityId);
        order.setUserId(userId);
        order.setNumber(number);
        order.setPrice(commodity.getPrice() * number);
        order.setState(0);
        orderService.saveOrder(order);
        JSONObject resultObject = new JSONObject();
        resultObject.put("order", order);
        return ResultObject.SUCCESS(resultObject).toJsonString();
    }

    /**
     * 通过购物车结账
     *
     * @param requestDate
     * @return
     */
    @PostMapping("shoppingCart")
    public String shoppingCart(@RequestBody String requestDate) {
        JSONObject requestObject = JSONObject.parseObject(requestDate);
        String token = requestObject.getString("token");
        JSONArray ids = requestObject.getJSONArray("ids");
        Long userId = requestObject.getLong("userId");
        long addressId = requestObject.getLong("addressId");
        if (!checkToken(token, userId)) {
            return ResultObject.ERROR(ErrorCode.tokenError.code(), ErrorCode.tokenError.value()).toJsonString();
        }
        User user = userService.getUserById(userId);
        long allMoney = 0;
        if (user == null) {
            return ResultObject.ERROR(ErrorCode.notLogin.code(), ErrorCode.notLogin.value()).toJsonString();
        }
        List<Order> orders = new ArrayList<>();
        for(int i = 0 ; i < ids.size() ; i++){
           JSONObject jsonObject =  ids.getJSONObject(i);
           Long id = jsonObject.getLong("id");
           int number = jsonObject.getInteger("number");
           Order order = orderService.getOrderById(id);
           Commodity commodity = commodityService.getCommodityById(order.getCommodityId());
           if(number>commodity.getNumber()){
               return ResultObject.ERROR(ErrorCode.goodNumError.code(), ErrorCode.goodNumError.value()).toJsonString();
           }
            /**
             * 更新订单的数量
             */
            allMoney+=number*commodity.getPrice();
            order.setNumber(number);
            orders.add(order);
        }
        /**
         * 判断用户余额是否够
         */
        if (allMoney > user.getMoney()) {
            return ResultObject.ERROR(ErrorCode.moneyError.code(), ErrorCode.moneyError.value()).toJsonString();
        }
        /**
         * 提交订单
         */
        for (Order order : orders) {
            Commodity commodity = commodityService.getCommodityById(order.getCommodityId());
            int newNumber = commodity.getNumber() - order.getNumber();
            commodity.setNumber(newNumber);
            order.setState(1);
            order.setCreateTime(new Date());
            order.setAddressId(addressId);
            commodityService.updateCommodity(commodity);
            orderService.updateOrder(order);
        }
        user.setMoney(user.getMoney() - allMoney);
        userService.updateUser(user);
        JSONObject resultObject = new JSONObject();
        resultObject.put("isSuccess", true);
        return ResultObject.SUCCESS(resultObject).toJsonString();
    }

    /**
     * 直接购买
     *
     * @return
     */
    @PostMapping("shop")
    public String shopping(@RequestBody String requestData) {
        JSONObject requestObject = JSONObject.parseObject(requestData);
        String token = requestObject.getString("token");
        long commodityId = requestObject.getLong("commodityId");
        int number = requestObject.getIntValue("number");
        long userId = requestObject.getLong("userId");
        long addressId = requestObject.getLong("addressId");
        if (!checkToken(token, userId)) {
            return ResultObject.ERROR(ErrorCode.tokenError.code(), ErrorCode.tokenError.value()).toJsonString();
        }
        Commodity commodity = commodityService.getCommodityById(commodityId);
        User user = userService.getUserById(userId);
        if (number > commodity.getNumber()) {
            return ResultObject.ERROR(ErrorCode.numberError.code(), ErrorCode.numberError.value()).toJsonString();
        }
        if (user == null) {
            return ResultObject.ERROR(ErrorCode.notLogin.code(), ErrorCode.notLogin.value()).toJsonString();
        }
        if (number * commodity.getPrice() > user.getMoney()) {
            return ResultObject.ERROR(ErrorCode.moneyError.code(), ErrorCode.moneyError.value()).toJsonString();
        }
        Order order = new Order();
        order.setUserId(userId);
        order.setCommodityId(commodityId);
        order.setState(1);
        order.setNumber(number);
        order.setPrice(number * commodity.getPrice());
        order.setCreateTime(new Date());
        order.setAddressId(addressId);
        orderService.saveOrder(order);
        commodity.setNumber(commodity.getNumber() - number);
        commodityService.updateCommodity(commodity);
        user.setMoney(user.getMoney() - number * commodity.getPrice());
        userService.updateUser(user);
        JSONObject resultObject = new JSONObject();
        resultObject.put("isSuccess", true);
        return ResultObject.SUCCESS(resultObject).toJsonString();
    }

    /**
     * 检查登录状态
     */
    @PostMapping("checkToken")
    public String checkToken(@RequestBody String requestData) {
        JSONObject requestObject = JSONObject.parseObject(requestData);
        String token = requestObject.getString("token");
        long userId = requestObject.getLong("userId");
        if (!checkToken(token, userId)) {
            return ResultObject.ERROR(ErrorCode.tokenError.code(), ErrorCode.tokenError.value()).toJsonString();
        }
        JSONObject resultObject = new JSONObject();
        resultObject.put("isLogin", true);
        return ResultObject.SUCCESS(resultObject).toJsonString();
    }

    /**
     * 获取工作的种类
     *
     * @param requestData
     * @return
     */
    @PostMapping("getGoodType")
    public String getGoodType(@RequestBody String requestData) {
        List<Type> list = typeService.getAllType();
        JSONObject resultObject = new JSONObject();
        resultObject.put("type", list);
        return ResultObject.SUCCESS(resultObject).toJsonString();
    }

    /**
     * 添加商品
     */
    @PostMapping("addGood")
    public String addGood(@RequestBody String requestData) {
        JSONObject requestObject = JSONObject.parseObject(requestData);
        logger.info(requestData);
        String token = requestObject.getString("token");
        long userId = requestObject.getLong("userId");
        String title = requestObject.getString("title");
        double price = requestObject.getDouble("price")*100;
        String introduce = requestObject.getString("introduce");
        String image = requestObject.getString("image");
        String detailed = requestObject.getString("detailed");
        long type = requestObject.getLong("type");
        int number = requestObject.getInteger("number");
        if (StringUtils.isBlank(title) || StringUtils.isBlank(introduce) || StringUtils.isBlank(image) || StringUtils.isBlank(detailed)) {
            return ResultObject.ERROR(ErrorCode.paramError.code(), ErrorCode.paramError.value()).toJsonString();
        }
        Type type1 = typeService.getTypeById(type);
        if (type1 == null || number <= 0) {
            return ResultObject.ERROR(ErrorCode.paramError.code(), ErrorCode.paramError.value()).toJsonString();
        }
        if (!checkToken(token, userId)) {
            return ResultObject.ERROR(ErrorCode.tokenError.code(), ErrorCode.tokenError.value()).toJsonString();
        }
        Commodity commodity = new Commodity();
        commodity.setNumber(number);
        commodity.setName(title);
        //添加商品时考虑到精度问题，在前端*100
        commodity.setPrice(new Double(price).longValue());
        commodity.setImage(image);
        commodity.setState(0);
        commodity.setDetailed(detailed);
        commodity.setType((int) type);
        commodity.setIntroduce(introduce);
        commodity.setUserId(userId);
        logger.info(commodity.toString());
        commodityService.saveCommodity(commodity);
        JSONObject resultObject = new JSONObject();
        resultObject.put("isSuccess", true);
        return ResultObject.SUCCESS(resultObject).toJsonString();
    }

    @PostMapping("getGoodList")
    public String getGoodList(@RequestBody String requestData) {
        JSONObject requestObject = JSONObject.parseObject(requestData);
        String token = requestObject.getString("token");
        long userId = requestObject.getLong("userId");
        if (!checkToken(token, userId)) {
            return ResultObject.ERROR(ErrorCode.tokenError.code(), ErrorCode.tokenError.value()).toJsonString();
        }
        List<Commodity> commodities = commodityService.getCommodityByUserId(userId);
        JSONObject resultObject = new JSONObject();
        resultObject.put("commodities", commodities);
        return ResultObject.SUCCESS(resultObject).toJsonString();
    }

    /**
     * 删除商品
     */
    @PostMapping("deleteGood")
    public String deleteGood(@RequestBody String requestData) {
        JSONObject requestObject = JSONObject.parseObject(requestData);
        String token = requestObject.getString("token");
        long userId = requestObject.getLong("userId");
        long commodityId = requestObject.getLong("commodityId");
        if (!checkToken(token, userId)) {
            return ResultObject.ERROR(ErrorCode.tokenError.code(), ErrorCode.tokenError.value()).toJsonString();
        }
        boolean isDelete = commodityService.deleteCommodityById(commodityId);
        List<Commodity> commodities = commodityService.getCommodityByUserId(userId);
        JSONObject resultObject = new JSONObject();
        resultObject.put("isDelete", isDelete);
        resultObject.put("commodities", commodities);
        return ResultObject.SUCCESS(resultObject).toJsonString();
    }
    /**
     * 删除商品
     */
    @PostMapping("deleteAddress")
    public String deleteAddress(@RequestBody String requestData) {
        JSONObject requestObject = JSONObject.parseObject(requestData);
        String token = requestObject.getString("token");
        long userId = requestObject.getLong("userId");
        long addressId = requestObject.getLong("addressId");
        if (!checkToken(token, userId)) {
            return ResultObject.ERROR(ErrorCode.tokenError.code(), ErrorCode.tokenError.value()).toJsonString();
        }
        Address address = addressService.getAddressById(addressId);
        if(address != null){
            address.setState(0);
            addressService.update(address);
        }
        List<Address>addresses=addressService.getAddressByUserId(userId);
        List<Address> addressList = new ArrayList<>();
        for(Address address1 :addresses){
            if(address1.getState() != 0){
                addressList.add(address1);
            }
        }
        JSONObject resultObject = new JSONObject();
        resultObject.put("isDelete", true);
        resultObject.put("addresses", addressList);
        return ResultObject.SUCCESS(resultObject).toJsonString();
    }

    /**
     * 通过种类或缺商品
     */
    @PostMapping("getGoodByType")
    public String getGoodByType(@RequestBody String requestData) {
        JSONObject requestObject = JSONObject.parseObject(requestData);
        int type = requestObject.getInteger("type");
        List<Commodity> commodities = commodityService.getCommodityByType(type);
        List<Commodity> newCommodities = new ArrayList<>();
        for (Commodity commodity : commodities) {
            if (commodity.getNumber() <= 0) {
                continue;
            }
            if(commodity.getState()==0 ||commodity.getState()==2){
                continue;
            }
            newCommodities.add(commodity);
        }
        JSONObject resultObject = new JSONObject();
        resultObject.put("commodities", newCommodities);
        Type type1 = typeService.getTypeById((long) type);
        if (type1 == null) {
            resultObject.put("type", "全部商品");
        } else {
            resultObject.put("type", "热门" + type1.getType());
        }

        return ResultObject.SUCCESS(resultObject).toJsonString();
    }

    /**
     * 通过id商品
     */
    @PostMapping("getGoodByTyId")
    public String getGoodByTyId(@RequestBody String requestData) {
        JSONObject requestObject = JSONObject.parseObject(requestData);

        long commodityId = requestObject.getLong("commodityId");

        Commodity commodity = commodityService.getCommodityById(commodityId);
        User user = userService.getUserByIdMast(commodity.getUserId());
        JSONObject resultObject = new JSONObject();
        resultObject.put("commodity", commodity);
        resultObject.put("score", user.getScore());
        return ResultObject.SUCCESS(resultObject).toJsonString();
    }

    /**
     * 获取所有的地址
     *
     * @param requestData
     * @return
     */
    @PostMapping("getAllAddress")
    public String getAllAddress(@RequestBody String requestData) {
        JSONObject requestObject = JSONObject.parseObject(requestData);
        String token = requestObject.getString("token");
        long userId = requestObject.getLong("userId");
        if (!checkToken(token, userId)) {
            return ResultObject.ERROR(ErrorCode.tokenError.code(), ErrorCode.tokenError.value()).toJsonString();
        }
        List<Address> addresses = addressService.getAddressByUserId(userId);
        List<Address> addressList = new ArrayList<>();
        for(Address address :addresses){
            if(address.getState() != 0){
                addressList.add(address);
            }
        }
        JSONObject resultObject = new JSONObject();
        resultObject.put("addresses", addressList);
        return ResultObject.SUCCESS(resultObject).toJsonString();
    }

    /**
     * 添加收货地址
     *
     * @param requestData
     * @return
     */
    @PostMapping("addAddress")
    public String addAddress(@RequestBody String requestData) {
        JSONObject requestObject = JSONObject.parseObject(requestData);
        String token = requestObject.getString("token");
        String name = requestObject.getString("name");
        String tel = requestObject.getString("tel");
        String address = requestObject.getString("adress");
        long userId = requestObject.getLong("userId");
        if (!checkToken(token, userId)) {
            return ResultObject.ERROR(ErrorCode.tokenError.code(), ErrorCode.tokenError.value()).toJsonString();
        }
        if (StringUtils.isBlank(name) || StringUtils.isBlank(tel) || StringUtils.isBlank(address)) {
            return ResultObject.ERROR(ErrorCode.paramError.code(), ErrorCode.paramError.value()).toJsonString();
        }
        List<Address> addresses = addressService.getAddressByUserId(userId);
        if (addresses.size() >= 4) {
            return ResultObject.ERROR(ErrorCode.AddressError.code(), ErrorCode.AddressError.value()).toJsonString();
        }
        Address address1 = new Address();
        address1.setAdress(address);
        address1.setName(name);
        address1.setTel(tel);
        address1.setState(1);
        address1.setUserId(userId);
        addressService.saveAddress(address1);
        JSONObject resultObject = new JSONObject();
        resultObject.put("isSuccess", true);
        return ResultObject.SUCCESS(resultObject).toJsonString();
    }

    /**
     * 获取账单（按照种类）
     *
     * @param requestData
     * @return
     */
    @PostMapping("getOrderByUserId")
    public String getOrderByUserId(@RequestBody String requestData) {
        JSONObject requestObject = JSONObject.parseObject(requestData);
        String token = requestObject.getString("token");
        long userId = requestObject.getLong("userId");
        int state = requestObject.getIntValue("state");
        if (!checkToken(token, userId)) {
            return ResultObject.ERROR(ErrorCode.tokenError.code(), ErrorCode.tokenError.value()).toJsonString();
        }
        List<Order> orders = orderService.getOrderByUserId(userId);
        List<OrderResult> orderResults = new ArrayList<>();
        for (Order order : orders) {
            if(order.getState()==state){
            Commodity commodity = commodityService.getCommodityById(order.getCommodityId());
            Address address = addressService.getAddressById(order.getAddressId());
            orderResults.add(OrderResult.ValueOf(order.getId(), commodity.getImage(), commodity.getName(), commodity.getPrice(), order.getPrice(), order.getNumber(), DateUtil.date2String(order.getCreateTime(), "yyyy-MM-dd HH:mm:ss"), address,commodity.getNumber()));
            }}
        JSONObject resultObject = new JSONObject();
        resultObject.put("orderResults", orderResults);
        return ResultObject.SUCCESS(resultObject).toJsonString();
    }

    /**
     * 获取购物车的内容
     * @param requestData
     * @return
     */
    @PostMapping("getShopCart")
    public String getShopCart(@RequestBody String requestData){
        JSONObject requestObject = JSONObject.parseObject(requestData);
        String token = requestObject.getString("token");
        long userId = requestObject.getLong("userId");
        if (!checkToken(token, userId)) {
            return ResultObject.ERROR(ErrorCode.tokenError.code(), ErrorCode.tokenError.value()).toJsonString();
        }
        List<Order> orders = orderService.getOrderByUserId(userId);
        List<OrderResult>  orderResults= new ArrayList<>();
        for(Order order:orders){
            if(order.getState()==0){
                Commodity commodity = commodityService.getCommodityById(order.getCommodityId());
                Address address = addressService.getAddressById(order.getAddressId());
                orderResults.add(OrderResult.ValueOf(order.getId(), commodity.getImage(), commodity.getName(), commodity.getPrice(), order.getPrice()* order.getNumber(), order.getNumber(), DateUtil.date2String(order.getCreateTime(), "yyyy-MM-dd HH:mm:ss"), address,commodity.getNumber()));
            }
        }
        JSONObject resultObject = new JSONObject();
        resultObject.put("orderResults", orderResults);
        return ResultObject.SUCCESS(resultObject).toJsonString();
    }

    /**
     * 退出登录
     * @return
     */
    @PostMapping("loginOut")
    public String loginOut( @RequestBody String requestData){
        JSONObject requestObject = JSONObject.parseObject(requestData);
        String token = requestObject.getString("token");
        long userId = requestObject.getLong("userId");
        if (!checkToken(token, userId)) {
            return ResultObject.ERROR(ErrorCode.tokenError.code(), ErrorCode.tokenError.value()).toJsonString();
        }
        userService.removeUserById(userId);
        JSONObject resultObject = new JSONObject();
        if(userService.getUserById(userId) == null ){
            resultObject.put("isSuccess", true);
            return ResultObject.SUCCESS(resultObject).toJsonString();
        }
        else {
            resultObject.put("isSuccess", false);
            return ResultObject.SUCCESS(resultObject).toJsonString();
        }
    }

    /**
     * 充值
     * @param requestData
     * @return
     */
    @PostMapping("addMoney")
    public String addMoney( @RequestBody String requestData){
        JSONObject requestObject = JSONObject.parseObject(requestData);
        String token = requestObject.getString("token");
        long userId = requestObject.getLong("userId");
        long money = requestObject.getLong("money");
        if (!checkToken(token, userId)) {
            return ResultObject.ERROR(ErrorCode.tokenError.code(), ErrorCode.tokenError.value()).toJsonString();
        }
       User user =  userService.getUserById(userId);
        user.setMoney(user.getMoney()+money*100);
        userService.updateUser(user);
        JSONObject resultObject = new JSONObject();
        resultObject.put("money", user.getMoney());
        return ResultObject.SUCCESS(resultObject).toJsonString();

    }

    /**
     * 提交个人消息
     * @param requestData
     * @return
     */
    @PostMapping("subPersonMessage")
    public String subPersonMessage(@RequestBody String requestData){
        JSONObject requestObject = JSONObject.parseObject(requestData);
        String token = requestObject.getString("token");
        long userId = requestObject.getLong("userId");
        String nick = requestObject.getString("nick");
        if (!checkToken(token, userId)) {
            return ResultObject.ERROR(ErrorCode.tokenError.code(), ErrorCode.tokenError.value()).toJsonString();
        }

        User user =   userService.getUserById(userId);
        user.setNick(nick);
        userService.updateUser(user);
        JSONObject resultObject = new JSONObject();
        resultObject.put("user", user);
        return ResultObject.SUCCESS(resultObject).toJsonString();

    }

    /**
     * 获取权限
     * @param requestData
     * @return
     */
    @PostMapping("getMyPermission")
    public String getMyPermission(@RequestBody String requestData){
        JSONObject requestObject = JSONObject.parseObject(requestData);
        String token = requestObject.getString("token");
        long userId = requestObject.getLong("userId");
        if (!checkToken(token, userId)) {
            return ResultObject.ERROR(ErrorCode.tokenError.code(), ErrorCode.tokenError.value()).toJsonString();
        }

       List<PermissionUser> permissionUsers =  perMissionUserService.getPermissionUserByUserId(userId);
       List<PermissionsResult> permissionsResultList = new ArrayList<>();
        //取出一级菜单
        for(PermissionUser permissionUser:permissionUsers){
            Permission permission= permissionService.getPermissionServiceById(permissionUser.getPermissionId());
            PermissionsResult permissionsResult = new PermissionsResult();
          if(permission.getGrade()==1){
              permissionsResult.setId(permission.getId());
              permissionsResult.setGrade(permission.getGrade());
              permissionsResult.setUrl(permission.getUrl());
              permissionsResult.setName(permission.getName());
              permissionsResult.setLevel(permission.getLevel());
              permissionsResult.setPermissions(new ArrayList<>());
              permissionsResultList.add(permissionsResult);
          }
        }
        for(PermissionUser permissionUser:permissionUsers){
            Permission permission= permissionService.getPermissionServiceById(permissionUser.getPermissionId());
            if(permission.getGrade()==2){
               for(PermissionsResult permissionsResult:permissionsResultList){
                   if(permissionsResult.getId().equals(permission.getParetId())){
                       permissionsResult.getPermissions().add(permission);
                   }
               }
            }
        }
        //取出二级菜单

        JSONObject resultObject = new JSONObject();
        resultObject.put("permissionsResultList", permissionsResultList);
        return ResultObject.SUCCESS(resultObject).toJsonString();

    }
    /**
     * 获取权限
     * @param requestData
     * @return
     */
    @PostMapping("getAllPermission")
    public String getAllPermission(@RequestBody String requestData){
        JSONObject requestObject = JSONObject.parseObject(requestData);
        String token = requestObject.getString("token");
        long userId = requestObject.getLong("userId");
        if (!checkToken(token, userId)) {
            return ResultObject.ERROR(ErrorCode.tokenError.code(), ErrorCode.tokenError.value()).toJsonString();
        }

        List<PermissionUser> permissionUsers =  perMissionUserService.getPermissionUserByUserId(userId);
        List<Permission> permissions = new ArrayList<>();
        for(PermissionUser permissionUser:permissionUsers){
            Permission permission= permissionService.getPermissionServiceById(permissionUser.getPermissionId());
            permissions.add(permission);

        }
        //取出二级菜单

        JSONObject resultObject = new JSONObject();
        resultObject.put("permissions", permissions);
        return ResultObject.SUCCESS(resultObject).toJsonString();

    }
    /**
     * 获取审核中的商品
     * @param requestData
     * @return
     */
    @PostMapping("getInspectCommodity")
    public String getInspectCommodity(@RequestBody String requestData){
        JSONObject requestObject = JSONObject.parseObject(requestData);
        String token = requestObject.getString("token");
        long userId = requestObject.getLong("userId");
        if (!checkToken(token, userId)) {
            return ResultObject.ERROR(ErrorCode.tokenError.code(), ErrorCode.tokenError.value()).toJsonString();
        }
        List<Commodity> commodities = commodityService.getCommodityByState(0);
        JSONObject resultObject = new JSONObject();
        resultObject.put("commodities", commodities);
        return ResultObject.SUCCESS(resultObject).toJsonString();

    }
    /**
     * 获取审核中的商品
     * @param requestData
     * @return
     */
    @PostMapping("inspectCommodity")
    public String inspectCommodity(@RequestBody String requestData){
        JSONObject requestObject = JSONObject.parseObject(requestData);
        String token = requestObject.getString("token");
        long userId = requestObject.getLong("userId");
        int state = requestObject.getIntValue("state");
        long commodityId = requestObject.getLong("commodityId");
        if (!checkToken(token, userId)) {
            return ResultObject.ERROR(ErrorCode.tokenError.code(), ErrorCode.tokenError.value()).toJsonString();
        }
        Commodity commodity = commodityService.getCommodityById(commodityId);
        commodity.setState(state);
        commodityService.updateCommodity(commodity);
                //返回剩下的商品
        List<Commodity> commodities = commodityService.getCommodityByState(0);
        JSONObject resultObject = new JSONObject();
        resultObject.put("commodities", commodities);
        return ResultObject.SUCCESS(resultObject).toJsonString();

    }

    /**
     * 改变商品状态每次状态改变加一
     * @param requestData
     * @return
     */
    @PostMapping("changeGoodState")
    public String  changeGoodState(@RequestBody String requestData){
        JSONObject requestObject = JSONObject.parseObject(requestData);
        String token = requestObject.getString("token");
        long userId = requestObject.getLong("userId");
        long orderId = requestObject.getLong("orderId");
        int state = requestObject.getIntValue("state");
        if (!checkToken(token, userId)) {
            return ResultObject.ERROR(ErrorCode.tokenError.code(), ErrorCode.tokenError.value()).toJsonString();
        }
        Order order1 =  orderService.getOrderById(orderId);
        if(order1 != null ){
            order1.setState(state+1);
            orderService.updateOrder(order1);
        }
        List<Order> orders = orderService.getOrderByUserIdAndState(userId,state);

        List<OrderResult> orderResults = new ArrayList<>();
        for (Order order : orders) {
            Commodity commodity = commodityService.getCommodityById(order.getCommodityId());
            Address address = addressService.getAddressById(order.getAddressId());
            orderResults.add(OrderResult.ValueOf(order.getId(), commodity.getImage(), commodity.getName(), commodity.getPrice(), order.getPrice(), order.getNumber(), DateUtil.date2String(order.getCreateTime(), "yyyy-MM-dd HH:mm:ss"), address,commodity.getNumber()));
        }
        JSONObject resultObject = new JSONObject();
        resultObject.put("orderResults", orderResults);
        return ResultObject.SUCCESS(resultObject).toJsonString();
    }


    /**
     * 改变商品状态每次状态改变加一
     * @param requestData
     * @return
     */
    @PostMapping("score")
    public String  score(@RequestBody String requestData){
        JSONObject requestObject = JSONObject.parseObject(requestData);
        String token = requestObject.getString("token");
        long userId = requestObject.getLong("userId");
        float score = requestObject.getFloat("score");
        if (!checkToken(token, userId)) {
            return ResultObject.ERROR(ErrorCode.tokenError.code(), ErrorCode.tokenError.value()).toJsonString();
        }
        User user=  userService.getUserById(userId);
        float newScore = (user.getScore()+score)/2;
        user.setScore(newScore);
        userService.updateUser(user);
        JSONObject resultObject = new JSONObject();
        resultObject.put("score", score);
        return ResultObject.SUCCESS(resultObject).toJsonString();
    }

    /**
     * 改变商品状态每次状态改变加一
     * @param requestData
     * @return
     */
    @PostMapping("getAllUser")
    public String  getAllUser(@RequestBody String requestData){
        JSONObject requestObject = JSONObject.parseObject(requestData);
        String token = requestObject.getString("token");
        long userId = requestObject.getLong("userId");
        if (!checkToken(token, userId)) {
            return ResultObject.ERROR(ErrorCode.tokenError.code(), ErrorCode.tokenError.value()).toJsonString();
        }
        List<User> userList = userService.getAllUser();
        userList.sort((o1, o2) -> o2.getComCount() - o1.getComCount());
        JSONObject resultObject = new JSONObject();
        resultObject.put("userList", userList);
        return ResultObject.SUCCESS(resultObject).toJsonString();
    }


    /**
     * 改变商品状态每次状态改变加一
     * @param requestData
     * @return
     */
    @PostMapping("changeUserState")
    public String  changeUserState(@RequestBody String requestData){
        JSONObject requestObject = JSONObject.parseObject(requestData);
        String token = requestObject.getString("token");
        long userId = requestObject.getLong("userId");
        long otherId = requestObject.getLong("otherId");
        int state = requestObject.getIntValue("state");
        if (!checkToken(token, userId)) {
            return ResultObject.ERROR(ErrorCode.tokenError.code(), ErrorCode.tokenError.value()).toJsonString();
        }
        User user =  userService.getUserByIdMast(otherId);
        user.setStatus(state);
        userService.updateUser(user);
        List<User> userList = userService.getAllUser();
        JSONObject resultObject = new JSONObject();
        resultObject.put("userList", userList);
        return ResultObject.SUCCESS(resultObject).toJsonString();
    }

    /**
     * 改变商品状态每次状态改变加一
     * @param requestData
     * @return
     */
    @PostMapping("complaint")
    public String  complaint(@RequestBody String requestData){
        JSONObject requestObject = JSONObject.parseObject(requestData);
        String token = requestObject.getString("token");
        long userId = requestObject.getLong("userId");
        long orderId = requestObject.getLong("orderId");
        String value = requestObject.getString("value");
        if (!checkToken(token, userId)) {
            return ResultObject.ERROR(ErrorCode.tokenError.code(), ErrorCode.tokenError.value()).toJsonString();
        }

        Order order = orderService.getOrderById(orderId);
        Commodity commodity = commodityService.getCommodityById(order.getCommodityId());
        User user = userService.getUserByIdMast(commodity.getUserId());
        Complaint complaint = new Complaint();
        complaint.setCommodityId(commodity.getId());
        complaint.setUserId(user.getId());
        complaint.setText(value);
        complaint.setState(1);
        complaintService.save(complaint);
        JSONObject resultObject = new JSONObject();
        return ResultObject.SUCCESS(resultObject).toJsonString();
    }


    /**
     * 改变商品状态每次状态改变加一
     * @param requestData
     * @return
     */
    @PostMapping("getComplaint")
    public String  getComplaint(@RequestBody String requestData){
        JSONObject requestObject = JSONObject.parseObject(requestData);
        String token = requestObject.getString("token");
        long userId = requestObject.getLong("userId");
        if (!checkToken(token, userId)) {
            return ResultObject.ERROR(ErrorCode.tokenError.code(), ErrorCode.tokenError.value()).toJsonString();
        }
       List<Complaint> complaintList =  complaintService.getAll();
        List<Complaint> complaintList1= new ArrayList<>();
        for(Complaint complaint:complaintList){
            if(complaint.getState()==1){
                complaintList1.add(complaint);
            }
        }
        List<Map<String,Object>> list = new ArrayList<>();
        for(Complaint complaint:complaintList1){
            Map<String,Object> map = new HashMap<>();
            Commodity commodity = commodityService.getCommodityById(complaint.getCommodityId());
            map.put("commodityName",commodity.getName());
            User user = userService.getUserByIdMast(complaint.getUserId());
            map.put("userName",user.getNick());
            map.put("text",complaint.getText());
            map.put("id",complaint.getId());
            list.add(map);
        }
        JSONObject resultObject = new JSONObject();
        resultObject.put("complaintList",list);
        return ResultObject.SUCCESS(resultObject).toJsonString();
    }


    /**
     * 改变商品状态每次状态改变加一
     * @param requestData
     * @return
     */
    @PostMapping("doComplaint")
    public String  doComplaint(@RequestBody String requestData){
        JSONObject requestObject = JSONObject.parseObject(requestData);
        String token = requestObject.getString("token");
        int state = requestObject.getIntValue("state");
        long complaintId = requestObject.getLong("complaintId");
        long userId = requestObject.getLong("userId");
        if (!checkToken(token, userId)) {
            return ResultObject.ERROR(ErrorCode.tokenError.code(), ErrorCode.tokenError.value()).toJsonString();
        }

        Complaint complaint1 = complaintService.getComplanitById(complaintId);
        complaint1.setState(state);
        complaintService.update(complaint1);
        List<Complaint> complaintList =  complaintService.getAll();
        List<Complaint> complaintList1= new ArrayList<>();
        for(Complaint complaint:complaintList){
            if(complaint.getState()==1){
                complaintList1.add(complaint);
            }
        }
        List<Map<String,Object>> list = new ArrayList<>();
        for(Complaint complaint:complaintList1){
            Map<String,Object> map = new HashMap<>();
            Commodity commodity = commodityService.getCommodityById(complaint.getCommodityId());
            map.put("commodityName",commodity.getName());
            User user = userService.getUserByIdMast(complaint.getUserId());
            map.put("userName",user.getNick());
            map.put("text",complaint.getText());
            map.put("id",complaint.getId());
            list.add(map);
        }
        JSONObject resultObject = new JSONObject();
        resultObject.put("complaintList",list);
        return ResultObject.SUCCESS(resultObject).toJsonString();
    }
    /**
     * 图片上传
     *
     * @param file
     * @return
     */

    @PostMapping("imgup")
    public String imgUP(MultipartFile file) {
        String folder = "D:/workspace/img";
        File imageFolder = new File(folder);
        File f = new File(imageFolder, ImgUp.getRandomString(6) + file.getOriginalFilename()
                .substring(file.getOriginalFilename().length() - 4));
        if (!f.getParentFile().exists())
            f.getParentFile().mkdirs();
        try {
            file.transferTo(f);
            String imgURL = "http://localhost:8082/api/file/" + f.getName();
            return imgURL;
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }

    }
}
