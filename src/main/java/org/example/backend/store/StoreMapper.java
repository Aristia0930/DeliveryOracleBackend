package org.example.backend.store;

import org.example.backend.comments.dto.CommentsVo;
import org.example.backend.store.dto.ReportsVo;
import org.example.backend.store.dto.StoreInformationVo;
import org.example.backend.store.dto.StoreOrderInformationVo;
import org.example.backend.store.dto.StoreRegistrationVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StoreMapper {

    int storeInsert(StoreRegistrationVo storeRegistrationVo);

    int approveR(int id);

    int menuRs(StoreInformationVo storeInformationVo);

    List<StoreInformationVo> menuList(int id);

    int menuedit(StoreInformationVo storeInformationVo);

    int menuedit2(StoreInformationVo storeInformationVo);

    int menudel(int id, String name);

    List<StoreOrderInformationVo> order(int id);

    int cook(int id);

    int rider(int id);

    int refuse(int id);

    List<StoreOrderInformationVo> orderReceipt(int storeId);

    List<StoreOrderInformationVo> orderSalesInfo(int storeId);

    StoreRegistrationVo storeInfo(int id);

    int storeEditImg(StoreRegistrationVo storeRegistrationVo);

    int storeEdit(StoreRegistrationVo storeRegistrationVo);

    int storeDelete(int storeId);

    List<CommentsVo> commentList(int id);

    int report(ReportsVo reportsVo);

    int reportOrder(int id);

    int exist(int id);

    StoreOrderInformationVo orderUserId(int id);
}
