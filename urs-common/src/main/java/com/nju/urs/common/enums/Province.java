package com.nju.urs.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Province {

// 11       北京市          43       湖南省
// 12       天津市          44       广东省
// 13       河北省          45       广西壮族自治区
// 14       山西省          46       海南省
// 15       内蒙古自治区     50       重庆市
// 21       辽宁省          51       四川省
// 22       吉林省          52       贵州省
// 23       黑龙江省        53       云南省
// 31       上海市          54       西藏自治区
// 32       江苏省          61       陕西省
// 33       浙江省          62       甘肃省
// 34       安徽省          63       青海省
// 35       福建省          64       宁夏回族自治区
// 36       江西省          65       新疆维吾尔自治区
// 37       山东省          71       台湾省
// 41       河南省          81       香港特别行政区
// 42       湖北省          82       澳门特别行政区
    // 定义所有省份的枚举常量
    BEIJING(11, "北京"),
    TIANJIN(12, "天津"),
    HEBEI(13, "河北"),
    SHANXI(14, "山西"),
    NEIMENGGU(15, "内蒙古"),
    LIAONING(21, "辽宁"),
    JILIN(22, "吉林"),
    HEILONGJIANG(23, "黑龙江"),
    SHANGHAI(31, "上海"),
    JIANGSU(32, "江苏"),
    ZHEJIANG(33, "浙江"),
    ANHUI(34, "安徽"),
    FUJIAN(35, "福建"),
    JIANGXI(36, "江西"),
    SHANDONG(37, "山东"),
    HENAN(41, "河南"),
    HUBEI(42, "湖北"),
    HUNAN(43, "湖南"),
    GUANGDONG(44, "广东"),
    GUANGXI(45, "广西"),
    HAINAN(46, "海南"),
    CHONGQING(50, "重庆"),
    SICHUAN(51, "四川"),
    GUIZHOU(52, "贵州"),
    YUNNAN(53, "云南"),
    XIZANG(54, "西藏"),
    SHANXI_XX(61, "陕西"),
    GANSU(62, "甘肃"),
    QINGHAI(63, "青海"),
    NINGXIA(64, "宁夏"),
    XINJIANG(65, "新疆"),
    TAIWAN(71, "台湾"),
    HONGKONG(81, "香港"),
    MACAO(82, "澳门");

    private final int id;
    private final String name;

    public static String getNameById(int id) {
        for (Province province : Province.values()) {
            if (province.getId() == id) {
                return province.name;
            }
        }
        return null;
    }

    public static int getIdByName(String name) {
        for (Province province : Province.values()) {
            if (province.getName().equals(name)) {
                return province.id;
            }
        }
        return -1;
    }
}
