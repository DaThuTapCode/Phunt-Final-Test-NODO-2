package com.trongphu.finalintern2.mapper;

import java.util.List;

/**
 * Created by Trong Phu on 17/09/2024 22:56
 *
 * @author Trong Phu
 */
public interface BaseMapper <E, D>{
    D toDTO (E e);
    List<D> toListDTO(List<E> eList);

    E toEntity (D d);
    List<D> toListEntity(List<D> dList);
}
