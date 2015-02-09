package com.github.yablonski.majordom.source;

/**
 * Created by Acer on 25.11.2014.
 */
public interface DataSource<Result, Params> {

    Result getResult(Params params) throws Exception;

}