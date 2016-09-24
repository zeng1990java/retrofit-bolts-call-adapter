package com.github.zeng1990java.retrofit2.adapter.bolts.sample.model;

import java.util.List;

/**
 * Created by Administrator on 2016/9/24.
 */
public class GankList {
    private boolean error;
    private List<Gank> results;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<Gank> getResults() {
        return results;
    }

    public void setResults(List<Gank> results) {
        this.results = results;
    }
}
