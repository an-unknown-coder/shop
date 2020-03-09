package com.qf.dto;

import java.io.Serializable;

public class ResultBean implements Serializable {

    private static final long serialVersionUID = 42L;




    //结果的状态码：1表示返回的是错误的信息，0表示返回的是正确（正常）的信息
    private int errno;

    //结果的数据
    private Object data;

    //具体的信息
    private String message;


    /**
     * 返回成功的结果
     * @return
     */
    public static ResultBean success(){
        ResultBean resultBean = new ResultBean();
        resultBean.setErrno(0);
        resultBean.setData(null);
        resultBean.setMessage("success");
        return  resultBean;

    }

    /**
     * 返回成功的结果
     * @param message 具体的信息
     * @return
     */
    public static ResultBean success(String message){
        ResultBean resultBean = new ResultBean();
        resultBean.setErrno(0);
        resultBean.setData(null);
        resultBean.setMessage(message);
        return  resultBean;

    }


    /**
     * 返回成功的结果
     * @param data 具体的数据
     * @return
     */
    public static ResultBean success(Object data){
        ResultBean resultBean = new ResultBean();
        resultBean.setErrno(0);
        resultBean.setData(data);
        resultBean.setMessage("success");
        return  resultBean;

    }

    /**
     * 返回成功的结果
     * @param data 具体的数据
     * @param message 具体的信息
     * @return
     */
    public static ResultBean success(Object data, String message){
        ResultBean resultBean = new ResultBean();
        resultBean.setErrno(0);
        resultBean.setData(data);
        resultBean.setMessage(message);
        return  resultBean;

    }

    /**
     * 返回失败的结果
     * @return
     */
    public static ResultBean error(){
        ResultBean resultBean = new ResultBean();
        resultBean.setErrno(1);
        resultBean.setData(null);
        resultBean.setMessage("error");
        return  resultBean;

    }



    /**
     * 返回成功的结果
     * @param message 具体的信息
     * @return
     */
    public static ResultBean error(String message){
        ResultBean resultBean = new ResultBean();
        resultBean.setErrno(1);
        resultBean.setData(null);
        resultBean.setMessage(message);
        return  resultBean;

    }





    public int getErrno() {
        return errno;
    }

    public void setErrno(int errno) {
        this.errno = errno;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
