package com.stylefeng.guns.test.jsonToObject;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Test {




//    一、java普通对象和json字符串的互转


    /**
     * 对象--字符串
     */
    public static void convertObject() {

        Student stu = new Student();
        stu.setName("JSON");
        stu.setAge("23");
        stu.setAddress("北京市西城区");

        //1、使用JSONObject
        JSONObject json = JSONObject.fromObject(stu);
        //2、使用JSONArray
        JSONArray array = JSONArray.fromObject(stu);

        String strJson = json.toString();
        String strArray = array.toString();

        System.out.println("strJson:" + strJson);
        System.out.println("strArray:" + strArray);
    }

    /**
     * 字符串--对象
     */
    public static void jsonStrToJava() {
        //定义两种不同格式的字符串
        String objectStr = "{\"name\":\"JSON\",\"age\":\"24\",\"address\":\"北京市西城区\"}";
        String arrayStr = "[{\"name\":\"JSON\",\"age\":\"24\",\"address\":\"北京市西城区\"}]";

        //1、使用JSONObject
        JSONObject jsonObject = JSONObject.fromObject(objectStr);
        Student stu = (Student) JSONObject.toBean(jsonObject, Student.class);

        //2、使用JSONArray
        JSONArray jsonArray = JSONArray.fromObject(arrayStr);
        //获得jsonArray的第一个元素
        Object o = jsonArray.get(0);
        JSONObject jsonObject2 = JSONObject.fromObject(o);
        Student stu2 = (Student) JSONObject.toBean(jsonObject2, Student.class);
        System.out.println("stu:" + stu);
        System.out.println("stu2:" + stu2);

    }



//    二、list和json字符串的互转



    /**
     * list--》》json字符串
     */
    public static void listToJSON() {
        Student stu = new Student();
        stu.setName("JSON");
        stu.setAge("23");
        stu.setAddress("北京市海淀区");
        List<Student> lists = new ArrayList<Student>();
        lists.add(stu);
        //1、使用JSONObject
        //JSONObject listObject=JSONObject.fromObject(lists);
        //2、使用JSONArray
        JSONArray listArray = JSONArray.fromObject(lists);

        //System.out.println("listObject:"+listObject.toString());
        System.out.println("listArray:" + listArray.toString());

    }

    /**
     * json字符串--》》list
     */
    public static void jsonToList(){
        String arrayStr="[{\"name\":\"JSON\",\"age\":\"24\",\"address\":\"北京市西城区\"}]";
        //转化为list
        List<Student> list2=(List<Student>)JSONArray.toList(JSONArray.fromObject(arrayStr), Student.class);

        for (Student stu : list2) {
            System.out.println(stu);
        }
        //转化为数组
        Student[] ss =(Student[])JSONArray.toArray(JSONArray.fromObject(arrayStr),Student.class);
        for (Student student : ss) {
            System.out.println(student);
        }

    }


//    三、map和json字符串的互转


    public static void mapToJSON(){
        Student stu=new Student();
        stu.setName("JSON");
        stu.setAge("23");
        stu.setAddress("中国上海");
        Map<String,Student> map=new HashMap<String,Student>();
        map.put("first", stu);

        //1、JSONObject
        JSONObject mapObject=JSONObject.fromObject(map);
        System.out.println("mapObject"+mapObject.toString());

        //2、JSONArray
        JSONArray mapArray=JSONArray.fromObject(map);
        System.out.println("mapArray:"+mapArray.toString());
    }



    public static void jsonToMap(){
        String strObject="{\"first\":{\"address\":\"中国上海\",\"age\":\"23\",\"name\":\"JSON\"}}";

        //JSONObject
        JSONObject jsonObject=JSONObject.fromObject(strObject);
        Map map=new HashMap();
        map.put("first", Student.class);

        //使用了toBean方法，需要三个参数
        MyBean my=(MyBean)JSONObject.toBean(jsonObject, MyBean.class, map);
        System.out.println(my.getFirst());

    }


    public static void main(String[] args) {
        jsonToMap();
    }
}
