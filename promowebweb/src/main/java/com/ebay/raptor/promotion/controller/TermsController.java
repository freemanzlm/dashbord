package com.ebay.raptor.promotion.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.ebay.raptor.promotion.promo.service.ViewResource;
import com.ebay.raptor.promotion.service.ResourceProvider;



@Controller
@RequestMapping(ResourceProvider.Terms.base)
public class TermsController {

    @RequestMapping(ResourceProvider.Terms.hotSell)
    public String  hotSell() {
       return ViewResource.HV_AGGREMENT.getPath();
    }
    
    @RequestMapping(ResourceProvider.Terms.deals)
    public String  deals() {
       return ViewResource.DU_AGGREMENT.getPath();
    }
    
    @RequestMapping(ResourceProvider.Terms.dealsPreset)
    public String  dealsPreset() {
       return ViewResource.DP_AGGREMENT.getPath();
    }
    
    @RequestMapping(ResourceProvider.Terms.other)
    public String  other() {
       return ViewResource.OTHER_AGGREMENT.getPath();
    }
}
