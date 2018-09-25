package com.forezp.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.io.*;

/**
 * @author gongxb
 * @date 2018/9/14
 * @desc
 * @return
 */
public class MyRedisObjectSerializer implements RedisSerializer<Object> {
    static final byte[] EMPTY_BYTES = new byte[0];
    static final Logger logger= LoggerFactory.getLogger(MyRedisObjectSerializer.class);

    @Override
    public byte[] serialize(Object o) throws SerializationException  {
        if (null == o) {
            return EMPTY_BYTES;
        }
        ObjectOutputStream oos = null;
        ByteArrayOutputStream bais = null;

        try {
            bais = new ByteArrayOutputStream();
            oos=new ObjectOutputStream(bais);
            oos.writeObject(o);
            byte[] bytes=bais.toByteArray();
            return bytes;
        }catch (Exception e){
            logger.error("catch a serialize exception ,e={}",e.fillInStackTrace());
        }
        return new byte[0];
    }

    @Override
    public Object deserialize(byte[] bytes) throws SerializationException {
        if(isEmpty(bytes)){
            return null;
        }
        ObjectInputStream oii=null;
        ByteArrayInputStream bis =null;
        bis=new ByteArrayInputStream(bytes);
        try{
            oii = new ObjectInputStream(bis);
            Object obj = oii.readObject();
            return obj;
        }catch (Exception e){
            logger.error("catch a deserialize exception e={}",e.fillInStackTrace());
        }
        return  null ;
    }

    private boolean isEmpty(byte[] bytes) {
        return bytes == null || bytes.length == 0;
    }
}
