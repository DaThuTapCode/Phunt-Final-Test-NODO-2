package com.trongphu.finalintern2.objectutil;

import com.trongphu.finalintern2.exception.InvalidArgumentException;
import lombok.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

/**
 * Created by Trong Phu on 18/09/2024 11:24
 * <br>
 * Đối tượng hỗ trợ phân trang
 *
 * @author Trong Phu
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaginationObject {
    private int page;
    private int size;
    private String sortBy;
    private String direction;

    private void validate() {
        if (this.page < 0) {
            throw new InvalidArgumentException("exception.InvalidArgumentException", "page", page + "");
        }
        if (size <= 0) {
            throw new InvalidArgumentException("exception.InvalidArgumentException", "size", size + "");
        }
    }

    public Pageable toPageable() {
        validate();
        if(sortBy == null){
            return PageRequest.of(page, size);
        }
        return null;
    }
}
