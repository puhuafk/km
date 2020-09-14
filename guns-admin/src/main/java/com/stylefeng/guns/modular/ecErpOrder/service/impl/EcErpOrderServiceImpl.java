package com.stylefeng.guns.modular.ecErpOrder.service.impl;

import com.stylefeng.guns.modular.system.model.EcErpOrder;
import com.stylefeng.guns.modular.system.dao.EcErpOrderMapper;
import com.stylefeng.guns.modular.ecErpOrder.service.IEcErpOrderService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 电商、erp订单表 服务实现类
 * </p>
 *
 * @author chengshuai
 * @since 2018-10-22
 */
@Service
public class EcErpOrderServiceImpl extends ServiceImpl<EcErpOrderMapper, EcErpOrder> implements IEcErpOrderService {


}
