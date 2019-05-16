/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bizstudio.utils;

import java.util.List;

/**
 *
 * @author obinna.asuzu
 */
public class Page<T> {

    private int totalPages;
    private long totalElements;
    private int number;
    private int size;
    private List<T> content;
    
    

    public Page(Pagination pagination, List<T> content) {
        this.number = pagination.pageNo;
        this.size = pagination.pageSize;
        this.totalElements = pagination.totalElement;
        this.totalPages = (int) ((totalElements / size) + (totalElements % size));
        this.content = content;
    }

    /**
     * Returns whether the {@link Slice} has content at all.
     *
     * @return
     */
    public boolean hasContent() {
        return !content.isEmpty();
    }

    /**
     * Returns whether the current {@link Slice} is the first one.
     *
     * @return
     */
    public boolean isFirst() {
        return getNumber() == 0;
    }

    ;

    /**
     * Returns whether the current {@link Slice} is the last one.
     *
     * @return
     */
    public boolean isLast() {
        return getNumber() + 1 == getTotalPages();
    }

    /**
     * Returns if there is a next {@link Slice}.
     *
     * @return if there is a next {@link Slice}.
     */
    public boolean hasNext() {
        return !isLast();
    }

    /**
     * Returns if there is a previous {@link Slice}.
     *
     * @return if there is a previous {@link Slice}.
     */
    public boolean hasPrevious() {
        return !isFirst();
    }

    /**
     * Returns the {@link Pageable} that's been used to request the current
     * {@link Slice}.
     *
     * @return
     * @since 2.0
     */
    public PageRequest getPageable() {
        return PageRequest.of(getNumber(), getSize(), getTotalElements());
    }

    /**
     * Returns the {@link Pageable} to request the next {@link Slice}. Can be
     * {@literal null} in case the current {@link Slice} is already the last
     * one. Clients should check {@link #hasNext()} before calling this method
     * to make sure they receive a non-{@literal null} value.
     *
     * @return
     */
    public PageRequest nextPageable() {
        if (hasNext()) {
            return PageRequest.of(getNumber() + 1, getSize(), getTotalElements());
        }
        return null;
    }
    
    public PageRequest nextPageable(int next) {
        return PageRequest.of(getNumber() + next, getSize(), getTotalElements());
    }

    /**
     * Returns the {@link Pageable} to request the previous {@link Slice}. Can
     * be {@literal null} in case the current {@link Slice} is already the first
     * one. Clients should check {@link #hasPrevious()} before calling this
     * method make sure receive a non-{@literal null} value.
     *
     * @return
     */
    PageRequest previousPageable() {
        if (hasPrevious()) {
            return PageRequest.of(getNumber() - 1, getSize(), getTotalElements());
        }
        return null;
    }
    
    PageRequest previousPageable(int prev) {
        if (hasPrevious()) {
            return PageRequest.of(getNumber() - prev, getSize(), getTotalElements());
        }
        return null;
    }

    /**
     * @return the totalPages
     */
    public int getTotalPages() {
        return totalPages;
    }

    /**
     * @return the totalElements
     */
    public long getTotalElements() {
        return totalElements;
    }

    /**
     * @return the number
     */
    public int getNumber() {
        return number;
    }

    /**
     * @return the size
     */
    public int getSize() {
        return size;
    }

    /**
     * @return the content
     */
    public List<T> getContent() {
        return content;
    }

    

    public static class Pagination {

        private int pageNo;
        private int pageSize;
        private Long totalElement;
    }

    public static class PageRequest {

        private final int pageNo;
        private final int pageSize;
        private final Long totalElement;

        private PageRequest(int pageNo, int pageSize) {
            this.pageNo = pageNo;
            this.pageSize = pageSize;
            this.totalElement = null;
        }

        private PageRequest(int pageNo, int pageSize, Long totalElement) {
            this.pageNo = pageNo;
            this.pageSize = pageSize;
            this.totalElement = totalElement;
        }

        public static PageRequest of(int pageNo, int pageSize) {
            return new PageRequest(pageNo, pageSize);
        }

        public static PageRequest of(int pageNo, int pageSize, Long totalElement) {
            return new PageRequest(pageNo, pageSize, totalElement);
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

        /**
         * @return the totalElement
         */
        public Long getTotalElement() {
            return totalElement;
        }

        public Pagination getPagination(JpaRepository repository) {
            Pagination pagination = new Pagination();
            pagination.pageNo = PageRequest.this.getPageNo();
            pagination.pageSize = PageRequest.this.getPageSize();
            pagination.totalElement = repository.getCount();
            return pagination;
        }
    }
}
