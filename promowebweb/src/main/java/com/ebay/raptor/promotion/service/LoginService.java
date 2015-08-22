package com.ebay.raptor.promotion.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ebay.app.raptor.promocommon.CommonLogger;
import com.ebay.app.raptor.promocommon.httpRequest.HttpRequestException;
import com.ebay.app.raptor.promocommon.pojo.db.Parameter;
import com.ebay.app.raptor.promocommon.pojo.db.ParameterType;
import com.ebay.app.raptor.promocommon.util.CommonConstant;

@Service
public class LoginService {

    private static CommonLogger logger =
            CommonLogger.getInstance(LoginService.class);

    @Autowired PromoDataService promoDataService;

    private static List <String> validIps;

    @PostConstruct
    public void init () {
        validIps = loadValidIps();
    }

    public boolean isLoginIPValid (String ip) {
        // load for every check
        validIps = loadValidIps();

        for (String validIp : validIps) {
            if (validIp.equalsIgnoreCase(ip)) {
                return true;
            }
        }

        return true; // TODO - Change to false
    }

    private List <String> loadValidIps () {
        List<String> ips = new ArrayList<String>();
        try {
            List<Parameter> params =
                    promoDataService.getParameters(ParameterType.PromoBackendUserIp,
                            CommonConstant.PARAMETER_ENABLE);

            if (params != null && params.size() > 0) {
                Iterator<Parameter> it = params.iterator();

                while (it.hasNext()) {
                    ips.add(it.next().getValue());
                }
            }
        } catch (HttpRequestException e) {
            logger.error("Unable to load the ip list.", e);
        }
        return ips;
    }
}
