package com.qmlx.usercenter.utils;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.Collectors;

public class RandomUtils {
    public static final String[] PROGRAMMING_SKILLS = {
            "Java", "Python", "C++", "JavaScript", "HTML/CSS",
            "数据结构与算法", "机器学习", "深度学习", "人工智能", "数据库管理"
    };

    // 教育与学习标签
    public static final String[] EDUCATION_AND_STUDY = {
            "考研", "高考", "四六级", "托福", "雅思",
            "GRE", "专业牛逼", "选修牛逼", "论文写作", "学术研究"
    };

    // 学生生活与年级标签
    public static final String[] STUDENT_LIFE_AND_GRADE = {
            "大一", "大二", "大三", "大四", "研究生",
            "高三", "高二", "高一", "小学生", "博士生"
    };

    // 兴趣爱好与娱乐标签
    public static final String[] HOBBIES_AND_ENTERTAINMENT = {
            "旅行", "音乐", "电影", "摄影", "读书",
            "健身", "游泳", "篮球", "足球", "美食"
    };

    // 职业与行业标签
    public static final String[] CAREER_AND_INDUSTRY = {
            "互联网", "金融", "教育", "医疗", "电商",
            "市场营销", "人力资源管理", "项目管理", "创业", "咨询+v"
    };
    public static final String[] GENDER = {
            "男", "女", "未知"
    };

    public static String GetRandomTags() {
        // 假设这些是之前定义的标签数组
        // 编程与技能标签
        // 创建一个新的数组来存储拼接后的标签
        String[] concatenatedTags = new String[6];
        Random random = new Random();
        // 从每个类别中取一个标签并放入新数组
        concatenatedTags[0] = PROGRAMMING_SKILLS[random.nextInt(10)]; // 假设数组不为空
        concatenatedTags[1] = EDUCATION_AND_STUDY[random.nextInt(10)];
        concatenatedTags[2] = STUDENT_LIFE_AND_GRADE[random.nextInt(10)];
        concatenatedTags[3] = HOBBIES_AND_ENTERTAINMENT[random.nextInt(10)];
        concatenatedTags[4] = CAREER_AND_INDUSTRY[random.nextInt(10)];
        concatenatedTags[5]=GENDER[random.nextInt(3)];

        // 将拼接后的标签数组转换为指定格式的字符串
        String formattedTags = "[" + Arrays.stream(concatenatedTags).map(tag -> "\"" + tag + "\"").collect(Collectors.joining(",")) + "]";
        return formattedTags;
    }


    public static String getRandomPhone(){
        Random random = new Random();
        int firstDigit = random.nextInt(8) + 1; // 第一位数字通常不是0或1（取决于具体电话号码规则）
        int secondToSeventhDigits = random.nextInt(900000000); // 剩下的6位数字

        // 组合成电话号码
        String phoneNumber = firstDigit + String.format("%06d", secondToSeventhDigits);

       return phoneNumber;
    }

}
