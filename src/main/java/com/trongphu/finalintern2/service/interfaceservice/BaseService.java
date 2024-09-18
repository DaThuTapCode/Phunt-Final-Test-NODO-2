package com.trongphu.finalintern2.service.interfaceservice;

import java.util.List;

/**
 * Created by Trong Phu on 17/09/2024 22:58
 *
 * @author Trong Phu
 */
public interface BaseService <E, ID, RQ, RP>{

    /**
     * @return toàn bộ danh sách đối tượng
     * */
    List<RP> getAll();

    /**
     * @param id id của đối tượng cần tìm
     * @return đối tượng theo id
     * */
    RP getById(ID id);

    RP getByCode(String code);

    /**
     * Thêm 1 entity mới*/
    RP create (RQ rq);

    /**
     * Update entity theo id*/
    RP update (Long id, RQ rq);

}
