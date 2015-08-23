package com.ebay.raptor.promotion.service.bean;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import com.ebay.app.raptor.promocommon.CommonLogger;
import com.ebay.kernel.bean.configuration.BaseConfigBean;
import com.ebay.kernel.bean.configuration.BeanConfigCategoryInfo;
import com.ebay.kernel.bean.configuration.BeanPropertyInfo;
import com.ebay.kernel.bean.configuration.ConfigCategoryCreateException;
import com.ebay.raptor.promotion.util.PromotionUtil;

public class PromoConfigBean extends BaseConfigBean implements
		PropertyChangeListener {

	private static CommonLogger logger = CommonLogger.getInstance(PromoConfigBean.class);
	
	private static final long serialVersionUID = -7520474759891279308L;

	private static final String CATEGORY_ID = "com.ebay.raptor.promotion.service.bean.PromoConfigBean";

	private static final String GROUP_ID = "com.ebay.promotion.service";

	private static final String PROMO_SERVICE_ENDPOINT = "promoServiceEndpoint";

	private String promoServiceEndpoint = PromotionUtil._promoServicePrefix;

	private final BeanPropertyInfo promoService = createBeanPropertyInfo(
	        PROMO_SERVICE_ENDPOINT, PROMO_SERVICE_ENDPOINT, true);
	
	private static final String USER_ID = "PromoReportTestUserId";
	
	private long PromoReportTestUserId = -1;

	private final BeanPropertyInfo userIdService = createBeanPropertyInfo(
			USER_ID, USER_ID, true);

	private static final PromoConfigBean INSTANCE = new PromoConfigBean();

	private PromoConfigBean() {
		try {
			BeanConfigCategoryInfo info = BeanConfigCategoryInfo
					.createBeanConfigCategoryInfo(CATEGORY_ID, CATEGORY_ID,
							GROUP_ID, true, true, null,
							"Configuration for the Promotion endpoint.", true);
			init(info, true);
		} catch (ConfigCategoryCreateException e) {
			logger.error("Failed to init the Promotion config bean.", e);
		}
	}

	public static PromoConfigBean getInstance() {
		return INSTANCE;
	}
	
	public String getPromoServiceEndpoint(){
		return this.promoServiceEndpoint;
	}
	
	public long getUserId(){
		return this.PromoReportTestUserId;
	}
	
	public void setUserId (long userId) {
	    this.PromoReportTestUserId = userId;
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		final BeanPropertyInfo info = BaseConfigBean.getBeanPropertyInfo(evt);
		try {
			if (info.equals(this.promoService)) {
				this.promoServiceEndpoint = (String) info.getPropertyValue(INSTANCE);
			} else if(info.equals(this.userIdService)){
				this.PromoReportTestUserId = (long) info.getPropertyValue(INSTANCE);
			}
		} catch (ClassCastException e) {
			logger.error("Failed to change the Promotion config bean.", e);
		}
	}
}
