package com.epam.order.controllers;

import com.epam.order.data.EpamOrderData;
import com.epam.order.facades.EpamOrderSearchCriteria;
import com.epam.order.facades.impl.DefaultEpamOrderFacade;
import de.hybris.platform.core.model.order.OrderModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

/**
 * @author Iaroslav_Bezhenar
 */
@Controller
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private DefaultEpamOrderFacade defaultEpamOrderFacade;

    @RequestMapping(value = "/{orderCode}", method = RequestMethod.GET)
    @ResponseBody
    public EpamOrderData getCustomer(@PathVariable("orderCode") String orderCode) {
        return defaultEpamOrderFacade.getOrderByCode(orderCode);
    }

    @RequestMapping(method = RequestMethod.PUT)
    @ResponseBody
    public Collection<EpamOrderData> getOrderByCriteria(@RequestBody EpamOrderSearchCriteria searchCriteria) {
        return defaultEpamOrderFacade.getOrderByCriteria(searchCriteria);
    }
}
