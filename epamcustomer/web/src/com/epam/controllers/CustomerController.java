package com.epam.controllers;

import com.epam.customer.facades.CustomerFacade;
import de.hybris.platform.commercefacades.user.data.AddressData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collection;

/**
 * @author Roman_Kovalenko
 */
@Controller
@RequestMapping("/v1/customers/{customerId}")
public class CustomerController {

    @Autowired
    private CustomerFacade customerFacade;

    @RequestMapping(value = "/address", method = RequestMethod.GET)
    public  @ResponseBody Collection<AddressData> getCustomerAddresses(@PathVariable("customerId") String customerId){
        return customerFacade.findCustomerAddresses(customerId);
    }
}
