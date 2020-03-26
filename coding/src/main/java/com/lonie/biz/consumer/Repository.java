package com.lonie.biz.consumer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author huzeming Created time 2020/3/26 : 10:57 上午 Desc:
 */

public class Repository {

    private List<String> goodList = new ArrayList<>(5);

    public boolean in(String good) {
        if (goodList.size() == 5) {
            return false;
        }
        goodList.add(good);

        System.out.println("rep add good:" + good);

        return true;
    }

    public String out() {
        if (goodList.size() == 0) {
            System.out.println("rep no good");
            return null;
        }
        String good = goodList.get(0);
        //System.out.println("rep out good:" + good);

        goodList.remove(0);
        return good;
    }

}
