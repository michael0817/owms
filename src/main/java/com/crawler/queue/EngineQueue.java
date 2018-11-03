package com.crawler.queue;

import com.crawler.model.CrawlerAction;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @author xumx
 * @date 2018/10/19
 */
public class EngineQueue {

    public static BlockingQueue<CrawlerAction> CRAWLER_ACTION_QUEUE =
            new ArrayBlockingQueue<CrawlerAction>(1000);


}
