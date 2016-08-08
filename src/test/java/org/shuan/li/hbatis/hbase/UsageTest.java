package org.shuan.li.hbatis.hbase;

import org.apache.commons.collections.map.HashedMap;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.yarn.util.SystemClock;
import org.junit.Test;

import java.util.Map;

/**
 * Created by jinshuan.li on 2016/8/8.
 */
public class UsageTest {

    @Test
    public void testGetComment(){


        String familyName="familyName";
        Result result=new Result(); // the result get from habse;
        CommentDO commentDO=HBaseMapHelper.mapResult(CommentDO.class,result,familyName);
    }

    @Test
    public void testInsertComment(){
        String familyName="familyName";
        CommentDO commentDO=new CommentDO();
        byte [] rowKey=null; //getRowKey
        Put put = HBaseMapHelper.mapInsert(commentDO, familyName, rowKey);
    }

    @Test
    public void testUpdate(){
        String familyName="familyName";
        byte [] rowKey=null; //getRowKey
        Map<String,Object> params=new HashedMap();
        params.put("gmtCreate", System.currentTimeMillis());
        params.put("gmtModified", System.currentTimeMillis());
        params.put("appName","hbatis"); // key must match with property name in CommentDO
        Put put = HBaseMapHelper.buildPut(CommentDO.class, familyName,params, rowKey);
    }
}
