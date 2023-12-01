package com.example.talk.common;

import java.io.Serializable;

/**
 * 更新状态请求对象
 *
 * @author iumyxF
 * @date 2023/3/10 20:36
 */
public class UpdateStatusRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
