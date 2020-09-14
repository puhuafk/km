package com.stylefeng.guns.modular.system.dao;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.stylefeng.guns.modular.system.model.EcErpOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
public class EcErpOrderMapperTest {


    @Autowired
    private EcErpOrderMapper ecErpOrderMapper;

    @Test
    public void in(){
        Wrapper wrapper = new EntityWrapper();
        List list = new ArrayList();
        List<EcErpOrder> ecErpOrderList = ecErpOrderMapper.selectList(wrapper.in("id", list));
        System.out.println(ecErpOrderList);
    }



}