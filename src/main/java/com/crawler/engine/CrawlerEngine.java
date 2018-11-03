package com.crawler.engine;

import org.springframework.boot.ApplicationRunner;

/**
 * @author xumx
 * @date 2018/10/19
 */
public interface CrawlerEngine extends ApplicationRunner {

    public boolean start();

    public boolean stop();

    public boolean restart();

    public boolean status();
}
