package com.supets.pet.bus;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * LedView
 *
 * @user lihongjiang
 * @description
 * @date 2017/8/23
 * @updatetime 2017/8/23
 */

public class BusUtils {

    public static void main(String[] args) {
        String url = "http://map.baidu.com/mobile/webapp/search/search/qt=inf&uid=229abe20df4728442ad7ab79/?third_party=webapp-aladdin";
        getListPic(url);
    }

    public static List<BusInfo> getListPic(String url) {
        List<BusInfo> map = new ArrayList<>();
        Document doc;
        try {
            doc = Jsoup.connect(url).get();
            Elements ListDiv = doc.getElementsByAttributeValue("class", "bustop-list-info");
            for (Element element : ListDiv) {

                BusInfo info = new BusInfo();

                Elements links = element.getElementsByAttributeValue("class", "list-icon detail-line-no -ft-small");
                for (Element element1 : links) {
                    info.setLineNumber(element1.text().trim());
                }
                Elements links2 = element.getElementsByAttributeValue("class", "-ft-middle linename");
                for (Element element1 : links2) {
                    info.setName(element1.text().trim());
                }
                Elements links3 = element.getElementsByAttributeValue("class", "icon -bus rticon");
                for (Element element1 : links3) {
                    info.setStop(true);
                }
                map.add(info);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

}
