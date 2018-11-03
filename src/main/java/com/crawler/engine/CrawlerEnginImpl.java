package com.crawler.engine;

import org.springframework.boot.ApplicationArguments;

/**
 * @author xumx
 * @date 2018/10/19
 */
public class CrawlerEnginImpl implements CrawlerEngine {

    @Override
    public boolean start() {
        return false;
    }

    @Override
    public boolean stop() {
        return false;
    }

    @Override
    public boolean restart() {
        return false;
    }

    @Override
    public boolean status() {
        return false;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        this.start();
    }
}
