package org.example.backend.service.search;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.backend.comments.dto.CommentsVo;
import org.example.backend.service.OrderListVo;
import org.example.backend.service.OrderVo;
import org.example.backend.store.dto.StoreInformationVo;
import org.example.backend.store.dto.StoreRegistrationVo;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface SearchMapper {

    List<StoreRegistrationVo> storeList(@Param("num") String num, @Param("x") BigDecimal x, @Param("y") BigDecimal y);

    List<StoreInformationVo> menuList(@Param("id") int id);

    int order(OrderVo orderVo);

    String email(@Param("id") int id);

    String emailTrue(@Param("id") int id);

    List<OrderListVo> getUserOrders(@Param("userId") int userId);

    List<StoreRegistrationVo> storeList2(@Param("x") BigDecimal x, @Param("y") BigDecimal y, @Param("word") String word);

    List<StoreRegistrationVo> storeList3(@Param("x") BigDecimal x, @Param("y") BigDecimal y, @Param("word") String word);

    List<CommentsVo> review(@Param("id") int id);
}
