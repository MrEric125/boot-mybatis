package com.louis.minaProject;

import com.google.common.collect.Maps;
import com.louis.common.common.WrapMapper;
import com.louis.common.common.Wrapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.StorageLevels;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import scala.Tuple2;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author jun.liu
 * @date created on 2020/7/4
 * description:
 */
@RestController
@RequestMapping("spark")
public class SparkController {

    @Autowired
    private JavaSparkContext context;

//    @Autowired
//    private SparkContext sparkContext;

    @RequestMapping("filePath")
    public Wrapper wrapper(@RequestParam String filePath) {
        JavaRDD<String> javaRDD = context.textFile(filePath);
        JavaRDD<String> words = javaRDD.flatMap(s -> {
            List<String> list = Arrays.asList(s.split(" "));
            return list.iterator();
        }).filter(s -> {
            if (StringUtils.isNotEmpty(s)) {
                String sub = s.substring(0, 1);
                return !NumberUtils.isInteger(sub);
            }
            return false;
        });

        JavaPairRDD<String, Integer> counts = words.mapToPair(s -> new Tuple2<>(s, 1)).reduceByKey(Integer::sum).persist(StorageLevels.MEMORY_ONLY);

        List<String> collect = counts.keys().collect();

        Map<String, Integer> countMap = counts.collectAsMap();

        Map<String, Object> returnMap = Maps.newHashMap();

        returnMap.put("keys", collect);
        returnMap.put("count", countMap);
        return WrapMapper.ok(returnMap);
    }

    @RequestMapping("scala")
    public Wrapper scala(@RequestParam String filePath) {
//        val input=sparkContext.textFile(filePath);
//        return WrapMapper.ok(countMap);

        return null;
    }





}
