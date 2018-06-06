package by.bsu.rfe.smsservice.common.pagination;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class ChunkRequest implements Pageable {

    private int limit = 0;
    private int offset = 0;
    private Sort sort;

    public ChunkRequest(int skip, int offset, Sort sort) {
        if (skip < 0)
            throw new IllegalArgumentException("Skip must not be less than zero!");

        if (offset < 0)
            throw new IllegalArgumentException("Offset must not be less than zero!");

        this.limit = offset;
        this.offset = skip;
        this.sort = sort;
    }

    public ChunkRequest() {
    }

    @Override
    public int getPageNumber() {
        return 0;
    }

    @Override
    public int getPageSize() {
        return limit;
    }

    @Override
    public int getOffset() {
        return offset;
    }

    @Override
    public Sort getSort() {
        return sort;
    }

    @Override
    public Pageable next() {
        return null;
    }

    @Override
    public Pageable previousOrFirst() {
        return this;
    }

    @Override
    public Pageable first() {
        return this;
    }

    @Override
    public boolean hasPrevious() {
        return false;
    }
}