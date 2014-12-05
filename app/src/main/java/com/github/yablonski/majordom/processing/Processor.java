package com.github.yablonski.majordom.processing;

/**
 * Created by Acer on 25.11.2014.
 */
public interface Processor<ProcessingResult, Source> {

    ProcessingResult process(Source source) throws Exception;

}