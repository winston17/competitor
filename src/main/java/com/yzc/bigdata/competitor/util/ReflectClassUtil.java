package com.yzc.bigdata.competitor.util;

import com.yzc.bigdata.competitor.model.BCComNameLikeModel;
import com.yzc.bigdata.competitor.model.BCCompanyContactModel;
import com.yzc.bigdata.competitor.model.BCQueryCompeteRecordDto;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * @Classname ReflectClassUtil
 * @Author lizonghuan
 * @Description 反射更改对象的属性,将String中的null值去掉, Integer中的null值改为0
 * @Date-Time 2019/7/5-17:21
 * @Version 1.0
 */
public class ReflectClassUtil<T> {
    //返回转换null值的对象, String类型null转为""， Integer类型null转为0
    public T changeNullAttributeObject(T t){
        if (t == null){
            return null;
        }
        Class tClass = t.getClass();
        Field[] fields = tClass.getDeclaredFields();
        String attributeName, methodName, stringAttributeValue;
        Type type;
        Method getMethodMethod, setMethodMethod;

        for(int i = 0; i < fields.length; i++){
            attributeName = fields[i].getName();
            type = fields[i].getGenericType();
            methodName = attributeName.substring(0, 1).toUpperCase()
                    + attributeName.substring(1);
            try{
                if (type == String.class){
                    setMethodMethod = tClass.getMethod("set" + methodName, String.class);
                    getMethodMethod = tClass.getMethod("get" + methodName, null);
                    stringAttributeValue = String.valueOf(getMethodMethod.invoke(t));
                    if ("null".equals(stringAttributeValue)){
                        setMethodMethod.invoke(t, "");
                    }
                }else if (type == Integer.class){
                    setMethodMethod = tClass.getMethod("set" + methodName, Integer.class);
                    getMethodMethod = tClass.getMethod("get" + methodName, null);
                    stringAttributeValue = String.valueOf(getMethodMethod.invoke(t));
                    if (stringAttributeValue == null){
                        setMethodMethod.invoke(t, 0);
                    }
                }
            }catch(Exception e){
                e.printStackTrace();
                continue;
            }
        }
        return t;
    }

    //对对象列表每一个元素进行转换null值操作
    public List<T> changeNullAttributeObjectList(List<T> tList){
        if (tList == null){
            return null;
        }
        List<T> resultList = new ArrayList<>();
        for(T t : tList){
            resultList.add(changeNullAttributeObject(t));
        }
        return resultList;
    }

    public static void main(String[] args){
        BCCompanyContactModel bcCompanyContactModel = new BCCompanyContactModel(
                "安徽", "null", "123");
        BCComNameLikeModel bcComNameLikeModel = new BCComNameLikeModel(null, 0);
        BCQueryCompeteRecordDto bcQueryCompeteRecordDto = new BCQueryCompeteRecordDto(
                "null", null, 1, null, "desc");

        System.out.println(bcQueryCompeteRecordDto);
        ReflectClassUtil<BCQueryCompeteRecordDto> reflectClassUtil = new ReflectClassUtil<>();
        bcQueryCompeteRecordDto = reflectClassUtil.changeNullAttributeObject(bcQueryCompeteRecordDto);
        System.out.println(bcQueryCompeteRecordDto);
    }


}
