package com.example.user.myhomejarvis.Gson_package;

import com.example.user.myhomejarvis.Data_Info_package.UserInfoVO;

import java.io.Serializable;

/**
 * Created by user on 2018-03-21.
 */

public class GsonResponse_Login implements Serializable {

    private String event;
    private String status;
    private UserInfoVO vo;



    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public UserInfoVO getVo() {
        return vo;
    }

    public void setVo(UserInfoVO vo) {
        this.vo = vo;
    }
}
