package com.yzc.bigdata.competitor.util;

import com.yzc.bigdata.competitor.model.EsWinCandidateModel;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * @Classname SerializeUtil
 * @Author lizonghuan
 * @Description 序列化对象 对象必须实现Serializable
 * @Date-Time 2019/6/20-18:10
 * @Version 1.0
 */
public class SerializeUtil {

    public static String serialize(Object obj) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream;
        objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(obj);
        String string = byteArrayOutputStream.toString("ISO-8859-1");
        objectOutputStream.close();
        byteArrayOutputStream.close();
        return string;
    }
    public static Object serializeToObject(String str) throws IOException, ClassNotFoundException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(str.getBytes("ISO-8859-1"));
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        Object object = objectInputStream.readObject();
        objectInputStream.close();
        byteArrayInputStream.close();
        return object;
    }


    public static void main(String[] args) throws Exception{

        EsWinCandidateModel esWinCandidateModel = new EsWinCandidateModel("公司","集团",3.0,1,2, "", "", "");
        String str = serialize(esWinCandidateModel);
        System.out.println(str);
        System.out.println(1);
        System.out.println(serializeToObject(str));

    }
}
