/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bizstudio.utils;

/**
 *
 * @author obinna.asuzu
 */
public class Page {

    private final int pageNo;
    private final int pageSize;

    private Page(int pageNo, int pageSize) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }

    public static Page of(int pageNo, int pageSize) {
        return new Page(pageNo, pageSize);
    }

    /**
     * @return the pageNo
     */
    public int getPageNo() {
        return pageNo;
    }

    /**
     * @return the pageSize
     */
    public int getPageSize() {
        return pageSize;
    }

}
